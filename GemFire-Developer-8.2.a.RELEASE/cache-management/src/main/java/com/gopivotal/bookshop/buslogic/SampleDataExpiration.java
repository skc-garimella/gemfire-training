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
		// TODO-06a: Write code to initialize cache, get the customer region and populate the set of customers.
		//          Note: The functionality has been encapsulated in methods. You just need to call the methods.

		// TODO-06b: Show the contents by calling retrieveAndPrintRegionData for the customer region

		System.out.println("Before the idle time expiration, access and update one entry each...\n");
		
		// TODO-06c: Get the expiration time by calling the getExpirationTime() method and use that value to calculate
		//          a sleep time. You want to sleep perhaps 1 second less than the configured idle time so you have time
		//          to modify some entries before they expire.

		// TODO-06d: Make a call to get a customer (5540) and update customer 5541. These methods have been written for you.

		// TODO-06e: Perform another sleep for perhaps 1/2 the idle time
		
		System.out.println("After the expiration timeout, the cache contains:\n");

		// TODO-06f: Show the contents of the region again

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
		// TODO-03: Add call to get the expiration time from the customer region and return that value
		return 0;
	}

	public void updateCustomer(int customerKey) {
		// TODO-04: Add code to modify a Customer entry per the key supplied and store it back in the region.
		//          Tip: Try just adding a new address

		System.out.println("************ Updated customer data ****************\n");
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
	 * TODO-05: Review this code. 
	 *          The goal is to retrieve the entries from the region supplied and 
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
