package com.success.springboot.controllers;

import java.util.Date;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
  @GetMapping("/")
  @Cacheable(cacheNames = "test")
  public String cacheTest() {
	  System.out.println(new Date());
    return "Hello..." + new Date();
  }
}
