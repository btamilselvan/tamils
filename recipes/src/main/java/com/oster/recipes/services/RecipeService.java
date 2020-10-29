package com.oster.recipes.services;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.TransactionWriteRequest;
import com.amazonaws.services.dynamodbv2.datamodeling.encryption.DynamoDBEncryptor;
import com.amazonaws.services.dynamodbv2.datamodeling.encryption.EncryptionContext;
import com.amazonaws.services.dynamodbv2.datamodeling.encryption.EncryptionFlags;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ComparisonOperator;
import com.amazonaws.services.dynamodbv2.model.Condition;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.oster.recipes.dtos.CollectionDto;
import com.oster.recipes.dtos.RecipeDto;
import com.oster.recipes.dtos.UserDto;
import com.oster.recipes.entities.dynamodb.Data;
import com.oster.recipes.entities.dynamodb.Data.DataMap;
import com.oster.recipes.repositories.dynamodb.DataRepository;
import com.oster.recipes.social.FacebookUtil;
import com.oster.recipes.social.TwitterUtil;
import com.oster.recipes.utils.Constants;
import com.oster.recipes.utils.DataMapper;
import com.oster.recipes.utils.DateUtils;
import com.oster.recipes.utils.JwtUtil;
import com.oster.recipes.utils.Messages;
import com.oster.recipes.utils.Result;

import lombok.extern.log4j.Log4j;
import twitter4j.auth.AccessToken;

@Log4j
@Service
@Transactional
public class RecipeService {

  @Autowired private DataRepository repo;

  @Autowired private BCryptPasswordEncoder passwordEncoder;

  @Autowired private JwtUtil jwtUtil;

  @Autowired private DataMapper mapper;

  @Autowired private ObjectMapper objectMapper;

  @Autowired private FacebookUtil fbUtil;

  @Autowired private TwitterUtil twitterUtil;

  @Autowired private RestHighLevelClient client;

  @Autowired
  @Qualifier("dynamoMapper")
  private DynamoDBMapper dynamoDbMmapper;

  @Autowired
  @Qualifier("dynamoMapperEncrypted")
  private DynamoDBMapper dynamoDbMmapperEncrypted;

  private static final String RANGE_KEY = "category";
  private static final String CATEGORY_INDEX_NAME = "CategoryGsi";

  public Result<String> addCountries(List<Map<String, String>> countries) {
    List<Data> entities = new ArrayList<>();

    countries.forEach(
        m ->
            entities.addAll(
                m.entrySet()
                    .stream()
                    .filter(e -> StringUtils.isNoneBlank(e.getKey(), e.getValue()))
                    .map(
                        e ->
                            new Data(
                                Constants.COUNTRY_PK,
                                e.getKey().trim().toUpperCase(),
                                e.getValue().trim()))
                    .collect(Collectors.toList())));
    if (!entities.isEmpty()) {
      dynamoDbMmapper.batchSave(entities);
    }
    return new Result<String>(true, Messages.COUNTRY_CREATE_SUCCESS);
  }

  public Result<UserDto> addUser(UserDto dto) {
    Result<UserDto> response = null;
    if (StringUtils.isBlank(dto.getUserName())) {
      return new Result<UserDto>(Messages.USER_NAME_REQUIRED);
    }
    Data entity = findUserByUsername(dto);

    if (entity != null) {
      response = new Result<UserDto>(Messages.USER_EXISTS);
    } else {
      entity = mapper.fromUserDto(dto);
      entity.setCategory(dto.getUserName().toLowerCase().trim());
      entity.setPassword(passwordEncoder.encode(dto.getPassword()));
      repo.save(entity);
      dto.setPk(entity.getPk());
      response = new Result<UserDto>(true, Messages.USER_CREATE_SUCCESS, dto);
    }
    return response;
  }

