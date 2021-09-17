package com.success.rabbitmq;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.success.rabbitmq.receiver.MyMessageReceiver;

@Configuration
public class MessageConfig {

  @Value("${spring.rabbitmq.queue}")
  private String queueName;

  @Value("${spring.rabbitmq.exchange}")
  private String topicName;

  @Value("${spring.rabbitmq.bidningKey}")
  private String bindingKey;

  @Value("${spring.rabbitmq.username}")
  private String username;

  @Value("${spring.rabbitmq.password}")
  private String password;

  @Value("${spring.rabbitmq.host}")
  private String hostname;

  @Bean
  Queue queue() {
    return new Queue(queueName);
  }

  @Bean
  TopicExchange topic() {
    return new TopicExchange(topicName);
  }

  @Bean
  Binding binding(Queue queue, TopicExchange topic) {
    return BindingBuilder.bind(queue).to(topic).with(bindingKey);
  }

  @Bean
  MessageListenerAdapter adapter(MyMessageReceiver receiver) {
    //    MessageListenerAdapter adapter = new MessageListenerAdapter(receiver, "receiveMessage");
    MessageListenerAdapter adapter = new MessageListenerAdapter(receiver);
    adapter.setMessageConverter(messageConverter());
    return adapter;
  }

  @Bean
  SimpleMessageListenerContainer container(
      ConnectionFactory connectionFactory, MessageListenerAdapter adapter) {
    SimpleMessageListenerContainer smc = new SimpleMessageListenerContainer();
    smc.addQueueNames(queueName);
    smc.setConnectionFactory(connectionFactory);
    smc.setMessageListener(adapter);
    return smc;
  }

  @Bean
  ConnectionFactory connectionFactory() {
    CachingConnectionFactory cf = new CachingConnectionFactory(hostname);
    cf.setUsername(username);
    cf.setPassword(password);
    return cf;
  }

  @Bean
  MessageConverter messageConverter() {
    return new Jackson2JsonMessageConverter();
  }

  @Bean
  RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
    RabbitTemplate rt = new RabbitTemplate(connectionFactory);
    rt.setMessageConverter(messageConverter());
    return rt;
  }
}
