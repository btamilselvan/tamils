package com.success.websocket;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.success.websocket.security.AppProperties;

@EnableConfigurationProperties(AppProperties.class)
@SpringBootApplication
@EnableJpaRepositories
public class WebsocketApplication {

  public static void main(String[] args) {
    SpringApplication.run(WebsocketApplication.class, args);
  }
}
