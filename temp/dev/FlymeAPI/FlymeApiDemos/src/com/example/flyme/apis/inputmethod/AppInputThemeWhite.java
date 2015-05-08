
package com.example.flyme.apis.inputmethod;


import android.os.Bundle;
import android.widget.Toast;

import com.example.flyme.apis.BaseActivity;
import com.example.flyme.apis.R;
import com.meizu.flyme.reflect.InputMethodProxy;


public class AppInputThemeWhite extends BaseActivity {
    private boolean mSetSuccessful = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inputmethod_layout);
        mSetSuccessful = InputMethodProxy.setInputThemeLight(this, true);
        String show = mSetSuccessful ? "成功设置导航栏和输入法背景" : "设置导航栏和输入法背景失败";
        Toast toast = Toast.makeText(this, show, Toast.LENGTH_SHORT);
        toast.show();
    }
}
