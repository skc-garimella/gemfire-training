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
		if (context instanceof RegionFunctionContext) {
			RegionFunctionContext rfc = (RegionFunctionContext) context;
			String fieldString = (String) rfc.getArguments();
			Region<Object, PdxInstance> localRegion = PartitionRegionHelper.getLocalDataForContext(rfc);
			BigDecimal summable = BigDecimal.ZERO;
			for (PdxInstance item : localRegion.values()) {
				Object field = item.getField(fieldString);
				if ( field instanceof Float) {
					summable = summable.add(BigDecimal.valueOf((Float) field));
				} else {
					System.out.println("Field : " + fieldString + " is NOT a Float. Value= " + field);
				}
			}
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
	
	@Override
	public boolean optimizeForWrite() {
		return true;
	}
	
	@Override
	public void init(Properties props) {
		// Nothing to do
		
	}

}
