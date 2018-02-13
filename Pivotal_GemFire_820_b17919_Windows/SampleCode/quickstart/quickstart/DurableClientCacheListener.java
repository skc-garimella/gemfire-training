package quickstart;

import com.gemstone.gemfire.cache.RegionEvent;

/**
 * CacheListener for the durable client.
 * </p>
 *
 * @author GemStone Systems, Inc.
 */
public class DurableClientCacheListener<K,V> extends SimpleCacheListener<K,V> {

  @Override
  public void afterRegionLive(RegionEvent<K,V> e) {
    System.out.println("    Received afterRegionLive event, sent to durable clients after the server has finished replaying stored events.");
  }
}
