package com.oster.recipes.aws;

import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.services.cloudwatchevents.AmazonCloudWatchEvents;
import com.amazonaws.services.cloudwatchevents.AmazonCloudWatchEventsClientBuilder;
import com.amazonaws.services.cloudwatchevents.model.PutRuleRequest;
import com.amazonaws.services.cloudwatchevents.model.PutTargetsRequest;
import com.amazonaws.services.cloudwatchevents.model.RuleState;
import com.amazonaws.services.cloudwatchevents.model.Target;

public class AwsCloudWatchEventHelper {

  private static AmazonCloudWatchEvents client =
      AmazonCloudWatchEventsClientBuilder.standard()
          .withCredentials(new ProfileCredentialsProvider("trocks_app_user"))
          .build();

  public static void createSchedule() {
    PutRuleRequest request =
        new PutRuleRequest()
            .withName("testevent")
            .withScheduleExpression("rate(5 minutes)")
            .withState(RuleState.ENABLED);
    client.putRule(request);
  }

  public static void createTarget() {
    Target lambda =
        new Target()
            .withArn("arn:aws:lambda:us-east-1:633972974677:function:testfunction")
            .withInput("{\"recipeId\":\"test-recipe-id\"}")
            .withId("testtarget");
    PutTargetsRequest request = new PutTargetsRequest().withTargets(lambda).withRule("testevent");
    client.putTargets(request);
  }

  public static void main(String[] args) {
    createSchedule();
    createTarget();
  }
}
