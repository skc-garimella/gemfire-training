/*=========================================================================
 * Copyright (c) 2010-2014 Pivotal Software, Inc. All Rights Reserved.
 * This product is protected by U.S. and international copyright
 * and intellectual property laws. Pivotal products are covered by
 * one or more patents listed at http://www.pivotal.io/patents.
 *=========================================================================
 */
package quickstart;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import com.gemstone.gemfire.cache.Cache;
import com.gemstone.gemfire.cache.CacheFactory;

/**
 * A simple GemFire manager which manages only one node in the system, but the 
 * concept can be extended to a multiple-node distributed system as well.
 * </p>
 * The SystemAlertClient is a standard JMX client which connects to this 
 * manager and listens for GemFire alerts which are wrapped in JMX 
 * notifications.
 * </p>
 * 
 * @author GemStone Systems, Inc.
 * @since  8.0
 */
public class SystemAlertManager {
  
  private final BufferedReader stdinReader;

  public SystemAlertManager() {
    this.stdinReader = new BufferedReader(new InputStreamReader(System.in));
  }

  public static void main(String[] args) throws Exception {
    new SystemAlertManager().run(args);
  }

  public void run(String[] args) throws Exception {

    writeToStdout("This node acts as a JMX manager for a small, example GemFire system."); 
    writeToStdout("By default this class uses port 1099 to run Manager Agent. If required pass a different port as argument to main.");
    writeNewLine();
    writeToStdout("Connecting to the distributed system and creating a cache...");

    String port = "1099";
    if (args.length == 1) {
      port = args[0];
    }

    // Create the cache which causes a manager to get started
    Cache cache = new CacheFactory()
        .set("name", "SystemAlertManager")
        .set("jmx-manager", "true")
        .set("jmx-manager-start", "true")
        .set("jmx-manager-port", port)
        .set("jmx-manager-http-port", "0") // No need to start an HTTP server for this case
        .create();

    writeNewLine();
    
    writeToStdout("Please start SystemAlertClient now which connects to this manager and listens for notifications. After you have started SystemAlertClient press Enter."); 
    stdinReader.readLine();
    
    writeNewLine();
    writeToStdout("Please press Enter to log an example alert. GemFire alerts are automatically produced for actual error conditions.");
    stdinReader.readLine();
    
    logSevereAlert(cache);
    
    writeNewLine();
    writeToStdout("Check SystemAlertClient for notification.");
    
    writeNewLine();
    writeToStdout("Please press Enter to close.");
    stdinReader.readLine();
    
    writeToStdout("Closing the cache and disconnecting.");
    cache.close();

    writeNewLine();
    writeToStdout("Please press Enter in the SystemAlertClient.");
  }

  private static void writeToStdout(String msg) {
    System.out.println(msg);
  }

  private static void writeNewLine() {
    System.out.println();
  }
  
  private static void logSevereAlert(Cache cache) {
    cache.getLogger().severe("SystemAlertManager: A simple Alert.");
  }
}
