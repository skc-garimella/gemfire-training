package com.gopivotal.training.prb;

import com.gemstone.gemfire.cache.Region;
import com.gemstone.gemfire.cache.client.ClientCache;
import com.gemstone.gemfire.cache.client.ClientCacheFactory;
import com.gemstone.gemfire.cache.execute.Execution;
import com.gemstone.gemfire.cache.execute.FunctionService;
import com.gemstone.gemfire.cache.execute.ResultCollector;
import com.gopivotal.bookshop.domain.Customer;

public class PRBFunctionExecutor {
	private ClientCache cache;
	private Region <Integer, Customer> customers;

	public static void main(String[] args) {
		PRBFunctionExecutor executor = new PRBFunctionExecutor();
		executor.getCache();
		executor.getCustomerRegion();
		executor.executePRB();
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
	
	public void getCustomerRegion()
	{
		customers = cache.getRegion("Customer");
		System.out.println("Got the Customer Region: " + customers);
	}

	public void executePRB() { 
		Execution execution = FunctionService.onRegion(this.customers).withCollector(new PRBResultCollector());
		ResultCollector collector = execution.execute("PRBFunction"); 
		String result = (String) collector.getResult(); System.out.println(result); 
		System.out.println(result);
	}

}
