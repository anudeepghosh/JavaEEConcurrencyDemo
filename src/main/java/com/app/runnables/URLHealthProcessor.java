package com.app.runnables;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Anudeep
 *
 */
public class URLHealthProcessor implements Runnable {

	private static final String URL = "http://www.google.com";
	
	@Override
	public void run() {
		System.out.println("URL checker thread name: "+Thread.currentThread().getName());
		String statusOfApp = "";		
		try {
			URL url = new URL(URL);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
			connection.connect();
			int responseCode = connection.getResponseCode();
			if (responseCode == 200) {
				statusOfApp = "Working!";
			} else {
				statusOfApp = "Sorry! Something went wrong...";
			}	
		} catch (MalformedURLException e) {
			Logger.getLogger(URLHealthProcessor.class.getName()).log(Level.SEVERE, null, e);
		} catch (IOException e) {
			Logger.getLogger(URLHealthProcessor.class.getName()).log(Level.SEVERE, null, e);
		}
		System.out.println("Status of the App URL :"+statusOfApp);
	}
}
