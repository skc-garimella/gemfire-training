package cacheRunner;

import com.gemstone.gemfire.cache.*;

/**
 * A <code>CacheListener</code> that logs information about the events it
 * receives.
 * 
 * @author GemStone Systems, Inc.
 * @since 4.0
 */
public class LoggingCacheListener<K, V> extends LoggingCacheCallback implements
    CacheListener<K, V> {

  public void afterCreate(EntryEvent<K, V> event) {
    log("CacheListener.afterCreate", event);
  }

  public void afterUpdate(EntryEvent<K, V> event) {
    log("CacheListener.afterUpdate", event);
  }

  public void afterInvalidate(EntryEvent<K, V> event) {
    log("CacheListener.afterInvalidate", event);
  }

  public void afterDestroy(EntryEvent<K, V> event) {
    log("CacheListener.afterDestroy", event);
  }

  public void afterRegionInvalidate(RegionEvent<K, V> event) {
    log("CacheListener.afterRegionInvalidate", event);
  }

  public void afterRegionDestroy(RegionEvent<K, V> event) {
    log("CacheListener.afterRegionDestroy", event);
  }

  public void afterRegionClear(RegionEvent<K, V> event) {
    log("CacheListener.afterRegionClear", event);
  }
  
  public void afterRegionCreate(RegionEvent<K, V> event) {
	  log("CacheListener.afterRegionCreate", event);
  }

  public void afterRegionLive(RegionEvent<K, V> event) {
	  log("CacheListener.afterRegionLive", event);
  }
}
