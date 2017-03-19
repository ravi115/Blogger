package com.blog.querybusiness;

import java.io.IOException;

import org.apache.log4j.Logger;

import com.blog.elastic.ElsticSearch;
import com.blog.query.QueryBuilder;
import com.blog.request.RequestInformation;

public class QueryBusiness {

	private static final Logger LOG = Logger.getLogger(QueryBusiness.class);

	public String getQueryResult(RequestInformation reqId) {

		LOG.debug("query building and searching ...");
		String query = new QueryBuilder().getQuery(reqId.id);
		LOG.debug("query for elastic search is : " + query);
		String queryResult = null;
		try {
			queryResult = new ElsticSearch().performSearch(query);
			LOG.debug("the result returned from query is : " + queryResult);
		} catch (IOException e) {
			LOG.error("error caught is : " + e);
		}
		return queryResult;
	}
}
