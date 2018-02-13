package com.gopivotal.bookshop.buslogic;

import com.gemstone.gemfire.cache.Region;
import com.gemstone.gemfire.cache.client.ClientCache;
import com.gemstone.gemfire.cache.client.ClientCacheFactory;
import com.gopivotal.bookshop.domain.BookMaster;

public class BookLoader
{
		private ClientCache cache;
		private Region <Integer, BookMaster> books;

	  public BookLoader() { }
		public BookLoader(ClientCache cache) {
			this.cache = cache;
			setupBookMasterRegion();
		}

	  private void setupBookMasterRegion() {
			books = cache.getRegion("BookMaster");
			System.out.println("Got the BookMaster Region: " + books);
		}

		public void closeCache()
		{
			cache.close();
		}
		
		public BookLoader getCache()
		{
			this.cache = new ClientCacheFactory()
	        .set("name", "ClientWorker")
	        .set("cache-xml-file", "xml/clientCache.xml")
	        .create();
			setupBookMasterRegion();
			return this;
		}

		public BookLoader populateBooks()
		{
			BookMaster book = new BookMaster(123, "Run on sentences and drivel on all things mundane",
					(float) 34.99, 2011, "Daisy Mae West", "A Treatise of Treatises");
			books.put(123, book);
			System.out.println("Inserted a book: " + book);
			BookMaster book2 = new BookMaster(456, "A book about a dog",
					(float) 11.99, 1971, "Clarence Meeks", "Clifford the Big Red Dog");
			books.put(456, book2);
			System.out.println("Inserted a book: " + book2);
			BookMaster book3 = new BookMaster(789, "Theoretical information about the structure of Operating Systems",
					(float) 59.99, 2011, "Jim Heavisides", "Operating Systems: An Introduction");
			books.put(789, book3);
			System.out.println("Inserted a book: " + book3);

			return this;
		}

	public static void main(String[] args)
	{
		new BookLoader()
				.getCache()
				.populateBooks()
				.closeCache();
	}

}
