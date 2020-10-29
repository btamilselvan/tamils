package com.oster.recipes.social;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.databind.JsonNode;
import com.oster.recipes.entities.dynamodb.Data.DataMap;
import com.oster.recipes.utils.Messages;
import com.oster.recipes.utils.Result;

import lombok.extern.log4j.Log4j;

@Log4j
@Component
public class FacebookUtil {

  @Value("${fb.app-id}")
  private String appId;

  @Value("${fb.app-secret}")
  private String appSecret;

  @Autowired private RestTemplate rt;

  @PostConstruct
  public void init() {
    // get app-id and app-secret from AWS Secret Manager
    // this.appId = "244313950037633";
    // this.appSecret = "";
  }

  private String getLongLivedUserAccessToken(String shortLivedUserAccessToken) {
    try {
      MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
      params.add("grant_type", "fb_exchange_token");
      params.add("client_id", this.appId);
      params.add("client_secret", this.appSecret);
      params.add("fb_exchange_token", shortLivedUserAccessToken);
      UriComponentsBuilder url =
          UriComponentsBuilder.fromHttpUrl("https://graph.facebook.com/oauth/access_token")
              .queryParams(params);
      ResponseEntity<JsonNode> response =
          rt.getForEntity(url.build().encode().toUri(), JsonNode.class);
      return response.getBody().get("access_token").asText();
    } catch (HttpClientErrorException e) {
      log.error("unable to get longlived facebook token", e);
    }
    return null;
  }

  public String getLongLivedPageAccessToken(String shortLivedUserAccessToken, String pageName) {
    JsonNode pageDetails = getPageDetails(shortLivedUserAccessToken, pageName);
    if (pageDetails == null) {
      return null;
    } else {
      return pageDetails.get("access_token").asText();
    }
  }

  public JsonNode getPageDetails(String shortLivedUserAccessToken, String pageName) {
    String longLivedUserAccessToken = getLongLivedUserAccessToken(shortLivedUserAccessToken);
    String userId = getUserIdFromAccessToken(shortLivedUserAccessToken);
    UriComponentsBuilder url =
        UriComponentsBuilder.fromHttpUrl("https://graph.facebook.com/" + userId + "/accounts")
            .queryParam("access_token", longLivedUserAccessToken)
            .queryParam("fields", "name,access_token");
    ResponseEntity<JsonNode> response =
        rt.getForEntity(url.build().encode().toUri(), JsonNode.class);
    log.info(response.getBody());
    for (JsonNode c : response.getBody().get("data")) {
      if (c.get("name").asText().equals(pageName.trim())) {
        return c;
      }
    }
    return null;
  }

  public Result<String> publishPost(DataMap pageMap, String message) {

    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

    MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
    params.add("access_token", pageMap.getValue());
    params.add("message", message);

    HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);
    ResponseEntity<String> response =
        rt.postForEntity(
            "https://graph.facebook.com/" + pageMap.getId() + "/feed", request, String.class);
    log.info(response.getBody());
    return new Result<String>(true, Messages.POST_PUBLISH_SUCCESS);
  }

  private String getUserIdFromAccessToken(String shortLivedUserAccessToken) {
    UriComponentsBuilder url =
        UriComponentsBuilder.fromHttpUrl("https://graph.facebook.com/me")
            .queryParam("access_token", shortLivedUserAccessToken)
            .queryParam("fields", "email,name,id");
    ResponseEntity<JsonNode> response =
        rt.getForEntity(url.build().encode().toUri(), JsonNode.class);
    return response.getBody().get("id").asText();
  }
}
