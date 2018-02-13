package com.gopivotal.bookshop.buslogic;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import com.gemstone.gemfire.cache.Cache;
import com.gemstone.gemfire.cache.CacheFactory;
import com.gemstone.gemfire.cache.Region;
import com.gopivotal.bookshop.domain.Address;
import com.gopivotal.bookshop.domain.Customer;

public class SampleDataEviction {

	private Cache cache;
    private Region<Integer, Customer> customer_region;

	public static void main(String[] args) throws InterruptedException {
		SampleDataEviction testData = new SampleDataEviction();
		testData.run();
	}

	private void run() {
		getCache();
		getCustomerRegion();
		populateCustomer();
		printRegionContents(customer_region);
		System.out
		.println("************ Loaded customers data ****************");
		insertOneMoreCustomer();
		printRegionContents(customer_region);
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
				.println("************ Loaded customers data ****************");
	}
	
	private void insertOneMoreCustomer() {
		Customer cust5 = new Customer(5545, "Fifth", "Customer", null,
				null);
		cust5.addAddress(new Address("123 Main St.", null, null, "Topeka",
				"CA", "50505", "US", "423-555-3322", "HOME"));
		cust5.addOrder(17699);
		customer_region.put(5545, cust5);
		System.out
		.println("************ Inserted one more customer data ****************");
	}

	private void printRegionContents(Region<?, ?> region) {
		Object[] keys = region.keySet().toArray();
		Arrays.sort(keys);
		for (Object key : keys) {
			System.out.println("    " + key + " => " + region.get(key));
		}
	}

}