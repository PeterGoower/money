package com.guu.money.pages;


import java.util.List;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.FindCallback;
import com.avos.avoscloud.SaveCallback;
import com.guu.money.R;
import com.guu.money.adapter.ThemeAdapter;
import com.guu.money.listener.MyTabListener;
import com.guu.money.listener.TipEvent;
import com.guu.money.utils.Global;
import com.guu.money.utils.Setting;
import com.guu.money.utils.Utily;
import com.guu.money.views.Tip;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class SettingPage extends Fragment implements OnClickListener, OnItemClickListener, TipEvent{
	public static final int TIP_LOGOUT = 0;
	private Dialog dialog;
	
	private Activity activity;
	private Tip tip;
	
	private RelativeLayout logoutBtn;
	private RelativeLayout themeBtn;
	private RelativeLayout planBtn;
	private RelativeLayout itemsBtn;
	private RelativeLayout feedbackBtn;
	private RelativeLayout aboutBtn;
	
	private TextView plan;
	
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View settingView = inflater.inflate(R.layout.page_setting, container, false);
        
        activity = this.getActivity();
        tip = new Tip(activity, this);
        
        TextView username = (TextView) settingView.findViewById(R.id.username);
 		AVUser currentUser = AVUser.getCurrentUser();
 		username.setText(currentUser.getUsername());
 		
 		plan = (TextView) settingView.findViewById(R.id.plan);
 		plan.setText(String.valueOf(Global.currPlan));
    	
        logoutBtn = (RelativeLayout) settingView.findViewById(R.id.btn_logout);
        logoutBtn.setOnClickListener(this);
        
        themeBtn = (RelativeLayout) settingView.findViewById(R.id.btn_theme);
        themeBtn.setOnClickListener(this);
        
        planBtn = (RelativeLayout) settingView.findViewById(R.id.btn_plan);
        planBtn.setOnClickListener(this);
        
        itemsBtn = (RelativeLayout) settingView.findViewById(R.id.btn_items);
        itemsBtn.setOnClickListener(this);
        
        feedbackBtn = (RelativeLayout) settingView.findViewById(R.id.btn_feedback);
        feedbackBtn.setOnClickListener(this);
        
        aboutBtn = (RelativeLayout) settingView.findViewById(R.id.btn_about);
        aboutBtn.setOnClickListener(this);
        
        return settingView;
    }
    
    @Override
	public void onClick(View v) {
		int id = v.getId();
		switch(id){
		    case R.id.btn_logout:
		    	tip.showChoose(R.string.ask_logout);
				tip.setEventTag(TIP_LOGOUT);
				break;
		    case R.id.btn_theme:
		    	showThemeDialog();
				break;
		    case R.id.btn_plan:
		    	showPlanDialog();
				break;
		    case R.id.btn_items:
		    	Intent intent = new Intent(activity, ItemsPage.class);
		        startActivity(intent);
				break;
		    case R.id.btn_feedback:
		    	//
				break;
		    case R.id.btn_about:
		    	//
				break;
		}
		
	}
    
    private void showPlanDialog(){
    	
    	LayoutInflater mInflater = LayoutInflater.from(activity);
		View view = mInflater.inflate(R.layout.view_plan_dialog, null);
		dialog = new Dialog(activity, R.style.GuuDialog); 
		
        final EditText edit = (EditText) view.findViewById(R.id.edit);
		
		Button btn = (Button) view.findViewById(R.id.btn);
		btn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				commitPlan(Integer.parseInt(edit.getText().toString()));
			}
		});
		
		dialog.setContentView(view);  
		dialog.show(); 
    }
    
    private void commitPlan(int planValue){
    	dialog.dismiss();
    	tip.showWaitting();
    	
    	final int value;
    	value = planValue;
        
        AVQuery<AVObject> query = new AVQuery<AVObject>(Global.DATA_TABLE_SETTING);
    	query.whereEqualTo(Global.DATA_SETTING_KEY, Global.DATA_SETTING_VALUE_PLAN);
    	query.findInBackground(new FindCallback<AVObject>() {
    		@Override
    	    public void done(List<AVObject> avObjects, AVException e) {
    	        if (e == null) {
    	        	int count = avObjects.size();
    	        	if(count == 0){
    	        		setPlan(null, value);
    	        	}else{
    	        		setPlan(avObjects.get(0).getObjectId(), value);
    	        	}
    	        }else{
    	        	setPlan(null, value);
    	        }
    	    }
    	});
    }
    
    private void setPlan(String objId, int planValue){
    	final int value;
    	value = planValue;
    	AVObject item = new AVObject(Global.DATA_TABLE_SETTING);
    	item.put(Global.DATA_SETTING_KEY, Global.DATA_SETTING_VALUE_PLAN);
    	item.put(Global.DATA_SETTING_INT, planValue);
    	if(objId != null){
    		item.setObjectId(objId);
    	}
        item.setACL(Global.currAcl);
    	item.saveInBackground(new SaveCallback() {
    	    public void done(AVException e) {
    	    	tip.dismissWaitting();
    	        if (e == null) {
    	        	Global.currPlan = value;
    	        	Global.dataChange = true;
    	        	plan.setText(String.valueOf(Global.currPlan));
    	        }else{
    	        	tip.showHint(R.string.edit_fail);
    	        }
    	    }
    	});
    }
    
    private void showThemeDialog(){
    	
    	LayoutInflater mInflater = LayoutInflater.from(activity);
		View view = mInflater.inflate(R.layout.view_theme_dialog, null);
		dialog = new Dialog(activity, R.style.GuuDialog); 
		
		GridView gridView = (GridView) view.findViewById(R.id.grid);
        ThemeAdapter mAdapter = new ThemeAdapter(activity);
        gridView.setSelector(new ColorDrawable(Color.TRANSPARENT));
        gridView.setAdapter(mAdapter);
		gridView.setOnItemClickListener(this);
		
		dialog.setContentView(view);  
		dialog.show(); 
    }
    
    @Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
    	int[] themes = {R.style.Blue, R.style.Glu, R.style.Green, R.style.Pink, R.style.Red, R.style.Yellow};
    	Global.changeTheme(activity, themes[position]);
    	ActionBar bar = activity.getActionBar();
    	bar.removeTab(bar.getTabAt(2));
    	bar.removeTab(bar.getTabAt(1));
		bar.removeTab(bar.getTabAt(0));
		bar.addTab(bar.newTab().setIcon(Global.getIndexIcon(activity))
				.setTabListener(new MyTabListener<IndexPage>(activity, "index", IndexPage.class)),0,true);
		bar.addTab(bar.newTab().setIcon(Global.getGalleryIcon(activity))
				.setTabListener(new MyTabListener<GalleryPage>(activity, "gallery", GalleryPage.class)),1,true);
		bar.addTab(bar.newTab().setIcon(Global.getSettingIcon(activity))
				.setTabListener(new MyTabListener<SettingPage>(activity, "setting", SettingPage.class)),2,true);
		dialog.dismiss();
	}

	@Override
	public void onChoose(int which, int eventTag) {
		if(eventTag == TIP_LOGOUT && which == Tip.CHOOSE_RIGHT){
			AVUser.logOut();  
			Global.currItemData = null;
			Global.currMonthData = null;
			Global.currUser = null;
			Global.currAcl = null;
			activity.finish();  
	        activity.startActivity(new Intent(activity, LoginPage.class));  
		}
	}

	@Override
	public void onConfirm(int eventTag) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void onHintDismiss(int eventTag) {
		// TODO Auto-generated method stub
	}
}
