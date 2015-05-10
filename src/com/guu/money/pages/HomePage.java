package com.guu.money.pages;
import java.util.Timer;
import java.util.TimerTask;

import com.guu.money.R;
import com.guu.money.listener.MyTabListener;
import com.guu.money.listener.TipEvent;
import com.guu.money.views.Tip;
import com.meizu.flyme.reflect.ActionBarProxy;

import android.app.ActionBar;
import android.os.Bundle;
import android.view.KeyEvent;

public class HomePage extends BasePage implements TipEvent{
	private Tip tip = new Tip(this,this);
	private Timer timer;
	private long exitTime = 0;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        final ActionBar bar = getActionBar();
        bar.addTab(bar.newTab().setIcon(R.drawable.ic_tab_index)
				.setTabListener(new MyTabListener<IndexPage>(this, "index", IndexPage.class)));
		bar.addTab(bar.newTab().setIcon(R.drawable.ic_tab_gallery)
				.setTabListener(new MyTabListener<GalleryPage>(this, "gallery", GalleryPage.class)));
		bar.addTab(bar.newTab().setIcon(R.drawable.ic_tab_setting)
				.setTabListener(new MyTabListener<SettingPage>(this, "setting", SettingPage.class)));
        
        bar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        
		ActionBarProxy.setActionBarTabsShowAtBottom(bar, true);
		
		
    }
    
    @Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		
		if (keyCode == KeyEvent.KEYCODE_BACK) 
		{
			if((System.currentTimeMillis() - exitTime) > 5000){  
				tip.showHint(R.string.ask_quit);
	            exitTime = System.currentTimeMillis();   
	        } else {
	            finish();
	            System.exit(0);
	        }
			
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	public void onHintDismiss(int eventTag) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onChoose(int which, int eventTag) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onConfirm(int eventTag) {
		// TODO Auto-generated method stub
		
	}
}