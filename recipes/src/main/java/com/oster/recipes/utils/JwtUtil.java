package com.oster.recipes.utils;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.oster.recipes.dtos.UserDto;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtUtil implements Serializable {

  private static final long serialVersionUID = -3301605591108950415L;

  @Value("${jwt.secret}")
  private String secret;

  @Value("${jwt.access.token.expiration}")
  private Long accessTokenExpiration;

  @Value("${jwt.header}")
  private String tokenHeader;

  @Value("${spring.profiles.active:dev}")
  private String activeProfile;

  public String getUsernameFromToken(String token) {
    try {
      return getClaimFromToken(token, Claims::getSubject);
    } catch (ExpiredJwtException e) {
      return e.getClaims().getSubject();
    }
  }

  public String getClaimFromToken(HttpServletRequest request, String claimName) {
    String token = getToken(request);
    if (token != null) {
      return getClaimFromToken(token, claimName);
    }
    return null;
  }

  public String getClaimFromToken(String token, String claimName) {
    Claims claims = getAllClaimsFromToken(token);
    return claims.get(claimName, String.class);
  }

  public Date getIssuedAtDateFromToken(String token) {
    return getClaimFromToken(token, Claims::getIssuedAt);
  }

  public Date getExpirationDateFromToken(String token) {
    try {
      return getClaimFromToken(token, Claims::getExpiration);
    } catch (ExpiredJwtException e) {
      return e.getClaims().getExpiration();
    }
  }

  public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
    final Claims claims = getAllClaimsFromToken(token);
    return claimsResolver.apply(claims);
  }

  private Claims getAllClaimsFromToken(String token) {
    return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
  }

  public Boolean isTokenExpired(String token) {
    return getExpirationDateFromToken(token).before(new Date());
  }

  private Boolean isCreatedBeforeLastPasswordReset(Date created, Date lastPasswordReset) {
    return (lastPasswordReset != null && created.before(lastPasswordReset));
  }

  public String generateAccessToken(UserDto dto) {
    Map<String, Object> claims = new HashMap<>();
    claims.put(Constants.USER_ID_PARAM, dto.getPk());
    return doGenerateToken(claims, dto.getUserName(), accessTokenExpiration);
  }

  private String doGenerateToken(Map<String, Object> claims, String subject, Long expirationTime) {
    final Date createdDate = new Date();
    final Date expirationDate = calculateExpirationDate(createdDate, expirationTime);

    return Jwts.builder()
        .setClaims(claims)
        .setSubject(subject)
        .setIssuedAt(createdDate)
        .setExpiration(expirationDate)
        .signWith(SignatureAlgorithm.HS512, secret)
        .compact();
  }

  public Boolean canTokenBeRefreshed(String token, Date lastPasswordReset) {
    final Date created = getIssuedAtDateFromToken(token);
    return !isCreatedBeforeLastPasswordReset(created, lastPasswordReset)
        && (!isTokenExpired(token) /* || ignoreTokenExpiration(token)*/);
  }

  private Date calculateExpirationDate(Date createdDate, Long expirationTime) {
    return new Date(createdDate.getTime() + expirationTime);
  }

  public void setTokenHeader(String token, HttpServletResponse response) {
    response.setHeader("Authorization", "Bearer " + token);
    exposeHeader("Authorization", response);
  }

  public void setRefreshTokenHeader(String token, HttpServletResponse response) {
    response.setHeader("refresh_token", token);
    exposeHeader("refresh_token", response);
  }

  private void exposeHeader(String headerName, HttpServletResponse response) {
    String header = "access-control-expose-headers";
    if ("dev".equals(activeProfile)) {
      if (response.getHeaders(header).isEmpty()) {
        response.setHeader(header, headerName);
      } else {
        response.addHeader(header, headerName);
      }
    }
  }

  public String getToken(HttpServletRequest request) {
    String requestHeader = request.getHeader(tokenHeader);
    if (requestHeader != null
        && requestHeader.startsWith("Bearer ")
        && requestHeader.substring(7) != null
        && !"null".equals(requestHeader.substring(7))) {
      return requestHeader.substring(7);
    }
    return null;
  }
}
