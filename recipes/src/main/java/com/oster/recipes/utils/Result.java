package com.oster.recipes.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Result<T> {
  private boolean status;
  private String message;
  private T data;

  public Result(boolean status, String message) {
    this.status = status;
    this.message = message;
  }

  public Result(String message) {
    this.message = message;
  }
}
