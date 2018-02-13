package com.gopivotal.bookshop.tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.gemstone.gemfire.cache.client.ClientCache;
import com.gemstone.gemfire.cache.client.ClientCacheFactory;
import com.gemstone.gemfire.cache.query.SelectResults;
import com.gemstone.gemfire.cache.query.Struct;
import com.gopivotal.bookshop.buslogic.OQLInquirer;
import com.gopivotal.bookshop.domain.BookOrder;
import com.gopivotal.bookshop.domain.Customer;

public class OqlInquirerTests {
	private ClientCache cache;

	@Before
	public void setUp() throws Exception {
		this.cache = new ClientCacheFactory()
        .set("name", "DataOperations Client")
        .set("cache-xml-file", "xml/clientCache.xml")
        .create();
	}

	// TODO-03: Run the tests and this (and only this) test should pass. The other two will initially fail.
	@Test
	public void testBasicQuery() {
		OQLInquirer queryObject = new OQLInquirer(cache);
		SelectResults<Customer> results = queryObject.doCustomerQuery();
		assertEquals("Failed basic query: ", 3, results.size());
		// Can't guarantee order but can guarantee that there is an entry with a customer first name of 'Lula'
		boolean found = false;
		for (Customer item : results) {
			if ("Lula".equals(item.getFirstName())) {
				found = true;
				break;
			}
		}
		if (! found) 
			fail("No entry found for Customer First Name = 'Lula'");
	}

	// TODO-05: Run the tests again after implementing doStructQuery() and now this and the testBasicQuery() test should pass.
	@Test 
	public void testStructQuery() {
		OQLInquirer queryObject = new OQLInquirer(cache);
		SelectResults<Struct> results = queryObject.doStructQuery();
		assertEquals("Failed struct query: ", 3, results.size());
		// Can't guarantee order but can guarantee that there is an entry with a customer first name of 'Lula'
		boolean found = false;
		for (Struct item : results) {
			if ("Lula".equals(item.get("firstName"))) {
				found = true;
				break;
			}	
		}
		if (! found) 
			fail("No entry found for Customer First Name = 'Lula'");
	}
	
	// TODO-07: Run the tests again after implementing doJoin() and now all three tests should pass. 
	@Test
	public void testJoinQuery() {
		OQLInquirer queryObject = new OQLInquirer(cache);
		SelectResults<Customer> results = queryObject.doJoin();
		// Assert that only one customer item is returned
		assertEquals("Join query should return only one Customer",1, results.size());
		// Assert that the customer returned has last name = 'Wax'
		assertEquals("Join query should have returned customer with last name 'Wax'","Wax",results.asList().get(0).getLastName());

	}
}
