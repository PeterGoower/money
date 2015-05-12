package com.guu.money.pages;

import com.avos.avoscloud.AVObject;
import com.guu.money.R;
import com.guu.money.utils.Global;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

public class AddPage extends BasePage{
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page_add);
    }
   
    private void test(){
    	AVObject th = Global.currItemData.get(1);
    	Log.d("Goower", th.get(Global.DATA_NAME_ID).toString());
    	Log.d("Goower", th.get(Global.DATA_NAME_NAME).toString());
    }
    
    @Override  
    public boolean onCreateOptionsMenu(Menu menu) {  
        MenuItem edit = menu.add(0,0,0, this.getResources().getString(R.string.edit_items));
        edit.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
        
        return true;  
    }  
  
    @Override  
    public boolean onOptionsItemSelected(MenuItem item) {  
        switch (item.getItemId()) {  
        case 0:  
            test();
            break;  
        
        default:  
            break;  
        }  
        return super.onOptionsItemSelected(item);  
    }  
}