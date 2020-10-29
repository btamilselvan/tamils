package com.success.wip.controllers;

import java.util.Date;
import java.util.TimeZone;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.success.wip.controllers.utils.DateUtils;

@RestController
public class DemoController {
  @RequestMapping("/")
  public String ping() {
    System.out.println("hello .... " + new Date());
    return "current time " + new Date();
  }

  @GetMapping("/cd")
  public String getClientDate(TimeZone tz) {
    Date currentDate = DateUtils.getCurrentDateTime(tz);
    System.out.println(currentDate);
    System.out.println(tz.toString());
    return "client date is .. " + currentDate;
  }
}
