
package com.example.flyme.apis.wallpaper;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.flyme.apis.BaseActivity;
import com.example.flyme.apis.R;
import com.meizu.flyme.reflect.WallpaperManagerProxy;

public class WallPaperFromPath extends BaseActivity {
    private EditText mPathEdit;
    private Button mHomePaperSet;
    private Button mLockPaperSet;
    private boolean setSeccessful = false;
    private Toast mToast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wallpaper_layout);
        mPathEdit = (EditText) findViewById(R.id.pc_path);
        mLockPaperSet = (Button) findViewById(R.id.set_lock_wallpaper);
        mHomePaperSet = (Button) findViewById(R.id.set_home_wallpaper);
        mHomePaperSet.setOnClickListener(mClickListener);
        mLockPaperSet.setOnClickListener(mClickListener);
    }

    OnClickListener mClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            mToast = Toast.makeText(WallPaperFromPath.this, "", Toast.LENGTH_SHORT);
            String path = mPathEdit.getText().toString().trim();
            String string = "";
            if (path.equals("")) {
                string = "路径为空，请输入正确的图片路径";
            } else {
                switch (v.getId()) {
                    case R.id.set_lock_wallpaper:
                        setSeccessful = WallpaperManagerProxy.setLockWallpaper(WallPaperFromPath.this, path);
                        string = setSeccessful ? "已设置锁屏壁纸" : "设置锁屏壁纸失败";
                        break;
                    case R.id.set_home_wallpaper:
                        setSeccessful = WallpaperManagerProxy.setHomeWallpaper(WallPaperFromPath.this, path);
                        string = setSeccessful ? "已设置桌面壁纸" : "设置桌面壁纸失败";
                        break;
                    default:
                        string = "点击异常";
                        break;
                }
            }
            mToast.setText(string);
            mToast.show();
        }
    };
}
