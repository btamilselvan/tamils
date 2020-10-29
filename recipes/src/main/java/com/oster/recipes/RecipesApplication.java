package com.oster.recipes;

import org.socialsignin.spring.data.dynamodb.repository.config.EnableDynamoDBRepositories;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
@EnableAutoConfiguration
@EnableDynamoDBRepositories(basePackages = "com.oster.recipes.repositories.dynamodb")
public class RecipesApplication extends SpringBootServletInitializer {

  public static void main(String[] args) {
    SpringApplication.run(RecipesApplication.class, args);
  }
}
