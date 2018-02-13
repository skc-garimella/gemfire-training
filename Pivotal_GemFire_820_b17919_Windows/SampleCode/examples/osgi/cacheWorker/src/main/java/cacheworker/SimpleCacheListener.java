/*=========================================================================
 * Copyright (c) 2011-2014 Pivotal Software, Inc. All Rights Reserved.
 * This product is protected by U.S. and international copyright
 * and intellectual property laws. Pivotal products are covered by
 * more patents listed at http://www.pivotal.io/patents.
 *========================================================================
 */
package cacheworker;

import com.gemstone.gemfire.cache.Declarable;
import com.gemstone.gemfire.cache.EntryEvent;
import com.gemstone.gemfire.cache.util.CacheListenerAdapter;
import java.util.Properties;

/**
 * Simple CacheListener that prints caching events to System.out.
 *
 * @author VMware, Inc.
 * @since 6.6
 */
public class SimpleCacheListener<K,V> extends CacheListenerAdapter<K,V> implements Declarable {

  @Override
  public void afterCreate(EntryEvent<K,V> e) {
    System.out.println(this + " received afterCreate event for entry: " +
      e.getKey() + ", " + e.getNewValue());
  }

  @Override
  public void afterUpdate(EntryEvent<K,V> e) {
    System.out.println(this + " received afterUpdate event for entry: " +
      e.getKey() + ", " + e.getNewValue());
  }

  @Override
  public void afterDestroy(EntryEvent<K,V> e) {
    System.out.println(this + " received afterDestroy event for entry: " +
      e.getKey());
  }

  @Override
  public void afterInvalidate(EntryEvent<K,V> e) {
    System.out.println(this + " received afterInvalidate event for entry: " +
      e.getKey());
  }

  @Override
  public void init(Properties props) {
    System.out.println("Initialized SimpleCacheListener " + this);
  }

  /** Returns the name of this listener. */
  @Override
  public String toString() {
    return getClass().getName();
  }
}
