package com.guu.money.pages;

import java.util.List;

import com.avos.avoscloud.AVACL;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.FindCallback;
import com.avos.avoscloud.SaveCallback;
import com.guu.money.R;
import com.guu.money.listener.MyTabListener;
import com.guu.money.utils.Global;
import com.guu.money.utils.Utily;
import com.meizu.flyme.reflect.ActionBarProxy;

import android.app.ActionBar;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;

public class HomePage extends BasePage{
	private long exitTime = 0;
	
	
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        final ActionBar bar = getActionBar();
        bar.addTab(bar.newTab().setIcon(Global.getIndexIcon(this))
				.setTabListener(new MyTabListener<IndexPage>(this, "index", IndexPage.class)));
		bar.addTab(bar.newTab().setIcon(Global.getGalleryIcon(this))
				.setTabListener(new MyTabListener<GalleryPage>(this, "gallery", GalleryPage.class)));
		bar.addTab(bar.newTab().setIcon(Global.getSettingIcon(this))
				.setTabListener(new MyTabListener<SettingPage>(this, "setting", SettingPage.class)));
        
        bar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        
		ActionBarProxy.setActionBarTabsShowAtBottom(bar, true);
		
		
    }
    
    
//    Fragment fragment =(Fragment)getFragmentManager().findFragmentByTag("index");
//	IndexPage test = (IndexPage)fragment;
//	test.test();
    
    @Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) 
		{
			if((System.currentTimeMillis() - exitTime) > 5000){  
				Utily.showToast(this, R.string.ask_quit);
	            exitTime = System.currentTimeMillis();   
	        } else {
	            finish();
	            System.exit(0);
	        }
			
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
}