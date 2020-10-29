package com.success.websocket.security;

import java.util.Collections;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

import com.success.websocket.utils.Constants;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
@EnableWebSocketMessageBroker
// we want web socket security takes precedence over web security to allow the inbound connections
// after validating the tokens...
@Order(Ordered.HIGHEST_PRECEDENCE)
public class WebsocketMessageBrokerAuthentication implements WebSocketMessageBrokerConfigurer {

  @Autowired private TokenProvider tokenProvider;

  @Override
  public void configureClientInboundChannel(ChannelRegistration registration) {
    registration.interceptors(
        new ChannelInterceptor() {
          @Override
          public Message<?> preSend(Message<?> message, MessageChannel channel) {
            StompHeaderAccessor accessor =
                MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
            log.info("command is " + accessor.getCommand());
            /*
             * Intercept connect/subscribe/send commands and verify the authToken
             */
            if (StringUtils.equalsAny(
                accessor.getCommand().name(),
                StompCommand.CONNECT.name(),
                StompCommand.SUBSCRIBE.name(),
                StompCommand.SEND.name())) {

              List<String> authorization = accessor.getNativeHeader("X-Authorization");
              log.info("authtoken is " + authorization.get(0));
              if (!tokenProvider.validateToken(authorization.get(0))) {
                log.error("invalid token.. access denied.");
                accessor.setUser(null);
                return message;
              }

              String email = tokenProvider.getSubjectFromToken(authorization.get(0));
              log.info("email is " + email);

              if (StompCommand.SUBSCRIBE.equals(accessor.getCommand())
                  && !getActualDestination(email)
                      .equals(accessor.getNativeHeader("destination").get(0))) {
                log.info("destination is " + accessor.getNativeHeader("destination"));
                log.info("destination mismatch");
                accessor.setUser(null);
                return message;
              }

              Authentication authentication =
                  new UsernamePasswordAuthenticationToken(
                      tokenProvider.getSubjectFromToken(authorization.get(0)),
                      null,
                      Collections.emptyList());
              accessor.setUser(authentication);
            }
            return message;
          }
        });
  }

  private String getActualDestination(String email) {
    return Constants.FINAL_DESTINATION_PLACEHOLDER.replace("{userId}", email);
  }
}
