package com.example.flyme.blurdrawable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.meizu.flyme.blur.drawable.BlurDrawable;
import com.meizu.flyme.bulrdrawabledemo.R;
import com.meizu.flyme.reflect.ActionBarProxy;

import android.animation.ObjectAnimator;
import android.app.ActionBar;
import android.app.ListActivity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.SimpleAdapter;
import android.widget.Toast;

public class MainActivity extends ListActivity {
	private BlurDrawable mBlurDrawable;
	private ActionBar ab;
	private View blurview;
	String devicename1;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_ACTION_BAR_OVERLAY);
		setContentView(R.layout.activity_main);
		blurview = this.findViewById(R.id.blur_rect_view);
		List<Map<String, Object>> contents = new ArrayList<Map<String, Object>>();
		for (int i = 1; i <= 30; i++) {
			Map<String, Object> map = new HashMap<String, Object>();
			if (i % 2 == 0) {
				map.put("ICON", R.drawable.jpeg6);
				map.put("TITLE", i + "  Test Title one");
				map.put("CONTENT", "Test Content one");
			} else {
				map.put("ICON", R.drawable.jpeg7);
				map.put("TITLE", i + "  Test Title one");
				map.put("CONTENT", "Test Content two Test Content two");
			}
			contents.add(map);
		}
		SimpleAdapter adapter = new SimpleAdapter(this, contents,
				R.layout.list_item,
				new String[] { "ICON", "TITLE", "CONTENT" }, new int[] {
						android.R.id.icon, android.R.id.text1,
						android.R.id.text2 });
		setListAdapter(adapter);
	    // 设置ActionBar背景为动态模糊背景，同时考虑了顶部ActionBar下面的横条
         ab= getActionBar();
        ColorDrawable cd = new ColorDrawable(0xFFF56313);
        LayerDrawable ld = new LayerDrawable(new Drawable[] { new BlurDrawable(), cd });
        int paddingBottom = ActionBarProxy.getSmartBarHeight(this, getActionBar());
        ld.setLayerInset(1, 0, paddingBottom-5, 0, 0);
        
		try {
			String devicename="ro.product.device";
			Method method = Class.forName("android.os.SystemProperties").getMethod("get",String.class);
		    devicename1=(String) method.invoke(null, devicename);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
        Log.d("dengxintest"," System.getProperty(ro.product.device) : "+devicename1);
        if(devicename1.contains("mx"))
        ab.setBackgroundDrawable(ld);
        else ab.setBackgroundDrawable(new BlurDrawable());
        ab.setSplitBackgroundDrawable(new BlurDrawable());
        mBlurDrawable = new BlurDrawable();
		blurview.setBackground(mBlurDrawable);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		switch (item.getItemId()) {
		
		case R.id.blur_level:
			  // 用动画的方式演示：可以通过setBlurLevel设置模糊的等级
			Toast.makeText(this, "通过blurLevel设置模糊等级",Toast.LENGTH_SHORT).show();
            ObjectAnimator blurLevelanim = ObjectAnimator.ofFloat(mBlurDrawable,"blurLevel", BlurDrawable.DEFAULT_BLUR_LEVEL, 0.0f, BlurDrawable.DEFAULT_BLUR_LEVEL);
            blurLevelanim.setDuration(2000);
            blurLevelanim.start();
			break;
			
		case R.id.blur_alpha:
	        // 用动画的方式演示：可以通过setAlpha设置模糊效果在原有图像上叠加时采用的透明度
			Toast.makeText(this, "通过alpha在模糊上设置透明度",Toast.LENGTH_SHORT ).show();
            ObjectAnimator alphaanim = ObjectAnimator.ofInt(mBlurDrawable, "alpha", 255, 0, 255);
            alphaanim.setDuration(2000);
            alphaanim.start();
			break;

		case R.id.blur_color:
            // 可以通过setColorFilter设置在模糊效果基础上叠加的颜色效果
            // 默认颜色效果是 PorterDuff.Mode.SRC_OVER(正常绘制显示，上下层绘制叠盖)
			Toast.makeText(this, "通过colorFilter在模糊上叠加颜色效果",Toast.LENGTH_SHORT ).show();
            int r = (int)(Math.random() * 255);
            int g = (int)(Math.random() * 255);
            int b = (int)(Math.random() * 255);
            mBlurDrawable.setColorFilter(Color.argb(0x88, r, g, b), PorterDuff.Mode.SRC_OVER);
			break;
		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}
}
