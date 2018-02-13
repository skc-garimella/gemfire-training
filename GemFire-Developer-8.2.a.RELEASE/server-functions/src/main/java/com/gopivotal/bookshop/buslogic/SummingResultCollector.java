package com.gopivotal.bookshop.buslogic;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.concurrent.TimeUnit;

import com.gemstone.gemfire.cache.execute.FunctionException;
import com.gemstone.gemfire.cache.execute.ResultCollector;
import com.gemstone.gemfire.distributed.DistributedMember;

public class SummingResultCollector implements
		ResultCollector<Serializable, Serializable> {

	BigDecimal total =  BigDecimal.ZERO;
	
	@Override
	public void addResult(DistributedMember memberID,
			Serializable resultOfSingleExecution) {
		total = total.add((BigDecimal) resultOfSingleExecution);
	}

	@Override
	public void clearResults() {
		total = BigDecimal.ZERO;
		
	}

	@Override
	public void endResults() {
		// No special processing required.
		
	}

	@Override
	public Serializable getResult() throws FunctionException {
		return total.setScale(2, BigDecimal.ROUND_HALF_UP);
	}

	@Override
	public Serializable getResult(long timeout, TimeUnit unit)
			throws FunctionException, InterruptedException {
		return this.getResult();
	}

}
