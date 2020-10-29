package com.oster.recipes.dtos;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CollectionDto {
  private String pk;
  private String collectionId;
  private String name;
  private List<String> countries;
}
