/**
 * 
 */
package transaction;

import com.gemstone.gemfire.cache.EntryEvent;
import com.gemstone.gemfire.cache.util.CacheListenerAdapter;

/**
 * Cache listener which prints changes in data to {@link System#out}
 */
public class LoggingCacheListener extends CacheListenerAdapter {
  @Override
  public void afterCreate(EntryEvent event) {
    System.out.printf("Entry Created in region %10s\tkey:%10s\tvalue:%s\n",event.getRegion().getName(), event.getKey(), event.getNewValue());
  }
  @Override
  public void afterUpdate(EntryEvent event) {
    System.out.printf("Entry Updated in region %10s\tkey:%10s\tvalue:%s\n",event.getRegion().getName(), event.getKey(), event.getNewValue());
  }
}
