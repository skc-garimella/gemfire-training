package com.gopivotal.bookshop.buslogic;

import java.util.Date;

import com.gemstone.gemfire.cache.CacheTransactionManager;
import com.gemstone.gemfire.cache.Region;
import com.gemstone.gemfire.cache.client.ClientCache;
import com.gopivotal.bookshop.domain.Customer;
import com.gopivotal.bookshop.domain.Order;

public class TransactionalService {

	private CacheTransactionManager tx;
	private Region<Integer, Customer> customerRegion;
	private Region<Integer, Order> orderRegion;

	public TransactionalService(ClientCache cache) {
		customerRegion = cache.getRegion("Customer");
		orderRegion = cache.getRegion("Order");
		
		// TODO-02: Initialize the reference to the CacheTransactionManager
		tx = cache.getCacheTransactionManager();
	}

	public void updateCustomerAndOrder(Integer customerKey, Integer orderKey, String updatedCustomerPhone, Date updatedOrderDate )  {
		// TODO-03: Use a try/catch block to implement transactional operations to fetch a customer given the customer key, 
		//          Order given the orderKey and make updates to the appropriate fields, save each and commit the work, rolling back on any exceptions.
		
		   // TODO-03a: Begin the transaction
		
		   // TODO-03b: Fetch the customer and order using keys passed in and update with values from updatedCustomerPhone and updatedOrderDate
		
		   // TODO-03c: Save the customer and order objects back using the same keys used to fetch them
		
		   // TODO-03d: Commit the transaction
		
		   // TODO-04: Catch any exceptions that may be thrown, roll back the transaction and re-throw the exception
		try {
			tx.begin();

			Customer cust = customerRegion.get(customerKey);
			cust.setPhoneNumber(updatedCustomerPhone);
			Order order = orderRegion.get(orderKey);
			order.setOrderDate(updatedOrderDate);
			
			customerRegion.put(customerKey, cust);
			orderRegion.put(orderKey, order);

			tx.commit();
		} catch (Exception e) {
			tx.rollback();
			throw e;
		}
	}

}
