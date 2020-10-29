package com.oster.recipes.security;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.oster.recipes.utils.Constants;
import com.oster.recipes.utils.JwtUtil;

public class JWTAuthenticationFilter extends OncePerRequestFilter {

  @Autowired private JwtUtil jwtUtil;

  @Value("${jwt.header}")
  private String tokenHeader;

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

    String token = jwtUtil.getToken(request);

    if (token != null && !jwtUtil.isTokenExpired(token)) {
      // a valid jwt token found . use that token to authenticate the requests
      // validate the token
      String userName = jwtUtil.getUsernameFromToken(token);
      if (StringUtils.hasText(userName)) {
        setAuthentication(userName);
        setTokenHeader(token, response);
      }
    }
    filterChain.doFilter(request, response);
  }

  private void setAuthentication(String userName) {
    SecurityContextHolder.getContext()
        .setAuthentication(
            new UsernamePasswordAuthenticationToken(userName, null, new ArrayList<>()));
  }

  private void setTokenHeader(String token, HttpServletResponse response) {
    jwtUtil.setTokenHeader(token, response);
  }
}
