package com.dwarf.spider.utils;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ExecutionException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.elasticsearch.action.admin.indices.analyze.AnalyzeResponse;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequestBuilder;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexResponse;
import org.elasticsearch.action.admin.indices.mapping.put.PutMappingRequestBuilder;
import org.elasticsearch.action.admin.indices.mapping.put.PutMappingResponse;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.xcontent.XContentBuilder;

import com.alibaba.fastjson.JSONObject;

/**
 * _id:是doc的主键，而非_source里面的id或者_id，更新是按照外围的_id更新的
 * 新增时如果未指定外围的_id，则会自动生成一个主键
 * 
 * 指定field type为string，必须要还需要在index时将analyzer改为not_analyzed,否则还是会切词
 * @author jiyu
 *
 */
public class ESAction {
	
	private static Log logger = LogFactory.getLog(ESAction.class);
	
	private static ESAction _ESAction;
	
	private static Properties prop = new Properties();
	
	static {
		InputStream in = ESAction.class.getClassLoader().getResourceAsStream("server.properties");
		try {
			prop.load(in);
			logger.info("server.properties loading succeed");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public Client client;
	
	private ESAction(){
		Settings settings = Settings.settingsBuilder()
		        .put("cluster.name", prop.getProperty("es.clusterName")).build();
		
		TransportClient tsClient = TransportClient.builder().settings(settings).build();
		for(int i = 0; i < prop.getProperty("es.server").split(",").length; i++){
			try {
				tsClient.addTransportAddress(new InetSocketTransportAddress(
						InetAddress.getByName(prop.getProperty("es.server").split(",")[i]), Integer.parseInt(prop.getProperty("es.port").split(",")[i])));
			} catch (NumberFormatException e) {
				e.printStackTrace();
			} catch (UnknownHostException e) {
				e.printStackTrace();
			}
		}
		
		client = tsClient;
	}
	
	public static ESAction getInstance(){
		if(_ESAction == null){
			_ESAction = new ESAction();
		}
		return _ESAction;
	}
	
	public static void main(String[] args) throws IOException {
		JSONObject object = new JSONObject();
		object.put("name", "扬州");
		ESAction.getInstance().createIndex("vampire", "house");
	}
	
	/**
	 * Analyzer
	 */
	public void analyzer(){
		try {
			AnalyzeResponse response = client.admin().indices().prepareAnalyze("扬州").execute().get();
			System.out.println(response.getTokens());
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 新增索引，有更新，没有新增
	 * @return
	 */
	public boolean insertData(String idx, String json, String index, String type){
		BulkRequestBuilder bulkRequest = client.prepareBulk();
		bulkRequest.add(client.prepareIndex(index, type, idx).setSource(json));
		BulkResponse bulkResponse = bulkRequest.get();
		return bulkResponse.hasFailures();
	}
	
	/**
	 * 批量新增索引bulk
	 * @param jsonList
	 * @param index
	 * @param mapping
	 * @return
	 */
	public boolean batchInsertData(Map<String, String> map, String index, String type){
		BulkRequestBuilder bulkRequest = client.prepareBulk();
		for(Map.Entry<String, String> entry : map.entrySet()){
			bulkRequest.add(client.prepareIndex(index, type, entry.getKey()).setSource(entry.getValue()));
		}
		BulkResponse bulkResponse = bulkRequest.get();
		return bulkResponse.hasFailures();
	}
	
	/**
	 * 创建索引type(未存在index的情况下)
	 * @param index
	 * @param mapping
	 * @return
	 */
	public boolean createIndex(String index, String type){
		CreateIndexRequestBuilder createRequestBuilder = client.admin().indices().prepareCreate(index);
		try {
			XContentBuilder mappingBuilder = jsonBuilder().startObject()
					.startObject(type)
			        	.startObject("properties")
			        		.startObject("id").field("type", "long").field("store", "yes").endObject()
			        		.startObject("flag").field("type", "integer").field("store", "yes").endObject()
			        		.startObject("price").field("type", "long").field("store", "yes").endObject()
			        		.startObject("source").field("type", "string").field("store", "yes").endObject()
			        		.startObject("city").field("type", "string").field("store", "yes").field("index", "not_analyzed").endObject()
			        		.startObject("name").field("type", "string").field("store", "yes").field("analyzer", "ik_smart").field("search_analyzer", "ik_smart").endObject()
			        		.startObject("address").field("type", "string").field("store", "yes").field("analyzer", "ik_smart").field("search_analyzer", "ik_smart").endObject()
			        		.startObject("developer").field("type", "string").field("store", "yes").field("analyzer", "ik_smart").field("search_analyzer", "ik_smart").endObject()
			        		.startObject("tenement").field("type", "string").field("store", "yes").field("analyzer", "ik_smart").field("search_analyzer", "ik_smart").endObject()
			        		.startObject("tenement_price").field("type", "string").field("store", "yes").field("analyzer", "ik_smart").field("search_analyzer", "ik_smart").endObject()
			        		.startObject("volumn").field("type", "string").field("store", "yes").field("index", "not_analyzed").endObject()
			        		.startObject("green").field("type", "string").field("store", "yes").field("index", "not_analyzed").endObject()
			        		.startObject("openingtime").field("type", "string").field("store", "yes").field("index", "not_analyzed").endObject()
			        		.startObject("time").field("type", "date").field("store", "yes").endObject()
			        		.startObject("pic").field("type", "string").field("store", "yes").endObject()
			        	.endObject()
			        .endObject()
			.endObject();
			createRequestBuilder.addMapping(type, mappingBuilder);
		} catch (IOException e) {
			e.printStackTrace();
		}
		CreateIndexResponse createIndexResponse = createRequestBuilder.execute().actionGet();
		return createIndexResponse.isAcknowledged();
	}
	
	/**
	 * 创建索引type(存在index的情况下,同一索引下，不同的type，相同字段的fieldType也必须相同)
	 * @param index
	 * @param type
	 * @return
	 */
	public boolean createExistIndex(String index, String type){
		PutMappingRequestBuilder putMappingRequestBuilder = client.admin().indices().preparePutMapping(index);
		try {
			XContentBuilder mappingBuilder = jsonBuilder().startObject()
					.startObject(type)
			        	.startObject("properties")
			        		.startObject("name").field("type", "string").field("store", "yes").endObject()
			        		.startObject("url").field("type", "string").field("store", "yes").endObject()
			        	.endObject()
			        .endObject()
			.endObject();
			putMappingRequestBuilder.setType(type).setSource(mappingBuilder);
		} catch (IOException e) {
			e.printStackTrace();
		}
		PutMappingResponse response = null;
		try {
			response = putMappingRequestBuilder.execute().get();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		return response.isAcknowledged();
	}
	
	/**
	 * 删除索引(包含索引下的type)
	 * @param index
	 * @return
	 */
	public boolean deleteIndex(String index){
		DeleteIndexResponse deleteIndexResponse = client.admin().indices().delete(new DeleteIndexRequest(index)).actionGet();
		return deleteIndexResponse.isAcknowledged();
	}
	
	/**
	 * 更新单个doc的索引
	 * @param index
	 * @param type
	 * @param id
	 * @param json
	 * @return
	 */
	public boolean updateData(String index, String type, String id, String json){
		UpdateResponse response = client.prepareUpdate(index, type, id).setDoc(json).get();
		return response.isCreated();
	}
	
	/**
	 * 获取单个doc
	 * @param index
	 * @param type
	 * @param idx
	 * @return
	 */
	public Object get(String index, String type, String idx){
		GetResponse response = client.prepareGet(index, type, idx).get();
		return response.getSource();
	}
	
	/**
	 * 删除单个doc
	 * @param index
	 * @param type
	 * @param idx
	 * @return
	 */
	public boolean deleteData(String index, String type, String idx){
		DeleteResponse response = client.prepareDelete(index, type, idx).get();
		return !response.isFound();
	}
	
}
