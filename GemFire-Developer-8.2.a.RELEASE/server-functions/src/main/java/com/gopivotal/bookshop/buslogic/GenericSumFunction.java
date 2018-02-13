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
		// TODO-01: Ensure the FunctionContext is a RegionFunctionContext and if so, cast it up to
        //          RegionFunctionContext
		
			// TODO-02: Get the argument from the FunctionContext representing the field to perform sum on

		    // TODO-03: Use the PartionRegionHelper to get all the local region data

            BigDecimal summable = BigDecimal.ZERO;
            // TODO-04: Iterate over the values in the local region data

				// TODO-05: Get the requested field, assert it is a Numeric type, cast it and add it to
				//          the summable variable defined above

		    // TODO-06: Return the final sum

	}

	// TODO-07: Add an over ridden method to force only primary buckets to be considered

	
	@Override
	public String getId() {
		return getClass().getName();
	}	
	
	@Override
	public void init(Properties props) {
		// Nothing to do	
	}

}
