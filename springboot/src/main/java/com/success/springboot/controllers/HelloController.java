package com.success.springboot.controllers;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RefreshScope
@RestController
public class HelloController {
  @Value("${hello}")
  private String hello;

  @GetMapping("/")
  @Cacheable(cacheNames = "test")
  public String cacheTest() {
    System.out.println(new Date());
    return "Hello..." + new Date();
  }

  @GetMapping("/config")
  public String configTest() {
    System.out.println(new Date());
    return "Hello..." + new Date() + " " + hello;
  }
}
