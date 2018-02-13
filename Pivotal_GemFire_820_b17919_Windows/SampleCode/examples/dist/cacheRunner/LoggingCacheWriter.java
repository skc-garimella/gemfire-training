package cacheRunner;

//import com.gemstone.gemfire.internal.LocalLogWriter;
import com.gemstone.gemfire.cache.*;

/**
 * A <code>CacheWriter</code> 
 *
 * @author GemStone Systems, Inc.
 * @since 4.0
 */
public class LoggingCacheWriter extends LoggingCacheCallback
  implements CacheWriter<Object, Object> {

  /**
   * Zero-argument constructor required for declarative caching
   */
  public LoggingCacheWriter() {
    super();
  }

  public final void beforeUpdate(EntryEvent<Object, Object> event)
    throws CacheWriterException {

    log("CacheWriter.beforeUpdate", event);
  }

  public final void beforeCreate(EntryEvent<Object, Object> event)
    throws CacheWriterException {

    log( "CacheWriter.beforeCreate", event);
  }

  public final void beforeDestroy(EntryEvent<Object, Object> event)
    throws CacheWriterException {

    log("CacheWriter.beforeDestroy", event);
  }

  public final void beforeRegionDestroy(RegionEvent<Object, Object> event)
    throws CacheWriterException {

    log("CacheWriter.beforeRegionDestroy", event);
  }

  public final void beforeRegionClear(RegionEvent<Object, Object> event)
    throws CacheWriterException {

    log("CacheWriter.beforeRegionClear", event);
  }

}
