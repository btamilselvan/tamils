package com.success.websocket.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDto {
  private String email = "t1@tamils.rocks";

  @JsonProperty(access = Access.WRITE_ONLY)
  private String password = "T1Password";

  private String firstName;
  private String lastName;

  @JsonProperty(access = Access.READ_ONLY)
  private String token;

  public String getFullName() {
    StringBuilder fullName = new StringBuilder();
    if (firstName != null) {
      fullName.append(firstName);
      fullName.append(" ");
    }
    if (lastName != null) {
      fullName.append(lastName);
    }
    return fullName.toString();
  }
}
