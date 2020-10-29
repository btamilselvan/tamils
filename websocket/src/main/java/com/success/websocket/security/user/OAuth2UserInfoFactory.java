package com.success.websocket.security.user;

import java.util.Map;

public class OAuth2UserInfoFactory {

  private OAuth2UserInfoFactory() {}

  public static OAuth2UserInfo getOAuth2UserInfo(
      String registrationId, Map<String, Object> attributes) {
    if (registrationId.equalsIgnoreCase(AuthProvider.GOOGLE.getName())) {
      return new GoogleOAuth2UserInfo(attributes);
    } else if (registrationId.equalsIgnoreCase(AuthProvider.FACEBOOK.getName())) {
      return new FacebookOAuth2UserInfo(attributes);
    } else if (registrationId.equalsIgnoreCase(AuthProvider.GITHUB.getName())) {
      return new GithubOAuth2UserInfo(attributes);
    } else if (registrationId.equalsIgnoreCase(AuthProvider.OKTA.getName())) {
      return new OktaOAuth2UserInfo(attributes);
    } else if (registrationId.equalsIgnoreCase(AuthProvider.LINKEDIN.getName())) {
      return new LinkedInOAuth2UserInfo(attributes);
    } else {
      throw new RuntimeException(
          "Sorry! Login with " + registrationId + " is not supported yet.");
    }
  }
}
