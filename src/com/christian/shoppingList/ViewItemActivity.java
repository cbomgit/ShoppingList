package com.christian.shoppingList;

import com.christian.grocerylist.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class ViewItemActivity extends Activity {

	private Item     viewedItem;
	private EditText itemName;
	private EditText itemQuantity;
	private Spinner  departmentSpinner;
	private Spinner  statusSpinner;
	private Button   deleteItemButton;
	private ArrayAdapter<String> statusSpinnerAdapter;
	
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.view_item_layout);
		itemName = (EditText) findViewById(R.id.view_item_name);
		itemQuantity = (EditText) findViewById(R.id.view_item_quantity);
		departmentSpinner = (Spinner) findViewById(R.id.view_item_dept_spinner);
		statusSpinner = (Spinner) findViewById(R.id.view_item_status_spinner);
		
		Intent i = getIntent();
		int selectedItemPosition = i.getIntExtra(ShoppingListFragment.SELECTED_ITEM, 0);
		viewedItem = (Item) ShoppingListFragment.shoppingListAdapter.getItem(selectedItemPosition);
		
		//set the name and the quantity
		itemName.setText(viewedItem.getName());
		itemQuantity.setText(viewedItem.getQuantity().toString());
		
		
		createDeptSpinner();
		createStatusSpinner();
		
		
	}
	
	private void createDeptSpinner() {
		
		//create Spinners for the departments and statuses
		departmentSpinner.setAdapter(AddItemActivity.spinnerAdapter);
		int deptSpinnerPos = AddItemActivity.spinnerAdapter.getPosition(viewedItem.getDepartment());
		
		departmentSpinner.setSelection(deptSpinnerPos);
		departmentSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				
				
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
			
		});
		
	}
	
	private void createStatusSpinner() {
		
		String [] statuses = {Item.IN_STOCK, 
							  Item.LOW,
							  Item.OUT};
		
		statusSpinnerAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, statuses);
		statusSpinner.setAdapter(statusSpinnerAdapter);
		
		int statusSpinnerPos = statusSpinnerAdapter.getPosition(viewedItem.getStockStatus());
		statusSpinner.setSelection(statusSpinnerPos);
		
		statusSpinner.setOnItemSelectedListener(null);
	}
}
