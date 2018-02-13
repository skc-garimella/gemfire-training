package quickstart;

import java.util.Properties;

import com.gemstone.gemfire.cache.Declarable;
import com.gemstone.gemfire.cache.EntryEvent;
import com.gemstone.gemfire.cache.util.CacheListenerAdapter;

/**
 * Delta Propagation quick start cache listener for simple uses
 * </p>
 * 
 * @author GemStone Systems, Inc.
 * @since 6.1
 */
public class DeltaSimpleListener extends CacheListenerAdapter<Object, Object> implements Declarable{
  
  /**
   * Processes an afterCreate event.
   * 
   * @param event The afterCreate <code>EntryEvent</code> received
   */
  @Override
  public void afterCreate(EntryEvent<Object, Object> event) {
    processEvent("afterCreate", event);
  }

  /**
   * Processes an afterUpdate event.
   * 
   * @param event The afterUpdate <code>EntryEvent</code> received
   */
  @Override
  public void afterUpdate(EntryEvent<Object, Object> event) {
    processEvent("afterUpdate", event);
  }

  protected void processEvent(String operation, EntryEvent<Object, Object> event) {
    if (!event.getKey().equals("LAST_KEY"))
      System.out.println("ServerListener received " + operation + " Region : "
          + event.getRegion().getName() + ": " + event.getKey() + "->"
          + ((DeltaObj)event.getNewValue()).toString());
  }

  @Override
  public void init(Properties props) {
    // do nothing
  }
}
