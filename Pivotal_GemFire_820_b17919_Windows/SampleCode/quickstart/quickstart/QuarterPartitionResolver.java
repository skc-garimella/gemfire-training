package quickstart;

import java.util.Calendar;
import java.util.Date;
import java.util.Properties;
import java.util.Set;

import com.gemstone.gemfire.cache.Declarable;
import com.gemstone.gemfire.cache.EntryOperation;
import com.gemstone.gemfire.cache.FixedPartitionResolver;

/**
 * Partitions data into two "quarters."
 * </p>
 * 
 * @author GemStone Systems, Inc.
 */
public class QuarterPartitionResolver implements FixedPartitionResolver<Date, String>, Declarable{

  @Override
  public String getPartitionName(EntryOperation<Date, String> opDetails, Set<String> targetPartitions) {
    Date date = opDetails.getKey();
    Calendar cal = Calendar.getInstance();
    cal.setTime(date);
    int month = cal.get(Calendar.MONTH);
    if (month == 0 || month == 1 || month == 2 || month == 3 || month == 4 || month == 5) {
      return "Q1";
    }
    else if (month == 6 || month == 7 || month == 8 || month == 9 || month == 10 || month == 11) {
      return "Q2";
    }
    else {
      return "Invalid Quarter";
    }
  }

  @Override
  public String getName() {
    return "QuarterPartitionResolver";
  }

  @Override
  public Object getRoutingObject(EntryOperation<Date, String> opDetails) {
    Date date = opDetails.getKey();
    Calendar cal = Calendar.getInstance();
    cal.setTime(date);
    int month = cal.get(Calendar.MONTH);
    return month;
  }

  @Override
  public void close() {
    // do nothing
  }

  @Override
  public void init(Properties props) {
    // do nothing
  }
}
