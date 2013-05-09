package com.christian.shoppingList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.christian.grocerylist.R;


public class ShoppingListFragment extends Fragment {

	private EditText searchBar;
	private ListView shoppingList;
	private Button goButton;
	private Button addItemButton;
	private Button showLowItemsButton;
	
	static final String SELECTED_ITEM = "Selected Item";
	static ShoppingListAdapter shoppingListAdapter;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View rootView = inflater.inflate(R.layout.main_list_fragment,
				container, false);
		
		shoppingListAdapter = new ShoppingListAdapter(getActivity());
		
		
		searchBar = (EditText) rootView.findViewById(R.id.searchBar);
		shoppingList = (ListView) rootView.findViewById(R.id.masterListView);
		goButton = (Button) rootView.findViewById(R.id.goButton);
		addItemButton = (Button) rootView.findViewById(R.id.addItemButton);
		showLowItemsButton = (Button) rootView.findViewById(R.id.showLowListButton);
		
		shoppingList.setAdapter(shoppingListAdapter);
		
		shoppingList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> adapter, View v, int pos,
					long id) {
				// TODO Auto-generated method stub
				startViewItemActivity(pos);
			}

			
			
		});
		
		shoppingList.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				
				startViewItemActivity(arg2);
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
			
			
		});
		
		shoppingList.requestFocus();
		
		addItemButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				
				startAddItemActivity();
			}
			
			
		});
		
		return rootView;
	}
	
	private void startAddItemActivity() {
		
		Intent i = new Intent(getActivity(), AddItemActivity.class);
		getActivity().startActivity(i);
	}
	
	private void startViewItemActivity(int selectedCellIndex) {
		Intent i = new Intent(getActivity(), ViewItemActivity.class);
		i.putExtra(SELECTED_ITEM, selectedCellIndex);
		getActivity().startActivity(i);
	}
}
