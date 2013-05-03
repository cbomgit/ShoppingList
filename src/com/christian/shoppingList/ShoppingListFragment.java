package com.christian.shoppingList;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;

import com.christian.grocerylist.R;


public class ShoppingListFragment extends Fragment {

	private EditText searchBar;
	private ListView shoppingList;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.main_list_fragment,
				container, false);
		
		searchBar = (EditText) rootView.findViewById(R.id.searchBar);
		shoppingList = (ListView) rootView.findViewById(R.id.masterListView);
		
		shoppingList.requestFocus();
		
		return rootView;
	}	
}
