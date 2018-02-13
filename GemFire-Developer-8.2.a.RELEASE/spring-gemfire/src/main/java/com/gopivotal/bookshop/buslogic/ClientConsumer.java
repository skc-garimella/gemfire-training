package com.gopivotal.bookshop.buslogic;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.springframework.context.support.ClassPathXmlApplicationContext;

// TODO-17: Open the ClientConsumer class and examine the code before executing. When ready, go ahead and run this program.
//          After initializing the Spring ApplicationContext, the application just basically waits for the ClientWorker to run.
//          Note that you will need to end this program once the ClientWorker is finished. You can do this by putting the cursor
//          in the console and hitting enter to continue to the end.
public class ClientConsumer {
	public static void main(String[] args) throws Exception {
		// Initialize the application context, which defines the BookMaster region and registers interest
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("META-INF/spring/gemfire/spring-config.xml");
		
		/*
		 * This code is simply output to the console to tell the user what's going on. This is especially important as 
		 * work will be coordinated between this class and the ClientWorker class running at the same time.
		 */
		System.out.println("The data region has a listener that reports all changes to standard out.");
		System.out.println();
		System.out.println("Please run the worker client in another session. It will update the");
		System.out.println("cache and the server will forward the updates to me. Note the listener");
		System.out.println("output in this session.");
		System.out.println();
		System.out.println("When the other client finishes, hit Enter to exit this program.");

		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
		bufferedReader.readLine();

		System.out.println("Closing the cache and disconnecting.");
	}

}
