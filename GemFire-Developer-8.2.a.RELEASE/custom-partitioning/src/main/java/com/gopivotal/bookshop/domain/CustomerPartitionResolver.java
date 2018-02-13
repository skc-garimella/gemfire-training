package com.gopivotal.bookshop.domain;

import java.io.Serializable;
import java.util.Properties;

import com.gemstone.gemfire.cache.Declarable;
import com.gemstone.gemfire.cache.EntryOperation;
import com.gemstone.gemfire.cache.PartitionResolver;

@SuppressWarnings("rawtypes")
public class CustomerPartitionResolver implements PartitionResolver, Serializable, Declarable
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void close()
	{
		//nothing to do when the Region closes
	}
	
	@Override
	public String getName()
	{
		return this.getClass().getName() + "PartitionResolver";
	}
	
	@Override
	public Serializable getRoutingObject(EntryOperation eo)
	{
		// TODO-03: Implement getRoutingObject to 
		return null;
	}

	//from Declarable
	@Override 
	public void init(Properties arg0)
	{
		// Auto generated - we're not using properties so, nothing to do
	}
	
}
