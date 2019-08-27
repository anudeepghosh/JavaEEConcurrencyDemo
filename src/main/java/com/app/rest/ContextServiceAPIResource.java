package com.app.rest;

import javax.annotation.Resource;
import javax.enterprise.concurrent.ContextService;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

import com.app.runnables.ContextServiceRunnable;

/**
 * @author Anudeep
 *
 */
@Path("contextService")
public class ContextServiceAPIResource {

	@Resource(lookup="java:comp/DefaultContextService")
	private ContextService contextService;
	
	@GET
	public String accessSecurityInfo() {
		ContextServiceRunnable contextServiceRunnable = new ContextServiceRunnable();
		Thread thread = new Thread(contextServiceRunnable);
		//No guarantee for availability of full context
		
		/*Runnable proxy = contextService.createContextualProxy(contextServiceRunnable, Runnable.class);
		Thread thread = new Thread(proxy);*/
		thread.start();
		
		return "Context capturing service running";
	}
	
}
