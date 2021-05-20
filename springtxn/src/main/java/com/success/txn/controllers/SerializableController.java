package com.success.txn.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.success.txn.services.SerializableService;

@RestController
@RequestMapping("/sz")
public class SerializableController {
  @Autowired private SerializableService service;

  @GetMapping("/io")
  public String insertOne() {
    return service.insertOne();
  }

  @GetMapping("/gc")
  public String getCount() {
    return service.getCount();
  }
}
