package com.gopivotal.bookshop.tests;

import static org.junit.Assert.*;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;

import com.gemstone.gemfire.cache.Region;
import com.gemstone.gemfire.cache.client.ClientCache;
import com.gemstone.gemfire.cache.client.ClientCacheFactory;
import com.gemstone.gemfire.cache.execute.Execution;
import com.gemstone.gemfire.cache.execute.FunctionService;
import com.gemstone.gemfire.cache.execute.ResultCollector;
import com.gopivotal.bookshop.buslogic.SummingResultCollector;
import com.gopivotal.bookshop.domain.BookMaster;

public class SummingFunctionTest {
	private ClientCache cache;
	private Region<Integer, BookMaster> books;
	
	@Before
	public void setup() {
		cache = new ClientCacheFactory()
		.set("name", "ClientWorker")
		.set("cache-xml-file", "xml/clientCache.xml").create();
		books = cache.getRegion("BookOrder");		
	}
	
	@Test
	// TODO-11: Run the test verifying the function performs as expected
	public void testSummingFunction() {
		Execution execution = FunctionService.onRegion(books).withArgs("totalPrice")
				.withCollector(new SummingResultCollector());

		ResultCollector rc = execution.execute("com.gopivotal.bookshop.buslogic.GenericSumFunction");

		BigDecimal result = (BigDecimal) rc.getResult();
		assertEquals(new BigDecimal("93.95"), result); // 40.98 + 52.97
	}
}
