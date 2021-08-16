package com.success.controllers;

import java.time.Instant;

import org.keycloak.TokenVerifier;
import org.keycloak.adapters.springsecurity.client.KeycloakRestTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/private")
public class PersonPrivateController {

  @Autowired private KeycloakRestTemplate krt;

  @GetMapping
  public String someMethod() {
    getAccessToken();
    return "from Person private contoller " + Instant.now();
  }

  @GetMapping("/p")
  public String someMethodWithParam(@RequestParam(required = false, name = "param") String param) {
    return "from Person private contoller " + param + " " + Instant.now();
  }

  private void getAccessToken() {
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

    MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
    params.add("grant_type", "client_credentials");
    params.add("client_id", "microclient1");
    params.add("client_secret", "4b790cd5-7b42-4829-8949-9f0ff62df1b2");

    HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);

    String token =
        krt.postForEntity(
                "http://localhost:8080/auth/realms/micro/protocol/openid-connect/token",
                request,
                String.class)
            .getBody();

    System.out.println(token);
    
//    TokenVerifier<JsonWebToken>

    //	  krt.
  }
}
