package com.christian.shoppingList;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
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
	static ShoppingListAdapter shoppingListAdapter;
	
	static final String SELECTED_ITEM = "Selected Item";
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		shoppingListAdapter = new ShoppingListAdapter(getActivity());
		
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View rootView = inflater.inflate(R.layout.main_list_fragment,
				container, false);		
		
		searchBar = (EditText) rootView.findViewById(R.id.searchBar);
		shoppingList = (ListView) rootView.findViewById(R.id.masterListView);
		goButton = (Button) rootView.findViewById(R.id.goButton);
		addItemButton = (Button) rootView.findViewById(R.id.addItemButton);
		showLowItemsButton = (Button) rootView.findViewById(R.id.showLowListButton);
		
		shoppingList.setAdapter(shoppingListAdapter);
		
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

}
