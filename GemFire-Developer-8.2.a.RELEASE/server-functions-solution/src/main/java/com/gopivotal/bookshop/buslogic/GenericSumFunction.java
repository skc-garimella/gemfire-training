package com.gopivotal.bookshop.buslogic;

import java.math.BigDecimal;
import java.util.Properties;

import com.gemstone.gemfire.cache.Declarable;
import com.gemstone.gemfire.cache.Region;
import com.gemstone.gemfire.cache.execute.FunctionAdapter;
import com.gemstone.gemfire.cache.execute.FunctionContext;
import com.gemstone.gemfire.cache.execute.FunctionException;
import com.gemstone.gemfire.cache.execute.RegionFunctionContext;
import com.gemstone.gemfire.cache.partition.PartitionRegionHelper;
import com.gemstone.gemfire.pdx.PdxInstance;

public class GenericSumFunction extends FunctionAdapter implements Declarable {

	@Override
	public void execute(FunctionContext context) {
		// TODO-01: Ensure the FunctionContext is a RegionFunctionContext
		if (context instanceof RegionFunctionContext) {
			RegionFunctionContext rfc = (RegionFunctionContext) context;
			// TODO-02: Get the argument from the FunctionContext representing the field to perform sum on
			String fieldString = (String) rfc.getArguments();
			// TODO-03: Use the PartionRegionHelper to get all the local region data
			Region<Object, PdxInstance> localRegion = PartitionRegionHelper.getLocalDataForContext(rfc);
			BigDecimal summable = BigDecimal.ZERO;
			// TODO-04: Iterate over the values in the local region data
			for (PdxInstance item : localRegion.values()) {
                // TODO-05: Get the requested field, assert it is a Numeric type, cast it and add it to
                //          the summable variable defined above
				Object field = item.getField(fieldString);
				if ( field instanceof Float) {
					summable = summable.add(BigDecimal.valueOf((Float) field));
				} else {
					System.out.println("Field : " + fieldString + " is NOT a Float. Value= " + field);
				}
			}
			// TODO-06: Return the final sum
			System.out.println("Returning: " + summable);
			rfc.getResultSender().lastResult(summable);
		} else {
			throw new FunctionException("Function must be called as onRegion()");
		}

	}

	@Override
	public String getId() {
		return getClass().getName();
	}
	
	// TODO-07: Add an over ridden method to force only primary buckets to be considered
	@Override
	public boolean optimizeForWrite() {
		return true;
	}
	
	@Override
	public void init(Properties props) {
		// Nothing to do
		
	}

}
