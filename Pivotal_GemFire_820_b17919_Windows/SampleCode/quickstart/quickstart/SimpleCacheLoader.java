package quickstart;

import com.gemstone.gemfire.cache.CacheLoader;
import com.gemstone.gemfire.cache.Declarable;
import com.gemstone.gemfire.cache.LoaderHelper;
import java.util.Properties;

/**
 * A simple CacheLoader implementation.
 * 
 * @author GemStone Systems, Inc.
 */
public class SimpleCacheLoader implements CacheLoader<String, String>, Declarable {

  @Override
  public String load(LoaderHelper<String, String> helper) {
    String key = helper.getKey();
    System.out.println("    Loader called to retrieve value for " + key);
    
    // Create a value using the suffix number of the key (key1, key2, etc.)
    return "LoadedValue" + (Integer.parseInt(key.substring(3)));
  }
  
  @Override
  public void close() {
    // do nothing
  }
  
  @Override
  public void init(Properties props) {
    // do nothing
  }
}
