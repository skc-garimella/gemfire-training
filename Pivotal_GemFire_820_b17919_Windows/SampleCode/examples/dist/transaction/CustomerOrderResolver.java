/**
 * 
 */
package transaction;

import com.gemstone.gemfire.cache.EntryOperation;
import com.gemstone.gemfire.cache.PartitionResolver;

import java.io.Serializable;

/**
 * This resolver ensures that customer and their order are colocated by
 * always returning {@link CustomerId}
 */
public class CustomerOrderResolver implements PartitionResolver {

  /* (non-Javadoc)
   * @see com.gemstone.gemfire.cache.PartitionResolver#getName()
   */
  public String getName() {
    return "customResolver";
  }

  /* (non-Javadoc)
   * @see com.gemstone.gemfire.cache.PartitionResolver#getRoutingObject(com.gemstone.gemfire.cache.EntryOperation)
   */
  public Serializable getRoutingObject(EntryOperation opDetails) {
    Serializable key = (Serializable)opDetails.getKey();
    if (key instanceof CustomerId) {
      return key;
    } else if (key instanceof OrderId) {
      return ((OrderId)key).getCustId();
    }
    return null;
  }

  /* (non-Javadoc)
   * @see com.gemstone.gemfire.cache.CacheCallback#close()
   */
  public void close() {
  }

}
