package com.success.websocket.security.services;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.BooleanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.success.websocket.jpa.entities.User;
import com.success.websocket.jpa.repositories.UserRepository;
import com.success.websocket.security.user.AuthProvider;
import com.success.websocket.security.user.OAuth2UserInfo;
import com.success.websocket.security.user.OAuth2UserInfoFactory;
import com.success.websocket.utils.Constants;

@Service
public class WebsocketOAuthUserService {
  @Autowired private UserRepository userRepo;
  @Autowired private RestTemplate rt;

  @Value("${li.email.url}")
  private String getEmailUrl;

  public OAuth2UserInfo processOAuthUser(HttpServletRequest request, HttpServletResponse response) {
    OAuth2UserRequest oAuth2UserRequest =
        (OAuth2UserRequest) request.getAttribute(Constants.OAUTH_REQUEST);
    OAuth2User user = (OAuth2User) request.getAttribute(Constants.OAUTH_USER);
    OAuth2UserInfo userInfo =
        OAuth2UserInfoFactory.getOAuth2UserInfo(
            oAuth2UserRequest.getClientRegistration().getRegistrationId(), user.getAttributes());
    if (userInfo == null || !BooleanUtils.toBoolean(userInfo.getVerified())) {
      throw new RuntimeException("Unauthorized access");
    }
    if (AuthProvider.LINKEDIN
        .getName()
        .equals(oAuth2UserRequest.getClientRegistration().getRegistrationId())) {
      userInfo.setEmail(getEmailFromLinkedIn(oAuth2UserRequest.getAccessToken().getTokenValue()));
    }
    createUpdateUser(userInfo);
    return userInfo;
  }

  private void createUpdateUser(OAuth2UserInfo userInfo) {
    List<User> users = userRepo.findByEmail(userInfo.getEmail());
    if (users.isEmpty()) {
      User entity = new User();
      entity.setEmail(userInfo.getEmail());
      entity.setFirstName(userInfo.getFirstName());
      entity.setLastName(userInfo.getLastName());
      userRepo.save(entity);
    }
  }

  private String getEmailFromLinkedIn(String accessToken) {
    ObjectMapper objectMapper = new ObjectMapper();
    HttpHeaders headers = new HttpHeaders();
    headers.set("Authorization", "Bearer " + accessToken);
    HttpEntity<?> request = new HttpEntity<>(headers);
    try {
      return objectMapper
          .readTree(rt.exchange(getEmailUrl, HttpMethod.GET, request, String.class).getBody())
          .path("elements")
          .findPath("handle~")
          .findPath("emailAddress")
          .textValue();
    } catch (RestClientException | IOException e) {
      throw new RuntimeException("Unauthorized access");
    }
  }
}
