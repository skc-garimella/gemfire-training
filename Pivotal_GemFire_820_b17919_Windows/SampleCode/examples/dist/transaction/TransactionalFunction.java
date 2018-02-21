/**
 * 
 */
package transaction;

import com.gemstone.gemfire.cache.CacheFactory;
import com.gemstone.gemfire.cache.CacheTransactionManager;
import com.gemstone.gemfire.cache.Region;
import com.gemstone.gemfire.cache.execute.FunctionAdapter;
import com.gemstone.gemfire.cache.execute.FunctionContext;
import com.gemstone.gemfire.cache.execute.RegionFunctionContext;

import java.util.Random;

/**
 * This function does transactional updates to customer and order regions
 */
public class TransactionalFunction extends FunctionAdapter {

  private Random random = new Random();

  /* (non-Javadoc)
   * @see com.gemstone.gemfire.cache.execute.FunctionAdapter#execute(com.gemstone.gemfire.cache.execute.FunctionContext)
   */
  @Override
  public void execute(FunctionContext context) {
    RegionFunctionContext rfc = (RegionFunctionContext)context;
    Region<CustomerId, String> custRegion = rfc.getDataSet();
    Region<OrderId, String> orderRegion = custRegion.getRegionService().getRegion("order");
    CacheTransactionManager mgr = CacheFactory.getAnyInstance().getCacheTransactionManager();
    CustomerId custToUpdate = (CustomerId)rfc.getFilter().iterator().next();
    OrderId orderToUpdate = (OrderId)rfc.getArguments();
    System.out.println("Starting a transaction...");
    mgr.begin();
    int randomInt = random.nextInt(1000);
    System.out.println("for customer region updating "+custToUpdate);
    custRegion.put(custToUpdate, "updatedCustomer_"+custToUpdate.getCustId()+"_"+randomInt);
    System.out.println("for order region updating "+orderToUpdate);
    orderRegion.put(orderToUpdate, "newOrder_"+orderToUpdate.getOrderId()+"_"+randomInt);
    mgr.commit();
    System.out.println("transaction completed");
    context.getResultSender().lastResult(Boolean.TRUE);
  }

  /* (non-Javadoc)
   * @see com.gemstone.gemfire.cache.execute.FunctionAdapter#getId()
   */
  @Override
  public String getId() {
    return "TxFuntion";
  }

}
