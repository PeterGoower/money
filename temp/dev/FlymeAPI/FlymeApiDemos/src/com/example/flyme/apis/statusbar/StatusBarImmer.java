
package com.example.flyme.apis.statusbar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.example.flyme.apis.R;
import com.meizu.flyme.reflect.ActionBarProxy;
import com.meizu.flyme.reflect.StatusBarProxy;

public class StatusBarImmer extends Activity {
    private ListView mListView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.statusbar_immer_layout);
        mListView = (ListView)findViewById(R.id.sb_show_list);
        List<Map<String, Object>> contents = new ArrayList<Map<String, Object>>();
        for (int i = 0; i < 100;) {
            Map<String, Object> map = new HashMap<String, Object>();
            if (i % 2 == 0) {
                map.put("ICON", R.drawable.music);
                map.put("TITLE", ++i + "  Test Title one");
                map.put("CONTENT", "Test Content one");
            } else {
                map.put("ICON", R.drawable.music);
                map.put("TITLE", ++i + "  Test Title two Title two");
                map.put("CONTENT", "Test Content two Test Content two");
            }
            contents.add(map);
        }
        SimpleAdapter adapter = new SimpleAdapter(this, contents, R.layout.sb_list_item_layout,
                new String[] {
                        "ICON", "TITLE", "CONTENT"
                },
                new int[] {
                        android.R.id.icon, android.R.id.text1, android.R.id.text2
                });
        mListView.setAdapter(adapter);
        
        //设置状态栏透明
        StatusBarProxy.setImmersedWindow(getWindow(), true);
        //设置状态栏图标文字为深色
        StatusBarProxy.setStatusBarDarkIcon(getWindow(), true);
        int paddingTop = StatusBarProxy.getStatusBarHeight(this) + ActionBarProxy.getActionBarHeight(this, getActionBar());
        int paddingBottom = ActionBarProxy.getActionBarHeight(this, getActionBar());
        mListView.setPadding(mListView.getPaddingLeft(), paddingTop, mListView.getPaddingRight(), paddingBottom);
    }
}
