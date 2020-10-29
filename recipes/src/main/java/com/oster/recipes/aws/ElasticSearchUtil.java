package com.oster.recipes.aws;

import org.springframework.stereotype.Component;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.elasticsearch.AWSElasticsearch;
import com.amazonaws.services.elasticsearch.AWSElasticsearchClientBuilder;

@Component
public class ElasticSearchUtil {

  private final String domainName = "tamils";

  private void createClient() {
    AWSElasticsearch client =
        AWSElasticsearchClientBuilder.standard()
            // Unnecessary, but lets you use a region different than your default.
            .withRegion(Regions.US_WEST_2)
            // Unnecessary, but if desired, you can use a different provider chain.
            .withCredentials(new AWSStaticCredentialsProvider(new BasicAWSCredentials("", "")))
            .build();
  }
}
