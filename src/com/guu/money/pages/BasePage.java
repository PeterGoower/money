package com.guu.money.pages;

import com.avos.avoscloud.AVUser;
import com.guu.money.R;
import com.guu.money.utils.Global;
import com.meizu.flyme.reflect.ActionBarProxy;
import com.meizu.flyme.reflect.StatusBarProxy;

import android.app.ActionBar;
import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

public class BasePage extends Activity{
	protected AVUser me;
	protected ActionBar bar;
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bar = getActionBar();
		if(bar != null){
			bar.setDisplayOptions(0);
			ActionBarProxy.setActionBarViewCollapsable(bar, true);
			
			bar.setSplitBackgroundDrawable(new ColorDrawable(this.getResources().getColor(R.color.dark)));
		}
        
        Global.setTheme(this); 
        
        StatusBarProxy.setStatusBarDarkIcon(getWindow(), false);
        StatusBarProxy.setImmersedWindow(getWindow(), true);
        
        me = AVUser.getCurrentUser();
        
   }
}