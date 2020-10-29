package com.oster.recipes.aws;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.AttributeEncryptor;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig.SaveBehavior;
import com.amazonaws.services.dynamodbv2.datamodeling.encryption.DynamoDBEncryptor;
import com.amazonaws.services.dynamodbv2.datamodeling.encryption.providers.DirectKmsMaterialProvider;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.kms.AWSKMS;
import com.amazonaws.services.kms.AWSKMSClientBuilder;

@Configuration
@Profile({"dev"})
public class AwsConfigDev {

  @Value("${aws.dynamodb.accesskey}")
  private String dynamoDBAccessKey;

  @Value("${aws.dynamodb.secretkey}")
  private String dynamoDBSecretKey;

  @Value("${aws.es.host}")
  private String elasticsearchHost;

  @Value("${aws.cmk.arn}")
  private String cmkArn;

  @Bean
  public AWSCredentials amazonAWSCredentials() {
    return new BasicAWSCredentials(dynamoDBAccessKey, dynamoDBSecretKey);
  }

  @Bean
  @Primary
  public DynamoDBMapperConfig dynamoDBMapperConfig() {
    return DynamoDBMapperConfig.DEFAULT;
  }

  @Bean
  @Primary
  @Qualifier("dynamoMapper")
  public DynamoDBMapper dynamoDBMapper(AmazonDynamoDB amazonDynamoDB, DynamoDBMapperConfig config) {
    return new DynamoDBMapper(amazonDynamoDB, config);
  }

  @Bean
  @Qualifier("dynamoMapperEncrypted")
  public DynamoDBMapper dynamoDBMapperEncrypted(
      AmazonDynamoDB amazonDynamoDB, DynamoDBMapperConfig config) {
    DynamoDBMapperConfig mapperConfig =
        DynamoDBMapperConfig.builder().withSaveBehavior(SaveBehavior.PUT).build();
    return new DynamoDBMapper(
        amazonDynamoDB(), mapperConfig, new AttributeEncryptor(dynamoEncryptor()));
  }

  @Bean
  public AmazonDynamoDB amazonDynamoDB() {
    return AmazonDynamoDBClientBuilder.standard()
        .withCredentials(amazonAWSCredentialsProvider())
        .withRegion(Regions.US_EAST_1)
        .build();
  }

  @Bean
  public DynamoDB dynamoDB() {
    return new DynamoDB(amazonDynamoDB());
  }

  private DynamoDBEncryptor dynamoEncryptor() {
    final AWSKMS kms =
        AWSKMSClientBuilder.standard()
            .withCredentials(amazonAWSCredentialsProvider())
            .withRegion(Regions.US_EAST_1)
            .build();
    final DirectKmsMaterialProvider cmp = new DirectKmsMaterialProvider(kms, cmkArn);
    return DynamoDBEncryptor.getInstance(cmp);
  }

  public AWSCredentialsProvider amazonAWSCredentialsProvider() {
    return new AWSStaticCredentialsProvider(amazonAWSCredentials());
  }

  @Bean(destroyMethod = "close")
  public RestHighLevelClient client() {
    RestHighLevelClient client =
        new RestHighLevelClient(RestClient.builder(new HttpHost(elasticsearchHost)));
    return client;
  }
}
