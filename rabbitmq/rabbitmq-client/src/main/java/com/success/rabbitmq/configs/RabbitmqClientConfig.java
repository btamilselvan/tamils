package com.success.rabbitmq.configs;

import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitmqClientConfig {

  /*@Value("${spring.rabbitmq.host}")
  private String hostname;

  @Value("${spring.rabbitmq.port}")
  private Integer port;

  @Value("${spring.rabbitmq.username}")
  private String username;

  @Value("${spring.rabbitmq.password}")
  private String password;*/

  @Value("${spring.rabbitmq.routingKeyChat}")
  private String routingKeyChat;

  @Value("${spring.rabbitmq.routingKeyPost}")
  private String routingKeyPost;

  @Value("${spring.rabbitmq.exchange}")
  private String exchange;

  /*@Bean
  ConnectionFactory connectionFactory() {
    CachingConnectionFactory cf = new CachingConnectionFactory(hostname);
    cf.setUsername(username);
    cf.setPassword(password);
    cf.setHost(hostname);
    cf.setPort(port);
    return cf;
  }*/

  @Bean
  MessageConverter messageConverter() {
    return new Jackson2JsonMessageConverter();
  }

  @Bean
  @Qualifier("chat")
  RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
    RabbitTemplate rt = new RabbitTemplate(connectionFactory);
    rt.setMessageConverter(messageConverter());
    rt.setRoutingKey(routingKeyChat); // default
    rt.setExchange(exchange); // default
    return rt;
  }

  @Bean
  @Qualifier("post")
  RabbitTemplate rabbitTemplateForPost(ConnectionFactory connectionFactory) {
    RabbitTemplate rt = new RabbitTemplate(connectionFactory);
    rt.setMessageConverter(messageConverter());
    rt.setRoutingKey(routingKeyPost); // for posts
    rt.setExchange(exchange); // default exchange
    return rt;
  }
}
