package com.christian.shoppingList;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.TextView;

import com.christian.grocerylist.R;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.SaveCallback;


public class ItemListAdapter extends BaseAdapter implements Filterable {
	
	private LayoutInflater 			layoutInflater;
    private ArrayList<ParseObject>  masterList;
    private ArrayList<ParseObject>  filteredList;
    private SearchFilter 			filter;
    private CharSequence 			searchConstraint;
    
    private int 					selectedItemPosition;
    boolean 				showShoppingList;
    
    public ItemListAdapter(Context theContext)
    {
            layoutInflater = LayoutInflater.from(theContext);
            masterList = new ArrayList<ParseObject>();
            
            //the filtered list should reference the master list on creation
            filteredList = masterList;
            filter = new SearchFilter("name");
            
    }
    
    public void add(ParseObject newItem) {

		masterList.add(newItem);
		
		if(showShoppingList)
			showShoppingList(true);
		
		filter.filter(searchConstraint);
    }

    public void setData(ArrayList<ParseObject> data) {
    	masterList = data;
    	filteredList = data;
    }
    
    
	@Override
	public int getCount() {
		return filteredList.size();
	}

	@Override
	public ParseObject getItem(int position) {
		
		return (filteredList == masterList ) ? masterList.get(position) : filteredList.get(position);
	}

	@Override
	public long getItemId(int position) {
 
		return position;
	}
	
	public void setFilterType(String type) {
		filter.filterType = type;
	}
	
	@Override
	public View getView(int position, View oldView, ViewGroup parent) {

		
		if (oldView == null) {                                        
            oldView = layoutInflater.inflate(
                     R.layout.item_list_cell, parent, false);
		} 
        
		selectedItemPosition = position;
		
		//init the View objects associated with the cell's layout
        TextView itemNameTextField = (TextView) oldView.findViewById(R.id.itemCellName);
        TextView itemQtyTextField =  (TextView) oldView.findViewById(R.id.itemCellQuantity);
        TextView itemDeptTextField = (TextView) oldView.findViewById(R.id.itemCellDept);
        
        ParseObject selectedItem = (filteredList == masterList) ? masterList.get(position) : filteredList.get(position);
        
        if(selectedItem != null) {
        	
	        Button increaseQtyButton = (Button) oldView.findViewById(R.id.itemCellIncreaseQty);
	        Button decreaseQtyButton = (Button) oldView.findViewById(R.id.itemCellDecreaseQty);
	        Button deleteItemButton  = (Button) oldView.findViewById(R.id.itemCellDeleteItem);
	        ImageButton toggleStatusButton =(ImageButton) oldView.findViewById(R.id.itemCellStatusButton);
	        
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
	        
	        increaseQtyButton.setBackground(null);
	        decreaseQtyButton.setBackground(null);
	        deleteItemButton.setBackground(null);
	        toggleStatusButton.setBackground(null);
	        
	        //set the cells initial status color to green (in_stock)
	        changeStatusIcon(toggleStatusButton, selectedItem);
        }
        return oldView;

	}
	
	
	private void changeStatusIcon(ImageButton statusButton, ParseObject selectedItem) {
		
		int status = selectedItem.getInt("status");
		if(status == Item.IN_STOCK)
			statusButton.setImageResource(R.drawable.ic_ok_icon);
		else if(status == Item.LOW)
			statusButton.setImageResource(R.drawable.ic_warning_icon);
		else if(status == Item.OUT)
			statusButton.setImageResource(R.drawable.ic_outofstock_icon);
	}
	
	public void showShoppingList(boolean value) {
		showShoppingList = value;
		
		if(showShoppingList) {
			ArrayList<ParseObject> tmpList = new ArrayList<ParseObject>();
			
			for(ParseObject p : masterList) {
				Log.d("shopping_list", "" + p.getInt("status"));
				if(p.getInt("status") > Item.IN_STOCK)
					tmpList.add(p);
			}
			masterList = filteredList = tmpList;
			notifyDataSetChanged();
		}		
		
	}
	
	View.OnClickListener deleteItemListener = new View.OnClickListener() {
		
		@Override
		public void onClick(View eventSource) {
			
			ParseObject selectedItem = (ParseObject) eventSource.getTag();
			
			if(selectedItem == null)
				Log.d("test error", "selectedItem is null");
			
			
			masterList.remove(selectedItem);
			if(filteredList != masterList)
				filteredList.remove(selectedItemPosition);
			
			selectedItem.deleteInBackground();
			notifyDataSetChanged();
			
		}
	};
	
	View.OnClickListener decreaseQtyListener = new View.OnClickListener() {
		
		@Override
		public void onClick(View eventSource) {
			
			ParseObject selectedItem = (ParseObject) eventSource.getTag();
			
			if(selectedItem.getInt("quantity") > 0)
				selectedItem.increment("quantity", -1);
			
			notifyDataSetChanged();
			selectedItem.saveInBackground();
			
			View rootView = (View) eventSource.getParent();
			ImageButton imageButton = (ImageButton) rootView.findViewById(R.id.itemCellStatusButton);
			changeStatusIcon(imageButton, selectedItem);
			
			
		}
	};
	
	View.OnClickListener increaseQtyListener = new View.OnClickListener() {
		
		@Override
		public void onClick(View eventSource) {
			
			ParseObject selectedItem = (ParseObject) eventSource.getTag();
			
			selectedItem.increment("quantity", 1);
			notifyDataSetChanged();
			selectedItem.saveInBackground();

			View rootView = (View) eventSource.getParent();
			ImageButton imageButton = (ImageButton) rootView.findViewById(R.id.itemCellStatusButton);
			changeStatusIcon(imageButton, selectedItem);
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
			
			changeStatusIcon((ImageButton) eventSource, selectedItem);
		}
	};


	@Override
	public Filter getFilter() {
		// TODO Auto-generated method stub
		return filter;
	}
	
	private class SearchFilter extends Filter {
		
		private FilterResults filterResults;
		private String filterType; //name, status, department, etc.
		
		public SearchFilter(String type) {
			filterType = type;
			filterResults = new FilterResults();
		}
		
		public void setFilterType(String type) {
			filterType = type;
		}

		@Override
		protected FilterResults performFiltering(CharSequence constraint) {
			
			FilterResults results = new FilterResults();
			ArrayList<ParseObject> resultsList = new ArrayList<ParseObject>(masterList.size());
			searchConstraint = constraint;
			
			if(constraint != null && constraint.length() > 0) {
				
				
				for(ParseObject p : masterList) {
					Log.d("filter", p.getString(filterType) + " " + constraint.toString());
					
					if(p.getString(filterType).contains(constraint.toString().toLowerCase()))
						resultsList.add(p);
					else if(p.getString(filterType).equals(constraint.toString()))
						resultsList.add(p);
				}
				
				results.count = resultsList.size();
				results.values = resultsList;
			}
			else {
				results.count  = masterList.size();
				results.values = masterList;
			}
			
			return results;
		}
		
		@Override
		protected void publishResults(CharSequence constraint,
				FilterResults results) {
						
			filteredList = (ArrayList<ParseObject>) results.values;
			
			notifyDataSetChanged();
			
		}
		
	}
	
}
