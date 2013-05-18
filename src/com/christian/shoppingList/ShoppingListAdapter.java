package com.christian.shoppingList;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.christian.grocerylist.R;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.SaveCallback;


public class ShoppingListAdapter extends BaseAdapter implements Filterable {
	
	private LayoutInflater layoutInflater;
    private ArrayList<ParseObject> myList;
    
    
    public ShoppingListAdapter(Context theContext)
    {
            layoutInflater = LayoutInflater.from(theContext);
            myList = new ArrayList<ParseObject>();
    }
    
    public void add(ParseObject newItem) {
    	myList.add(newItem);
    }

    public void setData(ArrayList<ParseObject> data) {
    	myList = data;
    }
	@Override
	public int getCount() {

		return myList.size();
	}

	@Override
	public ParseObject getItem(int position) {

		return myList.get(position);
	}

	@Override
	public long getItemId(int position) {
 
		return position;
	}
	
	public void uploadData() {
		
		ParseObject.saveAllInBackground(myList, new SaveCallback() {

			@Override
			public void done(ParseException e) {
				// TODO Auto-generated method stub
				if(e != null)
					System.out.println("Save failed");
			}
			
		});
	}
	
	public void refreshData() {
		
		ParseObject.fetchAllInBackground(myList, new FindCallback() {

			@Override
			public void done(List<ParseObject> objects, ParseException e) {

				if(e == null) {
					for(ParseObject p : objects)
						myList.add(p);
					notifyDataSetChanged();
				}
			}
			
		});
		
	}

	@Override
	public View getView(int position, View oldView, ViewGroup parent) {

		
		if (oldView == null) {                                        
            oldView = layoutInflater.inflate(
                     R.layout.item_list_cell, parent, false);
		} 
        
		//init the View objects associated with the cell's layout
        TextView itemNameTextField = (TextView) oldView.findViewById(R.id.itemCellName);
        TextView itemQtyTextField = (TextView) oldView.findViewById(R.id.itemCellQuantity);
        TextView itemDeptTextField = (TextView) oldView.findViewById(R.id.itemCellDept);
        
        ParseObject selectedItem = myList.get(position);
        
        Button increaseQtyButton = (Button) oldView.findViewById(R.id.itemCellIncreaseQty);
        Button decreaseQtyButton = (Button) oldView.findViewById(R.id.itemCellDecreaseQty);
        Button deleteItemButton  = (Button) oldView.findViewById(R.id.itemCellDeleteItem);
        Button toggleStatusButton =(Button) oldView.findViewById(R.id.itemCellStatusToggle);
        
        //populate the views with text about the Item
        itemNameTextField.setText(selectedItem.getString("name"));
        itemQtyTextField.setText("" + selectedItem.getInt("quantity"));
        itemDeptTextField.setText(selectedItem.getString("department"));
        
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
	
	
	private void colorViewByItemStatus(View cell, ParseObject selectedItem) {
		
		int status = selectedItem.getInt("status");
		
		if(status == Item.IN_STOCK)
			cell.setBackgroundColor(Color.GREEN);
		else if(status == Item.LOW)
			cell.setBackgroundColor(Color.YELLOW);
		else if(status == Item.OUT)
			cell.setBackgroundColor(Color.RED);
	}
	
	
	View.OnClickListener deleteItemListener = new View.OnClickListener() {
		
		@Override
		public void onClick(View eventSource) {
			
			ParseObject selectedItem = (ParseObject) eventSource.getTag();
			
			if(selectedItem == null)
				Log.d("test error", "selectedItem is null");
			
			myList.remove(selectedItem);
			selectedItem.deleteInBackground();
			notifyDataSetChanged();
			
			//TODO items in these listeners should either update to Parse immediately 
			//or not. User might specify manual push notifications
		}
	};
	
	View.OnClickListener decreaseQtyListener = new View.OnClickListener() {
		
		@Override
		public void onClick(View eventSource) {
			
			ParseObject selectedItem = (ParseObject) eventSource.getTag();
			
			selectedItem.increment("quantity", -1);
			notifyDataSetChanged();
			selectedItem.saveInBackground();
			colorViewByItemStatus((View) eventSource.getParent(), selectedItem);
			
			
		}
	};
	
	View.OnClickListener increaseQtyListener = new View.OnClickListener() {
		
		@Override
		public void onClick(View eventSource) {
			
			ParseObject selectedItem = (ParseObject) eventSource.getTag();
			
			selectedItem.increment("quantity", 1);
			notifyDataSetChanged();
			selectedItem.saveInBackground();
			colorViewByItemStatus((View) eventSource.getParent(), selectedItem);
						
		}
	};
	
	View.OnClickListener toggleStatusListener = new View.OnClickListener() {
		
		@Override
		public void onClick(View eventSource) {
			
			ParseObject selectedItem = (ParseObject) eventSource.getTag();
			
			int currentStatus = selectedItem.getInt("status");
			
			if(++currentStatus > 2)
				currentStatus = 0;
			
			selectedItem.put("status", currentStatus);
			selectedItem.saveInBackground();
			notifyDataSetChanged();
			
			colorViewByItemStatus((View)eventSource.getParent(), selectedItem);			
				
		}
	};


	@Override
	public Filter getFilter() {
		// TODO Auto-generated method stub
		return null;
	}
	
	private class SearchFilter extends Filter {
		
		private FilterResults filterResults;

		@Override
		protected FilterResults performFiltering(CharSequence constraint) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		protected void publishResults(CharSequence constraint,
				FilterResults results) {
			// TODO Auto-generated method stub
			
		}
		
	}
	
}
