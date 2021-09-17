package com.success.rabbitmq.services;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.success.rabbitmq.dtos.MessageDto;
import com.success.rabbitmq.dtos.PostDto;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class RabbitmqClientService {

  @Qualifier("post")
  @Autowired
  private RabbitTemplate rt;

  public String sendMessage(String message) {
    MessageDto dto = new MessageDto();
    dto.setMessage(message);
    rt.convertAndSend(dto);
    log.info("send message ...");
    return "sent";
  }

  public String addPost(PostDto dto) {
    rt.convertAndSend(dto);
    log.info("post sent...");
    return "sent";
  }
}
