package com.example.flyme.apis.smartbar;

import com.example.flyme.apis.BaseActivity;
import com.example.flyme.apis.R;
import com.meizu.flyme.reflect.ActionBarProxy;
import com.meizu.flyme.reflect.StatusBarProxy;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

/**
 * 调用 ActionBar.setActionBarViewCollapsable(boolean) 隐藏 ActionBar 顶栏的示例。
 */
public class HideActionBarTop extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.hide_actionbar_top);

		ActionBar bar = getActionBar();
		if(bar != null){
			//确保顶栏无内容
			bar.setDisplayOptions(0);
			// 设置若顶栏没有显示内空，则隐藏
			ActionBarProxy.setActionBarViewCollapsable(bar, true);
		}

        //设置状态栏图标文字为深色
        StatusBarProxy.setStatusBarDarkIcon(getWindow(), true);
        int paddingTop = StatusBarProxy.getStatusBarHeight(this);
        int paddingBottom = ActionBarProxy.getSmartBarHeight(this, getActionBar());
        View contentView = findViewById(android.R.id.content);
        contentView.setPadding(contentView.getPaddingLeft(), paddingTop,contentView.getPaddingRight(), paddingBottom);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.action_bar_top_menu, menu);
		return super.onCreateOptionsMenu(menu);
	}

	public void onSort(MenuItem item) {
	}

}
