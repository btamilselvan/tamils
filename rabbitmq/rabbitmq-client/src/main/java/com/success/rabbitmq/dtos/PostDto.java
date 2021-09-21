package com.success.rabbitmq.dtos;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class PostDto {
  private String author;
  private String createdOn;
  private String post;
  private String receiver;
  private String action;
  private Integer postId;
}
