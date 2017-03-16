package com.blog.elastic;

import java.io.IOException;

import org.elasticsearch.ElasticsearchException;
//import org.elasticsearch.action.get.GetResponse;
import org.json.JSONObject;

import io.searchbox.client.JestClient;
import io.searchbox.client.JestClientFactory;
import io.searchbox.client.config.HttpClientConfig;
import io.searchbox.core.Index;
import io.searchbox.indices.CreateIndex;
//import org.elasticsearch.client.Client;

public class ElsticSearch {

	private JestClient client = null;
	private final static String INDEX = "students";
	private final static String INDEX_TYPE = "studentdetail";
	//private final static int STATUS = 200;

	public ElsticSearch() {

		initializeElastic();
		createIndex();
	}

	private void initializeElastic() {
		JestClientFactory factory = new JestClientFactory();
		factory.setHttpClientConfig(new HttpClientConfig.Builder("http://localhost:9200").multiThreaded(true).build());
		client = factory.getObject();

	}

	private void createIndex() {
		try {
			client.execute(new CreateIndex.Builder(INDEX).build());
		} catch (IOException ex) {
			System.out.println("error in elastic search is :" + ex);

		}
	}

	public void createDocumet(JSONObject jsonString) {
		String id = String.valueOf(jsonString.getInt("id"));
		//GetResponse response = ((GetResponse) client).prepareGet(INDEX, INDEX_TYPE, id).execute().actionGet();
		// Check if a document exists
		//GetResponse response = ((Client) client).prepareGet(INDEX, INDEX_TYPE, id).setRefresh(true).execute()
			//	.actionGet();
		//System.out.println("above line worked successfully :  " + response);
		try {
			//if (!response.isExists()) {
				Index index = new Index.Builder(jsonString).index(INDEX).type(INDEX_TYPE).id(id).build();
				client.execute(index);
			//}
		} catch (IOException ex) {
			System.out.println("the error while creating index is :" + ex);
		}catch (ElasticsearchException ex) {
			//System.out.println("the error while searching document is :" + ex);
		}
	}
}

