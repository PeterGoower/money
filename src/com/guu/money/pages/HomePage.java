package com.guu.money.pages;
import com.guu.money.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;

public class HomePage extends BasePage {
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         
        setContentView(R.layout.page_home);
        
        Button setBtn = (Button) findViewById(R.id.btn1);
		setBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
			}
		});
		
		Button setBtn2 = (Button) findViewById(R.id.btn2);
		setBtn2.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
			}
		});
    }
    
    
}