  public Result<UserDto> login(String userName, String password, HttpServletResponse response) {
    UserDto dto = new UserDto();
    dto.setUserName(userName);
    dto.setPassword(password);
    if (StringUtils.isBlank(dto.getUserName())) {
      return new Result<UserDto>(Messages.USER_NAME_REQUIRED);
    }
    Data entity = findUserByUsername(dto);
    if (entity == null) {
      return new Result<UserDto>(Messages.INVALID_CREDENTIALS);
    }
    if (passwordEncoder.matches(dto.getPassword(), entity.getPassword())) {
      dto = mapper.toUserDto(entity);
      jwtUtil.setTokenHeader(jwtUtil.generateAccessToken(dto), response);
      return new Result<UserDto>(true, Messages.LOGIN_SUCCESS, dto);
    } else {
      return new Result<UserDto>(Messages.INVALID_CREDENTIALS);
    }
  }

  private Data findByUserName(HttpServletRequest request) {
    String userName = jwtUtil.getUsernameFromToken(jwtUtil.getToken(request));
    UserDto dto = new UserDto();
    dto.setUserName(userName);
    return findUserByUsername(dto);
  }

  private Data findUserByUsername(UserDto dto) {
    Map<String, AttributeValue> expressionAttributeValues = new HashMap<>();
    expressionAttributeValues.put(
        ":category", new AttributeValue().withS(dto.getUserName().toLowerCase().trim()));

    Data entity = new Data();
    entity.setCategory(dto.getUserName().toLowerCase().trim());
    DynamoDBQueryExpression<Data> ex =
        new DynamoDBQueryExpression<Data>()
            .withIndexName(CATEGORY_INDEX_NAME)
            .withConsistentRead(false)
            .withKeyConditionExpression("category = :category")
            .withExpressionAttributeValues(expressionAttributeValues);
    List<Data> queryResults = dynamoDbMmapper.query(Data.class, ex);
    return queryResults.size() > 0 ? queryResults.get(0) : null;
  }

  public String saveRecipe() {
    return "success";
  }

  public Result<CollectionDto> createCollection(CollectionDto dto, HttpServletRequest request) {
    Result<CollectionDto> result = null;

    if (StringUtils.isBlank(dto.getName())) {
      return new Result<CollectionDto>(Messages.COLLECTION_NAME_REQUIRED);
    }

    boolean exists = false;
    Data entity = new Data();
    entity.setPk(getUserId(request));

    Condition rk = new Condition();
    rk.withComparisonOperator(ComparisonOperator.BEGINS_WITH)
        .withAttributeValueList(new AttributeValue().withS(Constants.COLLECTION_PREFIX));

    DynamoDBQueryExpression<Data> ex =
        new DynamoDBQueryExpression<Data>()
            .withHashKeyValues(entity)
            .withRangeKeyCondition(RANGE_KEY, rk);
    /*
     * Map<String, AttributeValue> collAttrValMap = new HashMap<>();
     * collAttrValMap.put(":collName", new AttributeValue(dto.getName()));
     * ex.setExpressionAttributeValues(collAttrValMap);
     */

    List<Data> queryResults = dynamoDbMmapper.query(Data.class, ex);
    exists =
        queryResults
            .stream()
            .anyMatch(
                r -> dto.getName().trim().equalsIgnoreCase(r.getCategoryName().toLowerCase()));
    if (exists) {
      result = new Result<CollectionDto>(Messages.COLLECTION_EXISTS);
    } else {
      entity.setCategoryName(dto.getName().trim());
      entity.setCollectionId(UUID.randomUUID().toString());
      repo.save(entity);
      dto.setCollectionId(entity.getCollectionId());
      result = new Result<CollectionDto>(true, Messages.COLLECTION_CREATE_SUCCESS, dto);
    }
    return result;
  }

