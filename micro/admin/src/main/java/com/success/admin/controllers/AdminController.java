package com.success.admin.controllers;

import java.time.Instant;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AdminController {
  @GetMapping("/health/check")
  public String healthCheck() {
    return "Hello.. You have reached the admin controller. current time is " + Instant.now();
  }
}
