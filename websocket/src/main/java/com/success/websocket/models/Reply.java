package com.success.websocket.models;

public class Reply {
  private String content;

  public Reply() {}

  public Reply(String content) {
    this.content = content;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }
}
