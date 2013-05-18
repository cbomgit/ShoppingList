package com.christian.shoppingList;

import java.util.ArrayList;
import java.util.Arrays;

import com.christian.grocerylist.R;
import com.parse.ParseObject;
import com.parse.ParseUser;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class AddItemActivity extends Activity implements OnItemSelectedListener {

	private EditText 	 nameField;
	private EditText 	 quantityField;
	private Spinner 	 departmentSpinner;
	private Button 		 doneButton;
	private Button		 cancelButton;
	private String 		 selectedDepartment;
	
    ArrayList<String>  	 departmentList;
	static ArrayAdapter<String> spinnerAdapter;
	static String [] 	 departments = {"Produce", "Dairy", "Deli", "Meat", "Beverage", "Frozen", 
										"Canned/Dry Goods", "Bakery","Household", "General"};
	
	
    protected void onCreate(Bundle savedInstanceState) 
    {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.add_item_layout);
            
            
            
            //create the objects from their respective layouts
            nameField = (EditText) findViewById(R.id.addItemName);
            quantityField = (EditText) findViewById(R.id.itemQuantity);
            departmentSpinner = (Spinner) findViewById(R.id.addItemDeptSpinner);
            doneButton = (Button) findViewById(R.id.addItemDoneButton);
            cancelButton = (Button) findViewById(R.id.addItemCancelButton);
            
            doneButton.setEnabled(false);
            
            //set up the spinner
            spinnerAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, departments);
            spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            departmentSpinner.setAdapter(spinnerAdapter);
            departmentSpinner.setOnItemSelectedListener(this);
            
            doneButton.setOnClickListener( new OnClickListener() {

				@Override
				public void onClick(View v) {
					
					String inputName = nameField.getText().toString();
					
					String qtyText = quantityField.getText().toString();
					int inputQuantity = qtyText.equals("") ? 0 : Integer.parseInt(qtyText);
					int status = inputQuantity == 0 ? Item.OUT : Item.IN_STOCK;
															
					ParseObject newItem = new ParseObject("Food");
			    	
			    	newItem.put("name", nameField.getText().toString());
			    	newItem.put("quantity", String.valueOf(quantityField.toString()));
			    	newItem.put("status", status);
			    	newItem.put("department", selectedDepartment);
			    	newItem.put("user", ParseUser.getCurrentUser().getUsername());
			    	
			    	newItem.saveInBackground();
					
					ShoppingListFragment.shoppingListAdapter.add(newItem);
					ShoppingListFragment.shoppingListAdapter.notifyDataSetChanged();
					
					
					//TODO save new item to parse
					finish();
				}
            	
            });
            
            cancelButton.setOnClickListener( new OnClickListener() {
            	
            	public void onClick(View v) {
            		finish();
            	}
            });
            
            nameField.addTextChangedListener(new TextWatcher() {

				@Override
				public void afterTextChanged(Editable arg0) {
					if(!nameField.getText().toString().equals(""))
						doneButton.setEnabled(true);
					else
						doneButton.setEnabled(false);
				}

				@Override
				public void beforeTextChanged(CharSequence arg0, int arg1,
						int arg2, int arg3) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void onTextChanged(CharSequence arg0, int arg1,
						int arg2, int arg3) {
					// TODO Auto-generated method stub
					
				}
            	
            });
    }
   

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int pos,
			long id) {
		
		selectedDepartment = (String) parent.getItemAtPosition(pos);
		
	}


	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub
		
	}

	
}
