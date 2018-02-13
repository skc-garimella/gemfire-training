/*=========================================================================
 * Copyright (c) 2010-2014 Pivotal Software, Inc. All Rights Reserved.
 * This product is protected by U.S. and international copyright
 * and intellectual property laws. Pivotal products are covered by
 * one or more patents listed at http://www.pivotal.io/patents.
 *=========================================================================
 */
package quickstart;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;

import javax.management.InstanceNotFoundException;
import javax.management.MBeanServerConnection;
import javax.management.MalformedObjectNameException;
import javax.management.Notification;
import javax.management.NotificationFilter;
import javax.management.NotificationListener;
import javax.management.ObjectName;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;

/**
 * This is a standard JMX client which shows how to connect to a GemFire 
 * manager and listen for JMX notifications. All alerts in GemFire are wrapped 
 * in JMX notifications with the type "system.alert". Users can define a filter 
 * for this type of JMX notification.
 * </p>
 * This example does not use any GemFire API and depicts how a standard JMX 
 * client can monitor GemFire notifications.
 * </p>
 * 
 * @author GemStone Systems, Inc.
 * @since  8.0
 */
public class SystemAlertClient {

  /**
   * The ObjectName of the GemFire DistributedSystemMXBean.
   */
  private static final String DISTRIBUTED_SYSTEM_OBJECT_NAME = "GemFire:service=System,type=Distributed";
  
  /**
   * Notification type for a GemFire system alert.
   */
  private static final String GEMFIRE_SYSTEM_ALERT_NOTIFICATION_TYPE = "system.alert";
  
  /**
   * The GemFire log level of the alert. This is also defined in 
   * com.gemstone.gemfire.management.JMXNotificationUserData
   */
  private static final String ALERT_LEVEL = "AlertLevel";
  
  /**
   * The member of the distributed system that issued the alert, or null if the 
   * issuer is no longer a member of the distributed system. This is also 
   * defined in com.gemstone.gemfire.management.JMXNotificationUserData
   */
  private static final String MEMBER = "Member";
  
  /** 
   * The thread which issued the alert. This is also defined in 
   * com.gemstone.gemfire.management.JMXNotificationUserData
   */
  private static final String THREAD = "Thread";
  
  private final BufferedReader stdinReader;

  public SystemAlertClient() {
    this.stdinReader = new BufferedReader(new InputStreamReader(System.in));
  }

  public static void main(String[] args) throws Exception {
    new SystemAlertClient().run(args);
  }

  public void run(String[] args) throws Exception {

    writeToStdout("This is a JMX client which connects to a GemFire JMX Manager and listens for JMX notifications."); 
    writeToStdout("It filters notifications based on the notification type.");
    writeToStdout("By default it connects to port 1099 , but user can pass required port which has been passed as an argument to SystemAlertManager.");
    writeNewLine();
    writeToStdout("Connecting to the Manager and listening for notifications..."); 

    String port = "1099";
    if (args.length == 1) {
      port = args[0];
    }

    JMXServiceURL url = new JMXServiceURL("service:jmx:rmi:///jndi/rmi://:"+port+"/jmxrmi");
    JMXConnector jmxc = JMXConnectorFactory.connect(url, null);

    MBeanServerConnection mbsc = jmxc.getMBeanServerConnection();
    addNotificationListener(mbsc);

    writeNewLine();
    writeToStdout("Please press Enter in SystemAlertManager to log an example alert.");
    writeToStdout("After this SystemAlertClient prints the alert, press Enter to exit.");
    writeNewLine();
    
    stdinReader.readLine();
    
    writeNewLine();
    writeToStdout("Exiting.");
  }

  private static void addNotificationListener(MBeanServerConnection mbsc) {
    try {
      ObjectName distributedSystemObjectName = new ObjectName(DISTRIBUTED_SYSTEM_OBJECT_NAME);

      NotificationFilter notificationFilter = new NotificationFilter() {

        private static final long serialVersionUID = 1L;

        @Override
        public boolean isNotificationEnabled(Notification notification) {
          return notification.getType().equals(GEMFIRE_SYSTEM_ALERT_NOTIFICATION_TYPE);
        }
      };

      NotificationListener listener = new NotificationListener() {

        @Override
        public void handleNotification(Notification notification, Object handback) {
          System.out.println("Handling JMX Notification...");
          
          @SuppressWarnings("unchecked")
          Map<String, String> userData = (Map<String, String>) notification.getUserData();
          
          //JMXNotificationUserData Section of Notification
          String alertLevel = userData.get(ALERT_LEVEL);
          writeToStdout(ALERT_LEVEL + ": " + alertLevel);
          
          String member = userData.get(MEMBER);
          writeToStdout(MEMBER + ": " + member);
          
          String thread = userData.get(THREAD);          
          writeToStdout(THREAD + ": " + thread);
          
          String message = (String)notification.getMessage();
          writeToStdout("Notification Message: " + message);
          
          String source = (String)notification.getSource();
          writeToStdout("Notification Source: " + source);
          
          long alertTime = notification.getTimeStamp();
          writeToStdout("Notification Time: " + alertTime);
        }
      };

      mbsc.addNotificationListener(distributedSystemObjectName, listener, notificationFilter, null);

    } catch (MalformedObjectNameException e1) {
      e1.printStackTrace();
    } catch (NullPointerException e1) {
      e1.printStackTrace();
    } catch (InstanceNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private static void writeToStdout(String msg) {
    System.out.println(msg);
  }

  private static void writeNewLine() {
    System.out.println();
  }
}
