package com.success.websocket.utils;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public final class Constants {
  private static final String[] SPRING_SECURITY_ALLOWED_URLS =
      new String[] {
        "/p/**",
        "/favicon.ico",
        "/scripts/**",
        "/myendpoint/**",
        "/",
        "/auth/**",
        "/oauth2/**",
        "/h2-console/**"
      };

  public static final String USER_DESTINATION_PREFIX = "/user";
  public static final String TOPIC_DESTINATION_PREFIX = "/topic";
  public static final String APP_PREFIX = "/myprefix"; // used by clients to send messages
  public static final String CHAT_PREFIX = "/chat"; // used by clients to send messages
  public static final String WEBSOCKET_END_POINT = "/myendpoint";

  public static final String CHAT_DESTINATION_SUFFIX = "/mychat";
  public static final String FINAL_DESTINATION_PLACEHOLDER =
      USER_DESTINATION_PREFIX + "/{userId}" + CHAT_DESTINATION_SUFFIX;

  public static final List<String> ALLOWED_URLS_LIST =
      Collections.unmodifiableList(Arrays.asList(SPRING_SECURITY_ALLOWED_URLS));

  public static final String YES = "Y";
  public static final String NO = "N";

  public static final String OAUTH_USER = "oauth_user";
  public static final String OAUTH_REQUEST = "oauth_request";

  private Constants() {}
}
