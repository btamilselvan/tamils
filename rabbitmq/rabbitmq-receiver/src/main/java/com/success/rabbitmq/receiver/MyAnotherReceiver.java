package com.success.rabbitmq.receiver;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

// @Component
public class MyAnotherReceiver {
  /*@RabbitListener(queues = "another-queue")
  public String listen(String message) {
    return message;
  }*/
}
