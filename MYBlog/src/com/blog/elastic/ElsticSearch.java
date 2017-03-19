package com.blog.elastic;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.elasticsearch.ElasticsearchException;
import org.json.JSONObject;

import io.searchbox.client.JestClient;
import io.searchbox.client.JestClientFactory;
import io.searchbox.client.config.HttpClientConfig;
import io.searchbox.core.Index;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;
import io.searchbox.indices.CreateIndex;


public class ElsticSearch {

	private static final Logger LOG = Logger.getLogger(ElsticSearch.class);
	private JestClient client = null;
	private final static String INDEX = "students";
	private final static String INDEX_TYPE = "studentdetail";

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
			LOG.debug("error is : " + ex);

		}
	}

	public void createDocumet(JSONObject jsonString) {
		String id = String.valueOf(jsonString.getInt("id"));
				try {
			Index index = new Index.Builder(jsonString).index(INDEX).type(INDEX_TYPE).id(id).build();
			client.execute(index);
		} catch (IOException ex) {
			LOG.debug("error is : " + ex);
		} catch (ElasticsearchException ex) {
			LOG.debug("error is : " + ex);
		}
	}

	public String performSearch(final String query) throws IOException {
		Search search = new Search.Builder(query)
				.addIndex(INDEX)
				.addType(INDEX_TYPE)
				.build();
		SearchResult result = client.execute(search);
		return result.getJsonString();
	}
}
