package com.oster.recipes.utils;

import java.util.Arrays;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.oster.recipes.dtos.RecipeDto;
import com.oster.recipes.dtos.UserDto;
import com.oster.recipes.entities.dynamodb.Data;

@Component
public class DataMapper {

  public RecipeDto toRecipeDto(Data data) {
    RecipeDto dto = new RecipeDto();
    dto.setCollections(data.getCollections());
    dto.setCreatedOn(data.getCreatedOn());
    dto.setIngredients(data.getIngredients());
    dto.setPictureUrl(data.getPictureUrl());
    dto.setPk(data.getPk());
    dto.setPostedOn(data.getPostedOn());
    dto.setPreparation(data.getPreparation());
    dto.setRating(data.getRating());
    dto.setRecipeId(data.getRecipeId());
    dto.setScheduledOn(data.getScheduledOn());
    dto.setTags(data.getTags());
    dto.setTitle(data.getCategoryName());
    dto.setVideoUrl(data.getVideoUrl());
    dto.setCountries(data.getCountries());
    dto.setType(data.getType());
    if (data.getSeoSettings() != null) {
      dto.setSeoSettings(
          Arrays.asList(data.getSeoSettings())
              .stream()
              .map(s -> new RecipeDto.SettingsDto(s.getTitle(), s.getDescription()))
              .findFirst()
              .orElse(null));
    }
    if (data.getOpenGraphSettings() != null) {
      dto.setOpenGraphSettings(
          Arrays.asList(data.getOpenGraphSettings())
              .stream()
              .map(s -> new RecipeDto.SettingsDto(s.getTitle(), s.getDescription()))
              .findFirst()
              .orElse(null));
    }
    if (data.getPublications() != null) {
      dto.setPublications(
          data.getPublications()
              .stream()
              .map(
                  p ->
                      new RecipeDto.PublicationSettingsDto(
                          p.getCountry(),
                          p.getSocialMedia(),
                          p.getProductRelation(),
                          p.getCollection()))
              .collect(Collectors.toList()));
    }
    if (data.getPayments() != null) {
      dto.setPayments(
          data.getPayments()
              .stream()
              .map(e -> new RecipeDto.PaymentDto(e.getCard(), e.getExp(), e.getName(), e.getCvv()))
              .collect(Collectors.toList()));
    }
    return dto;
  }

  public Data fromRecipeDto(RecipeDto dto) {
    Data entity = new Data();
    entity.setCollections(dto.getCollections());
    entity.setCreatedOn(dto.getCreatedOn());
    entity.setIngredients(dto.getIngredients());
    entity.setPictureUrl(dto.getPictureUrl());
    entity.setPk(dto.getPk());
    entity.setPostedOn(dto.getPostedOn());
    entity.setPreparation(dto.getPreparation());
    entity.setRating(dto.getRating());
    entity.setRecipeId(dto.getRecipeId());
    entity.setScheduledOn(dto.getScheduledOn());
    entity.setTags(dto.getTags());
    entity.setCategoryName(dto.getTitle());
    entity.setVideoUrl(dto.getVideoUrl());
    entity.setCountries(dto.getCountries());
    entity.setType(dto.getType());
    if (dto.getCountries() != null) {
      entity.setCountryGsiPk(StringUtils.join(dto.getCountries(), ","));
    }
    if (dto.getSeoSettings() != null) {
      entity.setSeoSettings(
          Arrays.asList(dto.getSeoSettings())
              .stream()
              .map(s -> new Data.Settings(s.getTitle(), s.getDescription()))
              .findFirst()
              .orElse(null));
    }
    if (dto.getOpenGraphSettings() != null) {
      entity.setOpenGraphSettings(
          Arrays.asList(dto.getOpenGraphSettings())
              .stream()
              .map(s -> new Data.Settings(s.getTitle(), s.getDescription()))
              .findFirst()
              .orElse(null));
    }
    if (dto.getPublications() != null) {
      entity.setPublications(
          dto.getPublications()
              .stream()
              .map(
                  p ->
                      new Data.PublicationSettings(
                          p.getCountry(),
                          p.getSocialMedia(),
                          p.getProductRelation(),
                          p.getCollection()))
              .collect(Collectors.toList()));
    }
    if (dto.getPayments() != null) {
      entity.setPayments(
          dto.getPayments()
              .stream()
              .map(e -> new Data.Payment(e.getCard(), e.getExp(), e.getName(), e.getCvv()))
              .collect(Collectors.toList()));
    }
    return entity;
  }

  public UserDto toUserDto(Data data) {
    UserDto dto = new UserDto();
    dto.setFirstName(data.getFirstName());
    dto.setLastName(data.getLastName());
    dto.setPk(data.getPk());
    dto.setUserName(data.getCategory());
    return dto;
  }

  public Data fromUserDto(UserDto dto) {
    Data entity = new Data();
    entity.setFirstName(dto.getFirstName());
    entity.setLastName(dto.getLastName());
    entity.setPassword(dto.getPassword());
    entity.setPk(dto.getPk());
    entity.setCategory(dto.getUserName());
    return entity;
  }
}
