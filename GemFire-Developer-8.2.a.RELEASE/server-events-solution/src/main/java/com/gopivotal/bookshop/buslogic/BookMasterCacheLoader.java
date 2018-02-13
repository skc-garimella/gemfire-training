package com.gopivotal.bookshop.buslogic;

import java.util.Properties;

import com.gemstone.gemfire.cache.CacheLoader;
import com.gemstone.gemfire.cache.Declarable;
import com.gemstone.gemfire.cache.LoaderHelper;
import com.gopivotal.bookshop.domain.BookMaster;

public class BookMasterCacheLoader implements CacheLoader<String,BookMaster>, Declarable {
  
  public BookMaster load(LoaderHelper<String,BookMaster> helper) {
    String key = helper.getKey();
    return new BookMaster(123, "Run on sentences and drivel on all things mundane",	(float) 34.99, 2011, "Daisy Mae West",	"A Treatise of Treatises");
  }
  
  public void close() {
    // do nothing
  }
  
  public void init(Properties props) {
    // do nothing
  }
}