package quickstart;

import com.gemstone.gemfire.cache.Declarable;
import com.gemstone.gemfire.cache.EntryEvent;
import com.gemstone.gemfire.cache.util.CacheListenerAdapter;
import java.util.Properties;

/**
 * A simple CacheListener which prints lines to output.
 * 
 * @author GemStone Systems, Inc.
 */
public class SimpleCacheListener<K, V> extends CacheListenerAdapter<K, V> implements Declarable {

  @Override
  public void afterCreate(EntryEvent<K, V> e) {
    System.out.println("    Received afterCreate event for entry: " + e.getKey() + ", " + e.getNewValue());
  }
  
  @Override
  public void afterUpdate(EntryEvent<K, V> e) {
    System.out.println("    Received afterUpdate event for entry: " + e.getKey() + ", " + e.getNewValue());
  }
  
  @Override
  public void afterDestroy(EntryEvent<K, V> e) {
    System.out.println("    Received afterDestroy event for entry: " + e.getKey());
  }

  @Override
  public void afterInvalidate(EntryEvent<K, V> e) {
    System.out.println("    Received afterInvalidate event for entry: " + e.getKey());
  }

  @Override
  public void init(Properties props) {
    // do nothing
  }
}

