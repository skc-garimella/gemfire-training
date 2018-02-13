package com.gopivotal.bookshop.buslogic;

import java.util.ArrayList;
import java.util.Date;

import com.gemstone.gemfire.cache.Region;
import com.gemstone.gemfire.cache.client.ClientCache;
import com.gemstone.gemfire.cache.client.ClientCacheFactory;
import com.gopivotal.bookshop.domain.BookOrder;
import com.gopivotal.bookshop.domain.BookOrderItem;
import com.gopivotal.bookshop.domain.InventoryItem;

/*
 * This is a fallback class that was used early on to bootstrap the BookOrder data. It presumed you had a locator and server(s) started.
 * In general though, the preferred approach is to run the server-bootstrap start script to start the server processes. That way, this works
 * as a stand alone when a client-only course is defined.
 * 
 */
public class OrderLoader
{
	
	private ClientCache cache;
	
	public static void main(String[] args)
	{
		OrderLoader loader = new OrderLoader();
		loader.getCache();
		loader.populateBookOrders();
		loader.closeCache();
	}
	
	public void setCache (ClientCache cache)
	{
		this.cache = cache;
	}
	

	public void populateBookOrders()
	{
		Region<Integer, BookOrder> orders = cache.getRegion("BookOrder");
		// Order for Kari Powell for book: A Treatise of Treatises
		BookOrder order1 = new BookOrder(17699, new Date(), (float)5.99, new Date(), new ArrayList(), 5598, (float)40.98);
		order1.addOrderItem(new BookOrderItem (1, 123, (float)1, (float)0));
		orders.put(17699, order1);
		
		// Order for Lula Wax   book: A Treatise of Treatises & Clifford the Big Red Dog
		BookOrder order2 = new BookOrder(17700, new Date(), (float)5.99, new Date(), new ArrayList(), 5543, (float)52.97);
		order2.addOrderItem(new BookOrderItem (1, 123, (float)1, (float)0));
		order2.addOrderItem(new BookOrderItem(2,456, (float)1,(float)0));
		orders.put(17700, order2);
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
}