  public Result<RecipeDto> createOrUpdateRecipe(RecipeDto dto, HttpServletRequest request) {
    if (StringUtils.isAnyBlank(dto.getTitle(), dto.getIngredients(), dto.getPreparation())) {
      return new Result<RecipeDto>(Messages.RECIPE_MANDATORY_FIELDS);
    }
    Data entity = mapper.fromRecipeDto(dto);
    entity.setPk(getUserId(request));
    if (StringUtils.isBlank(dto.getRecipeId())) {
      entity.setRecipeId(UUID.randomUUID().toString());
      entity.setCreatedOn(DateUtils.getCurrentDateString());
    }
    repo.save(entity);
    return new Result<RecipeDto>(true, Messages.RECIPE_SAVE_SUCCESS, mapper.toRecipeDto(entity));
  }

  public Result<RecipeDto> getRecipe(String recipeId, HttpServletRequest request) {
    Data data = new Data();
    data.setPk(getUserId(request));
    data.setRecipeId(recipeId);
    Optional<Data> entity = repo.findById(data.getKey());
    if (entity.isPresent()) {
      return new Result<RecipeDto>(true, Messages.RECIPE_FOUND, mapper.toRecipeDto(entity.get()));
    } else {
      return new Result<RecipeDto>(Messages.RECIPE_NOT_FOUND);
    }
  }

  public Result<List<RecipeDto>> getRecipes(
      HttpServletRequest request, String collection, String country) {
    List<Data> entities = getAllRecipes(request, collection, country);
    List<RecipeDto> dtos = entities.stream().map(mapper::toRecipeDto).collect(Collectors.toList());
    return new Result<List<RecipeDto>>(true, Messages.RECIPE_FOUND, dtos);
  }

  private List<Data> getAllRecipes(HttpServletRequest request, String collection, String country) {
    Data data = new Data();
    data.setPk(getUserId(request));

    Condition rk = new Condition();
    rk.withComparisonOperator(ComparisonOperator.BEGINS_WITH)
        .withAttributeValueList(new AttributeValue().withS(Constants.RECIPE_PREFIX));

    DynamoDBQueryExpression<Data> ex =
        new DynamoDBQueryExpression<Data>()
            .withHashKeyValues(data)
            .withRangeKeyCondition(RANGE_KEY, rk);

    Map<String, AttributeValue> expressionAttributeValues = new HashMap<>();
    if (StringUtils.isNotBlank(collection)) {
      expressionAttributeValues.put(":collection", new AttributeValue().withS(collection.trim()));
      ex = ex.withFilterExpression("contains (collections, :collection)");
    }

    if (StringUtils.isNotBlank(country)) {
      expressionAttributeValues.put(":country", new AttributeValue().withS(country.trim()));
      ex = ex.withFilterExpression("contains (countries, :country)");
    }

    if (!expressionAttributeValues.isEmpty()) {
      ex = ex.withExpressionAttributeValues(expressionAttributeValues);
    }
    return dynamoDbMmapper.query(Data.class, ex);
  }

  public Result<String> deleteRecipe(String recipeId, HttpServletRequest request) {
    Data entity = new Data();
    entity.setRecipeId(recipeId);
    entity.setPk(getUserId(request));
    repo.delete(entity);
    return new Result<String>(true, Messages.RECIPE_DELETE_SUCCESS);
  }

  public Result<String> deleteCollection(String collection, HttpServletRequest request) {
    // update all recipes that are part of this collection
    List<Data> entitiesToUpdate = getAllRecipes(request, collection, null);
    TransactionWriteRequest transactionWriteRequest = new TransactionWriteRequest();

    for (Data e : entitiesToUpdate) {
      e.getCollections().remove(collection);
      transactionWriteRequest.addPut(e);
    }

    Data entityToDelete = new Data();
    entityToDelete.setPk(getUserId(request));
    entityToDelete.setCollectionId(collection.split("_")[0]);
    transactionWriteRequest.addDelete(entityToDelete);
    dynamoDbMmapper.transactionWrite(transactionWriteRequest);

    return new Result<String>(true, Messages.COLLECTION_DELETE_SUCCESS);
  }

