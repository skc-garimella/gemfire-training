/*
 * Copyright 2012 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.gopivotal.training;

import com.gemstone.gemfire.cache.client.ClientCache;
import com.gopivotal.bookshop.buslogic.BookLoader;
import com.gopivotal.bookshop.buslogic.CustomerLoader;
import com.gopivotal.bookshop.buslogic.OrderLoader;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class LoadRegions {
	private ClientCache cache;

	public static void main(String[] args) {
		AbstractApplicationContext context= new ClassPathXmlApplicationContext("META-INF/spring/gemfire/cache-config.xml");
		//context.registerShutdownHook();

		try {
			ClientCache cache = context.getBean("clientCache", ClientCache.class);
			new LoadRegions(cache).populateGemFire();
            cache.close();
		} finally {
			//context.close();
		}
	}
	
	public LoadRegions(ClientCache cache) {
		this.cache =  cache;
	}

	public void populateGemFire()
	{
		new BookLoader(cache).populateBooks();
		new CustomerLoader(cache).populateCustomers();
		new OrderLoader(cache).populateBookOrders().populateInventory();
	}
	
}
