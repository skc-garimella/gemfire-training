package com.gopivotal.bookshop.buslogic;

import com.gemstone.gemfire.cache.Region;
import com.gemstone.gemfire.cache.client.ClientCache;
import com.gemstone.gemfire.cache.client.ClientCacheFactory;
import com.gemstone.gemfire.cache.execute.Execution;
import com.gemstone.gemfire.cache.execute.FunctionService;
import com.gemstone.gemfire.cache.execute.ResultCollector;
import com.gopivotal.bookshop.domain.BookMaster;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;

// TODO-09: Run these tests to verify correct implementation (be sure to start server & locators using server-bootstrap starServers script)
public class SummingTests {

	private Region<Integer, BookMaster> books;
	private ClientCache cache;

	@Before
	public void setup() {
		cache = new ClientCacheFactory()
				.set("name", "ClientWorker")
				.set("cache-xml-file", "xml/clientCache.xml").create();
		books = cache.getRegion("BookOrder");
	}
	@After
	public void teardown() {
		System.out.println("********************************************");
		System.out.println("Closing the cache and disconnecting.");
		cache.close();
	}

	@Test
	public void shouldComputeTotalForAllOrders() throws Exception {
		// TODO-07: execute the function using the totalPrice field on the BookOrder object
		Execution execution = FunctionService.onRegion(books).withArgs("totalPrice")
				.withCollector(new SummingResultCollector());

		ResultCollector rc = execution.execute("com.gopivotal.bookshop.buslogic.GenericSumFunction");

		// TODO-08: Get result and assert that the two orders total $93.95
		BigDecimal result = (BigDecimal) rc.getResult();
		assertEquals(new BigDecimal("93.95"), result);
	}

}
