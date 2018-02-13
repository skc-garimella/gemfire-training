package quickstart;

import java.util.Set;
import javax.management.ObjectName;
import com.gemstone.gemfire.cache.Cache;
import com.gemstone.gemfire.cache.CacheFactory;
import com.gemstone.gemfire.distributed.DistributedMember;
import com.gemstone.gemfire.management.ManagementService;
import com.gemstone.gemfire.management.RegionMXBean;

/**
 * In this example of Manager Node. It first creates a cache. For each member 
 * of Distributed System it retrieves RegionMXBeand and use its data.
 * </p>
 * 
 * @author GemStone Systems, Inc.
 * @since 7.0
 */
public class ManagerNode {

  public static final String EXAMPLE_REGION_NAME = "exampleRegion";

  public static void main(String[] args) throws Exception {

    //Set waiting period for federation in milliseconds
    final int JMX_WAIT_PERIOD_FOR_FEDERATION_UPDATE = 2500;
    
    System.out.println("Connecting to the distributed system and creating the cache.");
    
    // Create the cache which causes the cache-xml-file to be parsed
    Cache cache = new CacheFactory()
        .set("name", "ManagerNode")
        .set("statistic-sampling-enabled", "true")
        .set("jmx-manager-start","true")
        .set("jmx-manager","true")      
        .set("jmx-manager-port","0")
        .set("http-service-port","0")
        .set("cache-xml-file", "xml/Managed-node.xml")
        .create(); 
    
    // Retrieve service
    System.out.println("Retrieving service ...");
    
    // ManagementService.getManagementService() will create a service in case not already created
    // ManagementService.getExistingManagementService() will return existing one, otherwise null.
    ManagementService service = ManagementService.getManagementService(cache);
    System.out.println("Retrieved service ");
    
    // Retrieve distributed system members
    System.out.println("Retrieving distributed members ...");
    Set<DistributedMember> dsMembers = cache.getDistributedSystem().getAllOtherMembers();    
    System.out.println("Retrieved " +dsMembers.size()+ " distributed members ");
    
    // Sleep is needed for federation data to get published
    System.out.println("Retrieving RegionMXBean for members in DS ...");
    
    Thread.sleep(JMX_WAIT_PERIOD_FOR_FEDERATION_UPDATE);

    for (DistributedMember dsMember: dsMembers) {
      System.out.println("Retrieved RegionMXBean for member "+ dsMember.getId());    
      
      //Get region member bean name using region path. 
      ObjectName regionMBeanName = service.getRegionMBeanName(dsMember, "/" + EXAMPLE_REGION_NAME);
      System.out.println("regionMBeanName " + regionMBeanName);
      
      //Get Region MBean Proxy
      RegionMXBean regionMXBean = service.getMBeanInstance(regionMBeanName, RegionMXBean.class);

      // Validate regionMXbean
      if(regionMXBean != null){        
        System.out.println("Entry count in " + EXAMPLE_REGION_NAME + " is: " + regionMXBean.getEntryCount());
      }
      else {
        System.out.println("Retrieved RegionMXBean is null.");        
      }
    }
    
    // Close the cache and disconnect from GemFire distributed system
    System.out.println("Closing the cache and disconnecting.");
    cache.close();
    
    //Complete Managed Node by pressing enter in managed node console
    System.out.println("");
    System.out.println("Press enter in Managed Node");
  }
}
