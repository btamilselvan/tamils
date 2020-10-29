package com.success.websocket.security.user;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.success.websocket.utils.Constants;

public class FacebookOAuth2UserInfo extends OAuth2UserInfo {
  public FacebookOAuth2UserInfo(Map<String, Object> attributes) {
    super(attributes);
  }

  @Override
  public String getId() {
    return (String) attributes.get("id");
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
  public String getFirstName() {
    if (attributes.containsKey("first_name")) {
      return (String) attributes.get("first_name");
    }
    return null;
  }

  @Override
  public String getLastName() {
    if (attributes.containsKey("last_name")) {
      return (String) attributes.get("last_name");
    }
    return null;
  }

  @Override
  public String getVerified() {
    return Constants.YES;
  }

  @SuppressWarnings("unchecked")
  @Override
  public String getImageUrl() {
    if (attributes.containsKey("picture")) {
      Map<String, Object> pictureObj = (Map<String, Object>) attributes.get("picture");
      if (pictureObj.containsKey("data")) {
        Map<String, Object> dataObj = (Map<String, Object>) pictureObj.get("data");
        if (dataObj.containsKey("url")) {
          return (String) dataObj.get("url");
        }
      }
    }
    return null;
  }

  @Override
  public String getAuthProvider() {
    return AuthProvider.FACEBOOK.getName();
  }
}
