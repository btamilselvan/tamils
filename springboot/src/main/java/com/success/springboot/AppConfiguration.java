package com.success.springboot;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

@Configuration
public class AppConfiguration {
  @Bean
  public JedisConnectionFactory createJedisConnectionFactory() {
    RedisStandaloneConfiguration config = new RedisStandaloneConfiguration();
    config.setHostName("localhost");
    config.setPort(6379);
    config.setPassword(RedisPassword.of("testpassword"));

    JedisClientConfiguration.JedisClientConfigurationBuilder builder =
        JedisClientConfiguration.builder();
    builder.usePooling();

    return new JedisConnectionFactory(config, builder.build());
  }

  @Bean
  public RedisTemplate<String, Object> createRedisTemplate() {
    RedisTemplate<String, Object> template = new RedisTemplate<>();
    template.setConnectionFactory(createJedisConnectionFactory());
    return template;
  }
}
