package com.christian.shoppingList;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.christian.grocerylist.R;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;


public class ShoppingListFragment extends Fragment {

	private EditText searchBar;
	private ListView shoppingList;
	private Button addItemButton;
	private Button showLowItemsButton;
	
	static ShoppingListAdapter shoppingListAdapter;
	
	static final String SELECTED_ITEM = "Selected Item";
	
	public void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		shoppingListAdapter = new ShoppingListAdapter(getActivity());
		
		getParseData();
		
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View rootView = inflater.inflate(R.layout.shopping_list_fragment,
				container, false);		
		
		searchBar = (EditText) rootView.findViewById(R.id.searchBar);
		shoppingList = (ListView) rootView.findViewById(R.id.masterListView);
		addItemButton = (Button) rootView.findViewById(R.id.addItemButton);
		showLowItemsButton = (Button) rootView.findViewById(R.id.showLowListButton);
		
		shoppingList.setAdapter(shoppingListAdapter);
		
		searchBar.addTextChangedListener(new TextWatcher() {
		     
		    @Override
		    public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
		        // When user changed the Text
		        shoppingListAdapter.getFilter().filter(cs.toString());   
		    }
		     
		    @Override
		    public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
		            int arg3) {

		         
		    }
		     
		    @Override
		    public void afterTextChanged(Editable arg0) {

		    }
		});
		
		showLowItemsButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				
				startShoppingActivity();
				
			}
			
			
			
		});
		
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
	
	public void getParseData() {
		
		ParseQuery query = new ParseQuery("Food");
		query.orderByDescending("name");
		query.whereEqualTo("user", ParseUser.getCurrentUser().getUsername());
		
		query.findInBackground(new FindCallback() {

			@Override
			public void done(List<ParseObject> objects, ParseException e) {
				
				if (e == null) {
					
					ArrayList<ParseObject> retrieved = new ArrayList<ParseObject>();
					
					for(ParseObject object: objects)
						retrieved.add(object);
					
					
					shoppingListAdapter.setData(retrieved);
					shoppingListAdapter.notifyDataSetChanged();
					
				}
				else if(e.getCode() == ParseException.CONNECTION_FAILED) {
						System.out.println("Show some dialog box indicating error");
				}
			}
			
		});
	}
	
	private void startShoppingActivity() {
		
	}

}
