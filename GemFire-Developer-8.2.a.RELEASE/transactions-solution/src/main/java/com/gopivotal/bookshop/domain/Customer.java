package com.gopivotal.bookshop.domain;


import java.io.Serializable;
import java.util.ArrayList;

public class Customer implements Serializable
{
	private static final long serialVersionUID = 7526471155622776147L;
	
	private String customerNumber;
		
	private String firstName;
	
	private String lastName;
	
	private String phoneNumber;
	
	private Address address;
	
	private ArrayList <Order> myOrders;
	
	
	public Customer(String customerNumber, String firstName, String lastName, String phone)
	{
		super();
		this.customerNumber = customerNumber;
		this.firstName = firstName;
		this.lastName = lastName;
		this.phoneNumber =  phone;
	}
	
	
	public Customer(String customerNumber, String firstName, String lastName, String phone, Address address, ArrayList <Order> orders)
	{
		super();
		this.customerNumber = customerNumber;
		this.firstName = firstName;
		this.lastName = lastName;
		this.phoneNumber = phone;
		this.address = address;
		this.myOrders = orders;
	}
	
	public void addAddress(Address addr)
	{
		if (address == null)
		{
			address = addr;
		}
		
	}
	
	public void addOrder(Order order)
	{
		if (myOrders == null)
		{
			myOrders = new ArrayList<Order>();
		}
		
		myOrders.add(order);
	}

	public ArrayList <Order> getMyOrders()
	{
		return myOrders;
	}

	public void setMyOrders(ArrayList <Order> orders)
	{
		this.myOrders = orders;
	}

	
	public String getCustomerNumber()
	{
		return customerNumber;
	}
	public void setCustomerNumber(String customerNumber)
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
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public Address getAddress()
	{
		return address;
	}
	public void setAddress(Address address)
	{
		this.address = address;
	}
	
	
	@Override
	public String toString()
	{
		return "Customer [customerNumber=" + customerNumber
				+ ", firstName=" + firstName + ", lastName=" + lastName
				+ ", Phone=" + phoneNumber + ", Address=" + address + "]" ;
	}
	
}
