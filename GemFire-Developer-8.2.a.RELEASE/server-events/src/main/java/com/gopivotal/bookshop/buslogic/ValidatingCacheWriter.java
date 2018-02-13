package com.gopivotal.bookshop.buslogic;

import java.util.Properties;

import com.gemstone.gemfire.cache.CacheWriterException;
import com.gemstone.gemfire.cache.Declarable;
import com.gemstone.gemfire.cache.EntryEvent;
import com.gemstone.gemfire.cache.Region;
import com.gemstone.gemfire.cache.query.Query;
import com.gemstone.gemfire.cache.query.QueryException;
import com.gemstone.gemfire.cache.query.QueryService;
import com.gemstone.gemfire.cache.query.SelectResults;
import com.gemstone.gemfire.cache.util.CacheWriterAdapter;
import com.gopivotal.bookshop.domain.BookMaster;

/**
 * This implementation provides a specific function for all new BookMaster entries. It validates that there are
 * no other entries having the same itemNumber. This in theory would ensure someone doesn't mistakenly try to create
 * two books with the same item number.
 * 
 * This implementation has factored out the validation part from the handling of invalid entries. It's the job of 
 * the CacheWriter method(s) to extract the value and region to validate and to handle an invalid case. It's the
 * job of validateNewValue to handle the logic of determining if the object in question is valid.
 *
 */
public class ValidatingCacheWriter extends CacheWriterAdapter<String, BookMaster> implements Declarable {

	@Override
	public void beforeCreate(EntryEvent<String, BookMaster> event) throws CacheWriterException {
		// Implement the functionality to obtain the correct value and validate it, issuing
        // a CacheWriterException if new entry is invalid.
	}

	/**
	 * New value is valid as long as the itemNumber doesn't exist
	 * 
	 * @param book New book value to validate
	 * @param books BookMaster region reference used to create a query
	 * @return True if new value is valid (i.e no other entry has the same itemNumber)
	 * @throws QueryException If this query fails for some reason
	 */
	private boolean validateNewValue(BookMaster book, Region books) throws QueryException {
		System.out.println("Validating Item: " + book);
		Object[] queryParams = new Object[1];
		queryParams[0] = book.getItemNumber();
		String queryString = "Select * from /BookMaster where itemNumber = $1";
		QueryService queryService = books.getRegionService().getQueryService();
		Query query = queryService.newQuery(queryString);
		SelectResults results = (SelectResults) query.execute(queryParams);
		
		return results.size() == 0;
	}
	
	@Override
	public void init(Properties props) {
		// Not passing parameters
		
	}

}
