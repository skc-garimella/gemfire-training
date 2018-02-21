/**
 * 
 */
package transaction;

import java.io.Serializable;

/**
 * Key for the customer region
 */
public class CustomerId implements Serializable {
  private final int custId;
  public CustomerId(int custId) {
    this.custId = custId;
  }
  public int getCustId() {
    return custId;
  }
  @Override
  public boolean equals(Object obj) {
    if (!(obj instanceof CustomerId)) {
      return false;
    }
    return ((CustomerId)obj).custId == custId;
  }
  @Override
  public int hashCode() {
    return custId * 31;
  }
  @Override
  public String toString() {
    return "CustId: "+custId;
  }
}
