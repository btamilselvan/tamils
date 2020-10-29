package com.success.websocket.security.user;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.success.websocket.utils.Constants;

public class LinkedInOAuth2UserInfo extends OAuth2UserInfo {

  private static final String LOCALIZED = "localized";
  private static final String EN_US = "en_US";

  public LinkedInOAuth2UserInfo(Map<String, Object> attributes) {
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
  public String getImageUrl() {
    return null;
  }

  @SuppressWarnings("unchecked")
  @Override
  public String getFirstName() {
    if (attributes.containsKey("firstName")) {
      Map<String, Object> pictureObj = (Map<String, Object>) attributes.get("firstName");
      if (pictureObj.containsKey(LOCALIZED)) {
        Map<String, Object> dataObj = (Map<String, Object>) pictureObj.get(LOCALIZED);
        if (dataObj.containsKey(EN_US)) {
          return (String) dataObj.get(EN_US);
        }
      }
    }
    return null;
  }

  @SuppressWarnings("unchecked")
  @Override
  public String getLastName() {
    if (attributes.containsKey("lastName")) {
      Map<String, Object> pictureObj = (Map<String, Object>) attributes.get("lastName");
      if (pictureObj.containsKey(LOCALIZED)) {
        Map<String, Object> dataObj = (Map<String, Object>) pictureObj.get(LOCALIZED);
        if (dataObj.containsKey(EN_US)) {
          return (String) dataObj.get(EN_US);
        }
      }
    }
    return null;
  }

  @Override
  public String getVerified() {
    return Constants.YES;
  }

  @Override
  public String getAuthProvider() {
    return AuthProvider.LINKEDIN.getName();
  }
}
