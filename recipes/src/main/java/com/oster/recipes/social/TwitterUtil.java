package com.oster.recipes.social;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.oster.recipes.utils.Messages;
import com.oster.recipes.utils.Result;

import lombok.extern.log4j.Log4j;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;

@Component
@Log4j
public class TwitterUtil {

  @Value("${twitter.api.key}")
  private String apiKey;

  @Value("${twitter.api.secret.key}")
  private String apiSecretKey;

  @Value("${twitter.callback.url}")
  private String callBackUrl;

  public boolean tweet(String message, String token, String tokenSecret) {
    boolean status = true;
    try {
      Twitter twitter = getTwitter();
      twitter.setOAuthAccessToken(new AccessToken(token, tokenSecret));
      twitter.updateStatus(message);
    } catch (TwitterException e) {
      status = false;
      log.error(e);
    }
    return status;
  }

  public Result<String> getOauthToken() {
    try {
      RequestToken requestToken = getTwitter().getOAuthRequestToken(callBackUrl);
      JSONObject object = new JSONObject();
      object.put("oauth_token", requestToken.getToken());
      object.put("token_secret", requestToken.getTokenSecret());
      return new Result<String>(true, Messages.TWITTER_OAUTH_SUCCESS, object.toString());
    } catch (Exception e) {
      log.error(Messages.TWITTER_OAUTH_FAILED, e);
    }
    return new Result<String>(Messages.TWITTER_OAUTH_FAILED);
  }

  public AccessToken getUserToken(String oauthToken, String tokenSecret, String oauthVerifier) {
    try {
      RequestToken reqToken = new RequestToken(oauthToken, tokenSecret);
      return getTwitter().getOAuthAccessToken(reqToken, oauthVerifier);
    } catch (Exception e) {
      log.error(e);
    }
    return null;
  }

  private Twitter getTwitter() {
    Twitter twitter = new TwitterFactory().getInstance();
    twitter.setOAuthConsumer(apiKey, apiSecretKey);
    return twitter;
  }
}
