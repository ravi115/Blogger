package com.blog.services;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import com.blog.dto.StudentInformation;
import com.blog.sqldatabase.StudentDatabase;

@Path("/newsfeed")
public class BlogServices {

	@GET
	@Produces("application/json")
	public Response returnMesg() {
		return Response.status(200).entity("Hello from rest Blog").build();
	}
	
	@POST
	@Consumes("application/json")
	@Produces("application/json")
	public Response showInformation(StudentInformation sInfo) {
		
		StudentDatabase sData = new StudentDatabase(sInfo);
		return Response.status(200).entity(sInfo).build();
	}
}
