package com.success.websocket.security.services;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class CustomOIDCUserService extends OidcUserService {
  @Autowired private HttpServletRequest httpServletRequest;

  @Override
  public OidcUser loadUser(OidcUserRequest userRequest) {
    log.info("load ODIC User");
    OidcUser user = super.loadUser(userRequest);
    httpServletRequest.setAttribute("oauth_user", user);
    httpServletRequest.setAttribute("oauth_request", userRequest);
    return user;
  }
}
