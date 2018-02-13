package com.gopivotal.bookshop.buslogic;

import java.math.BigDecimal;

import com.gemstone.gemfire.cache.Region;
import com.gemstone.gemfire.cache.client.ClientCache;
import com.gemstone.gemfire.cache.client.ClientCacheFactory;
import com.gemstone.gemfire.cache.execute.Execution;
import com.gemstone.gemfire.cache.execute.FunctionService;
import com.gemstone.gemfire.cache.execute.ResultCollector;
import com.gopivotal.bookshop.domain.Customer;

public class SummingClient {
	public static final String REGION_NAME = "BookOrder";

	public static void main(String[] args) throws Exception {
		new SummingClient().run();
	}

	public void run() throws Exception {

		ClientCache cache = new ClientCacheFactory()
				.set("name", "ClientWorker")
				.set("cache-xml-file", "xml/clientCache.xml").create();

		Region<Integer, Customer> customer = cache.getRegion(REGION_NAME);

		Execution execution = FunctionService.onRegion(customer).withArgs("totalAmount")
				.withCollector(new SummingResultCollector());

		ResultCollector rc = execution.execute("com.gopivotal.bookshop.buslogic.GenericSumFunction");

		BigDecimal result = (BigDecimal) rc.getResult();
		
		
		System.out.println("********************************************");
		System.out.println("Closing the cache and disconnecting.");
		cache.close();
	}

}
