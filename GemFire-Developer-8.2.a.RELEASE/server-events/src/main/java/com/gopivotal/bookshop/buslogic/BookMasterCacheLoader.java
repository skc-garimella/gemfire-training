package com.gopivotal.bookshop.buslogic;

import java.util.Properties;

import com.gemstone.gemfire.cache.CacheLoader;
import com.gemstone.gemfire.cache.Declarable;
import com.gemstone.gemfire.cache.LoaderHelper;
import com.gopivotal.bookshop.domain.BookMaster;

public class BookMasterCacheLoader implements CacheLoader<String,BookMaster>, Declarable {
  
  public BookMaster load(LoaderHelper<String,BookMaster> helper) {
	  BookMaster newBook = null;
	  
	  // Implement the 'load' functionality to create a new book
	  
	  return newBook;
  }
  
  public void close() {
    // do nothing
  }
  
  public void init(Properties props) {
    // do nothing
  }
}