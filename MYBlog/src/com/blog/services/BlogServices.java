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

@Path("/newsfeed")
public class BlogServices {

	private static final Logger LOG = Logger.getLogger(BlogServices.class);

	@GET
	@Produces("application/json")
	public Response returnMesg() {
		return Response.status(200).entity("Hello from rest Blog").build();
	}

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
}
