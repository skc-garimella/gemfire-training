package com.gopivotal.bookshop.buslogic;

import com.gemstone.gemfire.cache.Declarable;
import com.gemstone.gemfire.cache.EntryEvent;
import com.gemstone.gemfire.cache.util.CacheListenerAdapter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Properties;

public class LoggingCacheListener<K,V> extends CacheListenerAdapter<K,V> implements Declarable {

	private Logger logger = LogManager.getLogger(LoggingTransactionListener.class.getName());

	@Override
	public void init(Properties properties) {
		// nothing to do
	}

	@Override
	public void afterUpdate(EntryEvent<K, V> event) {
		logger.info("afterUpdate:   Entry updated for key: " + event.getKey() +
				    "\n               Old value: " + event.getOldValue() + 
				    "\n               New Value: " + event.getNewValue());
	}

}
