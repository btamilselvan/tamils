package com.success.websocket.security.user;

public enum AuthProvider {
  LOCAL("local"),
  FACEBOOK("facebook"),
  GOOGLE("google"),
  GITHUB("github"),
  OKTA("okta"),
  LINKEDIN("linkedin");

  private String name;

  private AuthProvider(String name) {
    this.name = name;
  }

  public String getName() {
    return this.name;
  }
}
