package partitionedRegionQuerying;

import java.io.Serializable;

/**
 * A simple stock Portfolio. Instances of <code>Portfolio</code>
 * can be stored in a GemFire <code>Region</code> and their contents can be
 * queried using the GemFire query service.
 * 
 * <P>
 * 
 * This class is <code>Serializable</code> because we want it to be distributed
 * to multiple members of a distributed system.
 * 
 */

public class Portfolio implements Serializable {
  private String id;
  private String status;
  private String secId;
  public static String secIds[] = { "SUN", "IBM", "YHOO", "GOOG", "MSFT",
      "AOL", "APPL", "ORCL", "SAP", "DELL", "RHAT", "NOVL", "HP" };

  public Portfolio(int i) {
    id = "" + i;
    status = i % 2 == 0 ? "active" : "inactive";
    secId = secIds[i % secIds.length];
  }

  public String getId() {
    return id;
  }

  public String getStatus() {
    return status;
  }

  public String getSecId() {
    return secId;
  }

  public String toString() {
    return "Portfolio [id = " + id + " status = " + status + " secId = " + secId + "]";
  }

}
