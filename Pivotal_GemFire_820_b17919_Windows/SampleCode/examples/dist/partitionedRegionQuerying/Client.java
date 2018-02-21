package partitionedRegionQuerying;

import static com.gemstone.gemfire.cache.client.ClientRegionShortcut.PROXY;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.gemstone.gemfire.cache.Region;
import com.gemstone.gemfire.cache.client.ClientCache;
import com.gemstone.gemfire.cache.client.ClientCacheFactory;
import com.gemstone.gemfire.cache.client.ClientRegionFactory;
import com.gemstone.gemfire.cache.execute.Function;
import com.gemstone.gemfire.cache.execute.FunctionService;
import com.gemstone.gemfire.cache.execute.ResultCollector;
import com.gemstone.gemfire.cache.query.QueryService;
import com.gemstone.gemfire.cache.query.SelectResults;

/**
 * This example shows how to execute a query using function from a client by setting a
 * filter in the function call.This client connects two servers with
 * Partitioned region, inserts data and executes a query.
 * <p>
 * See the <code>README.html</code> for instructions on how to run the example.
 * 
 * <p>
 * Gemfire by default routes the data in a Partitioned Region using the keys. Since a
 * function is used, the query gets routed to one server. The filter provided in
 * the function call then routes the query to the bucket where the filter key
 * exists on the server.
 * 
 */
public class Client {
  // Number of objects to be inserted
  private static int NUM_PUTS = 10;
  
  // The ports the cache servers are listening on
  private static final int SERVER1_PORT = Integer.getInteger("server1Port",
      40404).intValue();
  private static final int SERVER2_PORT = Integer.getInteger("server2Port",
      40405).intValue();

  public static void main(String[] args) {
    // Create client cache
    ClientCache clientCache = new ClientCacheFactory()
        .addPoolServer("localhost", SERVER1_PORT)
        .addPoolServer("localhost", SERVER2_PORT).create();
    log("Connected to servers on ports: " + SERVER1_PORT + " and " + SERVER2_PORT);
    
    // Create Region
    ClientRegionFactory<String, Portfolio> regionFactory = clientCache
        .createClientRegionFactory(PROXY);
    Region<String, Portfolio> region = regionFactory.create("exampleRegion");
    
    // Insert data
    // key is same as the id field of Portfolio
    for (int i = 0; i < NUM_PUTS; i++) {
      region.put("" + i, new Portfolio(i));
    }
    log("Inserted " + NUM_PUTS + " Portfolio objects");
    
    Set<String> filter = new HashSet<String>();
    // Filter data based on region key '1' for value Portfolio(1)
    filter.add("1");

    // Query to get Portfolio with id = '1'
    String qStr = "SELECT * FROM /exampleRegion WHERE id = '1'";

    Function func = new QueryingFunction();

    log("Invoking funtion on server to execute query: SELECT * FROM /exampleRegion WHERE id = '1'");
    // Function will be routed to one node containing the bucket
    // for id = '1' and query will execute on that bucket.
    ResultCollector rcollector = FunctionService.onRegion(region)
        .withArgs(qStr).withFilter(filter).execute(func);

    Object result = rcollector.getResult();

    // Results from one or multiple nodes.
    ArrayList resultList = (ArrayList) result;
    List queryResults = new ArrayList();

    if (resultList.size() != 0) {
      for (Object obj : resultList) {
        if (obj != null) {
          queryResults.addAll((ArrayList) obj);
        }
      }
    }

    log("Query returned " + queryResults.size() + " results");
    log("Query result:\t" + formatQueryResult(queryResults));

    // Close the cache and disconnect from GemFire distributed system
    clientCache.close();
  }

  public static String formatQueryResult(Object result) {
    if (result == null) {
      return "null";
    } else if (result == QueryService.UNDEFINED) {
      return "UNDEFINED";
    }
    if (result instanceof SelectResults) {
      Collection<?> collection = ((SelectResults<?>) result).asList();
      StringBuffer sb = new StringBuffer();
      for (Object e : collection) {
        sb.append(e + "\n\t");
      }
      return sb.toString();
    } else {
      return result.toString();
    }
  }
  
  public static void log(String msg){
    System.out.println(msg);
  }
}
