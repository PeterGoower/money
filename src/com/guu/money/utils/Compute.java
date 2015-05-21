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

public class Compute implements Runnable{
	private ComputDataEvent lis;
	private Handler mHandler;
	private List<Month> data;
	private int total;
	private int precent;
	
	public Compute(ComputDataEvent lis){
		   this.lis = lis;
		   
           mHandler = new Handler(){
			
			@Override
			public void handleMessage(Message msg) {
				super.handleMessage(msg);
				if(msg.what == 0){
					Compute.this.lis.onDataGot(total);
                	return;
                }else if(msg.what == 1){
                	Compute.this.lis.onPrecentGot(precent);
                }
                
			}
		};
	}
	
	public void start(){
		new Thread(this).start();
	}
	
	@Override
	public void run() {
        
		
		total = 0;
		data = new ArrayList<Month>();
		int count = Global.currMonthData.size();
		for(int i=0; i<count; i++){
			Month mon = new Month();
			mon.av = Global.currMonthData.get(i);
			total = total + Global.currMonthData.get(i).getInt(Global.DATA_MONTH_TOTAL);
			data.add(mon);
		}
		Collections.sort(data);
		
		
		Global.dataChange = false;
		Message msg = Message.obtain();
        msg.what = 0;
		msg.obj = null;
		mHandler.sendMessage(msg);
		
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
    	        		precent = total*100/plan;
    	        		Message msg = Message.obtain();
    	                msg.what = 1;
    	        		msg.obj = null;
    	        		mHandler.sendMessage(msg);
    	        	}
    	        }else{
    	        	setPlan();
    	        }
    	    }
    	});
	}
	
	private void setPlan(){
		precent = total*100/10000;;
		Global.currPlan = 10000;
    	AVObject item = new AVObject(Global.DATA_TABLE_SETTING);
    	item.put(Global.DATA_SETTING_KEY, Global.DATA_SETTING_VALUE_PLAN);
    	item.put(Global.DATA_SETTING_INT, 10000);
        item.setACL(Global.currAcl);
    	item.saveInBackground();
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
