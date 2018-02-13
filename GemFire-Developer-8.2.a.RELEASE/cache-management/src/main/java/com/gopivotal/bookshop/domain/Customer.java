package com.gopivotal.bookshop.domain;


import java.io.Serializable;
import java.util.ArrayList;

public class Customer implements Serializable
{
	private static final long serialVersionUID = 7526471155622776147L;

	  
	private Integer customerNumber;
		
	private String firstName;
	
	private String lastName;
	
	private ArrayList <Address> addresses;
	
	private ArrayList <Integer> myBookOrders;
	
	
	
	public Customer(Integer customerNumber, String firstName,
			String lastName, ArrayList<Address> addresses,
			ArrayList <Integer> orders)
	{
		super();
		this.customerNumber = customerNumber;
		this.firstName = firstName;
		this.lastName = lastName;
		this.addresses = addresses;
		this.myBookOrders = orders;
	}
	
	public void addAddress(Address addr)
	{
		if (addresses == null)
		{
			addresses = new ArrayList<Address>();
		}
		addresses.add(addr);
	}
	
	public void addOrder(Integer orderKey)
	{
		if (myBookOrders == null)
		{
			myBookOrders = new ArrayList<Integer>();
		}
		
		myBookOrders.add(orderKey);
	}

	public ArrayList <Integer> getMyBookOrders()
	{
		return myBookOrders;
	}

	public void setMyBookOrders(ArrayList <Integer> orders)
	{
		this.myBookOrders = orders;
	}

	public Customer(Integer customerNumber, String firstName,
			String lastName, ArrayList<Address> addresses)
	{
		super();
		this.customerNumber = customerNumber;
		this.firstName = firstName;
		this.lastName = lastName;
		this.addresses = addresses;
	}

	public Integer getCustomerNumber()
	{
		return customerNumber;
	}
	public void setCustomerNumber(Integer customerNumber)
	{
		this.customerNumber = customerNumber;
	}
	public String getFirstName()
	{
		return firstName;
	}
	public void setFirstName(String firstName)
	{
		this.firstName = firstName;
	}
	public String getLastName()
	{
		return lastName;
	}
	public void setLastName(String lastName)
	{
		this.lastName = lastName;
	}
	public ArrayList<Address> getAddresses()
	{
		return addresses;
	}
	public void setAddresses(ArrayList<Address> addresses)
	{
		this.addresses = addresses;
	}
	
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime
				* result
				+ ((customerNumber == null) ? 0 : customerNumber.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Customer other = (Customer) obj;
		if (customerNumber == null)
		{
			if (other.customerNumber != null)
				return false;
		} else if (!customerNumber.equals(other.customerNumber))
			return false;
		return true;
	}

	@Override
	public String toString()
	{
		return "Customer [customerNumber=" + customerNumber
				+ ", firstName=" + firstName + ", lastName=" + lastName
				+ "]";
	}
	
}
