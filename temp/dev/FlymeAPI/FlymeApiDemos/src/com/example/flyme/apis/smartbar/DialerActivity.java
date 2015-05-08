package com.example.flyme.apis.smartbar;


import com.example.flyme.apis.BaseActivity;
import com.example.flyme.apis.R;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class DialerActivity extends BaseActivity {
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.text_content);
        TextView text = (TextView)findViewById(android.R.id.text1);
        text.setText("Dialer");
    }
    
    @Override
    protected void onResume() {
    	getParent().getActionBar().setTitle("Dialer title");
    	super.onResume();
    }
}