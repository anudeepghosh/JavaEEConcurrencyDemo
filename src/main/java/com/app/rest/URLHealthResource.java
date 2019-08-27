package com.app.rest;

import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;
import javax.enterprise.concurrent.ManagedScheduledExecutorService;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

import com.app.runnables.URLHealthProcessor;

/**
 * @author Anudeep
 *
 */
@Path("checkUrl")
public class URLHealthResource {
	
	@Resource(lookup="java:comp/DefaultManagedScheduledExecutorService")
	private ManagedScheduledExecutorService scheduledExecutorService;
	
	public URLHealthResource() {
	}
	
	@GET
	public String checkApplicationHealth() {
		
		String message = "";
		scheduledExecutorService.schedule(new URLHealthProcessor(), 5, TimeUnit.SECONDS);
		
		message="Health check initiated";
		return message;
	}
	
	
	
	
	
}
