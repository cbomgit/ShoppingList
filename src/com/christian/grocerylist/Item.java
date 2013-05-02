package com.christian.grocerylist;

import java.util.Calendar;
import java.util.Date;

public class Item {

	
	private String             name;				//name of the item
	private String[] 	       location;			//where item was purchased
	private Integer            quantity;			//how many in stock (optional)
	private int            	   status;				//in stock/out of stock etc
	private String             datePurchased;		//date purchased
	
	//private String message; short description of item, ie. on sale, buy extra, check price before, etc
	
	public static final int    IN_STOCK = 0;
	public static final int    OUT = 1;
	public static final int    LOW = 2;
	
	public static final int    DEFAULT_NUM_LOCATIONS = 3;
	
	public Item(String itemName, String itemLocation, Integer howMany) {
		
	}
	
	
}
