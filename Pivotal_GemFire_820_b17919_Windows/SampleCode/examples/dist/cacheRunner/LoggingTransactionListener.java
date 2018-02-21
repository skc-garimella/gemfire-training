package cacheRunner;

import com.gemstone.gemfire.cache.*;
import java.util.*;

/**
 * A GemFire <code>TransactionListener</code> that logs information
 * about the events it receives.
 *
 * @author GemStone Systems, Inc.
 * @since 4.0
 */
public class LoggingTransactionListener extends LoggingCacheCallback
  implements TransactionListener {

  /**
   * Zero-argument constructor required for declarative cache XML
   * file. 
   */
  public LoggingTransactionListener() {
    super();
  }

  //////////////////////  Instance Methods  //////////////////////

  /**
   * Logs information about a <code>TransactionEvent</code>
   *
   * @param kind
   *        The kind of event to be logged
   */
  protected void log(String kind, TransactionEvent event) {
    StringBuffer sb = new StringBuffer();
    sb.append(kind);
    sb.append(" in transaction ");
    sb.append(event.getTransactionId());
    sb.append("\n");

    List<CacheEvent<?, ?>> events = event.getEvents();
    sb.append(events.size());
    sb.append(" Events\n");
    for (CacheEvent<?, ?> entryEvent : events) {
    	sb.append(format((EntryEvent<?, ?>) entryEvent));
    }
    sb.append("\n");

    log(sb.toString(), event.getCache());
  }

  public void afterCommit(TransactionEvent event) {
    log("TransactionListener.afterCommit", event);
  }

  public void afterFailedCommit(TransactionEvent event) {
    log("TransactionListener.afterFailedCommit", event);
  }

  public void afterRollback(TransactionEvent event) {
    log("TransactionListener.afterRollback", event);
  }

}
