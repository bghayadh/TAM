package com.aliat.alm.services;

public class ItemTest {
	
	String itemCode;
	String itemPrice;
	String itemCat;
	
	public ItemTest()
	{
		
	}
	
	public ItemTest(String itemCode, String itemPrice, String itemCat) {
		super();
		this.itemCode = itemCode;
		this.itemPrice = itemPrice;
		this.itemCat = itemCat;
	}
	public String getItemCode() {
		return itemCode;
	}
	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}
	public String getItemPrice() {
		return itemPrice;
	}
	public void setItemPrice(String itemPrice) {
		this.itemPrice = itemPrice;
	}
	public String getItemCat() {
		return itemCat;
	}
	public void setItemCat(String itemCat) {
		this.itemCat = itemCat;
	}
	
	

}
