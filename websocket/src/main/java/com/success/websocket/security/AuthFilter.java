package com.success.websocket.security;

import java.io.IOException;
import java.util.Collections;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import com.success.websocket.utils.Constants;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AuthFilter extends OncePerRequestFilter {
	
  @Override
  protected boolean shouldNotFilter(HttpServletRequest request) {
	  AntPathMatcher pathMatcher = new AntPathMatcher();
    return Constants.ALLOWED_URLS_LIST
        .stream()
        .anyMatch(p -> pathMatcher.match(p, request.getServletPath()));
  }

  @Override
  protected void doFilterInternal(
      HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {
    log.info("testig..." + request.getRequestURI());
    request.getHeaderNames();
    SecurityContextHolder.getContext()
        .setAuthentication(
            new UsernamePasswordAuthenticationToken("Tammy", null, Collections.emptyList()));
    filterChain.doFilter(request, response);
  }
}
