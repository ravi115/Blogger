package com.blog.services;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;

import com.blog.business.BlogBusiness;
import com.blog.dto.StudentInformation;
import com.blog.querybusiness.QueryBusiness;
import com.blog.request.RequestInformation;

@Path("/newsfeed")
public class BlogServices {

	private static final Logger LOG = Logger.getLogger(BlogServices.class);

	@GET
	@Produces("application/json")
	public Response returnMesg() {
		return Response.status(200).entity("Hello from rest Blog").build();
	}

	/**
	 * This method will insert data in MySQL as well as in Elastic Search too.
	 * @param sInfo
	 * @return
	 */
	@POST
	@Consumes("application/json")
	@Produces("application/json")
	public Response showInformation(StudentInformation sInfo) {
		LOG.debug("start for data insert in SQL - " + sInfo.id + "," + sInfo.name + "," + sInfo.college + ","
				+ sInfo.email);
		new BlogBusiness().insertQuery(sInfo);
		//StudentDatabase sData = new StudentDatabase(sInfo);
		return Response.status(200).entity(sInfo).build();
	}
	
	/**
	 * This method will return the query result for that requested id.
	 * @param sInfo
	 * @return
	 */
	@Path("/search")
	@POST
	@Consumes("application/json")
	@Produces("application/json")
	public Response getResult(RequestInformation reqId) {
		LOG.debug("query result for requested id : " + reqId.id + " starts");
		String result = new QueryBusiness().getQueryResult(reqId);
		return Response.status(200).entity(result).build();
	}
}
