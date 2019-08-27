package com.app.rest;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;
import javax.enterprise.concurrent.ManagedThreadFactory;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

import com.app.runnables.LoggingProcessor;

/**
 * @author Anudeep
 *
 */
@Path("log")
public class LoggingResource {

	@Resource(lookup="java:comp/DefaultManagedThreadFactory")
	private ManagedThreadFactory threadFactory;
	
	@GET
	public String logData() {
		Thread thread = threadFactory.newThread(new LoggingProcessor());
		thread.setName("Logging Thread");
		thread.start();
		
		ExecutorService executorService = getApplicationPool();
		for (int i = 0; i < 7; i++) {
			executorService.submit(new LoggingProcessor());			
		}
		
		return "ManagedThreadFactory thread logging...";
	}
	
	public ExecutorService getApplicationPool() {
		ExecutorService service = new ThreadPoolExecutor(3, 10, 5, TimeUnit.SECONDS, new ArrayBlockingQueue(2), threadFactory);
		return service;
	}
}
