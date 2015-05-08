package com.example.flyme.apis.smartbar;


import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.flyme.apis.BaseActivity;
import com.example.flyme.apis.R;
import com.meizu.flyme.reflect.ActionBarProxy;

public class HasSmartBar extends BaseActivity {
	private static final int SETTINGS_ID = Menu.FIRST;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (ActionBarProxy.hasSmartBar()) {
			// 如有SmartBar，则使用拆分ActionBar，使MenuItem显示在底栏
			getWindow().setUiOptions(
					ActivityInfo.UIOPTION_SPLIT_ACTION_BAR_WHEN_NARROW);
			Toast.makeText(this, "has SmartBar!", Toast.LENGTH_SHORT).show();
		} else {
			// 取消ActionBar拆分，使MenuItem显示在顶栏
			getWindow().setUiOptions(0);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuItem item = menu.add(0, SETTINGS_ID, 0, "settings");
		item.setIcon(R.drawable.ic_sb_praise);
		item.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
		return true;
	}

}
