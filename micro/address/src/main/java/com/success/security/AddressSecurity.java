package com.success.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.success.filters.AddressAuthFilter;

@Configuration
public class AddressSecurity extends WebSecurityConfigurerAdapter {

  @Autowired private AddressAuthFilter filter;

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.authorizeRequests()
        .anyRequest()
        .authenticated()
        .and()
        .addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);
  }
}
