package com.gopivotal.training.prb;

import com.gemstone.gemfire.cache.Region;
import com.gemstone.gemfire.cache.client.ClientCache;
import com.gemstone.gemfire.cache.client.ClientCacheFactory;
import com.gemstone.gemfire.cache.execute.Execution;
import com.gemstone.gemfire.cache.execute.FunctionService;
import com.gemstone.gemfire.cache.execute.ResultCollector;
import com.gopivotal.bookshop.domain.BookOrder;
import com.gopivotal.bookshop.domain.Customer;
import com.gopivotal.bookshop.domain.OrderKey;

// TODO-07: Run this function executor to obtain and print out bucket assignment info for the Customer and BookOrder regions
public class PRBFunctionExecutor {
	private ClientCache cache;
	private Region <Integer, Customer> customers;
	private Region <OrderKey, BookOrder> bookOrders;

	public static void main(String[] args) {
		PRBFunctionExecutor executor = new PRBFunctionExecutor();
		executor.getCache();
		executor.getRegions();
		executor.printBuckets();
		executor.closeCache();
	}
	
	public void closeCache()
	{
		cache.close();
	}
	
	public void getCache()
	{
		this.cache = new ClientCacheFactory()
        .set("name", "ClientWorker")
        .set("cache-xml-file", "xml/clientCache.xml")
        .create();
	}
	
	public void getRegions()
	{
		customers = cache.getRegion("Customer");
		System.out.println("Got the Customer Region: " + customers);
		bookOrders = cache.getRegion("BookOrder");
		System.out.println("Got the BookOrder Region: " + bookOrders);
	}

	public void printBuckets() {
		System.out.println("\nCustomer buckets");
		executePRB(this.customers);
		System.out.println("\nBookOrder buckets");
		executePRB(this.bookOrders);
	}
	public void executePRB(Region r) { 
		Execution execution = FunctionService.onRegion(r).withCollector(new PRBResultCollector());
		ResultCollector collector = execution.execute("PRBFunction"); 
		String result = (String) collector.getResult(); 
		System.out.println(result);
	}

}
