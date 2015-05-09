package com.guu.money.pages;
import com.guu.money.R;
import com.guu.money.listener.MyTabListener;
import com.meizu.flyme.reflect.ActionBarProxy;

import android.app.ActionBar;
import android.os.Bundle;

public class HomePage extends BasePage {
	
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
    
    
}