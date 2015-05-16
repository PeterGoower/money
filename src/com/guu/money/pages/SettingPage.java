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
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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
	private AlertDialog dialog;
	
	private Activity activity;
	private Tip tip;
	private RelativeLayout logoutBtn;
	private TextView logoutTip;
	
	private RelativeLayout themeBtn;
	
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View settingView = inflater.inflate(R.layout.page_setting, container, false);
        
        activity = this.getActivity();
        tip = new Tip(activity, this);
    	
        logoutBtn = (RelativeLayout) settingView.findViewById(R.id.btn_logout);
        logoutBtn.setOnClickListener(this);
        logoutTip = (TextView) settingView.findViewById(R.id.logout_tip);
 		AVUser currentUser = AVUser.getCurrentUser();
 		logoutTip.setText("注销 (" + currentUser.getUsername()+ ")");
        
        themeBtn = (RelativeLayout) settingView.findViewById(R.id.btn_theme);
        themeBtn.setOnClickListener(this);
        return settingView;
    }
    
    private void showThemeDialog(){
    	
    	LayoutInflater mInflater = LayoutInflater.from(activity);
		View view = mInflater.inflate(R.layout.view_theme_dialog, null);
		dialog = new AlertDialog.Builder(activity).create();
		dialog.show();
		
		GridView gridView = (GridView) view.findViewById(R.id.grid);
        ThemeAdapter mAdapter = new ThemeAdapter(activity);
        gridView.setSelector(new ColorDrawable(Color.TRANSPARENT));
        gridView.setAdapter(mAdapter);
		gridView.setOnItemClickListener(this);
		
		dialog.getWindow().setContentView(view);
    }
    
    @Override
	public void onClick(View v) {
		int id = v.getId();
		switch(id){
		    case R.id.btn_logout:
		    	tip.showChoose("真的要注销账号吗？");
				tip.setEventTag(TIP_LOGOUT);
				break;
		    case R.id.btn_theme:
		    	showThemeDialog();
				break;
		}
		
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
