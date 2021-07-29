package com.success.gateway;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

//@EnableWebSecurity
//@Configuration
public class SecurityConfig {

//  @Override
//  protected void configure(HttpSecurity http) throws Exception {
//    http.antMatcher("/**").authorizeRequests().anyRequest().permitAll();
//  }

  /*@Bean
    SecurityWebFilterChain authorization(ServerHttpSecurity security) {
  return security.authorizeExchange().pathMatchers("/**").permitAll().and().authorizeExchange().anyExchange().authenticated().and().build();
    }*/

  /*
   * @Bean public CorsFilter corsFilter() { UrlBasedCorsConfigurationSource source
   * = new UrlBasedCorsConfigurationSource(); CorsConfiguration config = new
   * CorsConfiguration(); config.addAllowedOrigin("*");
   * config.addAllowedHeader("*"); config.addAllowedMethod("OPTIONS");
   * config.addAllowedMethod("HEAD"); config.addAllowedMethod("GET");
   * config.addAllowedMethod("PUT"); config.addAllowedMethod("POST");
   * config.addAllowedMethod("DELETE"); source.registerCorsConfiguration("/**",
   * config); return new CorsFilter(source); }
   */

  @Bean
  public PasswordEncoder getPasswordEncoder() {
    return new BCryptPasswordEncoder();
  }

  public static void main(String[] args) {
    System.out.println(new BCryptPasswordEncoder().encode("tamil"));
  }
}
