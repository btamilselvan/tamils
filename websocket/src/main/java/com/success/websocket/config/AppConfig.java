package com.success.websocket.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class AppConfig {
  @Bean
  ModelMapper getMapper() {
    return new ModelMapper();
  }

  @Bean
  RestTemplate getRestTemplate() {
    return new RestTemplate();
  }
}
