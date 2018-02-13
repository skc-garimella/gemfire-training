package com.gopivotal.bookshop.buslogic;

import com.gemstone.gemfire.cache.Region;
import com.gemstone.gemfire.cache.client.ClientCache;
import com.gemstone.gemfire.cache.client.ClientCacheFactory;
import com.gemstone.gemfire.pdx.PdxInstance;
import com.gopivotal.bookshop.domain.Customer;

public class PdxInstanceClient
{

		private ClientCache cache;
		private Region <Integer, Customer> customers;

		/**
		 * @param args
		 */
		public static void main(String[] args)
		{
			PdxInstanceClient loader = new PdxInstanceClient();
			loader.getCache();
			loader.getRegions();
			loader.testPdxGet();

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
		
		private void testPdxGet() {
			// TODO-10: Add code to fetch the PdxInstance for key 9999 and extract just the telephoneNumber and print it out.
			Object customerEntry = customers.get(9999);
			if (customerEntry instanceof PdxInstance) {
				PdxInstance pdxCustomer = (PdxInstance) customerEntry;
				String telephoneNumber = (String) pdxCustomer.getField("telephoneNumber");
				System.out.println("The customer telephone number is: " + telephoneNumber);
			} else {
				System.out.println("The entry is not of type PdxInstance");
			}
		}


}
