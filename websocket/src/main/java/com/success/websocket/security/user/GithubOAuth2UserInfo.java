package com.success.websocket.security.user;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.success.websocket.utils.Constants;

public class GithubOAuth2UserInfo extends OAuth2UserInfo {

  public GithubOAuth2UserInfo(Map<String, Object> attributes) {
    super(attributes);
  }

  @Override
  public String getId() {
    return ((Integer) attributes.get("id")).toString();
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
    return (String) attributes.get("avatar_url");
  }

  @Override
  public String getFirstName() {
    if (attributes.containsKey("given_name")) {
      return (String) attributes.get("given_name");
    }else if(attributes.containsKey("name")) {
    	return (String) attributes.get("name");
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
    return Constants.YES;
  }

  @Override
  public String getAuthProvider() {
    return AuthProvider.GITHUB.getName();
  }
}
