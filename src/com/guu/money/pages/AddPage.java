package com.guu.money.pages;

import java.util.ArrayList;
import java.util.List;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
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
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

public class AddPage extends BasePage implements ContentChange, TipEvent{
	private final static int MONTH_SAVE_OK = 0;
	private final static int MONTH_SAVE_FAIL = 1;
	public static boolean itemChanegFlag = false;
	private EditListView list = null;
	private AddAdapter infoAdapter;
	private List<Items> data;
	private Tip tip = new Tip(this,this);
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page_add);
        
        itemChanegFlag = false;
        setData();
        list = (EditListView)findViewById(R.id.list);
        list.setEditId(R.id.content);
        infoAdapter = new AddAdapter(this, data, this);
		list.setAdapter(infoAdapter);
    }
    
    @Override
    public void onResume() {
        super.onResume();
        if(AddPage.itemChanegFlag){
        	setData();
        	infoAdapter.setData(data);
        	infoAdapter.notifyDataSetChanged();
        	AddPage.itemChanegFlag = false;
        }
    }
    
    private void setData(){
    	data = new ArrayList<Items>();
    	
    	int count = Global.currItemData.size();
    	for(int i=0; i<count; i++){
    		AVObject curr = Global.currItemData.get(i);
    		Items it = new Items();
    		it.id = curr.get(Global.DATA_NAME_ID).toString();
    		it.name = curr.getString(Global.DATA_NAME_NAME);
    		it.content = "";
    		data.add(it);
    	}
    	
    	Items other = new Items();
    	other.id = "other";
    	other.name = this.getResources().getString(R.string.other1);
    	other.content = "";
		data.add(other);
		
		Items desc = new Items();
		desc.id = "desc";
		desc.name = this.getResources().getString(R.string.desc);
		desc.content = "";
		data.add(desc);
    }
    
    private void save(){
    	if(!testData()){
    		return;
    	}
    	
    	tip.showWaitting();
    	AVObject item = new AVObject(Global.DATA_TABLE_MONTH);
    	int count = data.size();
    	for(int i=0; i<count; i++){
    		Items currData = data.get(i);
    		item.put(currData.id, currData.content);
    	}
    	item.put("date", Utily.getShowTime());
        item.setACL(Global.currAcl);
        
    	item.saveInBackground(new SaveCallback() {
    	    public void done(AVException e) {
    	    	tip.dismissWaitting();
    	        if (e == null) {
    	        	
    	        	tip.showHint(R.string.month_save_ok);
    	        	tip.setEventTag(MONTH_SAVE_OK);
    	        } else {
    	        	tip.showHint(R.string.month_save_fail);
    	        	tip.setEventTag(MONTH_SAVE_FAIL);
    	        }
    	    }
    	});
    }
    
    private boolean testData(){
    	boolean dataOk = true;
    	int countDigi = data.size() - 1;//备注不用限制填数字
    	for(int i=0; i<countDigi; i++){
    		String curr = data.get(i).content.trim();
    		if(curr == "" || !Utily.isNumeric(curr) || curr.length() < 1 || curr == null){
    			tip.showHint(R.string.content_null);
    			dataOk = false;
    			break;
    		}
    	}
    	return dataOk;
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
        	Intent intent = new Intent(this, ItemsPage.class);
	        startActivity(intent);
            break;  
        case 1:  
        	save();
            break;  
        default:  
            break;  
        }  
        return super.onOptionsItemSelected(item);  
    }

	@Override
	public void dataChaneg(int index, String value) {
		Items it = data.get(index);
		it.content = value;
		data.set(index, it);
	}

	@Override
	public void onHintDismiss(int eventTag) {
		if(eventTag == MONTH_SAVE_OK){
			this.finish();
		}
	}

	@Override
	public void onChoose(int which, int eventTag) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onConfirm(int eventTag) {
		// TODO Auto-generated method stub
		
	}  
}