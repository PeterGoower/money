package com.guu.money.pages;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.DeleteCallback;
import com.guu.money.R;
import com.guu.money.adapter.MonthAdapter;
import com.guu.money.listener.TipEvent;
import com.guu.money.utils.Global;
import com.guu.money.views.Tip;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

public class HisPage extends BasePage implements TipEvent{
	private final int DELETE_MONTH = 0;
	private Tip tip = new Tip(this,this);
	private View view1, view2, view3, view4, view5, view6, view7, view8, view9, view10, view11, view12;//需要滑动的页卡  
    private ViewPager viewPager;
    private PagerTabStrip pagerTabStrip;
    private List<View> viewList;
    private int currPos = 1000;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page_his);
        
        viewPager = (ViewPager) findViewById(R.id.viewpager); 
        pagerTabStrip=(PagerTabStrip) findViewById(R.id.pagertab); 
    }
    
    @Override
    public void onResume() {
        super.onResume();
        initView(); 
        
        viewPager.setAdapter(pagerAdapter);  
        viewPager.setCurrentItem(currPos);  
    }
    
    private void fillView(View view, String date){
    	int index = Global.getMonthDataIndex(date);
    	TextView hint = (TextView)view.findViewById(R.id.hint);
    	ListView list = (ListView)view.findViewById(R.id.list);
    	if(index != -1){
    		list.setVisibility(View.VISIBLE);
    		hint.setVisibility(View.INVISIBLE);
    		
    		AVObject av = Global.currMonthData.get(index);
        	MonthAdapter infoAdapter = new MonthAdapter(this, Global.fillItemsWithAv(av, Global.getItemsData(this, true), true));;
        	list.setAdapter(infoAdapter);
    	}else{
    		list.setVisibility(View.INVISIBLE);
    		hint.setVisibility(View.VISIBLE);
    	}
    	
    }
    
    private void goEdit(){
    	Intent intent = new Intent(this, AddPage.class); 
    	Bundle bundle = new Bundle();
    	currPos = viewPager.getCurrentItem();
        bundle.putString("edit_date", getTableDate(currPos));
    	intent.putExtras(bundle);
    	startActivity(intent);
    }
    
    private void askDelete(){
    	tip.showChoose(R.string.delete_month_hint);
    	tip.setEventTag(DELETE_MONTH);
    }
    
    private void doDelete(){
    	currPos = viewPager.getCurrentItem();
    	final int index = Global.getMonthDataIndex(getTableDate(currPos));
    	if(index != -1){
    		tip.showWaitting();
    		AVObject av = Global.currMonthData.get(index);
    		av.deleteInBackground(new DeleteCallback() {
        	    public void done(AVException e) {
        	    	tip.dismissWaitting();
        	        if (e == null) {
        	        	Global.currMonthData.remove(index);
        	        	Global.dataChange = true;
        	        	initView(); 
        	            
        	            viewPager.setAdapter(pagerAdapter);  
        	            viewPager.setCurrentItem(currPos);  
        	        } else {
        	        	tip.showHint(R.string.delete_fail);
        	        }
        	    }
        	});
        }
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

            return dateShow(position);

        }  
        
        @Override  
        public Object instantiateItem(ViewGroup container, int position) {  
        	View curr = viewList.get(position%12);
            container.addView(curr); 
            
            fillView(curr, getTableDate(position));
             
            return curr;  
        } 
    };  
    
    private void initView() { 
    	pagerTabStrip.setTabIndicatorColor(Color.parseColor(Global.getThemeColor(this)));  
        pagerTabStrip.setDrawFullUnderline(false); 
        pagerTabStrip.setTextColor(Color.parseColor(Global.getThemeColor(this)));
        pagerTabStrip.setBackgroundColor(getResources().getColor(R.color.gray)); 
        pagerTabStrip.setTextSpacing(200); 
        
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
    
    private String getTableDate(int position){
    	int date[] = getDateByPosition(position);
        int year = date[0];
        int month = date[1];
        String monthString = String.valueOf(month);
        if(month < 10){
        	monthString = "0" + month;
        }
        
        return year + "-" +monthString;
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
    	return year + this.getResources().getString(R.string.year) 
    			+ month 
    			+ this.getResources().getString(R.string.month) ;
    }
    
    @Override  
    public boolean onCreateOptionsMenu(Menu menu) {  
        
        MenuItem edit = menu.add(0,1,1, this.getResources().getString(R.string.edit));
        edit.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
        
        MenuItem delete = menu.add(0,0,0, this.getResources().getString(R.string.delete));
        delete.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
        
        return true;  
    }  
  
    @Override  
    public boolean onOptionsItemSelected(MenuItem item) {  
        switch (item.getItemId()) {  
        case 1:  
        	goEdit();
            break;  
        case 0:  
        	askDelete();
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
		if(which == Tip.CHOOSE_RIGHT && eventTag == DELETE_MONTH){
			doDelete();
		}
	}

	@Override
	public void onConfirm(int eventTag) {
		// TODO Auto-generated method stub
		
	}  
}