package com.gopivotal.bookshop.buslogic;


import com.gemstone.gemfire.cache.client.ClientCache;
import com.gemstone.gemfire.cache.query.Query;
import com.gemstone.gemfire.cache.query.QueryService;
import com.gemstone.gemfire.cache.query.SelectResults;
import com.gemstone.gemfire.cache.query.Struct;
import com.gopivotal.bookshop.domain.BookOrder;
import com.gopivotal.bookshop.domain.Customer;

public class OQLInquirer
{

    private ClientCache cache;
	
    public OQLInquirer(ClientCache cache) {
    	this.cache = cache;
    }
	
	@SuppressWarnings("unchecked")
	public SelectResults<Customer> doCustomerQuery()
	{
		// TODO-02: Implement query by 1) creating the query string and 2) return the results of calling doQuery
		// Alternative ways to write the query
		String queryString ="";

		return  null;
		
	}
	
	@SuppressWarnings("unchecked")
	public SelectResults <Struct> doStructQuery ()
	{
		// TODO-04: implement the struct query to return Struct results
		String queryString =   "";
		
		return  null;

	}
	
	@SuppressWarnings("unchecked")
	public SelectResults<Customer> doJoin ()
	{
	    // TODO-06: Implement a join query to select customers having orders totaling more than $45.00.
		//          The key to this is properly constructing the query string
		String queryString = "";
		
		return null;

	}


	
	// TODO-01: Implement the doQuery method to use the supplied query string to execute, returning the SelectResults
	//          Catch any exceptions and re-throw as a QueryException.
	public SelectResults<?> doQuery (String queryString)
	{

		return null;
	}
	

}