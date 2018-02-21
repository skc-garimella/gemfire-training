package portableDataExchange;

import java.util.Date;
import java.util.Properties;

import com.gemstone.gemfire.cache.Declarable;
import com.gemstone.gemfire.cache.EntryEvent;
import com.gemstone.gemfire.cache.util.CacheListenerAdapter;
import com.gemstone.gemfire.pdx.PdxInstance;

/**
 * An example of a cache listener that does not deserialize objects stored in 
 * the PDX format. Instead, it introspects the serialized version of the object.
 * </p>
 * 
 * @author GemStone Systems, Inc.
 * @since 6.6
 */
public class LoggingCacheListener extends CacheListenerAdapter<String, PdxInstance> implements Declarable {

  @Override
  public void afterCreate(EntryEvent<String, PdxInstance> event) {
    //The new value will be a PdxInstance, because the cache
    //has read-serialized set to true in server.xml.
    //PdxInstance is a wrapper around the serialized object.
    PdxInstance portfolio = event.getNewValue();
    
    Integer id = (Integer) portfolio.getField("id");
    Date creationDate = (Date) portfolio.getField("creationDate");

    //print out the id and creation time of the portfolio.
    System.out.println("LoggingCacheListener: - " + id + " created at " + creationDate);
  }

  /**
   * Initialize any properties in the specified in the cache.xml file.
   */
  @Override
  public void init(Properties props) {
    // do nothing
  }
}
