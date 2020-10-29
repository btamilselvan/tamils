package com.success.springboot;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.interceptor.CacheErrorHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

import com.success.springboot.handlers.MyCustomCacheErrorHandler;

@Configuration
public class AppConfiguration extends CachingConfigurerSupport {

  @Value("${redis.host}")
  private String redisHost;
  
  @Value("${redis.port}")
  private String redisPort;
  
  @Value("${redis.password:testpassword}")
  private String redisPassword;

  @Bean
  public JedisConnectionFactory createJedisConnectionFactory() {
    RedisStandaloneConfiguration config = new RedisStandaloneConfiguration();
    config.setHostName("localhost");
    config.setPort(6379);
    config.setPassword(RedisPassword.of(redisPassword));

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

  @Override
  public CacheErrorHandler errorHandler() {
    return new MyCustomCacheErrorHandler();
  }
}
