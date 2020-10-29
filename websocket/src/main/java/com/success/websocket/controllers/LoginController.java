package com.success.websocket.controllers;

import java.security.Principal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.success.websocket.dtos.UserDto;
import com.success.websocket.security.TokenProvider;
import com.success.websocket.services.LoginService;

@Controller
public class LoginController {

  @Autowired private LoginService service;
  @Autowired private TokenProvider tokenProvider;

  @GetMapping("/")
  public String index(Model model) {
    model.addAttribute("user", new UserDto());
    return "mylogin";
  }

  @GetMapping("/p/mylogin")
  public String loginPage(Model model) {
    model.addAttribute("user", new UserDto());
    return "mylogin";
  }

  @PostMapping(path = "/p/autenticate")
  public ModelAndView login(UserDto user, HttpServletResponse response) {
//    user.setPassword("T1Password");
    service.authenticate(user.getEmail(), user.getPassword(), response);
    return new ModelAndView("redirect:/home");
  }

  @GetMapping(path = "/home")
  public String home(Principal principal, Model model, HttpServletRequest request) {
    UserDto dto = service.getUserByEmail(principal.getName());
    dto.setToken(tokenProvider.getToken(request));
    model.addAttribute("user", dto);
    return "home";
  }

  @GetMapping(path = "/welcome")
  public String welcome(Principal principal, Model model) {
    UserDto user = new UserDto();
    user.setFirstName("Tammy");
    model.addAttribute("user", user);
    return "welcome";
  }
}
