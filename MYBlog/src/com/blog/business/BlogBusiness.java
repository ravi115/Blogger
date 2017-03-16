package com.blog.business;

import org.apache.log4j.Logger;
import org.json.JSONObject;

import com.blog.dto.StudentInformation;
import com.blog.elastic.ElsticSearch;
import com.blog.sqldatabase.StudentDatabase;

public class BlogBusiness {

	private static final Logger LOG = Logger.getLogger(BlogBusiness.class);
	
	public void insertQuery(StudentInformation sInfo) {
		StudentDatabase sData = new StudentDatabase();
		//store data into mySQL
		sData.insertQuery(sInfo.id, sInfo.name, sInfo.college, sInfo.email);
		//get result from SQL in Json format
		JSONObject jsonString = sData.getResult();
		LOG.debug("json string is : " + jsonString);
		ElsticSearch Esearch = new ElsticSearch();
		Esearch.createDocumet(jsonString);
	}
}
