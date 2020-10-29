package com.success.websocket.security.services;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class CustomOAuth2UserService extends DefaultOAuth2UserService {
  @Autowired private HttpServletRequest httpServletRequest;

  @Override
  public OAuth2User loadUser(OAuth2UserRequest oAuth2UserRequest) {
    OAuth2User user = super.loadUser(oAuth2UserRequest);
    httpServletRequest.setAttribute("oauth_user", user);
    httpServletRequest.setAttribute("oauth_request", oAuth2UserRequest);
    log.info("load OAuth2User");
    return user;
  }
}
