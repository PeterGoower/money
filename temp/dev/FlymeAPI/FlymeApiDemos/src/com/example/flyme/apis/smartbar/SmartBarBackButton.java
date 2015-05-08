
package com.example.flyme.apis.smartbar;

import android.app.ActionBar;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;

import com.example.flyme.apis.BaseActivity;
import com.example.flyme.apis.R;
import com.meizu.flyme.reflect.ActionBarProxy;

public class SmartBarBackButton extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		ActionBar bar = getActionBar();

		// 设置more键图标
		ActionBarProxy.SetOverFlowButtonDrawable(bar, getResources()
				.getDrawable(R.drawable.mz_ic_tab_more_normal));
		// 设置back键图标
		ActionBarProxy.SetBackButtonDrawable(bar,
				getResources().getDrawable(R.drawable.mz_ic_tab_back_normal));

	}
	
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_settings, menu);
        return true;
    }
}
