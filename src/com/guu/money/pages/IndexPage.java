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
	private boolean dataGot = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    	View indexView = inflater.inflate(R.layout.page_index, container, false);
    	
    	dataGot = true;
    	
    	total = (TextView) indexView.findViewById(R.id.num1);
    	recent = (TextView) indexView.findViewById(R.id.num2);
    	progress = (TextView) indexView.findViewById(R.id.num3);
    	average = (TextView) indexView.findViewById(R.id.num4);
    	
    	total.setText(Global.totalTemp);
    	recent.setText(Global.recentTemp);
    	progress.setText(Global.progressTemp);
    	average.setText(Global.averageTemp);
    	
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
        if(Global.dataChange == true || dataGot == true){
        	com.start();
        }
        dataGot = false;
    }

	@Override
	public void onDataGot(String totalValue, String recentValue, String averageValue, int[] monTotal) {
		Global.totalTemp = totalValue;
		total.setText(totalValue);
		
		Global.recentTemp = recentValue;
		recent.setText(recentValue);
		
		Global.averageTemp = averageValue;
		average.setText(averageValue);
	}
	
	@Override
	public void onPrecentGot(String precent) {
		Global.progressTemp = String.valueOf(precent);
		progress.setText(Global.progressTemp);
	}
}
