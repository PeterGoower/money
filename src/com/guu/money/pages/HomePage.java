package com.guu.money.pages;
import com.avos.avoscloud.AVUser;
import com.guu.money.R;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class HomePage extends BasePage {
	private Button setBtn;
	private Button setBtn2;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         
        setContentView(R.layout.page_home);
        
        setBtn = (Button) findViewById(R.id.btn1);
		setBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				
			}
		});
		AVUser currentUser = AVUser.getCurrentUser();
		setBtn.setText(currentUser.getUsername());
		
		setBtn2 = (Button) findViewById(R.id.btn2);
		setBtn2.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				AVUser.logOut();   
				setBtn.setText("未登录");
			}
		});
    }
    
    
}