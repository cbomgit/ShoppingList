package com.christian.shoppingList;

import java.util.Calendar;
import java.util.Date;

public class Item {

	
	private String             name;				//name of the item
	private Integer            quantity;			//how many in stock (optional)
	private String             status;				//in stock/out of stock etc
	private String 			   department;			//department item was purchased in
	
	//private String message; short description of item, ie. on sale, buy extra, check price before, etc
	
	public static final String    IN_STOCK = "In Stock";
	public static final String    OUT = "Running Low";
	public static final String    LOW = "Completely Out!!";	
	
	public enum Status {
		
		IN_STOCK (0, "In Stock"),
		LOW		 (1, "Running Low"),
		OUT		 (2, "Completely Out!!!");
		
		private final int index;
		private final String stockStatus;
		
		Status(int ndx, String status) {
			index = ndx;
			stockStatus = status;
		}
		
		int getPosition() {
			return index;
		}
		
		String getStatus() {
			return stockStatus;
		}
	}
	
	public Item(String itemName, Integer howMany, String dept) {
		
		name = itemName;
		quantity = howMany;
		department = dept;
		
		status = IN_STOCK;
	}
	
	public String getName() {
		return name;
	}
	
	public Integer getQuantity() {
		return quantity;
	}
	
	public String getStockStatus() {
		return status;
	}
	
	public String getDepartment() {
		return department;
	}
}
