package com.success.rabbitmq.controllers;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.success.rabbitmq.dtos.PostDto;
import com.success.rabbitmq.services.RabbitmqClientService;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class RabbitMqClientController {

  @Autowired private RabbitmqClientService service;

  @GetMapping("/health/check")
  public String healthCheck() {
    return "Hello" + new Date();
  }

  @GetMapping("/send")
  public String send(@RequestParam String message) {
    return service.sendMessage(message);
  }

  @PostMapping
  public String post(@RequestBody PostDto dto) {
    log.info("send post " + dto.toString());
    service.addPost(dto);
    return "done";
  }
}
