package com.gopivotal.bookshop.buslogic;

import com.gemstone.gemfire.cache.Region;
import com.gemstone.gemfire.cache.client.ClientCache;
import com.gemstone.gemfire.cache.client.ClientCacheFactory;
import com.gopivotal.bookshop.domain.Address;
import com.gopivotal.bookshop.domain.Customer;


public class CustomerLoader
{
	private ClientCache cache;
	private Region <Integer, Customer> customers;

	public CustomerLoader() {}
	public CustomerLoader(ClientCache cache) {
		this.cache = cache;
		setupCustomerRegion();
	}
	
	public void closeCache()
	{
		cache.close();
	}
	
	public CustomerLoader getCache()
	{
		this.cache = new ClientCacheFactory()
        .set("name", "ClientWorker")
        .set("cache-xml-file", "xml/clientCache.xml")
        .create();
		setupCustomerRegion();

		return this;
	}
	
	private void setupCustomerRegion()
	{
		customers = cache.getRegion("Customer");
		System.out.println("Got the Customer Region: " + customers);
	}

	public CustomerLoader populateCustomers()
	{
		Customer cust1 = new Customer(5598, "Kari", "Powell", ""+44444);
		cust1.addOrder(17699);
		cust1.addOrder (18009);
		cust1.addOrder (18049);
		cust1.setPrimaryAddress(new Address("123 Main St.", null, null, "Topeka", "KS", "50505", "US", "423-555-3322", "HOME"));
		customers.put(5598, cust1);
		System.out.println("Inserted a customer: " + cust1);

		Customer cust2 = new Customer (5543, "Lula", "Wax", ""+12345);
		cust2.addOrder(17700);
		cust2.setPrimaryAddress(new Address("123 Main St.", null, null, "Topeka", "KS", "50505", "US", "423-555-3322", "HOME"));
		customers.put(5543, cust2);
		System.out.println("Inserted a customer: " + cust2);
		
		Customer cust3 = new Customer (6024, "Trenton", "Garcia", ""+88888);
		cust3.setPrimaryAddress(new Address("123 Main St.", null, null, "San Francisco", "CA", "50505", "US", "423-555-3322", "HOME"));
		customers.put(6024, cust3);
		System.out.println("Inserted a customer: " + cust3);

		return this;
	}

	public static void main(String[] args)
	{
		new CustomerLoader()
				.getCache()
				.populateCustomers()
				.closeCache();
	}

}
