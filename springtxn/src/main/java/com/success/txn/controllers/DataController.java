package com.success.txn.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.success.txn.services.DataService;

@RestController
@RequestMapping("/r")
public class DataController {

  @Autowired private DataService service;

  @GetMapping
  public String paginate() {
    return service.paginate();
  }
}
