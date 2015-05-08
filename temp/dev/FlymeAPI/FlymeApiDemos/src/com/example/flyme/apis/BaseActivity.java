package com.example.flyme.apis;

import com.meizu.flyme.reflect.ActionBarProxy;
import com.meizu.flyme.reflect.StatusBarProxy;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

public class BaseActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        //设置状态栏图标文字为深色
        StatusBarProxy.setStatusBarDarkIcon(getWindow(), true);
        int paddingTop = StatusBarProxy.getStatusBarHeight(this) + ActionBarProxy.getActionBarHeight(this, getActionBar());
        int paddingBottom = ActionBarProxy.getSmartBarHeight(this, getActionBar());
        View contentView = findViewById(android.R.id.content);
        contentView.setPadding(contentView.getPaddingLeft(), paddingTop,contentView.getPaddingRight(), paddingBottom);
    }
}
