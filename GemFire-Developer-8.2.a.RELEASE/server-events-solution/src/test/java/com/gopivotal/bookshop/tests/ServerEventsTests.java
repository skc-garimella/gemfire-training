package com.gopivotal.bookshop.tests;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.gemstone.gemfire.cache.CacheWriterException;
import com.gemstone.gemfire.cache.Region;
import com.gemstone.gemfire.cache.client.ClientCache;
import com.gemstone.gemfire.cache.client.ClientCacheFactory;
import com.gopivotal.bookshop.domain.BookMaster;

public class ServerEventsTests {
	ClientCache cache;
	Region<String,BookMaster> region;
	
	@Before
	public void initCache() {
	  cache = new ClientCacheFactory()
                        .set("name", "ClientWorker")
                        .set("cache-xml-file", "xml/clientCache.xml")
                        .create();

	  region= cache.getRegion("BookMaster");
	  // Ensures that no entries exist before test runs
	  region.clear();
	}
	
	@After
	 public void closeCache() {
		cache.close();
	}
	
	@Test
	/**
	 * Asserts that when a key ('Key00') is requested that a new one is created by the cache loader.
	 * First asserts taht there is no entry with Key00, then performs a get operation, which should
	 * trigger the CacheLoader behavior. Then, asserts that a non-null value was returned AND that
	 * there is now a key = 'Key00' on the server AND the author and item number are as expected. 
	 * Note: These last two tests are probably unnecessary but were at least interesting to validate.
	 * 
	 */
	public void testCacheLoader() {
		String key="Key00";
		assertFalse("Region is not properly initialized",region.containsKeyOnServer(key));
		BookMaster newBook = region.get(key);
		assertNotNull("BookMasterCacheLoader failed to create book for Key00",newBook);
		assertTrue("Key " + key + " not found on serve",region.containsKeyOnServer(key));
		assertEquals("BookMasterCacheLoader created the incorrect book","Daisy Mae West",newBook.getAuthor());
		assertEquals("BookMasterCacheLoader created the incorrect book",123,newBook.getItemNumber());
	}
	
	@Test
	/**
	 * Asserts that when inserting a valid entry that no errors occur
	 */
	public void testValidatingCacheWriterSuccess() {
		region.put("Key01", new BookMaster(124,"A spy fiction thriller about a retrograde amnesiac who must discover who he is ",(float) 34.99,2011,"Robert Ludlum","Bourne Identity"));
	}
	
	@Test()
	/**
	 * Asserts that when attempting to insert two entries with the same itemNumber, the second one fails with an expected
	 * CacheWriterException.
	 */
	public void testValidatingCacheWriterFailure() {
		region.put("Key01", new BookMaster(124,"A spy fiction thriller about a retrograde amnesiac who must discover who he is ",(float) 34.99,2011,"Robert Ludlum","Bourne Identity"));
		try {
			region.put("Key02", new BookMaster(124,"A spy fiction thriller about a retrograde amnesiac who must discover who he is ",(float) 34.99,2011,"Robert Ludlum","Bourne Identity"));
			fail ("Failed to throw an exception");
		} catch (Exception e) {
			assert e.getCause() instanceof CacheWriterException;
		}
	}
}
