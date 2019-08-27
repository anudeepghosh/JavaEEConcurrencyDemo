package com.app.runnables;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Anudeep
 *
 */
public class LoggingProcessor implements Runnable {
	
	@Override
	public void run() {
		System.out.println("Thread :"+Thread.currentThread().getName());
		Logger.getLogger(LoggingProcessor.class.getName()).log(Level.INFO, "Logging Data for LoggerResource");;
	}
}
