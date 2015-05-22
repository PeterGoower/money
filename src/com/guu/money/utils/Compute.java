package com.guu.money.utils;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.FindCallback;
import com.avos.avoscloud.SaveCallback;
import com.guu.money.R;
import com.guu.money.listener.ComputDataEvent;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

public class Compute implements Runnable{
	private ComputDataEvent lis;
	private Handler mHandler;
	private List<Month> data;
	private int total;
	private int recent;
	private int average;
	private int precent;
	private int[] currs;
	
	public Compute(ComputDataEvent lis){
		   this.lis = lis;
		   
           mHandler = new Handler(){
			
			@Override
			public void handleMessage(Message msg) {
				super.handleMessage(msg);
				if(msg.what == 0){
					Compute.this.lis.onDataGot(String.valueOf(total), String.valueOf(recent), String.valueOf(average), currs);
                	return;
                }else if(msg.what == 1){
                	Compute.this.lis.onPrecentGot(precent + "%");
                }
                
			}
		};
	}
	
	public void start(){
		new Thread(this).start();
	}
	
	@Override
	public void run() {
		try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace(); 
        }
		
		total = 0;
		recent = 0;
		average = 0;
		precent = 0;
		currs = null;
		
		data = new ArrayList<Month>();
		if(Global.currMonthData == null){
			
			showInfos();
			getPrecent();
			return;
		}
		
		int count = Global.currMonthData.size();
		
		if(count < 2){
			if(count == 1){
				total = Global.currMonthData.get(0).getInt(Global.DATA_MONTH_TOTAL);
				currs = new int[1];
				currs[0] = total;
			}
			
			showInfos();
			getPrecent();
			return;
		}
		
		for(int i=0; i<count; i++){
			Month mon = new Month();
			mon.av = Global.currMonthData.get(i);
			data.add(mon);
		}
		Collections.sort(data);
		
		currs = new int[count];
		int[] diffs = new int[count-1];
		
		for(int i=0; i<count; i++){
			currs[i] = data.get(i).av.getInt(Global.DATA_MONTH_TOTAL);
			if(i > 0){
				diffs[i-1] = currs[i]-currs[i-1];
			}
			if(i == count-1){
				total = currs[i];
				average = total - currs[0];
			}
		}
		
		recent = diffs[count-1-1];
		average = average/(count-1);
		
		showInfos();
		getPrecent();
	}
	
	private void getPrecent(){
		AVQuery<AVObject> query = new AVQuery<AVObject>(Global.DATA_TABLE_SETTING);
    	query.whereEqualTo(Global.DATA_SETTING_KEY, Global.DATA_SETTING_VALUE_PLAN);
    	query.findInBackground(new FindCallback<AVObject>() {
    		@Override
    	    public void done(List<AVObject> avObjects, AVException e) {
    	        if (e == null) {
    	        	int count = avObjects.size();
    	        	if(count == 0){
    	        		setPlan();
    	        	}else{
    	        		int plan = avObjects.get(0).getInt(Global.DATA_SETTING_INT);
    	        		Global.currPlan = plan;
    	        		showPrecent(plan);
    	        	}
    	        }else{
    	        	setPlan();
    	        }
    	    }
    	});
	}
	
	private void setPlan(){
		Global.currPlan = 10000;
		showPrecent(10000);
    	AVObject item = new AVObject(Global.DATA_TABLE_SETTING);
    	item.put(Global.DATA_SETTING_KEY, Global.DATA_SETTING_VALUE_PLAN);
    	item.put(Global.DATA_SETTING_INT, 10000);
        item.setACL(Global.currAcl);
    	item.saveInBackground();
    }
	
	private void showInfos(){
		Global.dataChange = false;
		Message msg = Message.obtain();
        msg.what = 0;
		msg.obj = null;
		mHandler.sendMessage(msg);
	}
	
	private void showPrecent(int plan){
		precent = total*100/plan;
		if(precent == 0 && total > 0){
			precent = 1;
		}
        if(precent == 100 && total < plan){
        	precent = 99;
		}
		Message msg = Message.obtain();
        msg.what = 1;
		msg.obj = null;
		mHandler.sendMessage(msg);
	}
	
	public class Month implements Comparable<Month>{
		public AVObject av;
		
		public Integer getOrder() {
			String[] date = this.av.getString(Global.DATA_MONTH_DATE).split("-");
			
			int order = Integer.parseInt(date[0] + date[1]);
	        return order;
	    }

		@Override
		public int compareTo(Month another) {
			return this.getOrder().compareTo(another.getOrder());
		}
	}
}
