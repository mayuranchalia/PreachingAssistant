package com.temple.service;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

@Path("/greetingMessage")
public class HelloService {

	@GET
	@Path("/hello")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getGreetingMsg(@QueryParam("name") String name) 
			throws JSONException{
		JSONObject response=new JSONObject();
		response.put("message", "Hello "+name+" welcome to " +
				"Temple Preaching Assistant");
		return Response.ok().entity(response.toString()).build();
		
	}
}
