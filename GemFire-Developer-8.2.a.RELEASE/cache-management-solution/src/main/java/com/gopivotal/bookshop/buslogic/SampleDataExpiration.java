package com.gopivotal.bookshop.buslogic;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import com.gemstone.gemfire.cache.Cache;
import com.gemstone.gemfire.cache.CacheFactory;
import com.gemstone.gemfire.cache.ExpirationAttributes;
import com.gemstone.gemfire.cache.Region;
import com.gopivotal.bookshop.domain.Address;
import com.gopivotal.bookshop.domain.Customer;

public class SampleDataExpiration {

	private Cache cache;
    private Region<Integer, Customer> customer_region;

	public static void main(String[] args) throws InterruptedException {
		SampleDataExpiration testData = new SampleDataExpiration();
		testData.run();
	}

	private void run() throws InterruptedException {
		getCache();
		getCustomerRegion();
		populateCustomer();

		retrieveAndPrintRegionData(customer_region);

		System.out
				.println("Before the idle time expiration, access and update one entry each...\n");
		int idleTime = getExpirationTime();
		Thread.sleep((idleTime - 1) * 1000);

		getCustomer(5540);
		updateCustomer(5541);

		Thread.sleep((idleTime / 2) * 1000);
		System.out.println("After the expiration timeout, the cache contains:\n");
		retrieveAndPrintRegionData(customer_region);

		System.out.println("Closing the cache and disconnecting.");
		closeCache();
		
	}

	public void closeCache() {
		cache.close();
	}

	public void getCache() {
		this.cache = new CacheFactory().set("cache-xml-file",
				"xml/serverCache.xml").create();
	}

	public void getCustomerRegion() {
		customer_region = cache.getRegion("Customer");
	}

	private int getExpirationTime() {
		ExpirationAttributes expirationAttr = customer_region.getAttributes()
				.getEntryIdleTimeout();
		return expirationAttr.getTimeout();
	}

	public void updateCustomer(int customerKey) {
		Customer cust2 = customer_region.get(customerKey);
		cust2.addAddress(new Address("123 Main St.", null, null,
				"Topeka", "CA", "50505", "US", "423-555-3322", "HOME"));
		customer_region.put(customerKey, cust2);

		System.out
				.println("************ Updated customer data ****************\n");
	}

	public void populateCustomer() {
		Map<Integer, Customer> customer = new HashMap<Integer, Customer>();
		Customer cust1 = new Customer(5540, "Lula", "Wax", null,
				null);
		cust1.addAddress(new Address("123 Main St.", null, null, "Topeka",
				"KS", "50505", "US", "423-555-3322", "HOME"));
		cust1.addOrder(17699);
		customer.put(5540, cust1);

		Customer cust2 = new Customer(5541, "Tom", "Mcginns",
				null, null);
		cust2.addAddress(new Address("123 Main St.", null, null,
				"San Fransisco", "CA", "50505", "US", "423-555-3322", "HOME"));
		cust2.addOrder(17699);
		customer.put(5541, cust2);

		Customer cust3 = new Customer(5542, "Peter", "Fernendez",
				null, null);
		cust3.addAddress(new Address("123 Main St.", null, null,
				"San Fransisco", "CA", "50505", "US", "423-555-3322", "HOME"));
		cust3.addOrder(17699);
		customer.put(5542, cust3);

		Customer cust4 = new Customer(5543, "Jenny", "Tsai", null,
				null);
		cust4.addAddress(new Address("123 Main St.", null, null, "Topeka",
				"CA", "50505", "US", "423-555-3322", "HOME"));
		cust4.addOrder(17699);
		customer.put(5543, cust4);

		customer_region.putAll(customer);
		System.out
				.println("************ Server 1 : Loaded customers data ****************\n");
	}

	public void getCustomer(int key) {
		Customer cust = customer_region.get(key);
		System.out.println("Accessed customer, " + cust.getFirstName()
				+ "  data !!");
	}

	/**
	 *          The purpose of this method is to retrieve the entries from the region supplied and 
	 *          print out their details
	 *          
	 * @param region The region to print entries for
	 */
	private void retrieveAndPrintRegionData(Region<?, ?> region) {
		Object[] keys = region.keySet().toArray();
		Arrays.sort(keys);
		for (Object key : keys) {
			System.out.println("    " + key + " => " + region.get(key));
		}
	}

}