  public void fbTest(String accessToken, String pageName) {
    fbUtil.getLongLivedPageAccessToken(accessToken, pageName);
  }

  public Result<String> publishToFb(String pageName, String message, HttpServletRequest request) {
    Data entity = findByUserName(request);
    if (entity == null || entity.getFbPageMapList() == null) {
      return new Result<String>(Messages.FB_GET_PAGE_ACCESS_TOKEN_FAILED);
    }
    DataMap map =
        entity
            .getFbPageMapList()
            .stream()
            .filter(e -> e.getKey().equals(pageName.trim()))
            .findFirst()
            .orElse(null);
    if (map == null) {
      return new Result<String>(Messages.FB_GET_PAGE_ACCESS_TOKEN_FAILED);
    }
    return fbUtil.publishPost(map, message);
  }

  public Result<String> linkWithFacebook(
      String userAccessToken, String pageName, HttpServletRequest request) {
    if (StringUtils.isAnyBlank(userAccessToken, pageName)) {
      return new Result<String>(Messages.FB_USER_ACCESS_TOKEN_REQUIRED);
    }
    JsonNode pageDetails = fbUtil.getPageDetails(userAccessToken, pageName.trim());
    if (pageDetails == null || pageDetails.get("id") == null) {
      return new Result<String>(Messages.FB_GET_PAGE_ACCESS_TOKEN_FAILED);
    }
    Data entity = findByUserName(request);
    if (entity == null) {
      return new Result<String>(Messages.USER_NOT_EXISTS);
    }
    List<DataMap> pageMapList = entity.getFbPageMapList();
    if (pageMapList == null) {
      pageMapList = new ArrayList<>();
    }
    DataMap pageMap =
        pageMapList
            .stream()
            .filter(e -> e.getKey().equals(pageName.trim()))
            .findFirst()
            .orElse(new DataMap(pageDetails.get("id").asText()));
    pageMap.setKey(pageName.trim());
    pageMap.setValue(pageDetails.get("access_token").asText());
    if (pageMapList.contains(pageMap)) {
      pageMapList.get(pageMapList.indexOf(pageMap)).setValue(pageMap.getValue());
    } else {
      pageMapList.add(pageMap);
    }
    entity.setFbPageMapList(pageMapList);
    repo.save(entity);
    return new Result<String>(true, Messages.FB_INTEGRATION_SUCCESS);
  }

  public Result<String> getTwitterOauthToken() {
    return twitterUtil.getOauthToken();
  }

  public Result<String> linkWithTwitter(
      String oauthToken, String tokenSecret, String oauthVerifier, HttpServletRequest request) {
    AccessToken accessToken = twitterUtil.getUserToken(oauthToken, tokenSecret, oauthVerifier);
    if (accessToken == null) {
      return new Result<String>(Messages.TWITTER_OAUTH_ACCESS_TOKEN_FAILED);
    }
    Data entity = findByUserName(request);

    List<DataMap> pageMapList = entity.getTwitterData();
    if (pageMapList == null) {
      pageMapList = new ArrayList<>();
    }
    DataMap pageMap =
        pageMapList
            .stream()
            .filter(e -> e.getKey().equals(accessToken.getScreenName()))
            .findFirst()
            .orElse(new DataMap(accessToken.getScreenName()));
    pageMap.setKey(accessToken.getToken());
    pageMap.setValue(accessToken.getTokenSecret());
    if (pageMapList.contains(pageMap)) {
      pageMapList.get(pageMapList.indexOf(pageMap)).setValue(pageMap.getValue());
    } else {
      pageMapList.add(pageMap);
    }
    entity.setTwitterData(pageMapList);
    repo.save(entity);

    return new Result<String>(true, Messages.TWITTER_LINK_SUCCESS);
  }

