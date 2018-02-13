package com.gopivotal.bookshop.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class Order implements Serializable
{
	private static final long serialVersionUID = 7526471155622776147L;

	private String orderNumber;
	private Date orderDate;
	private ArrayList <ProductItem> orderItems;
	private String customerNumber;
	private float totalPrice;
	
	
	public Order(String orderNumber, Date orderDate, ArrayList<ProductItem> orderItems, String customerNumber, float totalPrice)
	{
		super();
		this.orderNumber = orderNumber;
		this.orderDate = orderDate;
		this.orderItems = orderItems;
		this.customerNumber = customerNumber;
		this.totalPrice = totalPrice;
	}
	
	public float getTotalPrice()
	{
		return totalPrice;
	}


	public void setTotalPrice(float totalPrice)
	{
		this.totalPrice = totalPrice;
	}
	
	
	public String getCustomerNumber()
	{
		return customerNumber;
	}


	public void setCustomerNumber(String customerNumber)
	{
		this.customerNumber = customerNumber;
	}



	public void addOrderItem(ProductItem item)
	{
		if (orderItems == null)
		{
			orderItems = new ArrayList();
		}
		
		orderItems.add(item);
	}
	
	
	public String getOrderNumber()
	{
		return orderNumber;
	}
	
	
	public void setOrderNumber(String orderNumber)
	{
		this.orderNumber = orderNumber;
	}
	
	public Date getOrderDate()
	{
		return orderDate;
	}
	
	public void setOrderDate(Date orderDate)
	{
		this.orderDate = orderDate;
	}
	
	
	public ArrayList<ProductItem> getOrderItems()
	{
		return orderItems;
	}
	public void setOrderItems(ArrayList<ProductItem> orderItems)
	{
		this.orderItems = orderItems;
	}
	
	@Override
	public String toString() {
		return "Order [orderNumber=" + orderNumber
				+ ", orderDate=" + orderDate + ", customerNumber=" + customerNumber
				+ ", totalPrice=" + totalPrice + ", orderItems=" + orderItems + "]" ;
	}

}
