package com.gopivotal.bookshop.buslogic;

import com.gemstone.gemfire.cache.CacheEvent;
import com.gemstone.gemfire.cache.Declarable;
import com.gemstone.gemfire.cache.EntryEvent;
import com.gemstone.gemfire.cache.TransactionEvent;
import com.gemstone.gemfire.cache.util.TransactionListenerAdapter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Properties;

public class LoggingTransactionListener extends TransactionListenerAdapter implements Declarable {

	private Logger logger = LogManager.getLogger(LoggingTransactionListener.class.getName());

	@Override
	public void init(Properties properties) {
		// nothing to do
	}
	
	// TODO-08: Add an afterCommit method that overrides the adapter method. Write a logger message that notes when a transaction is committed.
	//          Write code that will get the list of events and print the key, old value and new value for each (you can use the code from the 
	//          LoggingCacheListener as a guide).
	@Override
	public void afterCommit(TransactionEvent event) {
		logger.info("afterCommit: TxId= " + event.getTransactionId());
		for (CacheEvent ce : event.getEvents()) {
			if (ce instanceof EntryEvent) {
				EntryEvent ee = (EntryEvent) ce;
				logger.info("   Entry updated for key: " + ee.getKey()
						+ "\n          Old value: " + ee.getOldValue()
						+ "\n          New Value: " + ee.getNewValue());
			} else {
				logger.info("   Cache event received with operation: " + ce.getOperation());
			}
		}
	}

	// TODO-09: Add an afterRollback method and use the method to log that the rollback occurred.
	@Override
	public void afterRollback(TransactionEvent event) {
		logger.info("afterRollback: TxId= " + event.getTransactionId());
		for (CacheEvent ce : event.getEvents()) {			
				logger.info("   Cache event received with operation: " + ce.getOperation());
		}
	}

}
