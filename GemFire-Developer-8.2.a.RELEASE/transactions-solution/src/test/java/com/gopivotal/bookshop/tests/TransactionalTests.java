package com.gopivotal.bookshop.tests;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import com.gemstone.gemfire.cache.Region;
import com.gemstone.gemfire.cache.TransactionDataRebalancedException;
import com.gemstone.gemfire.cache.client.ClientCache;
import com.gemstone.gemfire.cache.client.ClientCacheFactory;
import com.gopivotal.bookshop.buslogic.TransactionalService;
import com.gopivotal.bookshop.domain.Address;
import com.gopivotal.bookshop.domain.Customer;
import com.gopivotal.bookshop.domain.Order;
import com.gopivotal.bookshop.domain.ProductItem;

// TODO-01: Open the test harness and observe the two transaction tests to be run
// TODO-07: Ensure the locator & servers have been started, then run the tests, verifying both tests pass.
// TODO-11: Stop & restart the locator & servers and re-run the tests. Verify the output of the listeners.log file
public class TransactionalTests {
	private ClientCache cache;
	private Region<Integer, Order> order_region;
	private Region<Integer, Customer> customer_region;

	@Before
	public void setUp() throws Exception {
		this.cache = new ClientCacheFactory()
				.set("name", "DataOperations Client")
				.set("cache-xml-file", "xml/clientCache.xml").create();
		order_region = cache.getRegion("Order");
		customer_region = cache.getRegion("Customer");

		// Force the region data to be removed so log will only show true
		// updates. The region.clear() method doesn't
		// work for clients calling clear() on partitioned regions.
		order_region.removeAll(order_region.keySetOnServer());
		customer_region.removeAll(customer_region.keySetOnServer());

		loadCustomers();
		loadOrders();
	}

	@Test
	public void testTransactionCommit() {
		TransactionalService svc = new TransactionalService(cache);

		// Parameterized data for test
		Integer customerKey = 1001;
		Integer orderKey = 1001;
		Calendar updatedOrderDate = Calendar.getInstance();
		updatedOrderDate.set(2013, Calendar.APRIL, 25, 0, 0, 0);
		String updatedCustomerPhone = "222-22222-0000";

		svc.updateCustomerAndOrder(customerKey, orderKey, updatedCustomerPhone, updatedOrderDate.getTime());

		// Verify that changes were made in both regions
		Customer cust = customer_region.get(customerKey);
		Order order = order_region.get(orderKey);

		assertEquals("Failed to update Customer", updatedCustomerPhone, cust.getPhoneNumber());
		assertEquals("Failed to update Order", updatedOrderDate.getTime(), order.getOrderDate());
	}

	@Test(expected = TransactionDataRebalancedException.class)
	public void testTransactionRollback() {
		TransactionalService svc = new TransactionalService(cache);

		// Parameterized data for test
		Integer customerKey = 1001;
		Integer orderKey = 1002;
		Calendar updatedOrderDate = Calendar.getInstance();
		updatedOrderDate.set(2013, Calendar.APRIL, 25, 0, 0, 0);
		String updatedCustomerPhone = "222-22222-0000";

		svc.updateCustomerAndOrder(customerKey, orderKey, updatedCustomerPhone, updatedOrderDate.getTime());
	}

	private void loadOrders() {
		Map<Integer, Order> order = new HashMap<Integer, Order>();

		Calendar orderDate = Calendar.getInstance();
		orderDate.set(2013, Calendar.DECEMBER, 3, 0, 0, 0);
		ArrayList<ProductItem> arr1 = new ArrayList<ProductItem>();
		arr1.add(new ProductItem("P001", "Toy", 30.5f));
		arr1.add(new ProductItem("P002", "Watch", 60.5f));
		arr1.add(new ProductItem("P003", "Pen", 12.5f));
		Order order01 = new Order("ORD001", orderDate.getTime(), arr1, "C001", 103.5f);
		order.put(1001, order01);

		ArrayList<ProductItem> arr2 = new ArrayList<ProductItem>();
		arr2.add(new ProductItem("P004", "Shirt", 60.5f));
		arr2.add(new ProductItem("P005", "Socks", 12.5f));
		Order order02 = new Order("ORD002", orderDate.getTime(), arr2, "C002", 73.0f);
		order.put(1002, order02);

		order_region.putAll(order);
	}

	private void loadCustomers() {
		Map<Integer, Customer> customer = new HashMap<Integer, Customer>();
		Customer cust1 = new Customer("C001", "Lula", "Wax", "123-654-543", null, null);
		cust1.addAddress(new Address("123 Main St.", null, null, "Topeka", "KS", "50505", "US", "HOME"));
		customer.put(1001, cust1);

		Customer cust2 = new Customer("C002", "Tom", "Mcginns", "123-456-789", null, null);
		cust2.addAddress(new Address("123 Main St.", null, null, "San Fransisco", "CA", "50505", "US", "HOME"));
		customer.put(1002, cust2);

		Customer cust3 = new Customer("C003", "Peter", "Fernendez", "123-456-789", null, null);
		cust3.addAddress(new Address("123 Main St.", null, null, "San Fransisco", "CA", "50505", "US", "HOME"));
		customer.put(1003, cust3);

		customer_region.putAll(customer);
	}

}
