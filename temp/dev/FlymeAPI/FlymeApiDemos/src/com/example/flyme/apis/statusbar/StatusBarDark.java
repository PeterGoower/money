
package com.example.flyme.apis.statusbar;

import android.R.anim;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.example.flyme.apis.R;
import com.meizu.flyme.reflect.StatusBarProxy;


/**
 * @author MEIZU.SDK Team
 */
public class StatusBarDark extends Activity {
    private Button mStatusConf;
    private boolean mIsDark = false;
    private View mContentView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.statusbar_bg_conf_layout);
        mStatusConf = (Button) findViewById(R.id.set_dark);
        mStatusConf.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                mIsDark = !mIsDark;
                //切换状态栏主题颜色风格
                boolean successful = StatusBarProxy.setStatusBarDarkIcon(getWindow(), mIsDark);
                String string = "";
                if (successful) {
                    string = mIsDark ? "已设置状态栏深色主题" : "已设置状态栏浅色主题";
                }else {
                    string = "设置状态栏颜色失败";
                }
                Toast.makeText(StatusBarDark.this,string, Toast.LENGTH_SHORT).show();
            }
        });
        //设置状态栏透明
        StatusBarProxy.setImmersedWindow(getWindow(), true);
        //隐藏导航栏
        mStatusConf.setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
    }
    
    
}
