package com.success.utils;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;

public class TwilioWhatsApp {
  public static final String ACCOUNT_SID = System.getProperty("TWILIO_ACCOUNT_SID"); 
  public static final String AUTH_TOKEN = System.getProperty("TWILIO_AUTH_TOKEN");
  public static final String TWILIO_NUMBER = System.getProperty("TWILIO_AUTH_TOKEN");

  public static void main(String[] args) {
    Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
    Message message =
        Message.creator(
                new com.twilio.type.PhoneNumber("whatsapp:+17862863730"), //to number
                new com.twilio.type.PhoneNumber("whatsapp:+15852298565"), //from number
                "¡Tienes un nuevo pedido de Yumitos!")
            .create();

    System.out.println(message.getSid());
  }
}
