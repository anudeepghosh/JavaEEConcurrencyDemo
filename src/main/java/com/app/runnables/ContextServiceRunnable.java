package com.app.runnables;

import java.security.AccessController;

import javax.security.auth.Subject;

/**
 * @author Anudeep
 *
 */
public class ContextServiceRunnable implements Runnable {

	@Override
	public void run() {
		System.out.println("Thread : "+Thread.currentThread().getName());
		Subject subject = Subject.getSubject(AccessController.getContext());
		System.out.println("Security Information from a normal thread: "+subject);
	}
}
