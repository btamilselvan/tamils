package com.success.websocket.security.user;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;

public class OktaOAuth2UserInfo extends OAuth2UserInfo {

  public OktaOAuth2UserInfo(Map<String, Object> attributes) {
    super(attributes);
  }

  @Override
  public String getId() {
    return (String) attributes.get("sub");
  }

  @Override
  public String getName() {
    return StringUtils.join(getFirstName(), getLastName());
  }

  @Override
  public String getEmail() {
    return (String) attributes.get("email");
  }

  @Override
  public String getImageUrl() {
    return null;
  }

  @Override
  public String getFirstName() {
    if (attributes.containsKey("given_name")) {
      return (String) attributes.get("given_name");
    }
    return null;
  }

  @Override
  public String getLastName() {
    if (attributes.containsKey("family_name")) {
      return (String) attributes.get("family_name");
    }
    return null;
  }

  @Override
  public String getVerified() {
    return (String) attributes.get("email_verified");
  }

  @Override
  public String getAuthProvider() {
    return AuthProvider.OKTA.getName();
  }
}
