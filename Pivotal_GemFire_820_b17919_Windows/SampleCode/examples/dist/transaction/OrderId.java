/**
 * 
 */
package transaction;

import java.io.Serializable;

/**
 * Key for the Order region
 */
public class OrderId implements Serializable {
  private final int orderId;
  private final CustomerId custId;
  public OrderId(int orderId, CustomerId custId) {
    this.orderId = orderId;
    this.custId = custId;
  }
  public int getOrderId() {
    return orderId;
  }
  public CustomerId getCustId() {
    return custId;
  }
  @Override
  public boolean equals(Object obj) {
    if (!(obj instanceof OrderId)) {
      return false;
    }
    OrderId other = (OrderId)obj;
    return this.orderId == other.orderId && this.custId.equals(other.custId);
  }
  @Override
  public int hashCode() {
    return orderId * 17 + custId.hashCode();
  }
  @Override
  public String toString() {
    return "OrderId: "+orderId;
  }
}
