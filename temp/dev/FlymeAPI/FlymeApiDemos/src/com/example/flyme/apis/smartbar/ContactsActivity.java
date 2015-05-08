package com.example.flyme.apis.smartbar;


import com.example.flyme.apis.BaseActivity;
import com.example.flyme.apis.R;

import android.app.Activity;
import android.os.Bundle;
import android.widget.EditText;

public class ContactsActivity extends BaseActivity {
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.text_content_contacts);
        EditText text = (EditText)findViewById(android.R.id.text1);
        text.setText("Contacts1");
    }
    
    @Override
    protected void onResume() {
    	getParent().getActionBar().setTitle("Contacts title");
    	super.onResume();
    }
}