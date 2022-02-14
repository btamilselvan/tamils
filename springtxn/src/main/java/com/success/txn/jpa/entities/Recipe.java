package com.success.txn.jpa.entities;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.Serializable;
import java.time.Instant;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Transient;

/** @author Tamil */
@Entity
@Table(name = "recipe")
public class Recipe implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @Basic(optional = false)
  @Column(name = "recipe_id")
  private String recipeId;

  @Basic(optional = false)
  @Column(name = "title")
  private String title;

  @Lob
  @Column(name = "additional_title")
  private String additionalTitle;

  @Column(name = "recipe_description")
  private String description;

  @Lob
  @Column(name = "preparation")
  @Basic(fetch = FetchType.LAZY)
  private String preparation;

  @Column(name = "preparation_time")
  private String preparationTime;

  @Column(name = "cook_time")
  private String cookTime;

  @Column(name = "portion")
  private String portion;

  @Lob
  @Column(name = "ingredients")
  @Basic(fetch = FetchType.LAZY)
  private String ingredients;

  @Lob
  @Column(name = "additional_ingredients")
  private String additionalIngredients;

  @Column(name = "sch_ingredients")
  private String searchIngredients;

  @Column(name = "sch_meals")
  private String searchMeals;

  @Column(name = "picture_url")
  private String pictureUrl;

  @Column(name = "video_link")
  private String videoLink;

  @Column(name = "created_on")
  private Instant createdOn;

  @Column(name = "updated_on")
  private Instant updatedOn;

  @Column(name = "posted_on")
  private Instant postedOn;

  @Column(name = "scheduled_on")
  private Instant scheduledOn;

  @Column(name = "avg_rating")
  private Integer avgRating;

  @Column(name = "raters_count")
  private Integer ratersCount;

  @Column(name = "recipe_num")
  private Integer recipeNum;

  @Transient private Float score;

  public Recipe() {}

  public Recipe(String recipeId) { // NOSONAR
    this.recipeId = recipeId;
  }

  public Recipe(String recipeId, String title) { // NOSONAR
    this.recipeId = recipeId;
    this.title = title;
  }

  public Recipe(String recipeId, Float score) { // NOSONAR
    this.recipeId = recipeId;
    this.score = score;
  }

  public Recipe( // NOSONAR
      String recipeId,
      String title,
      String preparationTime,
      String cookTime,
      String portion,
      String pictureUrl,
      String videoLink,
      Integer avgRating,
      Instant createdOn,
      Instant updatedOn) {
    this.recipeId = recipeId;
    this.title = title;
    this.preparationTime = preparationTime;
    this.cookTime = cookTime;
    this.portion = portion;
    this.pictureUrl = pictureUrl;
    this.videoLink = videoLink;
    this.avgRating = avgRating;
    this.createdOn = createdOn;
    this.updatedOn = updatedOn;
  }

  public Recipe( // NOSONAR
      String recipeId,
      String title,
      String preparationTime,
      String cookTime,
      String portion,
      String pictureUrl,
      String videoLink,
      Integer avgRating,
      Integer ratersCount,
      Instant createdOn,
      Instant updatedOn) {
    this(
        recipeId,
        title,
        preparationTime,
        cookTime,
        portion,
        pictureUrl,
        videoLink,
        avgRating,
        createdOn,
        updatedOn);
    this.ratersCount = ratersCount;
  }

  public String getRecipeId() {
    return recipeId;
  }

  public void setRecipeId(String recipeId) {
    this.recipeId = recipeId;
  }

  public String getTitle() {
    return title;
  }

  @Transient
  public String getTitleForUrl() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getPreparation() {
    return preparation;
  }

  public void setPreparation(String preparation) {
    this.preparation = preparation;
  }

  public String getIngredients() {
    return ingredients;
  }

  public void setIngredients(String ingredients) {
    this.ingredients = ingredients;
  }

  public String getPictureUrl() {
    return pictureUrl;
  }

  public void setPictureUrl(String pictureUrl) {
    this.pictureUrl = pictureUrl;
  }

  public String getVideoLink() {
    return videoLink;
  }

  public void setVideoLink(String videoLink) {
    this.videoLink = videoLink;
  }

  public Instant getCreatedOn() {
    return createdOn;
  }

  public void setCreatedOn(Instant createdOn) {
    this.createdOn = createdOn;
  }

  public Instant getPostedOn() {
    return postedOn;
  }

  public void setPostedOn(Instant postedOn) {
    this.postedOn = postedOn;
  }

  public Instant getScheduledOn() {
    return scheduledOn;
  }

  public void setScheduledOn(Instant scheduledOn) {
    this.scheduledOn = scheduledOn;
  }

  @Override
  public int hashCode() {
    int hash = 0;
    hash += (recipeId != null ? recipeId.hashCode() : 0);
    return hash;
  }

  @Override
  public boolean equals(Object object) {
    if (!(object instanceof Recipe)) {
      return false;
    }
    Recipe other = (Recipe) object;
    return (this.recipeId == null && other.recipeId != null)
        || (this.recipeId != null && !this.recipeId.equals(other.recipeId));
  }

  @Override
  public String toString() {
    return "com.oster.recipes.entities.jpa.Recipe[ recipeId=" + recipeId + " ]";
  }

  public String getPreparationTime() {
    return preparationTime;
  }

  public void setPreparationTime(String preparationTime) {
    this.preparationTime = preparationTime;
  }

  public String getCookTime() {
    return cookTime;
  }

  public void setCookTime(String cookTime) {
    this.cookTime = cookTime;
  }

  public String getPortion() {
    return portion;
  }

  public void setPortion(String portion) {
    this.portion = portion;
  }

  public Integer getAvgRating() {
    return avgRating;
  }

  public void setAvgRating(Integer avgRating) {
    this.avgRating = avgRating;
  }

  public Instant getUpdatedOn() {
    return updatedOn;
  }

  public void setUpdatedOn(Instant updatedOn) {
    this.updatedOn = updatedOn;
  }

  public Integer getRatersCount() {
    return ratersCount;
  }

  public void setRatersCount(Integer ratersCount) {
    this.ratersCount = ratersCount;
  }

  public Float getScore() {
    return score;
  }

  public void setScore(Float score) {
    this.score = score;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getSearchMeals() {
    return searchMeals;
  }

  public void setSearchMeals(String searchMeals) {
    this.searchMeals = searchMeals;
  }

  public String getSearchIngredients() {
    return searchIngredients;
  }

  public void setSearchIngredients(String searchIngredients) {
    this.searchIngredients = searchIngredients;
  }

  public String getAdditionalTitle() {
    return additionalTitle;
  }

  public void setAdditionalTitle(String additionalTitle) {
    this.additionalTitle = additionalTitle;
  }

  public String getAdditionalIngredients() {
    return additionalIngredients;
  }

  public void setAdditionalIngredients(String additionalIngredients) {
    this.additionalIngredients = additionalIngredients;
  }

  public Integer getRecipeNum() {
    return recipeNum;
  }

  public void setRecipeNum(Integer recipeNum) {
    this.recipeNum = recipeNum;
  }
}
