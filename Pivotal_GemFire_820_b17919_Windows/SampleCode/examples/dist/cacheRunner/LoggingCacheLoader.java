package cacheRunner;

import com.gemstone.gemfire.cache.*;

/**
 * A <code>CacheLoader</code> that logs information about the events
 * it receives.
 *
 * @see LoggingCacheWriter
 *
 * @author GemStone Systems, Inc.
 * @since 4.0
 */
public class LoggingCacheLoader extends LoggingCacheCallback
  implements CacheLoader {

  /**
   * Before creating a new value for the requested key, this loader
   * will do a {@link LoaderHelper#netSearch} to look for the value in
   * another member of the distributed system.
   */
  public final Object load(LoaderHelper helper)
    throws CacheLoaderException {

    StringBuffer sb = new StringBuffer();
    sb.append("Loader invoked for key: ");
    sb.append(format(helper.getKey()));
    sb.append("\n");

    if (helper.getArgument() != null) {
      sb.append("  With argument: ");
      sb.append(format(helper.getArgument()));
      sb.append("\n");
    }
              
    Object value;
    try {
      value = helper.netSearch(false /* netLoad */);

    } catch (TimeoutException ex) {
      String s = "Timed out while performing net search";
      throw new CacheLoaderException(s, ex);
    }

    if (value != null) {
      sb.append("  Net search found value: ");
      sb.append(format(value));
      sb.append("\n");

    } else {
      sb.append("  Net search did not find a value");
      value = "Loader Invoked by Thread ID " + Thread.currentThread();
    }

    log(sb.toString(), helper.getRegion());

    return value;
  }

}
