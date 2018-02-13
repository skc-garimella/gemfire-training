package quickstart;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import com.gemstone.gemfire.cache.Cache;
import com.gemstone.gemfire.cache.CacheFactory;
import com.gemstone.gemfire.cache.Region;
import com.gemstone.gemfire.management.ManagementService;
import com.gemstone.gemfire.management.RegionMXBean;

/**
 * In this example of Managed Node.</br> 
 * 1. It creates a cache and adds entries in it.</br>
 * 2. From ManagementService it retrieves service.</br>
 * 3. From service gets Region Mbean.
 * </p>
 * 
 * @author GemStone Systems, Inc.
 * @since 7.0
 */
public class ManagedNode {

  public static final String EXAMPLE_REGION_NAME = "exampleRegion";

  public static void main(String[] args) throws Exception {

    //Set waiting period for federation in milliseconds
    final int JMX_WAIT_PERIOD_FOR_FEDERATION_UPDATE = 2500;
    
    System.out.println("Connecting to the distributed system and creating the cache.");

    // Create the cache which causes the cache-xml-file to be parsed
    System.out.println("Creating cache using Mbean.xml");
    Cache cache = new CacheFactory().set("name", "ManagedNode")
        .set("statistic-sampling-enabled", "true")                      
        .set("cache-xml-file", "xml/Managed-node.xml")
        .create();

    System.out.println("Created cache using Mbean.xml");

    // Create one sample region
    Region<Object, Object> region = cache.getRegion("/" + EXAMPLE_REGION_NAME);

    // Add some entries to region
    if (region != null) {
      System.out.println("Adding key and value to region in member " 
          + cache.getDistributedSystem().getDistributedMember().getId());
      for (int count = 0; count < 10; count++) {
        String key = "key" + count;
        String value = "value" + count;
        region.put(key, value);
      }
    }

    System.out.println("Retrieving service using cache ...");

    // Retrieve service in this node
    ManagementService service = ManagementService.getExistingManagementService(cache);
    System.out.println("Retrieved service.");

    System.out.println("Retrieving RegionMXBean for " + EXAMPLE_REGION_NAME);   
    
    Thread.sleep(JMX_WAIT_PERIOD_FOR_FEDERATION_UPDATE);
    
    RegionMXBean regionBean = service.getLocalRegionMBean("/" + EXAMPLE_REGION_NAME);

    if (regionBean != null) {
      System.out.println("Retrieved RegionMXBean for " + EXAMPLE_REGION_NAME);
      System.out.println("Entry count for " + EXAMPLE_REGION_NAME + " is: " + regionBean.getEntryCount());
      System.out.println("Start Manager Node and wait till it completes");
    } 
    else {
      System.out.println("Could not retrieve RegionMXBean for " + EXAMPLE_REGION_NAME);
    }

    // Wait till user input, after manager node completes
    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
    bufferedReader.readLine();

    // Close the cache and disconnect
    System.out.println("Closing the cache and disconnecting.");
    cache.close();
  }
}
