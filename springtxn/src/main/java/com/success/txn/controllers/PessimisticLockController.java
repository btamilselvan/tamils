package com.success.txn.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.success.txn.services.PessimisticLockService;

@RestController
@RequestMapping("/pl")
public class PessimisticLockController {
  @Autowired private PessimisticLockService service;

  @GetMapping("/ro")
  public String readOne() {
    return service.readOne();
  }

  @GetMapping("/rt")
  public String readTwo() {
    return service.readTwo();
  }
}
