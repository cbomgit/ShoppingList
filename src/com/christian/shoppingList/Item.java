package com.christian.shoppingList;


public class Item {

	
	private String             	  name;				    //name of the item
	private Integer            	  quantity;			    //how many in stock (optional)
	private int             	  status;				//in stock/out of stock etc
	
	private String 			   	  department;			//department item was purchased in
	
	//private String message; short description of item, ie. on sale, buy extra, check price before, etc
	
	public static final int    	  IN_STOCK = 0;
	public static final int    	  OUT = 1;
	public static final int    	  LOW = 2;	
	public static final String [] statuses = {"In stock", 
											  "Running Low", 
											  "Out of Stock"};
	
	public Item(String itemName, Integer howMany, String dept) {
		
		name = itemName;
		quantity = howMany;
		department = dept;
		
		status = quantity == 0 ? OUT : IN_STOCK;
	}
	
	public String getName() {
		return name;
	}
	
	public Integer getQuantity() {
		return quantity;
	}
	
	public String getStockStatus() {
		return statuses[status];
	}
	
	public String getDepartment() {
		return department;
	}
	
	public void increaseQuantity() {
		//status should reflect a non-zero quantity
				
		if(++quantity > 0)
			status = IN_STOCK;
	}
	
	public void decreaseQuantity() {
		
		//status should reflect a zero quantity
		if(--quantity == 0)
			status = OUT;
	}
	
	public void toggleStatus() {
		
		if(++status > 2)
			status = 0;
			
	}
}
