package com.gopivotal.bookshop.buslogic;

import java.util.ArrayList;
import java.util.Date;

import com.gemstone.gemfire.cache.Region;
import com.gemstone.gemfire.cache.client.ClientCache;
import com.gemstone.gemfire.cache.client.ClientCacheFactory;
import com.gopivotal.bookshop.domain.BookOrder;
import com.gopivotal.bookshop.domain.BookOrderItem;
import com.gopivotal.bookshop.domain.InventoryItem;

public class OrderLoader
{
	private ClientCache cache;

	public OrderLoader() {}
	public OrderLoader(ClientCache cache) {
		this.cache = cache;
	}
	
	public OrderLoader populateBookOrders()
	{
		Region<Integer, BookOrder> orders = cache.getRegion("BookOrder");
		// Order for Kari Powell for book: A Treatise of Treatises
		BookOrder order1 = new BookOrder(17699, new Date(), (float)5.99, new Date(), new ArrayList(), 5598, (float)40.98);
		order1.addOrderItem(new BookOrderItem (1, 123, (float)1, (float)0));
		orders.put(17699, order1);
		System.out.println("Inserted a book order: " + order1);
		
		// Order for Lula Wax   book: A Treatise of Treatises & Clifford the Big Red Dog
		BookOrder order2 = new BookOrder(17700, new Date(), (float)5.99, new Date(), new ArrayList(), 5543, (float)52.97);
		order2.addOrderItem(new BookOrderItem (1, 123, (float)1, (float)0));
		order2.addOrderItem(new BookOrderItem(2,456, (float)1,(float)0));
		orders.put(17700, order2);
		System.out.println("Inserted a book order: " + order2);

		return this;
	}

	public OrderLoader populateInventory()
	{
		Region <Integer, InventoryItem> inventory = cache.getRegion("InventoryItem");

		InventoryItem item1 = new InventoryItem(123, (float) 12.50, (float) 34.99, (float) 12.0, "BookRUs", "Seattle");
		inventory.put(123, item1);
		System.out.println("Inserted an inventory item: "+item1);

		InventoryItem item2 = new InventoryItem(456, (float) 12.50, (float) 11.99, (float) 1.0, "BookRUs", "Seattle");
		inventory.put(456, item2);
		System.out.println("Inserted an inventory item: "+item2);

		return this;
	}
	
	public void closeCache()
	{
		cache.close();
	}
	
	public OrderLoader getCache()
	{
		this.cache = new ClientCacheFactory()
        .set("name", "ClientWorker")
        .set("cache-xml-file", "xml/clientCache.xml")
        .create();

		return this;
	}

	public static void main(String[] args)
	{
		new OrderLoader()
				.getCache()
				.populateBookOrders()
				.closeCache();
	}

}
