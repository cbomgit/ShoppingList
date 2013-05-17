package com.christian.shoppingList;

import com.christian.grocerylist.R;
import com.parse.LogInCallback;
import com.parse.ParseUser;
import com.parse.ParseException;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends Activity {
	
	private Button   			loginButton;
	private Button   			signUpButton;
	private EditText 			userNameField;
	private EditText 			passwordField;
	private UsrNamePWTxtWatcher userNamePWFieldWatcher;
	
	private static final int USER_SIGNUP = 99;
	
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
		
		UsrNamePWTxtWatcher textwatcher = new UsrNamePWTxtWatcher();
		//disable the login button until the user enters text in both the password and username field
		loginButton.setEnabled(false);
		userNameField.addTextChangedListener(textwatcher);
		passwordField.addTextChangedListener(textwatcher);
		
		loginButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				
				ParseUser.logInInBackground(userNameField.getText().toString(),
											passwordField.getText().toString(), new LogInCallback() {
					
					public void done(ParseUser user, ParseException e) {
						
					    if (user != null) {
					    	setResult(MainActivity.LOGIN_SUCCESSFUL, getIntent());
					    	finish();
					    }
					    else if(e != null){
					    	showErrorMessage("Login failed." + e.toString()); 				    	
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
	
	private void startSignUpActivity() {
		Intent i = new Intent(this, SignUpActivity.class);
		startActivityForResult(i, USER_SIGNUP);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		
		/*user should be signed up and logged in now so go back to 
		 * the main activity and start the app
		 */
		if(resultCode == SignUpActivity.SIGNUP_SUCCESSFUL) { 
			Log.d("user", "setting LOGIN_SUCCESSFUL flag");
			setResult(MainActivity.LOGIN_SUCCESSFUL, getIntent());
			finish();
		}
		else {
			
		}
	}
	
	
	
	//TextWatcher makes sure that the user can't click the login button until
	//there is text present in the user name and password fields
	private class UsrNamePWTxtWatcher implements TextWatcher {

		@Override
		public void afterTextChanged(Editable s) {
			
			String username = userNameField.getText().toString();
			String passwd = passwordField.getText().toString();
			
			if(username.equals("") || passwd.equals(""))
				loginButton.setEnabled(false);
			else
				loginButton.setEnabled(true);
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
	
	private void showErrorMessage(String theError) {
		
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage(theError);
		builder.setCancelable(true);
		builder.setTitle("ERROR!");
		
		AlertDialog dialog = builder.create();
		dialog.show();
		
	}

}
