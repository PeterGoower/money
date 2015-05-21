package com.guu.money.pages;

import com.guu.money.R;
import com.guu.money.listener.ComputDataEvent;
import com.guu.money.utils.Compute;
import com.guu.money.utils.Global;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class IndexPage extends Fragment implements ComputDataEvent{
	private Button hisBtn;
	private Button addBtn;
	private TextView total;
	private TextView recent;
	private TextView progress;
	private TextView average;
	
	private Compute com = new Compute(this);

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    	View indexView = inflater.inflate(R.layout.page_home, container, false);
    	
    	total = (TextView) indexView.findViewById(R.id.num1);
    	recent = (TextView) indexView.findViewById(R.id.num2);
    	progress = (TextView) indexView.findViewById(R.id.num3);
    	average = (TextView) indexView.findViewById(R.id.num4);
    	
    	hisBtn = (Button) indexView.findViewById(R.id.btn1);
    	hisBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(IndexPage.this.getActivity(), HisPage.class);
		        startActivity(intent);
			}
		});

		
    	addBtn = (Button) indexView.findViewById(R.id.btn2);
    	addBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(IndexPage.this.getActivity(), AddPage.class);
		        startActivity(intent);
			}
		});
        
        return indexView;
    }
    
    public void test(){
    }
    
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }
    
    @Override
    public void onResume() {
        super.onResume();
        Log.d("Goower", Global.dataChange + "");
        if(Global.dataChange == true){
        	com.start();
        }
    }

	@Override
	public void onDataGot(int totalValue) {
		total.setText(String.valueOf(totalValue));
	}
	
	@Override
	public void onPrecentGot(int precent) {
		progress.setText(String.valueOf(precent) + "%");
	}
}
