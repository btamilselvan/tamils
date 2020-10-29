package com.success.springsaml.controllers;

import java.util.Date;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.saml.metadata.MetadataManager;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {
  @Autowired private MetadataManager metadata;

  @GetMapping("/hello")
  public String sayHello() {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    if (auth == null) {
      System.out.println("Current authentication instance from security context is null");
    } else {
      System.out.println(
          "Current authentication instance from security context: "
              + this.getClass().getSimpleName());
    }

    Set<String> idps = metadata.getIDPEntityNames();
    for (String idp : idps) {
      System.out.println("Configured Identity Provider for SSO: " + idp);
    }
    return "logged in as "+((User)auth.getPrincipal()).getUsername();
//    return "current date time is" + new Date();
  }

  @GetMapping("/error")
  public String error() {
    return "login failed...current date time is" + new Date();
  }
}
