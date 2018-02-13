package quickstart;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import com.gemstone.gemfire.cache.Cache;
import com.gemstone.gemfire.cache.CacheFactory;
import com.gemstone.gemfire.cache.Region;

/**
 * In this example the server starts on a port with security properties for
 * client requests. Please refer to the quickstart guide for instructions on how
 * to run this example.
 * </p>
 * Add $GEMFIRE/lib/gfSecurityImpl.jar to your CLASSPATH before running this
 * example.
 * </p>
 * 
 * @author GemStone Systems, Inc.
 * @since 6.5
 */
public class MultiuserSecurityServer {
  
  public static void main(String[] args) throws Exception {
    if (args.length != 0) {
      System.err.println("Usage: java  quickstart.MultiuserSecurityServer");
      System.exit(1);
    }
    
    System.out.println("\nThis example demonstrates Security functionalities.\n"
        + "This program is a server, listening on a port for client requests.\n"
        + "The client in this example is configured with security properties.");

    System.out.println("Setting security properties for server");
    System.out.println("\nConnecting to the distributed system and creating the cache.");

    // Create the cache which causes the cache-xml-file to be parsed
    Cache cache = new CacheFactory()
        .set("name", "SecurityServer")
        .set("cache-xml-file", "xml/MultiuserSecurityServer.xml")
        .set("security-client-accessor", "templates.security.DummyAuthorization.create")
        .set("security-client-accessor-pp", "templates.security.DummyAuthorization.create")
        .set("security-client-authenticator", "templates.security.DummyAuthenticator.create")
        .create();

    // Get the exampleRegion which is a root region
    Region<String, String> exampleRegion = cache.getRegion("exampleRegion");
    if (exampleRegion == null) {
      System.out.println("The region exampleRegion could not be created in cache.");
      return;
    }
    System.out.println("Example region, " + exampleRegion.getFullPath() + ", created in cache.");

    Region<String, String> functionServiceExampleRegion = cache.getRegion("functionServiceExampleRegion");
    if (functionServiceExampleRegion == null) {
      System.out.println("The region functionServiceExampleRegion could not be created in cache.");
      return;
    }
    
    for (int i = 0; i < 20; i++) {
      functionServiceExampleRegion.put("KEY_" + i, "VALUE_" + i);
    }
    System.out.println("Example region, " + functionServiceExampleRegion.getFullPath()
        + ", created in cache and populated.");

    System.out.println("\nPlease start the security client and press Enter when the client finishes all the operations.\n");
    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
    bufferedReader.readLine();

    // Close the cache and disconnect from GemFire distributed system
    System.out.println("Closing the cache and disconnecting.");
    cache.close();
    System.out.println("Closed the Server Cache");
  }
}
