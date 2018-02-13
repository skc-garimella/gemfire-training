package quickstart;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import com.gemstone.gemfire.cache.client.ClientCacheFactory;
import com.gemstone.gemfire.cache.client.ClientCache;
import com.gemstone.gemfire.cache.Region;
import com.gemstone.gemfire.cache.query.CqAttributes;
import com.gemstone.gemfire.cache.query.CqAttributesFactory;
import com.gemstone.gemfire.cache.query.CqListener;
import com.gemstone.gemfire.cache.query.CqQuery;
import com.gemstone.gemfire.cache.query.QueryService;

/**
 * In this example of hierarchical caching, the server listens on a port for
 * client requests and updates. The client creates and executes CQ on the server
 * and waits for the events. Please refer to the quickstart guide for
 * instructions on how to run this example.
 * </p>
 * 
 * @author GemStone Systems, Inc.
 * @since 5.1
 */
public class CqClient {

  public static final String EXAMPLE_REGION_NAME = "exampleRegion";

  private final BufferedReader stdinReader;

  public CqClient() {
    this.stdinReader = new BufferedReader(new InputStreamReader(System.in));
  }

  public static void main(String[] args) throws Exception {
    new CqClient().run();
  }

  public void run() throws Exception {

    System.out.println("Connecting to the distributed system and creating the cache...");
    // Create the cache which causes the cache-xml-file to be parsed
    ClientCache cache = new ClientCacheFactory()
        .set("name", "CqClient")
        .set("cache-xml-file", "xml/CqClient.xml")
        .create();

    // Get the exampleRegion
    Region<String, String> exampleRegion = cache.getRegion(EXAMPLE_REGION_NAME);
    System.out.println("Example region \"" + exampleRegion.getFullPath() + "\" created in cache.");

    // Get the query service
    QueryService queryService = cache.getQueryService();

    // Create CQ Attributes.
    CqAttributesFactory cqAf = new CqAttributesFactory();

    // Initialize and set CqListener.
    CqListener[] cqListeners = { new SimpleCqListener() };
    cqAf.initCqListeners(cqListeners);
    CqAttributes cqa = cqAf.create();

    // Construct a new CQ.
    String cqName = "MyCq";
    String query = "SELECT * FROM /exampleRegion e where e='ClientFirstValue'";
    System.out.println("Creating CQ \"" + cqName + "\" with query");
    System.out.println("\"" + query + "\"");

    // Execute the Cq. This registers the cq on the server.
    System.out.println("Executing CQ \"" + cqName + "\"...");
    CqQuery myCq = queryService.newCq(cqName, query, cqa);
    myCq.execute();

    System.out.println("This client will update the server cache and its CQ listener will");
    System.out.println("get events for any changes to the CQ result set.");
    System.out.println("CQ events provide the base operation (the change to the server's cache),");
    System.out.println("and the query operation (the change to the CQ's result set).");
    pressEnterToContinue();

    // Create a new value in the cache
    System.out.println("___________________________________________________________________");
    System.out.println("CQ looking for entries whose value is 'ClientFirstValue'...");
    System.out.println("Putting key1 with value 'ClientFirstValue'");
    System.out.println("This satisfies the query, so the CqListener will report a query");
    System.out.println("creation event from the server cache.");
    exampleRegion.put("key1", "ClientFirstValue");

    // Wait for the events to come through so the screen output makes sense
    Thread.sleep(2000);
    pressEnterToContinue();

    // Update value.
    System.out.println("___________________________________________________________________");
    System.out.println("CQ looking for entries whose value is 'ClientFirstValue'...");
    System.out.println("Updating key1 with value 'ClientSecondValue'");
    System.out.println("This removes key1 from the CQ result set, to the CQListener will");
    System.out.println("report a query destroy event.");

    exampleRegion.put("key1", "ClientSecondValue");
    Thread.sleep(2000);
    pressEnterToContinue();

    // Update value.
    System.out.println("___________________________________________________________________");
    System.out.println("CQ looking for entries whose value is 'ClientFirstValue'...");
    System.out.println("Updating key1 with value 'ClientFirstValue'");
    System.out.println("This adds key1 back into the CQ result set, to the CQListener will");
    System.out.println("report a query create event.");

    exampleRegion.put("key1", "ClientFirstValue");
    Thread.sleep(2000);
    pressEnterToContinue();

    // Destroy value.
    System.out.println("___________________________________________________________________");
    System.out.println("CQ looking for entries whose value is 'ClientFirstValue'...");
    System.out.println("Destroying key1...");
    System.out.println("This removes key1 from the result set, to the CQListener will");
    System.out.println("report a query destroy event.");

    exampleRegion.destroy("key1");
    Thread.sleep(2000);
    pressEnterToContinue();

    // Close the cache and disconnect from GemFire distributed system
    System.out.println("Closing the cache and disconnecting.");
    cache.close();

    System.out.println("Please stop the cacheserver with 'gfsh stop server --dir=server_cq'.");
  }

  private void pressEnterToContinue() throws IOException {
    System.out.println("Press Enter to continue.");
    stdinReader.readLine();
  }
}
