package com.gopivotal.bookshop.buslogic;

import com.gemstone.gemfire.cache.Region;
import com.gemstone.gemfire.cache.client.ClientCache;
import com.gemstone.gemfire.cache.client.ClientCacheFactory;
import com.gopivotal.bookshop.domain.Customer;

public class NewCustomerClient
{

		private ClientCache cache;
		private Region <Integer, Customer> customers;

		/**
		 * @param args
		 */
		public static void main(String[] args)
		{
			NewCustomerClient loader = new NewCustomerClient();
			loader.getCache();
			loader.getRegions();
			loader.testCustomer();
			loader.testGet();

		}
		
		public void setCache (ClientCache cache)
		{
			this.cache = cache;
		}
		
		public void closeCache()
		{
			cache.close();
		}
		
		public void getCache()
		{

			this.cache = new ClientCacheFactory()
	        .set("name", "ClientWorker")
	        .set("cache-xml-file", "xml/clientCache.xml")
	        .create();
		}
		
		public void getRegions()
		{
			customers = cache.getRegion("Customer");
			System.out.println("Got the Customer Region: " + customers);
			
		}

		private void testCustomer() {
			// TODO-08: Add the code to create a new customer, including setting the new field, telephoneNumber. Insert it into
			//          the cache with key 9999.
				
		}
		
		private void testGet() {
			// TODO-09: Add code to fetch the Customer entry for key 9999 and print it out.
		}


}
