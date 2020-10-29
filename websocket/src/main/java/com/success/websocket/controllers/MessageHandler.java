package com.success.websocket.controllers;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import com.success.websocket.models.Message;
import com.success.websocket.models.Reply;
import com.success.websocket.utils.Constants;

@Controller
public class MessageHandler {

  @Autowired private SimpMessagingTemplate mt;

  @MessageMapping(value = "/incoming")
  @SendTo(value = "/topic/mydestination")
  public Reply handleMessage(
      Message message,
      @Header(name = "userId", defaultValue = "unknown") String userId,
      Principal user) {
    return new Reply(message.getName() + ": " + message.getContent());
  }

  @MessageMapping(value = "/trocks")
  public void handleChatMessage(Message message) {
    // the icoming messages with /trocks path (for e.g. /chat/trocks where /chat is app prefix) are
    // mapped to this method..
    // /mychat destination will be transformed to, /user/{message.getTo()}/mychat -> this is exactly
    // the path, the client subscribes to
    mt.convertAndSendToUser(
        message.getTo(),
        Constants.CHAT_DESTINATION_SUFFIX,
        new Reply(message.getName() + ": " + message.getContent()));
  }
}
