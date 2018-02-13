package com.gopivotal.bookshop.tests;
import static org.junit.Assert.assertNotNull;

import javax.annotation.Resource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.gemstone.gemfire.cache.Region;
import com.gopivotal.bookshop.domain.Customer;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/META-INF/spring/gemfire/cache-config.xml" })
@ActiveProfiles({"basic"})
public class VerifyServerTests {
	@Autowired
	ApplicationContext ac;
	
	@Resource(name="Customer")
	Region<Integer,Customer> customers ;
	
	@Test
	public void testServer() {
		Customer customer = customers.get(5598);
		assertNotNull("Failed to fetch customer", customer);
		
	}
}
