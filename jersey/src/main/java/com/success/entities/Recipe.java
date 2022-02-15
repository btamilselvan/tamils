package com.success.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Recipe {

  @Column(name = "recipe_id")
  @Id
  @EqualsAndHashCode.Include
  private String recipeId;

  private String title;
}
