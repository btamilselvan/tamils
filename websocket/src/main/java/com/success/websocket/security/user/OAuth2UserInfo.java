package com.success.websocket.security.user;

import java.util.Map;
import java.util.UUID;

public abstract class OAuth2UserInfo {
  private String email;
  protected Map<String, Object> attributes;

  public void setEmail(String email) {
    this.email = email;
  }

  public OAuth2UserInfo(Map<String, Object> attributes) {
    this.attributes = attributes;
  }

  public Map<String, Object> getAttributes() {
    return attributes;
  }

  public String getAjId() {
    return UUID.randomUUID().toString();
  }

  public abstract String getId();

  public abstract String getName();

  public String getEmail() {
    return this.email;
  }

  public abstract String getImageUrl();

  public abstract String getFirstName();

  public abstract String getLastName();

  public abstract String getVerified();

  public abstract String getAuthProvider();
}
