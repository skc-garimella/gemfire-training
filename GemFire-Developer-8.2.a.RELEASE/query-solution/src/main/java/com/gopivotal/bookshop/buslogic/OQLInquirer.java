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
		String queryString1 ="SELECT * FROM /Customer";
		// A more verbose version of the query
		String queryString2 ="IMPORT com.gopivotal.bookshop.domain.Customer; SELECT DISTINCT c FROM /Customer.values c TYPE Customer";

		return  (SelectResults <Customer>)doQuery(queryString1);
		
	}
	
	@SuppressWarnings("unchecked")
	public SelectResults <Struct> doStructQuery ()
	{
		// TODO-04: implement the struct query to return Struct results
		String queryString1 =   "SELECT c.customerNumber, c.firstName, c.lastName FROM /Customer c ";
		
		// A more verbose version of the query
		String queryString2 = 	"IMPORT com.gopivotal.bookshop.domain.Customer; " +
								"SELECT DISTINCT c.customerNumber, c.firstName, c.lastName " +
								"FROM /Customer.values c TYPE Customer";
		
		return  (SelectResults <Struct>)doQuery(queryString1);

	}
	
	@SuppressWarnings("unchecked")
	public SelectResults<Customer> doJoin ()
	{
	    // TODO-06: Implement a join query to select customers having orders totaling more than $45.00.
		//          The key to this is properly constructing the query string
		String queryString1 = "select distinct c " +
                              "from /Customer c, /BookOrder o " +
                              "where c.customerNumber = o.customerNumber and  o.totalPrice > 45.00";

		// A more verbose version of the query
		String queryString2 = 	"IMPORT com.gopivotal.bookshop.domain.Customer; " +
								"IMPORT com.gopivotal.bookshop.domain.BookOrder; " +
								"IMPORT java.lang.Integer; " +
								"SELECT DISTINCT c " +
								"FROM /Customer.values c TYPE Customer," +
								"/BookOrder.values o TYPE BookOrder " +
								"WHERE c.customerNumber = o.customerNumber " +
								"AND  o.totalPrice > 45.00";
		
		return (SelectResults <Customer>)doQuery(queryString1);

	}


	
	// TODO-01: Implement the doQuery method to use the supplied query string to execute, returning the SelectResults
	//          Catch any exceptions and re-throw as a QueryException.
	public SelectResults<?> doQuery (String queryString)
	{
		QueryService qs = cache.getQueryService();

		Query q = qs.newQuery(queryString);

		SelectResults<?> results = null;

		try
		{
			results = (SelectResults<?>)q.execute();
		}
		catch (Exception e)
		{
			throw new QueryException(e);
			
		}
		return results;
	}
	

}
