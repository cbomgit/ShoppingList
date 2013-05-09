package com.christian.shoppingList;

import java.util.ArrayList;

import com.christian.grocerylist.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ShoppingListAdapter extends BaseAdapter {
	
	private LayoutInflater layoutInflater;
    private ArrayList<Item> shoppingList;
    
    public ShoppingListAdapter(Context theContext)
    {
            layoutInflater = LayoutInflater.from(theContext);
            shoppingList = new ArrayList<Item>();
    }
    
    public void add(Item newItem) {
    	shoppingList.add(newItem);
    }

    
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return shoppingList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return shoppingList.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View oldView, ViewGroup parent) {
		// TODO Auto-generated method stub
		
		if (oldView == null) {                                        
            oldView = layoutInflater.inflate(
                     R.layout.item_list_cell, parent, false);
		} 
        
        TextView itemName = (TextView) oldView.findViewById(R.id.itemName);
        TextView itemQuantity = (TextView) oldView.findViewById(R.id.itemQuantity);
        TextView itemStatus = (TextView) oldView.findViewById(R.id.itemStockStatus);
        TextView itemDept = (TextView) oldView.findViewById(R.id.itemDepartment);
        
        Item selectedItem = shoppingList.get(position);
        
        itemName.setText(selectedItem.getName());
        itemQuantity.setText(selectedItem.getQuantity().toString());
        itemStatus.setText(selectedItem.getStockStatus());
        itemDept.setText(selectedItem.getDepartment());
        
        return oldView;

	}

}
