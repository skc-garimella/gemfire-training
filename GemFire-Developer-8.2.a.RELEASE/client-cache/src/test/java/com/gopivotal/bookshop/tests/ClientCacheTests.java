package com.gopivotal.bookshop.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.gemstone.gemfire.cache.Region;
import com.gemstone.gemfire.cache.client.ClientCache;
import com.gemstone.gemfire.cache.client.ClientCacheFactory;
import com.gopivotal.bookshop.domain.BookMaster;
import com.gopivotal.bookshop.domain.Customer;

// TODO-06: Run the test suite
public class ClientCacheTests {

	private ClientCache cache;
	private Region <Integer, BookMaster> books;
	private Region <Integer, Customer> customers;

	@Before
	public void setUp() throws Exception {
		// TODO-04: Write the getCache method to initialize the client cache with the clientCache.xml file

		
		// TODO-05: Write the getRegions() method to get the BookMaster and Customer regions 
		// and assign to instance variables

		
		assertNotNull("Failed to fetch BookMaster region", books);
		assertNotNull("Failed to fetch Customer region", customers);
	}

	@Test
	public void testFetchFromProxyRegions() {
		Customer customer = customers.get(5598);
		assertNotNull("Customer shouldn't be null", customer);
		assertEquals("Failed to fetch the correct customer object", "Kari", customer.getFirstName());

		BookMaster book = books.get(456);
		assertNotNull("Book shouldn't be null", book);
		assertEquals("Failed to fetch the correct book", "Clifford the Big Red Dog", book.getTitle());
	}
	
	@Test @Ignore
	// TODO-08: Remove @Ignore annotation, re-run tests and this one should pass as well
	// TODO-09: Re-run the tests and verify that both pass
	public void testLocalRegion() {
		Region<Integer, String> localRegion = cache.getRegion("LocalRegion");
		assertNotNull ("Region should not be null", localRegion);
	}

}
