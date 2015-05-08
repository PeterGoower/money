
package com.example.flyme.apis.smartbar;

import android.app.ActionBar;
import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;

import com.example.flyme.apis.BaseActivity;
import com.example.flyme.apis.R;
import com.meizu.flyme.reflect.ActionBarProxy;
import com.meizu.flyme.reflect.StatusBarProxy;

public class ActionBarStyle extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        final ActionBar bar = getActionBar();
        if(bar != null){
        	//设置ActionBar顶栏背景
        	bar.setBackgroundDrawable(new ColorDrawable(Color.GRAY));
        	//设置SmartBar背景
        	bar.setSplitBackgroundDrawable(new ColorDrawable(Color.GRAY));
        	//设置more键图标
        	ActionBarProxy.SetOverFlowButtonDrawable(bar, getResources().getDrawable(R.drawable.mz_ic_tab_more_normal));
        	//设置back键图标
        	ActionBarProxy.SetBackButtonDrawable(bar, getResources().getDrawable(R.drawable.mz_ic_tab_back_normal));
        }
        
      //设置状态栏图标文字为白色
        StatusBarProxy.setStatusBarDarkIcon(getWindow(), false);
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_settings, menu);
        return true;
    }
}
