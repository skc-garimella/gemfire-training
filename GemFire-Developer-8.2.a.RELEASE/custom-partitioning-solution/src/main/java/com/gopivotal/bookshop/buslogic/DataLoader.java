package com.gopivotal.bookshop.buslogic;

import java.util.ArrayList;
import java.util.Date;

import com.gemstone.gemfire.cache.Region;
import com.gemstone.gemfire.cache.client.ClientCache;
import com.gemstone.gemfire.cache.client.ClientCacheFactory;
import com.gopivotal.bookshop.domain.BookOrder;
import com.gopivotal.bookshop.domain.BookOrderItem;
import com.gopivotal.bookshop.domain.Customer;
import com.gopivotal.bookshop.domain.OrderKey;

public class DataLoader
{

	private ClientCache cache;
	
	/**
	 * @param args
	 */
	// TODO-06: Run this class to load Customer and BookOrder entries.
	public static void main(String[] args)
	{
		DataLoader loader = new DataLoader();
		loader.getCache();
		loader.populateCustomers();
		loader.populateBookOrders();
		loader.closeCache();
		

	}
	
	public void closeCache()
	{
		cache.close();
	}
	
	public void getCache()
	{
		this.cache = new ClientCacheFactory()
        .set("name", "DataLoader")
        .set("cache-xml-file", "xml/clientCache.xml")
        .create();
	}
	
	// TODO-05: Observe the values of the keys used for both Customer entries and BookOrder entries below.
	public void populateCustomers()
	{
		Region <Integer, Customer> customers = cache.getRegion("Customer");
		System.out.println("Got the Customer Region: " + customers);
		
		Customer cust1 = new Customer(5598, "Kari", "Powell", ""+44444);
		cust1.addOrder(17600);
		customers.put(cust1.getCustomerNumber(), cust1);
		System.out.println("Inserted a customer: " + cust1);
		
		Customer cust2 = new Customer (5542, "Lula", "Wax", ""+12345);
		customers.put(cust2.getCustomerNumber(), cust2);
		System.out.println("Inserted a customer: " + cust2);
		
		Customer cust3 = new Customer (6024, "Trenton", "Garcia", ""+88888);
		cust3.addOrder(17700);
		customers.put(cust3.getCustomerNumber(), cust3);
		System.out.println("Inserted a customer: " + cust3);	

	}
	public void populateBookOrders()
	{
		Region<OrderKey, BookOrder> orders = cache.getRegion("BookOrder");
		// Order for Kari Powell for book: A Treatise of Treatises
		OrderKey key1  = new OrderKey(5598, 17600);
		BookOrder order1 = new BookOrder(17699, new Date(), (float)5.99, new Date(), new ArrayList(), 5598, (float)40.98);
		order1.addOrderItem(new BookOrderItem (1, 123, (float)1, (float)0));
		orders.put(key1, order1);
		
		// Order for Lula Wax   book: A Treatise of Treatises & Clifford the Big Red Dog
		OrderKey key2  = new OrderKey(6024, 17700);
		BookOrder order2 = new BookOrder(17700, new Date(), (float)5.99, new Date(), new ArrayList(), 5543, (float)52.97);
		order2.addOrderItem(new BookOrderItem (1, 123, (float)1, (float)0));
		order2.addOrderItem(new BookOrderItem(2,456, (float)1,(float)0));
		orders.put(key2, order2);
	}
	
}
