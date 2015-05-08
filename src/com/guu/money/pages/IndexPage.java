package com.guu.money.pages;


import com.avos.avoscloud.AVUser;
import com.guu.money.R;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class IndexPage extends Fragment {
	private Button setBtn;
	private Button setBtn2;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    	View indexView = inflater.inflate(R.layout.page_home, container, false);
    	
    	setBtn = (Button) indexView.findViewById(R.id.btn1);
		setBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				
			}
		});
		AVUser currentUser = AVUser.getCurrentUser();
		setBtn.setText(currentUser.getUsername());
		
		setBtn2 = (Button) indexView.findViewById(R.id.btn2);
		setBtn2.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				AVUser.logOut();   
				setBtn.setText("未登录");
			}
		});
        
        return indexView;
    }

}
