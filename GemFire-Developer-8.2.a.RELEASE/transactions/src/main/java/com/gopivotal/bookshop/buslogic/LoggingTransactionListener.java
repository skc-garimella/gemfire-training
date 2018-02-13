package com.gopivotal.bookshop.buslogic;

import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.gemstone.gemfire.cache.Declarable;
import com.gemstone.gemfire.cache.util.TransactionListenerAdapter;

public class LoggingTransactionListener extends TransactionListenerAdapter implements Declarable {

	private Logger logger = LogManager.getLogger(LoggingTransactionListener.class.getName());

	@Override
	public void init(Properties properties) {
		// nothing to do
	}
	
	// TODO-08: Add an afterCommit method that overrides the adapter method. Write a logger message that notes when a transaction is committed.
	//          Write code that will get the list of events and print the key, old value and new value for each (you can use the code from the 
	//          LoggingCacheListener as a guide).

	
	// TODO-09: Add an afterRollback method and use the method to log that the rollback occurred.


}
