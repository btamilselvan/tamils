package com.success.txn.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.success.txn.services.ReadUncommitedService;

@RestController
@RequestMapping("/ru")
public class ReadUncommitedController {
  @Autowired private ReadUncommitedService service;

  @GetMapping("/io")
  public String insertOne() {
    return service.insertOne();
  }

  @GetMapping("/gc")
  public String getCount() {
    return service.getCount();
  }
}
