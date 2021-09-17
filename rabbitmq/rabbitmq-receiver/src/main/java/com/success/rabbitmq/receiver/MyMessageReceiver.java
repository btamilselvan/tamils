package com.success.rabbitmq.receiver;

import java.util.concurrent.CountDownLatch;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.success.rabbitmq.dtos.MessageDto;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class MyMessageReceiver {

  private CountDownLatch cl = new CountDownLatch(1);

  // either this method without @RabbitListener annotation or "handleMessage" with annotation.
  // when used without annotation, this method name should be added when configuring message adapter
  public void receiveMessage(MessageDto dto) {
    log.info("received....");
    cl.countDown();
    log.info(dto.getMessage());
  }

  @RabbitListener
  public void handleMessage(MessageDto dto) {
    log.info("received....");
    cl.countDown();
    log.info(dto.getMessage());
  }

  public CountDownLatch getLatch() {
    return cl;
  }
}
