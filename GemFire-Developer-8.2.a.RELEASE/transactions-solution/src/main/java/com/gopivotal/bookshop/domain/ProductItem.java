package com.gopivotal.bookshop.domain;

import java.io.Serializable;

public class ProductItem implements Serializable
{
	private static final long serialVersionUID = 7526471155622776147L;

	private String itemNumber;
	private String description;
	private float retailCost;
		
	public ProductItem(String itemNumber, String description, float retailCost)
	{
		super();
		this.itemNumber = itemNumber;
		this.description = description;
		this.retailCost = retailCost;
	}
	

	public String getItemNumber()
	{
		return itemNumber;
	}
	public void setItemNumber(String itemNumber)
	{
		this.itemNumber = itemNumber;
	}
	public String getDescription()
	{
		return description;
	}
	public void setDescription(String description)
	{
		this.description = description;
	}
	public float getRetailCost()
	{
		return retailCost;
	}
	public void setRetailCost(float retailCost)
	{
		this.retailCost = retailCost;
	}
	
	@Override
	public String toString()
	{
		return "ProductItem:  [itemNumber=" + itemNumber + ", Description=" + description
				+ "]";
	}
}
