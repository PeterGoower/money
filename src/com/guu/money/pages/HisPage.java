package com.guu.money.pages;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.FindCallback;
import com.avos.avoscloud.SaveCallback;
import com.guu.money.R;
import com.guu.money.adapter.AddAdapter;
import com.guu.money.listener.ContentChange;
import com.guu.money.listener.TipEvent;
import com.guu.money.utils.Global;
import com.guu.money.utils.Items;
import com.guu.money.utils.Utily;
import com.guu.money.views.EditListView;
import com.guu.money.views.Tip;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.PagerTitleStrip;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class HisPage extends BasePage implements TipEvent{
	private Tip tip = new Tip(this,this);
	private View view1, view2, view3, view4, view5, view6, view7, view8, view9, view10, view11, view12;//需要滑动的页卡  
    private ViewPager viewPager;
    private PagerTitleStrip pagerTitleStrip;
    private PagerTabStrip pagerTabStrip;
    private List<View> viewList;
    private Intent intent;  
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page_his);
        
        viewPager = (ViewPager) findViewById(R.id.viewpager); 
        pagerTabStrip=(PagerTabStrip) findViewById(R.id.pagertab); 
        pagerTabStrip.setTabIndicatorColor(getResources().getColor(R.color.theme_blue));  
        pagerTabStrip.setDrawFullUnderline(false); 
        pagerTabStrip.setTextColor(Color.WHITE);
        pagerTabStrip.setBackgroundColor(getResources().getColor(R.color.glass_dark)); 
        pagerTabStrip.setTextSpacing(200); 
        
        initView(); 
        
        viewPager.setAdapter(pagerAdapter);  
        viewPager.setCurrentItem(1000);  
    }
    
    private PagerAdapter pagerAdapter = new PagerAdapter() {  
    	  
        @Override  
        public boolean isViewFromObject(View arg0, Object arg1) {  

            return arg0 == arg1;  
        }  

        @Override  
        public int getCount() {  

            return 2000;  
        }  

        @Override  
        public void destroyItem(ViewGroup container, int position,  
                Object object) {  
            container.removeView(viewList.get(position%12));  
        }  

        @Override  
        public int getItemPosition(Object object) {  

            return super.getItemPosition(object);  
        }  

        @Override  
        public CharSequence getPageTitle(int position) {  

            return dateShow(position);//直接用适配器来完成标题的显示，所以从上面可以看到，我们没有使用PagerTitleStrip。当然你可以使用。  

        }  
        
        @Override  
        public Object instantiateItem(ViewGroup container, int position) {  
        	View curr = viewList.get(position%12);
            container.addView(curr);  
            TextView t = (TextView)curr.findViewById(R.id.title);
            t.setText(dateShow(position));
             
            return curr;  
        } 
    };  
    
    private void initView() { 
        view1 = findViewById(R.layout.tab);  
        view2 = findViewById(R.layout.tab);  
        view3 = findViewById(R.layout.tab); 
        view4 = findViewById(R.layout.tab); 
        view5 = findViewById(R.layout.tab); 
        view6 = findViewById(R.layout.tab); 
        view7 = findViewById(R.layout.tab); 
        view8 = findViewById(R.layout.tab); 
        view9 = findViewById(R.layout.tab); 
        view10 = findViewById(R.layout.tab); 
        view11 = findViewById(R.layout.tab); 
        view12 = findViewById(R.layout.tab); 
  
        LayoutInflater lf = getLayoutInflater().from(this);  
        view1 = lf.inflate(R.layout.tab, null);  
        view2 = lf.inflate(R.layout.tab, null);  
        view3 = lf.inflate(R.layout.tab, null);
        view4 = lf.inflate(R.layout.tab, null);
        view5 = lf.inflate(R.layout.tab, null);
        view6 = lf.inflate(R.layout.tab, null);
        view7 = lf.inflate(R.layout.tab, null);
        view8 = lf.inflate(R.layout.tab, null);
        view9 = lf.inflate(R.layout.tab, null);
        view10 = lf.inflate(R.layout.tab, null);
        view11 = lf.inflate(R.layout.tab, null);
        view12 = lf.inflate(R.layout.tab, null);
  
        viewList = new ArrayList<View>();// 将要分页显示的View装入数组中  
        viewList.add(view1);  
        viewList.add(view2);  
        viewList.add(view3);
        viewList.add(view4);
        viewList.add(view5);
        viewList.add(view6);
        viewList.add(view7);
        viewList.add(view8);
        viewList.add(view9);
        viewList.add(view10);
        viewList.add(view11);
        viewList.add(view12);
    } 
    
    private int[] getDateByPosition(int position){
        Date date = new Date(System.currentTimeMillis());
        int year = date.getYear()+1900;
        int month = date.getMonth()+1;
    	
        int[] ret = {0, 0};
    	int offset = position-1000;
    	int offYear = offset/12;
    	int offMonth = offset%12;;
    	
    	if(offYear > 0 || offMonth > 0){
    		month = month + offMonth;
    		if(month > 12){
    			month = month - 12;
    			year = year + 1;
    		}
    		
    		year = year + offYear;
    	}else if(offYear < 0 || offMonth < 0){
    		month = month + offMonth;
    		if(month < 1){
    			month = month + 12;
    			year = year - 1;
    		}
    		
    		year = year + offYear;
    	}
    		
    	ret[0] = year;
    	ret[1] = month;
    	 
    	return ret;
    }
    
    private String dateShow(int position){
    	int[] date = getDateByPosition(position);
    	
    	String year = String.valueOf(date[0]);
    	String month = String.valueOf(date[1]);
    	return year + "年" + month + "月";
    }
    
    @Override  
    public boolean onCreateOptionsMenu(Menu menu) {  
        MenuItem edit = menu.add(0,0,0, this.getResources().getString(R.string.setting));
        edit.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
        
        MenuItem save = menu.add(0,1,1, this.getResources().getString(R.string.save));
        save.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
        
        return true;  
    }  
  
    @Override  
    public boolean onOptionsItemSelected(MenuItem item) {  
        switch (item.getItemId()) {  
        case 0:  
            break;  
        case 1: 
        	
            break;  
        default:  
            break;  
        }  
        return super.onOptionsItemSelected(item);  
    }

	

	@Override
	public void onHintDismiss(int eventTag) {
	}

	@Override
	public void onChoose(int which, int eventTag) {
	}

	@Override
	public void onConfirm(int eventTag) {
		// TODO Auto-generated method stub
		
	}  
}