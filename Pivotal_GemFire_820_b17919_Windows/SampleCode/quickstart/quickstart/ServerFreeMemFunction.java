package quickstart;

import com.gemstone.gemfire.cache.execute.FunctionAdapter;
import com.gemstone.gemfire.cache.execute.FunctionContext;
import com.gemstone.gemfire.cache.execute.FunctionException;
import com.gemstone.gemfire.cache.execute.RegionFunctionContext;

/**
 * A non-data aware function to retrieve the amount of free Java heap memory on 
 * the server, in MB.
 * </p>
 * 
 * @author GemStone Systems, Inc.
 * @since 8.0
 */
public class ServerFreeMemFunction extends FunctionAdapter {

  private static final long serialVersionUID = 2908666709157111340L;

  @Override
  public void execute(FunctionContext fc) {
    if (fc instanceof RegionFunctionContext) {
      throw new FunctionException("This is a non-data aware function, and has to be called using FunctionService.onServer.");
    }
    
    fc.getResultSender().lastResult(Runtime.getRuntime().freeMemory()/(1024*1024));
  }

  @Override
  public String getId() {
    return getClass().getName();
  }
}
