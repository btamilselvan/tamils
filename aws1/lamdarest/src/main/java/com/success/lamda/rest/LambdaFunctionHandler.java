package com.success.lamda.rest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestStreamHandler;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.services.dynamodb.model.PutItemRequest;
import software.amazon.awssdk.services.dynamodb.model.PutItemResponse;

/**
 * lambda Test event:
 * 
	{
		"body": "{\"name\": \"Testing\", \"address\": \"VA\"}",
	  	"pathParameters": {
	    	"id": "11111"
	  	},
	  	"queryStringParameters": {
	    	"name": "Tamil"
	  	}
	}
 * 
 * @author Tamil
 *
 */
public class LambdaFunctionHandler implements RequestStreamHandler {

	private static DynamoDbClient dynamoDb;
    private static final String DYNAMODB_TABLE_NAME = "ndb_user";
//    private static final Region REGION = Region.US_EAST_1;
	
    static {
    	initDynamoDBClient();    	
    }
    
	public void handleRequest1(InputStream inputStream, OutputStream outputStream, Context context) throws IOException {
		
		context.getLogger().log("Inside handle request .... ... ");
		JSONParser jp = new JSONParser();
		JSONObject response = new JSONObject();
		try {
			JSONObject jo = (JSONObject) jp.parse(new BufferedReader(new InputStreamReader(inputStream)));
			
			context.getLogger().log("Request path paramters is " + jo.get("pathParameters").toString());
			JSONObject pp = (JSONObject) jo.get("pathParameters");
			context.getLogger().log("path paramters is " + pp.get("name").toString());

			response.put("statusCode", "200");

			JSONObject header = new JSONObject();
			JSONObject responseBody = new JSONObject();
			responseBody.put("message", "Welcome to my world....");
			responseBody.put("Current date time is ", new Date());
			header.put("x-custom-header", "some header set here");

			response.put("body", responseBody.toJSONString()); // make sure to send a string.. probably json string based on the requirement.
			response.put("headers", header);

		} catch (ParseException e) {
			context.getLogger().log("unable to parse... ");
			response.put("statusCode", "400");
			response.put("execption", e);
		}
		OutputStreamWriter writer = new OutputStreamWriter(outputStream, "UTF-8");
		writer.write(response.toJSONString());
		writer.close();
	}
	@Override	
	public void handleRequest(InputStream inputStream, OutputStream outputStream, Context context) throws IOException {
		context.getLogger().log("Inside put request...");
		JSONObject response = new JSONObject();
		JSONParser parser = new JSONParser();
		JSONObject responseBody = new JSONObject();
		try {
			JSONObject request  = (JSONObject) parser.parse(new BufferedReader(new InputStreamReader(inputStream)));
			if(request.get("body")!=null){
				String requestBody = request.get("body").toString();
				context.getLogger().log("request body is "+requestBody);
				
//				PutItemResponse result = putItem(requestBody);
				
//				response.put("attribute values", result.getAttributes().values().toString());
				responseBody.put("message", "successfully added an item to the table.. ");
				responseBody.put("Current Datetime is ", new Date());
			}
			if(request.get("pathParameters")!=null) {
				JSONObject pp = (JSONObject) request.get("pathParameters");
				if(pp.get("id")!=null) {
					context.getLogger().log("path parameter "+pp.get("id"));
					responseBody.put("pathParam", pp.get("id"));
				}
			}
			if(request.get("queryStringParameters")!=null) {
				JSONObject pp = (JSONObject) request.get("queryStringParameters");
				if(pp.get("name")!=null) {
					context.getLogger().log("query parameter "+pp.get("name"));
					responseBody.put("queryParam", pp.get("name"));
				}
			}
			response.put("body", responseBody.toJSONString());
			response.put("statusCode", "200");
			response.put("isBase64Encoded", false);
			rdsConnectTest(context);
		} catch (ParseException e) {
			context.getLogger().log("unable to parse... ");
			response.put("statusCode", "400");
			response.put("execption", e);
		}
		OutputStreamWriter writer = new OutputStreamWriter(outputStream, "UTF-8");
		writer.write(response.toJSONString());
		writer.close();
	}
	
	private PutItemResponse putItem(String body) throws JsonParseException, JsonMappingException, IOException{
		
		System.out.println("inside put item .. rb is "+body);
		ObjectMapper mapper = new ObjectMapper();
		Map<String, Object> map = new HashMap<String, Object>();
		map = mapper.readValue(body, new TypeReference<Map<String, String>>(){});
		System.out.println("map is "+map.toString());
		
		Map<String, AttributeValue> itemValues = new HashMap<>();
		for(String key: map.keySet()){
			System.out.println("Key is: "+key);
			itemValues.put(key, AttributeValue.builder().s(map.get(key).toString()).build());
		}
		PutItemRequest request = PutItemRequest.builder().tableName(DYNAMODB_TABLE_NAME).item(itemValues).build();
		return dynamoDb.putItem(request);
	}
	
	private static void initDynamoDBClient(){
//		new SystemPropertiesCredentialsProvider().getCredentials();
		dynamoDb = DynamoDbClient.builder().build();
//		dynamoDb = AmazonDynamoDBClientBuilder.standard().withRegion(REGION).build();
	}

  private void rdsConnectTest(Context context) {
    try {
    	Class.forName("com.mysql.cj.jdbc.Driver");
    	String username = System.getenv("username");
    	String password = System.getenv("password");
    	String url = System.getenv("url");
      Connection connection = DriverManager.getConnection(url, username, password);
      PreparedStatement ps = connection.prepareStatement("select count(*) from recipe");
      ResultSet rs = ps.executeQuery();
      if(rs.next()) {
    	  context.getLogger().log("count "+rs.getInt(1));
      }
      ps.close();
      connection.close();
    } catch (Exception e) {
    	e.printStackTrace();
    }
  }
}
