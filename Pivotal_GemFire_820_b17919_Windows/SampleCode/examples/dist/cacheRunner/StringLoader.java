package cacheRunner;

import com.gemstone.gemfire.cache.*;

/**
 * A <code>CacheLoader</code> that simply returns the 
 * value of the <code>key</code> that is
 * loaded. 
 *
 * @author GemStone Systems, Inc.
 * @since 3.5
 */
public class StringLoader implements CacheLoader, Declarable {

  /**
   * Simply return the string value of the key being loaded
   *
   * @see LoaderHelper#getKey
   * @see String#valueOf(Object)
   */
  public Object load(LoaderHelper helper)
    throws CacheLoaderException {

    return String.valueOf(helper.getKey());
  }

  /**
   * Nothing to initialize
   */
  public void init(java.util.Properties props) {

  }

  public void close() {

  }
}
