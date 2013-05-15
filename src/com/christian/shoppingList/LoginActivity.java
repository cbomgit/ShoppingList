package com.christian.shoppingList;

import com.christian.grocerylist.R;
import com.parse.LogInCallback;
import com.parse.ParseUser;
import com.parse.ParseException;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends Activity {
	
	private Button   loginButton;
	private Button   signUpButton;
	private EditText userNameField;
	private EditText passwordField;
	private UsrNamePWTxtWatcher userNamePWFieldWatcher;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		
		//set the layout
		setContentView(R.layout.user_login_layout);
		
		//get the view components
		loginButton  = (Button)   findViewById(R.id.loginUserLoginButton);
		signUpButton = (Button)   findViewById(R.id.loginUserSignUpButton);
		userNameField= (EditText) findViewById(R.id.loginUserNameField);
		passwordField= (EditText) findViewById(R.id.loginPasswordField);
		
		userNamePWFieldWatcher = new UsrNamePWTxtWatcher();
		
		//disable the login button until the user enters text in both the password and username field
		loginButton.setEnabled(false);
		loginButton.addTextChangedListener(null);
		
		loginButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				
				ParseUser.logInInBackground(userNameField.getText().toString(),
											passwordField.getText().toString(), new LogInCallback() {
					
					  public void done(ParseUser user, ParseException e) {
					    if (user != null) 
					    	finish();
					    else {
					      
					    if(e.getCode() == ParseException.CONNECTION_FAILED)
					    		System.out.println("Connection failed.");
					    
					    }
					  }
					});
				
			}
			
			
		});
		
		signUpButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				startSignUpActivity();
				
			}
			
			
		});
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	private void startSignUpActivity() {
		Intent i = new Intent(this, SignUpActivity.class);
		startActivity(i);
	}
	
	
	//TextWatcher makes sure that the user can't click the login button until
	//there is text present in the user name and password fields
	private class UsrNamePWTxtWatcher implements TextWatcher {

		@Override
		public void afterTextChanged(Editable s) {
			
			if(!userNameField.getText().toString().equals("") &&
			   !passwordField.getText().toString().equals(""))
				loginButton.setEnabled(true);
			else
				loginButton.setEnabled(false);
		}

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onTextChanged(CharSequence s, int start, int before,
				int count) {
			// TODO Auto-generated method stub
			
		}
		
	}

}
