package com.gopivotal.bookshop.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.gemstone.gemfire.cache.EntryExistsException;
import com.gemstone.gemfire.cache.Region;
import com.gemstone.gemfire.cache.client.ClientCache;
import com.gemstone.gemfire.cache.client.ClientCacheFactory;
import com.gopivotal.bookshop.buslogic.BookMasterDao;
import com.gopivotal.bookshop.domain.BookMaster;

// TODO-10: Open this file and inspect the various test methods.
// TODO-15: When you have completed implementing the BookMasterDao functionality, run this JUnit test to validate correct implementation
public class DataOperationsTests {
	private ClientCache cache;
	private static Integer key = 12345;

	/**
	 * Make sure this is run once 
	 * 
	 * @throws Exception
	 */
	@Before
	public void setUp() throws Exception {
		this.cache = new ClientCacheFactory()
        .set("name", "DataOperations Client")
        .set("cache-xml-file", "xml/clientCache.xml")
        .create();
	}

	@Test
	public void testInsertBook() {
		BookMasterDao dao = new BookMasterDao(cache);
		BookMaster book = new BookMaster(key, "New test book", (float) 28.77, 2014, "Some Author", "All About GemFire");
		dao.insertBook(key, book);
		
		verify(key, book);
		
	}
	
	@Test
	public void testGetBook() {
		BookMasterDao dao = new BookMasterDao(cache);
		BookMaster book = dao.getBook(key);
		verify(key,book);
	}
	
	@Test
	public void testUpdateBook() {
		BookMasterDao dao = new BookMasterDao(cache);
		BookMaster book = dao.getBook(key);
		assertNotNull(book);
		book.setTitle("A new title");
		dao.updateBook(key, book);
		verify(key,book);
	}
	
	@Test
	public void testDeleteBook() {
		BookMasterDao dao = new BookMasterDao(cache);
		dao.removeBook(key);
		assertNull("BookMaster entry should have been removed for key: " + key, dao.getBook(key));
	}
		
	@Test(expected=EntryExistsException.class)
	public void testCantInsertSameKey() {
		BookMasterDao dao = new BookMasterDao(cache);
		BookMaster book = new BookMaster(key, "New test book", (float) 28.77, 2014, "Some Author", "All About GemFire");
		dao.insertBook(key, book);
		BookMaster book2 = new BookMaster(key, "New test book2", (float) 33.77, 2014, "Some Author2", "All About GemFire2");
		dao.insertBook(key, book2);		
		
	}
	

	private void verify(Integer key, BookMaster book) {
		Region<Integer, BookMaster> books = cache.getRegion("BookMaster");

		BookMaster storedBook = books.get(key);
		assertNotNull("Failed to fetch book for key: " + key, storedBook);
		assertEquals("Stored book does not match original book", book, storedBook);
	}

}
