package com.christian.shoppingList;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import com.christian.grocerylist.R;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;


public class ItemListFragment extends Fragment implements OnItemSelectedListener{

	private   EditText 	   		     searchBar;
	private   ListView 	   		     masterListView;	
	private   Spinner		   		 deptSpinner;
	private   ArrayAdapter<String>   spinnerAdapter;
	static    ItemListAdapter 	 	 itemListAdapter;
	static    String [] 	   		 departments = {"All", "Produce", "Dairy", "Deli", "Meat", 
												"Beverage", "Frozen", "Canned/Dry Goods", 
												"Bakery","Household", "General"};
	
	public void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		itemListAdapter = new ItemListAdapter(getActivity());
		getParseData();
		setHasOptionsMenu(true);
		
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View rootView = inflater.inflate(R.layout.master_list_fragment,
				container, false);		
		
		searchBar =       (EditText) rootView.findViewById(R.id.searchBar);
		masterListView =  (ListView) rootView.findViewById(R.id.masterListView);
		deptSpinner = 	  (Spinner)  rootView.findViewById(R.id.mastserListDeptSpinner);
		
        spinnerAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, departments);
		spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		
        deptSpinner.setSelection(0);
		deptSpinner.setAdapter(spinnerAdapter);
        deptSpinner.setOnItemSelectedListener(this);
        
        masterListView.setAdapter(itemListAdapter);
		
		searchBar.addTextChangedListener(new TextWatcher() {
		     
		    @Override
		    public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
		        // When user changed the Text
		    	if(cs != null && itemListAdapter != null)
		    		itemListAdapter.setFilterType("name");
		    		itemListAdapter.getFilter().filter(cs.toString());   
		    }
		     
		    @Override
		    public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
		            int arg3) {

		         
		    }
		     
		    @Override
		    public void afterTextChanged(Editable arg0) {

		    }
		});
		
		
		return rootView;
	}
	
	private void startAddItemActivity() {
		
		Intent i = new Intent(getActivity(), AddItemActivity.class);
		getActivity().startActivity(i);
		
	}
	
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		// TODO Auto-generated method stub
		super.onCreateOptionsMenu(menu, inflater);
		
		inflater.inflate(R.menu.fragment_menu, menu);
		
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		
		if(item.getItemId() == R.id.action_add_item) {
			startAddItemActivity();
		}
		
		else if(item.getItemId() == R.id.action_show_shopping_list) {
			if(item.isChecked()) { 
				item.setChecked(false);
				itemListAdapter.showShoppingList(false);
				getParseData();
			}
			else {
				item.setChecked(true);
				itemListAdapter.showShoppingList(true);
			}
			
		}
		
		return super.onOptionsItemSelected(item);
	}
	
	public void getParseData() {
		
		ParseQuery query = new ParseQuery("Food");
		query.orderByAscending("name");
		query.whereEqualTo("user", ParseUser.getCurrentUser().getUsername());
		
		query.findInBackground(new FindCallback() {

			@Override
			public void done(List<ParseObject> objects, ParseException e) {
				
				if (e == null) {
					
					ArrayList<ParseObject> retrieved = new ArrayList<ParseObject>();
					
					for(ParseObject object: objects) {
						if(itemListAdapter.showShoppingList) {
							if(object.getInt("status") > Item.IN_STOCK)
								retrieved.add(object);
						}
						else
							retrieved.add(object);

					}
							
					itemListAdapter.setData(retrieved);
					itemListAdapter.notifyDataSetChanged();
					
				}
				else if(e.getCode() == ParseException.CONNECTION_FAILED) {
						System.out.println("Show some dialog box indicating error");
				}
			}
			
		});
		
	}

	@Override
	public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
			long arg3) {
		String selectedDep = departments[arg2];
		String constraint = selectedDep.equals(departments[0]) ? null : selectedDep;
		
		itemListAdapter.setFilterType("department");
		itemListAdapter.getFilter().filter(constraint);
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub
		
	}
	
	
	
}
