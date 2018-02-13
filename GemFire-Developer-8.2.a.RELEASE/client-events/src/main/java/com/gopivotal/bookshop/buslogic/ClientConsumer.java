package com.gopivotal.bookshop.buslogic;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import com.gemstone.gemfire.cache.client.ClientCacheFactory;
import com.gemstone.gemfire.cache.client.ClientCache;
import com.gemstone.gemfire.cache.Region;

public class ClientConsumer {

	public static final String REGION_NAME = "BookMaster";

	public static void main(String[] args) throws Exception {

		System.out.println("Connecting to the distributed system and creating the cache.");
		// Create the cache which causes the cache-xml-file to be parsed
		ClientCache cache = new ClientCacheFactory()
                  .set("name", "ClientConsumer")
                  .set("cache-xml-file", "xml/clientConsumerCache.xml")
                  .create();

		// Get the BookMaster region 
		Region<Integer,?> region = cache.getRegion(REGION_NAME);
		System.out.println("BookMaster region \"" + region.getFullPath() + "\" created in cache. ");

		System.out.println("Asking the server to send me events for data with the keys: 999, which will be inserted by the ClientWorker");
		
		// TODO-05: Using the Region API, register interest in the entry who's key is 999 (this will be inserted by the ClientWorker)
 
		
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
		cache.close();
	}
}