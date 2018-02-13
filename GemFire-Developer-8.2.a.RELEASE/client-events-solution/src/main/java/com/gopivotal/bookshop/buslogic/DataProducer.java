package com.gopivotal.bookshop.buslogic;

import java.io.BufferedReader;
import java.io.Console;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;

import com.gemstone.gemfire.cache.Region;
import com.gemstone.gemfire.cache.client.ClientCache;
import com.gemstone.gemfire.cache.client.ClientCacheFactory;
import com.gopivotal.bookshop.domain.BookOrder;
import com.gopivotal.bookshop.domain.BookOrderItem;

public class DataProducer
{

private ClientCache cache;

	Region <Integer, BookOrder> orders;
	
	
	public static void main(String[] args) throws Exception
	{
		DataProducer producer = new DataProducer();
		producer.getCache();
		
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("Press enter to populate an order over $100");
        bufferedReader.readLine();
        producer.addAnotherOrder();

		
        System.out.println("Press enter to populate an order less than $100");
		bufferedReader.readLine();
		producer.addLowOrder();

		producer.closeCache();

	}
	

	
	public void closeCache()
	{
		cache.close();
	}
	
	public void getCache()
	{
	    this.cache = new ClientCacheFactory()
	      .set("cache-xml-file", "xml/clientWorkerCache.xml")
	      .create();
	    orders = cache.getRegion("BookOrder");
	}

	
	public void addLowOrder()
	{

			BookOrder ord1 = new BookOrder(18049, new Date(), (float)5.99, new Date(), new ArrayList(), 5543, (float)80.94);
			ord1.addOrderItem(new BookOrderItem (1, 123, (float)5, (float)100));
			orders.put(18049, ord1);
			System.out.println("Added: " + ord1);

		
	}
	
	public void addAnotherOrder()
	{

			BookOrder ord1 = new BookOrder(18009, new Date(), (float)5.99, new Date(), new ArrayList(), 5543, (float)180.94);
			ord1.addOrderItem(new BookOrderItem (1, 123, (float)5, (float)0));
			orders.put(18009, ord1);
			System.out.println("Added: " + ord1);
		
	}

}