  public Result<String> tweet(String message, HttpServletRequest request) {
    Data entity = findByUserName(request);
    if (entity.getTwitterData() == null) {
      return new Result<String>(Messages.TWITTER_ACCESS_TOKEN_NOT_FOUND);
    }
    String failedAccounts =
        entity
            .getTwitterData()
            .stream()
            .map(e -> twitterUtil.tweet(message, e.getKey(), e.getValue()) == true ? null : e)
            .filter(e -> e != null)
            .map(e -> e.getId())
            .collect(Collectors.toList())
            .stream()
            .reduce("", (result, e) -> result + ", " + e);
    if (StringUtils.isBlank(failedAccounts)) {
      return new Result<String>(true, Messages.POST_PUBLISH_SUCCESS);
    } else {
      return new Result<String>(Messages.PUBLISH_FAILED_FOR_SOME_ACCOUNTS + failedAccounts);
    }
  }

  @SuppressWarnings("unchecked")
  public Result<String> indexData(RecipeDto dto) {
    Data entity = mapper.fromRecipeDto(dto);
    entity.setPk(UUID.randomUUID().toString());
    entity.setRecipeId("r_" + UUID.randomUUID().toString());
    Map<String, Object> dataMap = objectMapper.convertValue(entity, Map.class);
    log.info("dataMap is " + dataMap);
    IndexRequest indexRequest =
        new IndexRequest(Constants.INDEX_NAME, Constants.INDEX_TYPE, entity.getPk())
            .source(dataMap);
    try {
      IndexResponse indexResponse = client.index(indexRequest, RequestOptions.DEFAULT);
      return new Result<String>(true, "index success", indexResponse.getResult().name());
    } catch (IOException e) {
      log.error("unable to index .. ", e);
      return new Result<String>("index failed");
    }
  }

  public Result<RecipeDto> getDataFromIndex(String id) {
    GetRequest request = new GetRequest(Constants.INDEX_NAME, Constants.INDEX_TYPE, id);
    GetResponse response;
    try {
      response = client.get(request, RequestOptions.DEFAULT);
      Map<String, Object> resultMap = response.getSource();
      return new Result<RecipeDto>(
          true,
          "query success",
          mapper.toRecipeDto(objectMapper.convertValue(resultMap, Data.class)));
    } catch (IOException e) {
      log.error("unable to query index .. ", e);
      return new Result<RecipeDto>(false, "query failed");
    }
  }

  public Result<List<RecipeDto>> findAllFromIndex() {
    SearchRequest searchRequest = buildSearchRequest(Constants.INDEX_NAME, Constants.INDEX_TYPE);
    SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
    searchSourceBuilder.query(QueryBuilders.matchAllQuery());
    searchRequest.source(searchSourceBuilder);
    try {
      SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
      List<RecipeDto> result =
          Stream.of(searchResponse.getHits().getHits())
              .map(
                  e -> {
                    return mapper.toRecipeDto(
                        objectMapper.convertValue(e.getSourceAsMap(), Data.class));
                  })
              .collect(Collectors.toList());
      return new Result<List<RecipeDto>>(true, "", result);

    } catch (IOException e) {
      log.error("unable to find all from index .. ", e);
      return new Result<List<RecipeDto>>(false, "query failed");
    }
  }

