package quickstart;

import com.gemstone.gemfire.cache.Operation;
import com.gemstone.gemfire.cache.query.CqEvent;
import com.gemstone.gemfire.cache.query.CqListener;

/**
 * A simple CqListener implementation.
 * 
 * @author GemStone Systems, Inc.
 */
public class SimpleCqListener implements CqListener {

  StringBuffer eventLog = new StringBuffer();

  private String userName = "";

  public SimpleCqListener() {
  }

  public SimpleCqListener(String userName) {
    if (userName != null && userName.length() > 0) {
      this.userName = "[" + userName + "]";
    }
  }

  @Override
  public void onEvent(CqEvent cqEvent) {
    Operation baseOperation = cqEvent.getBaseOperation();
    Operation queryOperation = cqEvent.getQueryOperation();

    String baseOp = "";
    String queryOp = "";

    if (baseOperation.isUpdate()) {
      baseOp = " Update";
    } else if (baseOperation.isCreate()) {
      baseOp = " Create";
    } else if (baseOperation.isDestroy()) {
      baseOp = " Destroy";
    } else if (baseOperation.isInvalidate()) {
      baseOp = " Invalidate";
    }

    if (queryOperation.isUpdate()) {
      queryOp = " Update";
    } else if (queryOperation.isCreate()) {
      queryOp = " Create";
    } else if (queryOperation.isDestroy()) {
      queryOp = " Destroy";
    }

    eventLog = new StringBuffer();
    eventLog.append("\n    " + this.userName
        + " CqListener:\n    Received cq event for entry: " + cqEvent.getKey()
        + ", " + ((cqEvent.getNewValue()) != null ? cqEvent.getNewValue() : "")
        + "\n" + "    With BaseOperation =" + baseOp + " and QueryOperation ="
        + queryOp + "\n");
    System.out.print(eventLog.toString());

  }

  @Override
  public void onError(CqEvent cqEvent) {
    // do nothing
  }

  @Override
  public void close() {
    // do nothing
  }

  public void printEventLog() {
    System.out.println(eventLog.toString());
  }
}
