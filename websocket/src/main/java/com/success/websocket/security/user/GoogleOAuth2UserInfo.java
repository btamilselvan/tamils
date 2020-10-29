package com.success.websocket.security.user;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.success.websocket.utils.Constants;

public class GoogleOAuth2UserInfo extends OAuth2UserInfo {

  public GoogleOAuth2UserInfo(Map<String, Object> attributes) {
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
    if (attributes.containsKey("picture")) {
      return (String) attributes.get("picture");
    }
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
    if (attributes.get("email_verified") != null && (Boolean) attributes.get("email_verified")) {
      return Constants.YES;
    } else {
      return Constants.NO;
    }
  }

  @Override
  public String getAuthProvider() {
    return AuthProvider.GOOGLE.getName();
  }
}