  public Result<List<RecipeDto>> findByParamsFromIndex(Map<String, String> paramsMap) {

    if (paramsMap == null || paramsMap.values().isEmpty()) {
      return findAllFromIndex();
    }

    SearchRequest searchRequest = buildSearchRequest(Constants.INDEX_NAME, Constants.INDEX_TYPE);

    SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
    QueryBuilder qb =
        QueryBuilders.multiMatchQuery(paramsMap.get("text"), paramsMap.get("fields").split(","));

    searchSourceBuilder.query(qb);

    // QueryBuilders.boolQuery().must(QueryBuilders.termQuery(name, value));

    QueryBuilders.boolQuery().should(QueryBuilders.matchQuery(paramsMap.get("text"), "")).must();
    // QueryBuilders.boolQuery().must(QueryBuilders.matchQuery("location",
    // "experience")).must(QueryBuilders.matchQuery("12", "experience")).sh;

    // BoolQueryBuilder qBuilder = QueryBuilders.boolQuery();

    // BoolQueryBuilder qBuilder = QueryBuilders.boolQuery();
    //
    // List<MoreLikeThisQueryBuilder> moreLikeThisQueryBuilders =
    // paramsMap.entrySet().stream()
    // .filter(e -> StringUtils.isNoneBlank(e.getKey(), e.getValue())).map(e
    // -> {
    // return QueryBuilders.moreLikeThisQuery(new String[]{e.getKey()}, new
    // String[]{e.getValue()}, null);
    // }).collect(Collectors.toList());
    // for(MoreLikeThisQueryBuilder like : moreLikeThisQueryBuilders) {
    // qBuilder = qBuilder.must(like);
    // }
    // searchSourceBuilder.query(qBuilder);
    log.info("query json " + searchSourceBuilder.toString());
    searchRequest.source(searchSourceBuilder);
    try {
      SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
      List<RecipeDto> result =
          Stream.of(searchResponse.getHits().getHits())
              .map(
                  e -> {
                    return mapper.toRecipeDto(
                        objectMapper.convertValue(e.getSourceAsMap(), Data.class));
                  })
              .collect(Collectors.toList());
      return new Result<List<RecipeDto>>(true, "", result);

    } catch (IOException e) {
      log.error("unable to find all from index .. ", e);
      return new Result<List<RecipeDto>>(false, "query failed");
    }
  }

  public void storeCreditCardData_old(RecipeDto dto) {
    final EncryptionContext encryptionContext =
        new EncryptionContext.Builder()
            .withTableName("data")
            .withHashKeyName("pk")
            .withRangeKeyName("category")
            .build();
    final EnumSet<EncryptionFlags> encryptOnly =
        EnumSet.of(EncryptionFlags.SIGN, EncryptionFlags.ENCRYPT);
    final Map<String, Set<EncryptionFlags>> actions = new HashMap<>();
    final Map<String, AttributeValue> record = new HashMap<>();
    record.put("pk", new AttributeValue().withS(dto.getPk()));
    record.put("category", new AttributeValue().withS("p_" + UUID.randomUUID().toString()));
    try {
      DynamoDBEncryptor dynamoEncryptor = null;
      log.info(objectMapper.writeValueAsString(dto.getPayments()));
      record.put(
          "payments",
          new AttributeValue().withS(objectMapper.writeValueAsString(dto.getPayments())));
      actions.put("payments", encryptOnly);
      Map<String, AttributeValue> encryptedRecord =
          dynamoEncryptor.encryptRecord(record, actions, encryptionContext);
      log.info(encryptedRecord);

      final Map<String, AttributeValue> decryptedRecord =
          dynamoEncryptor.decryptRecord(encryptedRecord, actions, encryptionContext);

      log.info(decryptedRecord);

    } catch (GeneralSecurityException e) {
      e.printStackTrace();
    } catch (JsonProcessingException e) {
      e.printStackTrace();
    }
  }

  public void storeCreditCardData(RecipeDto dto) {
    Data entity = mapper.fromRecipeDto(dto);
    //		entity.getKey().setCategory("p_"+UUID.randomUUID().toString());
    dynamoDbMmapperEncrypted.save(entity);

    Data savedEntity =
        dynamoDbMmapperEncrypted.load(Data.class, entity.getPk(), entity.getCategory());
    log.info("saved data " + savedEntity);
  }

  private String getUserId(HttpServletRequest request) {
    return jwtUtil.getClaimFromToken(request, Constants.USER_ID_PARAM);
  }

  private SearchRequest buildSearchRequest(String index, String type) {
    SearchRequest searchRequest = new SearchRequest();
    searchRequest.indices(index);
    searchRequest.types(type);
    return searchRequest;
  }
}
