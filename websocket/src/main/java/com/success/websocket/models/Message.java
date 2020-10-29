package com.success.websocket.models;

public class Message {
  private String name;
  private String content;
  private String to;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

public String getTo(){return to;}

public void setTo(String to){this.to = to;}
}
