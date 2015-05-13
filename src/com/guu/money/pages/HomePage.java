package com.guu.money.pages;

import java.util.ArrayList;
import java.util.List;

import com.avos.avoscloud.AVACL;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.FindCallback;
import com.avos.avoscloud.SaveCallback;
import com.guu.money.R;
import com.guu.money.listener.MyTabListener;
import com.guu.money.utils.Global;
import com.guu.money.utils.Utily;
import com.meizu.flyme.reflect.ActionBarProxy;

import android.app.ActionBar;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;

public class HomePage extends BasePage{
	private long exitTime = 0;
	
	private String[] itemNameIni;
	private int itemCountInit;
	private int itemSaveIndexInit = 0;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        final ActionBar bar = getActionBar();
        bar.addTab(bar.newTab().setIcon(R.drawable.ic_tab_index)
				.setTabListener(new MyTabListener<IndexPage>(this, "index", IndexPage.class)));
		bar.addTab(bar.newTab().setIcon(R.drawable.ic_tab_gallery)
				.setTabListener(new MyTabListener<GalleryPage>(this, "gallery", GalleryPage.class)));
		bar.addTab(bar.newTab().setIcon(R.drawable.ic_tab_setting)
				.setTabListener(new MyTabListener<SettingPage>(this, "setting", SettingPage.class)));
        
        bar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        
		ActionBarProxy.setActionBarTabsShowAtBottom(bar, true);
		
		initData();
    }
    
    private void initData(){
    	Global.currUser = AVUser.getCurrentUser();
    	Global.currAcl =  new AVACL();
    	Global.currAcl.setReadAccess(Global.currUser,true);
    	Global.currAcl.setWriteAccess(Global.currUser, true);
    	
    	AVQuery<AVObject> query = new AVQuery<AVObject>(Global.DATA_TABLE_ITEMS);
    	query.whereNotEqualTo(Global.DATA_NAME_ID, "NULL");
    	query.findInBackground(new FindCallback<AVObject>() {
    		@Override
    	    public void done(List<AVObject> avObjects, AVException e) {
    	        if (e == null) {
    	        	int count = avObjects.size();
    	        	if(count == 0){
    	        		initItemData();
    	        	}else{
    	        		Global.currItemData = avObjects;
    	        		fetchAllData();
    	        	}
    	        }else{
    	        	initItemData();
    	        }
    	    }
    	});
    }
    
    private void initItemData(){
    	String itemIni = this.getResources().getString(R.string.init_items);
    	itemNameIni = itemIni.split("\\|");
    	itemCountInit= itemNameIni.length;
    	initItemSave();
    }
    
    private void initItemSave(){
    	AVObject item = new AVObject(Global.DATA_TABLE_ITEMS);
    	item.put(Global.DATA_NAME_ID, System.currentTimeMillis());
    	item.put(Global.DATA_NAME_NAME, itemNameIni[itemSaveIndexInit]);
    	item.put(Global.DATA_NAME_INDEX, itemSaveIndexInit);
        item.setACL(Global.currAcl);
        
    	item.saveInBackground(new SaveCallback() {
    	    public void done(AVException e) {
    	        if (e == null) {
    	        	if(itemSaveIndexInit < itemCountInit - 1){
    	        		Log.d("Goower", "itemSaveIndexInit:" + itemSaveIndexInit);
    	        		itemSaveIndexInit++;
    	        		initItemSave();
    	        	}else if(itemSaveIndexInit == itemCountInit - 1){
    	        		Log.d("Goower", "ok");
    	        		initData();
    	        	}
    	        }
    	    }
    	});
    }
    
    private void fetchAllData(){
    	Log.d("Goower", "data init ok");
    	
    }
//    Fragment fragment =(Fragment)getFragmentManager().findFragmentByTag("index");
//	IndexPage test = (IndexPage)fragment;
//	test.test();
    
    @Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) 
		{
			if((System.currentTimeMillis() - exitTime) > 5000){  
				Utily.showToast(this, R.string.ask_quit);
	            exitTime = System.currentTimeMillis();   
	        } else {
	            finish();
	            System.exit(0);
	        }
			
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
}