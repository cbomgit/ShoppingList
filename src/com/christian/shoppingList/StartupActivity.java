package com.christian.shoppingList;

import com.christian.grocerylist.R;
import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseInstallation;
import com.parse.ParseUser;
import com.parse.PushService;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

public class StartupActivity extends Activity {
	
	//activity request codes
	public static final int START_APP_MAIN = 90;
	public static final int USER_LOGIN = 91;
	
	//activity result codes
	public static final int EXIT_FOR_LOGIN = 92;
	public static final int LOGIN_SUCCESSFUL = 93;
		
	public void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		
		Parse.initialize(this, 
         	   "OGPF4PXMO4VAGzx500bqj5XSNc9l4XZ7D6fLmgQG", 
				   "HcK0Y0x9AzaSxMBCxQQP9TFNSssZSv4YjIvDvC6x");
         
         
         ParseACL defaultACL = new ParseACL();
         ParseACL.setDefaultACL(defaultACL, true);
         
         PushService.setDefaultPushCallback(this, MainActivity.class);
         ParseInstallation.getCurrentInstallation().saveInBackground();
        
		if(ParseUser.getCurrentUser() == null) {
			Log.d("user", "no user. Start login or sign up process");
			startLoginActivity();
				
		}
		else {
			Log.d("user", "User logged in. starting main fragment activity");
			startMainFragmentActivity();
		}
			
	}
	
	private void startMainFragmentActivity() {
		Intent i = new Intent(this, MainActivity.class);
		startActivityForResult(i, START_APP_MAIN );
	}
	
	private void startLoginActivity() {
		
		Intent i = new Intent(this, LoginActivity.class);
		startActivityForResult(i, USER_LOGIN);
		
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		
		if(resultCode == LOGIN_SUCCESSFUL) 
			startMainFragmentActivity(); //user has been signed up and logged in, start app
		else if(resultCode == EXIT_FOR_LOGIN)
			startLoginActivity(); //user logged out so show the login screen
		else
			finish();
		
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		
		if(item.getItemId() == R.id.action_logout) {
			ParseUser.logOut();
			recreate();
			return true;
		}
		
		return false;
	}
	

}
