package com.guu.money.pages;


import com.avos.avoscloud.AVUser;
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
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class SettingPage extends Fragment implements OnClickListener, OnItemClickListener, TipEvent{
	public static final int TIP_LOGOUT = 0;
	
	private Activity activity;
	private Tip tip;
	private RelativeLayout logoutBtn;
	private Button themeBtn;
	
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View settingView = inflater.inflate(R.layout.page_setting, container, false);
        
        activity = this.getActivity();
        tip = new Tip(activity, this);
    	
        logoutBtn = (RelativeLayout) settingView.findViewById(R.id.btn_logout);
        logoutBtn.setOnClickListener(this);
        
        themeBtn = (Button) settingView.findViewById(R.id.btn_theme);
        themeBtn.setOnClickListener(this);
        return settingView;
    }
    
    private void showThemeDialog(){
    	AlertDialog dialog;
    	LayoutInflater mInflater = LayoutInflater.from(activity);
		View view = mInflater.inflate(R.layout.view_theme_dialog, null);
		dialog = new AlertDialog.Builder(activity).create();
		dialog.show();
		
		GridView gridView = (GridView) view.findViewById(R.id.grid);
        ThemeAdapter mAdapter = new ThemeAdapter(activity);
        gridView.setAdapter(mAdapter);
		gridView.setOnItemClickListener(this);
		
		Button confirm = (Button) view.findViewById(R.id.btn_confirm);
		confirm.setOnClickListener(new View.OnClickListener(){
		    @Override
	        public void onClick(View v) {
	        }
		});
		
		dialog.getWindow().setContentView(view);
    }
    
    @Override
	public void onClick(View v) {
		int id = v.getId();
		switch(id){
		    case R.id.btn_logout:
		    	tip.showChoose("确定要注销当前登录的账号吗");
				tip.setEventTag(TIP_LOGOUT);
				break;
		    case R.id.btn_theme:
		    	showThemeDialog();
				break;
		}
		
	}

	@Override
	public void onChoose(int which, int eventTag) {
		if(eventTag == TIP_LOGOUT && which == Tip.CHOOSE_RIGHT){
			AVUser.logOut();  
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
	
	public void changeTheme(){
    	int[] themes = {R.style.Blue, R.style.Glu, R.style.Green, R.style.Pink, R.style.Red, R.style.Yellow};
		int currTheme = Setting.getInt(SettingPage.this.getActivity(), Global.THEME_CURR);
		int currIndex = 0;
		for(int i=0; i<6; i++){
			if(currTheme == themes[i]){
				currIndex = i;
				break;
			}
		}
		currIndex++;
		if(currIndex == 6){
			currIndex = 0;
		}
		
		Global.changeTheme(activity, themes[currIndex]);
		
//		AVUser currentUser = AVUser.getCurrentUser();
//		setBtn.setText(currentUser.getUsername());
		
		
		ActionBar bar = activity.getActionBar();
		bar.removeTab(bar.getTabAt(2));
		bar.addTab(bar.newTab().setIcon(R.drawable.ic_tab_setting)
				.setTabListener(new MyTabListener<SettingPage>(activity, "setting", SettingPage.class)),2,true);
	
    }

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		
	}
}
