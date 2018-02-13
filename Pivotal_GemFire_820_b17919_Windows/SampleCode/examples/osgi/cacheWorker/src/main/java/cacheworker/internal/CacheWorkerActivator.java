/*=========================================================================
 * Copyright (c) 2011-2014 Pivotal Software, Inc. All Rights Reserved.
 * This product is protected by U.S. and international copyright
 * and intellectual property laws. Pivotal products are covered by
 * more patents listed at http://www.pivotal.io/patents.
 *========================================================================
 */

package cacheworker.internal;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.Constants;

/**
 * Provides OSGi lifecycle for the CacheWorker example.
 *
 * @author VMware, Inc.
 * @since 6.6
 */
public class CacheWorkerActivator implements BundleActivator {

  private CacheWorker cacheWorker;

  /** Creates and starts a CacheWorker. */
  @Override
  public void start(BundleContext bc) throws Exception {
    System.out.println(bc.getBundle().getHeaders().get(Constants.BUNDLE_NAME)
        + " starting...");
    this.cacheWorker = new CacheWorker();
    this.cacheWorker.start();
  }

  /** Stops the CacheWorker. */
  @Override
  public void stop(BundleContext bc) throws Exception {
    System.out.println(bc.getBundle().getHeaders().get(Constants.BUNDLE_NAME)
        + " stopping...");
    this.cacheWorker.stop();
  }
}
