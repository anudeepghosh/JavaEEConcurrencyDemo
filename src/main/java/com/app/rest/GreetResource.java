package com.app.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

/**
 * @author Anudeep
 *
 */
@Path("greetUser")
public class GreetResource { //RESTful Bean
	
	@GET
	public String GreetUser() {
		System.out.println("RESTful API hit");
		return "Java EE Concurrency starts";
	}
}
