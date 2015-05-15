package com.guu.money.pages;

import com.guu.money.R;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;

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
				Intent intent = new Intent(IndexPage.this.getActivity(), HisPage.class);
		        startActivity(intent);
			}
		});

		
		setBtn2 = (Button) indexView.findViewById(R.id.btn2);
		setBtn2.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(IndexPage.this.getActivity(), AddPage.class);
		        startActivity(intent);
			}
		});
        
        return indexView;
    }
    
    public void test(){
    	setBtn2.setText("ooooo");
    }
    
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }
}
