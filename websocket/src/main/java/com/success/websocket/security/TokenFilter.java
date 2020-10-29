package com.success.websocket.security;

import java.io.IOException;
import java.util.Collections;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import com.success.websocket.utils.Constants;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TokenFilter extends OncePerRequestFilter {
  @Autowired private TokenProvider tokenProvider;

  @Override
  protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
    AntPathMatcher pathMatcher = new AntPathMatcher();
    return Constants.ALLOWED_URLS_LIST
        .stream()
        .anyMatch(p -> pathMatcher.match(p, request.getServletPath()));
  }

  @Override
  protected void doFilterInternal(
      HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {
    log.info("inside token filter ...." + request.getRequestURI());
    String token = tokenProvider.getToken(request);
    if (token != null && tokenProvider.validateToken(token)) {
      setAuthentication(tokenProvider.getSubjectFromToken(token));
    } else {
      log.error("invalid token");
    }
    filterChain.doFilter(request, response);
  }

  private void setAuthentication(String email) {

    /*List<SimpleGrantedAuthority> authorities =
    Stream.of(roles.split(",")).map(SimpleGrantedAuthority::new).collect(Collectors.toList());*/

    SecurityContextHolder.getContext()
        .setAuthentication(
            new UsernamePasswordAuthenticationToken(email, null, Collections.emptyList()));
  }
}
