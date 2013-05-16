package com.christian.shoppingList;

import java.util.ArrayList;
import java.util.LinkedList;

import com.christian.grocerylist.R;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;


public class ShoppingListAdapter extends ArrayAdapter<Item> {
	
	private LayoutInflater layoutInflater;
    private ArrayList<Item> myList;
    
    
    public ShoppingListAdapter(Context theContext)
    {
    		super(theContext, 10);
            layoutInflater = LayoutInflater.from(theContext);
            myList = new ArrayList<Item>();
    }
    
    public void add(Item newItem) {
    	myList.add(newItem);
    }

    public void setData(ArrayList<Item> data) {
    	myList = data;
    }
	@Override
	public int getCount() {

		return myList.size();
	}

	@Override
	public Item getItem(int position) {

		return myList.get(position);
	}

	@Override
	public long getItemId(int position) {
 
		return position;
	}

	@Override
	public View getView(int position, View oldView, ViewGroup parent) {

		
		if (oldView == null) {                                        
            oldView = layoutInflater.inflate(
                     R.layout.item_list_cell, parent, false);
		} 
        
		//init the View objects associated with the cell's layout
        TextView itemName = (TextView) oldView.findViewById(R.id.itemCellName);
        TextView itemQuantity = (TextView) oldView.findViewById(R.id.itemCellQuantity);
        TextView itemDept = (TextView) oldView.findViewById(R.id.itemCellDept);
        Item selectedItem = myList.get(position);
        
        Button increaseQtyButton = (Button) oldView.findViewById(R.id.itemCellIncreaseQty);
        Button decreaseQtyButton = (Button) oldView.findViewById(R.id.itemCellDecreaseQty);
        Button deleteItemButton  = (Button) oldView.findViewById(R.id.itemCellDeleteItem);
        Button toggleStatusButton =(Button) oldView.findViewById(R.id.itemCellStatusToggle);
        
        //populate the views with text about the Item
        itemName.setText(selectedItem.getName());
        itemQuantity.setText(selectedItem.getQuantity().toString());
        itemDept.setText(selectedItem.getDepartment());
        
        //set the button listeners
        increaseQtyButton.setOnClickListener(increaseQtyListener);
        decreaseQtyButton.setOnClickListener(decreaseQtyListener);
        deleteItemButton.setOnClickListener(deleteItemListener);
        toggleStatusButton.setOnClickListener(toggleStatusListener);
        
        //set the button tags
        increaseQtyButton.setTag(selectedItem);
        decreaseQtyButton.setTag(selectedItem);
        deleteItemButton.setTag(selectedItem);
        toggleStatusButton.setTag(selectedItem);
        
        //set the cells initial status color to green (in_stock)
        colorViewByItemStatus(oldView, selectedItem);
                   
        return oldView;

	}
	
	
	View.OnClickListener deleteItemListener = new View.OnClickListener() {
		
		@Override
		public void onClick(View eventSource) {
			
			Item selectedItem = (Item) eventSource.getTag();
			
			if(selectedItem == null)
				Log.d("test error", "selectedItem is null");
			myList.remove(selectedItem);
			notifyDataSetChanged();
			
			//TODO items in these listeners should either update to Parse immediately 
			//or not. User might specify manual push notifications
		}
	};
	
	View.OnClickListener decreaseQtyListener = new View.OnClickListener() {
		
		@Override
		public void onClick(View eventSource) {
			
			Item selectedItem = (Item) eventSource.getTag();
			
			selectedItem.decreaseQuantity();
			notifyDataSetChanged();
			
			colorViewByItemStatus((View) eventSource.getParent(), selectedItem);
			
			
		}
	};
	
	View.OnClickListener increaseQtyListener = new View.OnClickListener() {
		
		@Override
		public void onClick(View eventSource) {
			
			Item selectedItem = (Item) eventSource.getTag();
			
			selectedItem.increaseQuantity();
			notifyDataSetChanged();
			
			colorViewByItemStatus((View)eventSource.getParent(), selectedItem);
			
			
		}
	};
	
	View.OnClickListener toggleStatusListener = new View.OnClickListener() {
		
		@Override
		public void onClick(View eventSource) {
			
			Item selectedItem = (Item) eventSource.getTag();
			
			selectedItem.toggleStatus();
			notifyDataSetChanged();
			
			colorViewByItemStatus((View)eventSource.getParent(), selectedItem);
			
			
				
		}
	};
	
	private void colorViewByItemStatus(View cell, Item selectedItem) {
		
		String status = selectedItem.getStockStatus();
		
		if(status.equals(Item.statuses[Item.IN_STOCK]))
			cell.setBackgroundColor(Color.GREEN);
		else if(status.equals(Item.statuses[Item.LOW]))
			cell.setBackgroundColor(Color.YELLOW);
		else if(status.equals(Item.statuses[Item.OUT]))
			cell.setBackgroundColor(Color.RED);
	}
}
