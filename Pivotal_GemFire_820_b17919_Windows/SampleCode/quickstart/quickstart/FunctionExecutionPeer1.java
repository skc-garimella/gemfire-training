package quickstart;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import com.gemstone.gemfire.cache.Cache;
import com.gemstone.gemfire.cache.CacheFactory;
import com.gemstone.gemfire.cache.Region;
import com.gemstone.gemfire.cache.execute.FunctionService;

/**
 * This is the peer to which FunctionExecutionPeer2 connects for function execution.
 * This peer executes the function execution request and returns the results to
 * the requesting peer.
 * </p>
 * 
 * @author GemStone Systems, Inc.
 * @since 6.0
 */
public class FunctionExecutionPeer1 {

  public static final String EXAMPLE_REGION_NAME = "exampleRegion";

  private final BufferedReader stdinReader;

  public FunctionExecutionPeer1() {
    this.stdinReader = new BufferedReader(new InputStreamReader(System.in));
  }

  public static void main(String[] args) throws Exception {
    new FunctionExecutionPeer1().run();
  }

  public void run() throws Exception {

    writeToStdout("Peer to which other peer sends request for function Execution");
    writeToStdout("Connecting to the distributed system and creating the cache... ");

    // Create the cache which causes the cache-xml-file to be parsed
    Cache cache = new CacheFactory()
        .set("name", "FunctionExecutionPeer1")
        .set("cache-xml-file", "xml/FunctionExecutionPeer.xml")
        .create();

    // Get the exampleRegion
    Region<String, String> exampleRegion = cache.getRegion(EXAMPLE_REGION_NAME);
    writeToStdout("Example region \"" + exampleRegion.getFullPath() + "\" created in cache.");

    writeToStdout("Registering the function MultiGetFunction on Peer");
    MultiGetFunction function = new MultiGetFunction();
    FunctionService.registerFunction(function);

    writeToStdout("Please start Other Peer And Then Press Enter to continue.");
    stdinReader.readLine();
    
    System.out.println("Closing the cache and disconnecting.");
    cache.close();
  }

  private static void writeToStdout(String msg) {
    System.out.println(msg);
  }
}
