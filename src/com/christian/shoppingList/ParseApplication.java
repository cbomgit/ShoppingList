package com.christian.shoppingList;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseACL;

public class ParseApplication extends Application {


	@Override
    public void onCreate() {
    	
            super.onCreate();
            Parse.initialize(this, 
            	   "OGPF4PXMO4VAGzx500bqj5XSNc9l4XZ7D6fLmgQG", 
 				   "HcK0Y0x9AzaSxMBCxQQP9TFNSssZSv4YjIvDvC6x");
            
            
            ParseACL defaultACL = new ParseACL();
            ParseACL.setDefaultACL(defaultACL, true);
            
        }


}
