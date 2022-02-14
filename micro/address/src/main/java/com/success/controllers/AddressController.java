package com.success.controllers;

import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.success.AddressApplicaiton;
import com.success.dtos.AddressDto;
import com.success.services.AddressService;

@RestController
@RefreshScope
public class AddressController {

  private static final Logger log = LogManager.getLogger(AddressController.class);

  @Autowired private AddressService service;

  //	private final static Logger logger = Log

  @Value("${message: default hello}")
  private String message;

  @GetMapping(path = "/{personId}")
  public String getAddress(@PathVariable("personId") String personId) {
    log.info("message from properties file " + message + " My instance ID is " + this.toString());
    return "from Address controller. Given person ID is "
        + personId
        + ". message from properties file "
        + message
        + " Current date is "
        + new Date()
        + " My instance ID is "
        + this.toString();
  }

  @RequestMapping
  public String getAddress1() {
    log.debug("this is a debug log....");
    return "I am Address controller default destination. Current date is "
        + new Date()
        + " My instance ID is "
        + this.toString();
  }

  @GetMapping("/ping")
  public String ping() {
    log.debug("this is a debug log....");
    log.info("this is a info log....");
    return "I am Address controller. Current date is "
        + new Date()
        + " My instance ID is "
        + this.toString();
  }
  
  @PostMapping
  public AddressDto addressPost(@RequestBody AddressDto dto) {
	  return dto;
  }
  
  @GetMapping("/with/param")
  public String addressGet(@RequestParam String id) {
    return "address is "+id;
  }

  @GetMapping("/restart/one")
  public void restartMethodOne() {
    AddressApplicaiton.restart();
  }

  @GetMapping("/restart/two")
  public void restartMethoTwo() {
    service.restart();
  }
}
