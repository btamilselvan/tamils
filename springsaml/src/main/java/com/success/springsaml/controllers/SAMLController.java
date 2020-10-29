package com.success.springsaml.controllers;

import java.io.IOException;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.saml.SAMLDiscovery;
import org.springframework.security.saml.metadata.MetadataManager;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/saml")
public class SAMLController {

  private static final Logger log = LogManager.getLogger(SAMLController.class);
  @Autowired private MetadataManager metadata;
  @Autowired Environment env;

  @GetMapping("/discovery")
  public String sso(HttpServletRequest request, HttpServletResponse response /*, Model model*/) {
    log.info("==============" + env.getProperty(SAMLDiscovery.RETURN_URL));
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    if (auth == null) {
      log.info("Current authentication instance from security context is null");
    } else {
      log.info(
          "Current authentication instance from security context: "
              + this.getClass().getSimpleName());
    }
    if (auth == null || (auth instanceof AnonymousAuthenticationToken)) {
      Set<String> idps = metadata.getIDPEntityNames();
      for (String idp : idps) {
        log.info("Configured Identity Provider for SSO: " + idp);
      }
      try {
        response.sendRedirect("http://localhost:8084/saml/login?idp=https://idp.ssocircle.com");
      } catch (IOException e) { // TODO Auto-generated catch block
        e.printStackTrace();
      }
      return "pages/discovery";
    } else {
      log.warn("The current user is already logged.");
      return "redirect:/landing";
    }
  }
}
