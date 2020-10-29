package com.appliedjobs.DynamoDataIndexer.handler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpHost;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.json.JSONObject;
import org.slf4j.Logger;

import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.ItemUtils;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.StreamRecord;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.DynamodbEvent;
import com.amazonaws.services.lambda.runtime.events.DynamodbEvent.DynamodbStreamRecord;

public class IndexerFunctionHandler implements RequestHandler<DynamodbEvent, String> {

	private static String ES_ENDPOINT;
	private static final String CANDIDATE_INDEX = "candiate";
	private static final String JOB_INDEX = "job";
	private static final String TYPE = "_doc";
	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(IndexerFunctionHandler.class);
	private static RestHighLevelClient esClient;

	static {
		ES_ENDPOINT = System.getenv("es_host");
	}

	public String handleRequest(DynamodbEvent ddbEvent, Context context) {
		logger.info("start....");
		logger.info("total records size : " + ddbEvent.getRecords().size());
		for (DynamodbStreamRecord record : ddbEvent.getRecords()) {
			logger.info("EventName : " + record.getEventName());
			logger.info("data : " + record.getDynamodb());
			logger.info("keys .. " + record.getDynamodb().getKeys());
			String id = getIdfromJsonString(record);
			logger.info("id: " + id);			
			if ("remove".equalsIgnoreCase(record.getEventName())) {
				delete(id);
			} else {
				String data = getJsonstring(record.getDynamodb().getNewImage());
				if (esClient == null) {
					esClient = esClient();
				}
				String index = identifyIndex(data);
				if (index == null) {
					logger.error("cannot determine index... skip index...");
					continue;
				}
				logger.info("Index is " + index);
				if ("insert".equalsIgnoreCase(record.getEventName())) {
					insert(index, id, data);
				} else if ("modify".equalsIgnoreCase(record.getEventName())) {
					modify(index, id, data);
				}
			}
		}
		logger.info("end....");
		return "Done";
	}

	private void insert(String index, String id, String data) {
		IndexRequest indexRequest = new IndexRequest(index, TYPE, id).source(data, XContentType.JSON);
		try {
			logger.info(esClient.index(indexRequest, RequestOptions.DEFAULT).toString());
		} catch (IOException e) {
			logger.error("unable to insert index. ID is " + id, e);
		}
	}

	private void modify(String index, String id, String data) {
		try {
			UpdateRequest request = new UpdateRequest(index, TYPE, id);
			request.doc(data, XContentType.JSON);
			logger.info(esClient.update(request, RequestOptions.DEFAULT).toString());
		} catch (IOException e) {
			logger.error("unable to update index. ID is " + id, e);
		}
	}

	private void delete(String id) {
		try {
			DeleteRequest dr = new DeleteRequest(CANDIDATE_INDEX, TYPE, id);
			logger.info(esClient.delete(dr, RequestOptions.DEFAULT).toString());
			
			dr = new DeleteRequest(JOB_INDEX, TYPE, id);
			logger.info(esClient.delete(dr, RequestOptions.DEFAULT).toString());
		} catch (IOException e) {
			logger.error("unable to delete from index. ID is " + id, e);
		}
	}
	
	private String getJsonstring(Map<String, AttributeValue> newImg) {
		String json = null;
		Map<String, AttributeValue> newImage = newImg;
		List<Map<String, AttributeValue>> listOfMaps = new ArrayList<Map<String, AttributeValue>>();
		listOfMaps.add(newImage);
		List<Item> itemList = ItemUtils.toItemList(listOfMaps);
		for (Item item : itemList) {
			json = item.toJSON();
		}
		return json;
	}

	private String getIdfromJsonString(DynamodbStreamRecord record) {
		Map<String, AttributeValue> keyMap = record.getDynamodb().getKeys();
		StringBuilder key = new StringBuilder(keyMap.get("pk").getS());
		key.append("__");
		key.append(keyMap.get("category").getS());
		return key.toString();
	}

	private String identifyIndex(String Json) {
		JSONObject jsonObj = new JSONObject(Json);
		String type = String.valueOf(jsonObj.getString("type"));
		if ("candidate".equalsIgnoreCase(type)) {
			return CANDIDATE_INDEX;
		} else if ("job".equalsIgnoreCase(type)) {
			return JOB_INDEX;
		}
		return null;
	}

	private RestHighLevelClient esClient() {
		RestHighLevelClient client = new RestHighLevelClient(RestClient.builder(new HttpHost(ES_ENDPOINT)));
		return client;
	}

	public static void main(String[] args) {
		IndexerFunctionHandler handler = new IndexerFunctionHandler();
		DynamodbEvent e = new DynamodbEvent();
		List<DynamodbStreamRecord> records = new ArrayList<DynamodbEvent.DynamodbStreamRecord>();
		DynamodbStreamRecord record = new DynamodbStreamRecord();
		record.setEventName("test");
		StreamRecord sr = new StreamRecord();
		Map<String, AttributeValue> map = new HashMap<String, AttributeValue>();
		map.put("ID", new AttributeValue().withS("12121-12121-333232-ddffdf"));
		sr.setNewImage(map);
		record.setDynamodb(sr);
		records.add(record);
		e.setRecords(records);
		handler.handleRequest(e, null);
	}
}