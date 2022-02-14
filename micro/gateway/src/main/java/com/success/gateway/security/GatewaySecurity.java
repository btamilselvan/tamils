package com.success.gateway.security;

import java.time.Instant;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class GatewaySecurity implements GlobalFilter {

  @Override
  public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
    log.info("inside pre filter");
    // authenticate here ....
    //    exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
    //    exchange.getResponse().setComplete();
    return chain
        .filter(exchange)
        .log()
        .then(
            Mono.fromRunnable(
                () -> {
                  log.info("inside post filter");
                  log.info(
                      "{} - {} - {} - {} -  {} - {}",
                      Instant.now().toString(),
                      exchange.getRequest().getURI().toString(),
                      exchange.getRequest().getRemoteAddress().getAddress().getHostAddress(),
                      // exchange.getRequest().getHeaders().getFirst("X-Forwarded-For"),
                      exchange.getRequest().getMethod().toString(),
                      //                      exchange.getRequest().getPath().toString(),
                      exchange.getResponse().getStatusCode().value(),
                      exchange.getResponse().getHeaders().getContentLength());
                }));
  }
}
