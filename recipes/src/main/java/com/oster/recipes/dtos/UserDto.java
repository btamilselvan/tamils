package com.oster.recipes.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserDto {
  private String pk;
  private String firstName;
  private String lastName;

  @JsonProperty(access = Access.WRITE_ONLY)
  private String password;

  private String userName;
}
