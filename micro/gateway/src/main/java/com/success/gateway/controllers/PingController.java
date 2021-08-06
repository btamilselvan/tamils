package com.success.gateway.controllers;

import java.util.Date;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PingController {

	@GetMapping("/ping")
	public String ping() {
		return "You have reached the gateway instance" +this.toString()+". Current Data/time is "+new Date();
	}
	
	@GetMapping
	public String pingDefault() {
		return "You have reached the gateway instance" +this.toString()+". Current Data/time is "+new Date();
	}
}
