package com.christian.shoppingList;

import com.christian.grocerylist.R;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

public class SignUpActivity extends Activity {
	
	private EditText emailField;
	private EditText userNameField;
	private EditText passwordField;
	private EditText confirmPWField;
	private CheckBox loginPersistencePreference;
	private Button   signupDoneButton; 
	
	//sign up errors to display to user
	private static final String ACCOUNT_ALREADY_LINKED_MESSAGE = "Sorry, this account already exsits";
	private static final String CONNECTION_FAILED_MESSAGE = "The connection failed";
	private static final String EMAIL_TAKEN_MESSAGE = "The email provided is already in use.";
	private static final String INVALID_EMAIL_ADDRESS_MESSAGE = "Please enter a valid email address.\n ex. johndoe@example.com";
	private static final String USERNAME_TAKEN_MESSAGE = "This username is already taken.";	
	public static final int SIGNUP_SUCCESSFUL = 99;

	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.user_signup_layout);
		
		//set up the view's components
		emailField 				   = (EditText) findViewById(R.id.signupEmailField);
		userNameField 			   = (EditText) findViewById(R.id.signupUserNameField);
		passwordField 			   = (EditText) findViewById(R.id.signupPasswordField);
		confirmPWField 			   = (EditText) findViewById(R.id.signupPWConfirmField);
		loginPersistencePreference = (CheckBox) findViewById(R.id.checkBox1);
		signupDoneButton 		   = (Button)   findViewById(R.id.signupDoneButton);
		
		//as with the log in activity, fields must have values in them before signup can be clicked
		
		signupDoneButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				
				String inputPW = passwordField.getText().toString();
				String confirmPW = confirmPWField.getText().toString();
				ParseUser newUser = new ParseUser();
				
				if(inputPW.equals(confirmPW) && !inputPW.equals(""))
					newUser.setPassword(inputPW);
				else 
					showErrorMessage("Password fields are required and must match");
				
				if(!userNameField.getText().toString().equals(""))
					newUser.setUsername(userNameField.getText().toString());
				else 
					showErrorMessage("Username is required");
				
				if(!emailField.getText().toString().equals(""))
					newUser.setEmail(emailField.getText().toString());
				else
					showErrorMessage("email is required");
				
				newUser.signUpInBackground(new MySignUpCallback());
											
			}
			
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	private void showErrorMessage(String theError) {
		
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage(theError);
		builder.setCancelable(true);
		builder.setTitle("ERROR!");
		
		AlertDialog dialog = builder.create();
		dialog.show();
		
	}
	
	private class MySignUpCallback extends SignUpCallback {

		
		
		@Override
		public void done(ParseException e) {
			
			if(e == null) { 
				ParseUser.logInInBackground(userNameField.getText().toString(),
						passwordField.getText().toString(), new LogInCallback() {

					public void done(ParseUser user, ParseException e) {
						
					    if (user != null) {
					    	Log.d("user", "signup successful");
					    	setResult(SIGNUP_SUCCESSFUL, getIntent());
					    	finish();
					    }
					    else if(e != null) {
					    	showErrorMessage("Login failed. e." + e.toString()); 				    	
					    }
					    else
					    	showErrorMessage("Unknown error");
					}
				});

			}
			else {
				
				String errorMessage = "";
				
				switch (e.getCode()) {
					
					case ParseException.ACCOUNT_ALREADY_LINKED:
						errorMessage = ACCOUNT_ALREADY_LINKED_MESSAGE;
						break;
					case ParseException.CONNECTION_FAILED:
						errorMessage = CONNECTION_FAILED_MESSAGE;
						break;
					case ParseException.EMAIL_TAKEN:
						errorMessage = EMAIL_TAKEN_MESSAGE;
						break;
					case ParseException.INVALID_EMAIL_ADDRESS:
						errorMessage = INVALID_EMAIL_ADDRESS_MESSAGE;
						break;
					case ParseException.USERNAME_TAKEN:
						errorMessage = USERNAME_TAKEN_MESSAGE;
						break;
				}
				
				//display an error dialog
				showErrorMessage(errorMessage);
			}
		}
		
	}
}
