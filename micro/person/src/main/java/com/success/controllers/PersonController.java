package com.success.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.success.clients.AddressClient;

import lombok.extern.slf4j.Slf4j;

@RestController
@RefreshScope
@Slf4j
public class PersonController {

  @Autowired private AddressClient aClient;

  @GetMapping(path = "/ping")
  public String ping() {
    log.info("Inside person controller.........Instanc ID is {}", this.toString());
    return "I am a person controller and my instance ID is " + this.toString();
  }

  @GetMapping(path = "/")
  public String getPersonDefault() {
    log.info("Inside person controller.........Instanc ID is {}", this.toString());
    return "Hello I am a person and my instance ID is " + this.toString();
  }

  @GetMapping(path = "/address/{personId}")
  public String getPerson(@PathVariable("personId") String personId) {
    log.info("Inside person controller.........Instanc ID is {}", this.toString());
    return aClient.getAddress(personId);
  }
}
