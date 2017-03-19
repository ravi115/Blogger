package com.blog.test.service;

import javax.ws.rs.core.Response;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Test;

import com.blog.request.RequestInformation;
import com.blog.services.BlogServices;

import junit.framework.Assert;

public class TestBlogServices {

	@Test
	public void testgetResult() {
		RequestInformation reqId = new RequestInformation();
		reqId.id = 1;
		Response result = new BlogServices().getResult(reqId);
		JSONObject json = new JSONObject(result.getEntity().toString());
		JSONArray jsonArray = json.getJSONObject("hits").getJSONArray("hits");
		
		for (int i = 0; i < jsonArray.length(); i++ ) {
			JSONObject rec = jsonArray.getJSONObject(i).getJSONObject("_source").getJSONObject("map");
			String name = rec.getString("name");
			String college = rec.getString("college");
			String email = rec.getString("emailId");
			String id  = rec.getString("id");
			
			Assert.assertEquals(name, "ravi ranjan");
			Assert.assertEquals(college, "RNSIT");
			Assert.assertEquals(email, "ravi.ranjan@gmail.com");
			Assert.assertEquals(id, "1");
		}
	}
	
}
