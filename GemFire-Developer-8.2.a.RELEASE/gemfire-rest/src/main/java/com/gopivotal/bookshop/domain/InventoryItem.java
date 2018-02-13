package com.gopivotal.bookshop.domain;

import java.io.Serializable;

public class InventoryItem implements Serializable
{
	private static final long serialVersionUID = 7526471155622776147L;
	
	private int itemNumber;
	private float costToXYZ;
	private float priceToCustomer;
	private float quantityInStock;
	private String vendor;
	private String location;
	
	
	
	public InventoryItem(int itemNumber, float costToXYZ,
			float priceToCustomer, float quantityInStock, String vendor,
			String location)
	{
		super();
		this.itemNumber = itemNumber;
		this.costToXYZ = costToXYZ;
		this.priceToCustomer = priceToCustomer;
		this.quantityInStock = quantityInStock;
		this.vendor = vendor;
		this.location = location;
	}
	
	public String getVendor()
	{
		return vendor;
	}
	public void setVendor(String vendor)
	{
		this.vendor = vendor;
	}
	public String getLocation()
	{
		return location;
	}
	public void setLocation(String location)
	{
		this.location = location;
	}
	public void setItemNumber(int itemNumber)
	{
		this.itemNumber = itemNumber;
	}
	
	public float getCostToXYZ()
	{
		return costToXYZ;
	}
	public void setCostToXYZ(float costToXYZ)
	{
		this.costToXYZ = costToXYZ;
	}
	public float getPriceToCustomer()
	{
		return priceToCustomer;
	}
	public void setPriceToCustomer(float priceToCustomer)
	{
		this.priceToCustomer = priceToCustomer;
	}
	public float getQuantityInStock()
	{
		return quantityInStock;
	}
	public void setQuantityInStock(float quantityInStock)
	{
		this.quantityInStock = quantityInStock;
	}
	
	@Override
	public String toString()
	{
		return "InventoryItem [itemNumber=" + itemNumber + ", quantityInStock="
				+ quantityInStock + ", location=" + location + "]";
	}
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + itemNumber;
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
		InventoryItem other = (InventoryItem) obj;
		if (itemNumber != other.itemNumber)
			return false;
		return true;
	}

}
