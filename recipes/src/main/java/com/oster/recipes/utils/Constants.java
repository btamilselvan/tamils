package com.oster.recipes.utils;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public final class Constants {

  private static final String[] SPRING_SECURITY_ALLOWED_URLS =
      new String[] {
        "/v2/api-docs",
        "/swagger-resources/configuration/ui",
        "/swagger-resources",
        "/swagger-resources/configuration/security",
        "/swagger-ui.html",
        "/webjars/**",
        "/user-register",
        "/login",
        "/get-token",
        "/refresh-token",
        "/logout-me",
        "/ping",
        "/add-user"
      };

  public static final List<String> ALLOWED_URLS_LIST =
      Collections.unmodifiableList(Arrays.asList(SPRING_SECURITY_ALLOWED_URLS));

  public static final String COUNTRY_PK = "COUNTRY";

  public static final String USER_ID_PARAM = "userId";
  public static final String USER_PREFIX = "u-";
  public static final String RECIPE_PREFIX = "r-";
  public static final String COLLECTION_PREFIX = "c-";

  public static final String INDEX_NAME = "recipe";
  public static final String INDEX_TYPE = "rdata";
}
