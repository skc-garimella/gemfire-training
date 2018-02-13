package com.gopivotal.bookshop.buslogic;

import com.gemstone.gemfire.cache.Region;
import com.gemstone.gemfire.cache.client.ClientCache;
import com.gemstone.gemfire.cache.client.ClientCacheFactory;
import com.gopivotal.bookshop.domain.BookMaster;
import com.gopivotal.bookshop.domain.Customer;

public class BookMasterDao
{
		// The region object that stores BookMaster objects
		private Region <Integer, BookMaster> books;

		public BookMasterDao(ClientCache cache) {
			this.books = cache.getRegion("BookMaster");
		}
		
		public void insertBook(Integer key, BookMaster book)
		{
			// TODO-11: Write code to insert book with the given key. Use the method that assumes
            //          the entry doesn't already exist
			books.create(key, book);
		}
		
		public BookMaster getBook(Integer key)
		{
			// TODO-12: Write code to get & return a book for the specified key
			return books.get(key);
		}
		
	    public void updateBook(Integer key, BookMaster book)
	    {
	    	// TODO-13: Write code to update book for specified key
	    	books.put(key, book);
	    }

	    public void removeBook(Integer key) 
	    {
	        // TODO-14: Implement delete functionality for specified key
	    	books.remove(key);
	    }
		

}
