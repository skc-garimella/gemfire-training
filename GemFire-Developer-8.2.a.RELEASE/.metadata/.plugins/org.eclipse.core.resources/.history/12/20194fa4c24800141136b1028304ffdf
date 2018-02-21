package com.gopivotal.bookshop.buslogic;

import com.gemstone.gemfire.cache.query.CqEvent;
import com.gemstone.gemfire.cache.query.CqListener;
import com.gopivotal.bookshop.domain.BookOrder;

public class SimpleCQListener implements CqListener
{

	@Override
	public void close()
	{
		System.out.println("SimpleCQListener:Received Close Event");

	}

	@Override
	public void onError(CqEvent event)
	{
		System.out.println("SimpleCQListener:Received onError event");
		System.out.println("SimpleCQListener:Throwable: " + event.getThrowable());

	}

	@Override
	public void onEvent(CqEvent event)
	{
		// TODO-06: Implement functionality in this method to print out various items on the CqEvent object
		// Examples: the key, new value, the operation that triggered the event, etc.
		System.out.println("SimpleCQListener:Received event: " + event);
		System.out.println("SimpleCQListener:Key is:  " + (Integer)event.getKey());
		System.out.println("SimpleCQListener:Order is: " +  (BookOrder)event.getNewValue());
		System.out.println("SimpleCQListener:Base Operation is:  " + event.getBaseOperation());
		System.out.println("SimpleCQListener:Query Operation is: " + event.getQueryOperation());
		System.out.println("SimpleCQListener:Cq is: " + event.getCq());
		System.out.println("SimpleCQListener:DeltaValue: " + event.getDeltaValue());

	}

}

