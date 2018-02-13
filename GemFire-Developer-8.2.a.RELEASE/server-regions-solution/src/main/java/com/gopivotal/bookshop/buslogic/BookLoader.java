package com.gopivotal.bookshop.buslogic;

import com.gemstone.gemfire.cache.Region;
import com.gemstone.gemfire.cache.client.ClientCache;
import com.gemstone.gemfire.cache.client.ClientCacheFactory;
import com.gopivotal.bookshop.domain.BookMaster;

public class BookLoader
{

		private ClientCache cache;
		private Region <Integer, BookMaster> books;
		/**
		 * @param args
		 */
		public static void main(String[] args)
		{
			BookLoader loader = new BookLoader();
			loader.initializeCache();
			loader.initializeBookMasterRegion();
			loader.populateBooks();
			loader.closeCache();
			

		}
		
		public void setCache (ClientCache cache)
		{
			this.cache = cache;
		}
		
		public void closeCache()
		{
			cache.close();
		}
		
		public void initializeCache()
		{
			this.cache = new ClientCacheFactory()
	        .set("name", "ClientWorker")
	        .set("cache-xml-file", "xml/clientCache.xml")
	        .create();
		}
		
		public void initializeBookMasterRegion()
		{
			books = cache.getRegion("BookMaster");
			System.out.println("Got the BookMaster Region: " + books);
		}

		
		public void populateBooks()
		{
			BookMaster book = new BookMaster(123, "Run on sentences and drivel on all things mundane", (float) 34.99, 2011, "Daisy Mae West", "A Treatise of Treatises");
			books.put(123, book);
			System.out.println("Inserted a book: " + book);
			BookMaster book2 = new BookMaster(456, "A book about a dog", (float) 11.99, 1971, "Clarence Meeks", "Clifford the Big Red Dog");
			books.put(456, book2);
			System.out.println("Inserted a book: " + book2);
			BookMaster book3 = new BookMaster(789, "Theoretical information about the structure of Operating Systems", (float)59.99, 2011, "Jim Heavisides", "Operating Systems: An Introduction");
			books.put(789, book3);
			System.out.println("Inserted a book: " + book3);

			

		}
		
		

}
