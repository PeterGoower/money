package com.guu.money.pages;

import java.util.List;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.DeleteCallback;
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
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class AddPage extends BasePage implements ContentChange, TipEvent{
	private final static int MONTH_SAVE_OK = 0;
	private final static int MONTH_SAVE_FAIL = 1;
	private final static int MONTH_SAVE_COVER = 2;
	public static boolean itemChanegFlag = false;
	private EditListView list = null;
	private AddAdapter infoAdapter;
	private List<Items> data;
	private Tip tip = new Tip(this,this);
	private String month;
	private AVObject old = null;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page_add);
        
        TextView title = (TextView)findViewById(R.id.title);
        Bundle bundle = this.getIntent().getExtras();
        if(bundle != null){
        	month = bundle.getString("edit_date");
        	title.setText(month + this.getResources().getString(R.string.edit_month));
        }else{
        	month = Utily.getShowTime();
        }
        
        itemChanegFlag = false;
        data = Global.getItemsData(this, false);
        list = (EditListView)findViewById(R.id.list);
        list.setEditId(R.id.content);
        infoAdapter = new AddAdapter(this, data, this);
		list.setAdapter(infoAdapter);
		
		ifSaved();
    }
    
    private void ifSaved(){
    	
    	int index = Global.getMonthDataIndex(month);
    	if(index != -1){
    		old = Global.currMonthData.get(index);
    		data = Global.fillItemsWithAv(old, data, false);
    		infoAdapter.setData(data);
        	infoAdapter.notifyDataSetChanged();
    	}
    }
    
    @Override
    public void onResume() {
        super.onResume();
        if(AddPage.itemChanegFlag){
        	data = Global.getItemsData(this,false);
        	if(old != null){
        		data = Global.fillItemsWithAv(old, data, false);
        		infoAdapter.setData(data);
            	infoAdapter.notifyDataSetChanged();
        	}
        	infoAdapter.setData(data);
        	infoAdapter.notifyDataSetChanged();
        	AddPage.itemChanegFlag = false;
        }
    }
    
    private void save(){
    	if(!testData()){
    		return;
    	}
    	
    	tip.showWaitting();
    	
    	
    	if(old != null){
    		deleteOld();
    	}else{
    		saveNew();
    	}
    }
    
    private void deleteOld(){
    	AVObject item = new AVObject(Global.DATA_TABLE_MONTH);
    	item.setObjectId(old.getObjectId());
        
        item.deleteInBackground(new DeleteCallback() {
    	    public void done(AVException e) {
    	    	
    	        if (e == null) {
    	        	saveNew();
    	        } else {
    	        	tip.dismissWaitting();
    	        	Log.d("Goower","old.getObjectId() "+old.getObjectId());
    	        	Log.d("Goower","deleteOld "+e);
    	        	tip.showHint(R.string.month_save_fail);
    	        	tip.setEventTag(MONTH_SAVE_FAIL);
    	        }
    	    }
    	});
    }
    
    private void saveNew(){
    	AVObject item = new AVObject(Global.DATA_TABLE_MONTH);
    	int count = data.size();
    	int total = 0;
    	for(int i=0; i<count; i++){
    		Items currData = data.get(i);
    		String currContent = currData.content;
    		item.put(currData.id, currContent);
    		Log.d("Goower", currData.id +"/"+currContent );
    		if(i != count - 1){
    		    int currContentInt = Integer.parseInt(currContent);
    		    total = total + currContentInt;
    		}
    	}
    	item.put(Global.DATA_MONTH_TOTAL, total);
    	Log.d("Goower", Global.DATA_MONTH_TOTAL +"/"+total );
    	item.put(Global.DATA_MONTH_DATE, month);
    	Log.d("Goower", Global.DATA_MONTH_DATE +"/"+month );
        item.setACL(Global.currAcl);
        
    	item.saveInBackground(new SaveCallback() {
    	    public void done(AVException e) {
    	        if (e == null) {
    	        	loadNew();
    	        	
    	        } else {
    	        	tip.dismissWaitting();
    	        	Log.d("Goower","saveNew " + e);
    	        	tip.showHint(R.string.month_save_fail);
    	        	tip.setEventTag(MONTH_SAVE_FAIL);
    	        }
    	    }
    	});
    	
    }

    private void loadNew(){
    	AVQuery<AVObject> query = new AVQuery<AVObject>(Global.DATA_TABLE_MONTH);
    	query.whereNotEqualTo(Global.DATA_MONTH_DATE, "NULL");
    	query.findInBackground(new FindCallback<AVObject>() {
    		@Override
    	    public void done(List<AVObject> avObjects, AVException e) {
    			tip.dismissWaitting();
    	        if (e == null) {
    	        	int count = avObjects.size();
    	        	if(count != 0){
    	        		Global.currMonthData = avObjects;
    	        		tip.showHint(R.string.month_save_ok);
        	        	tip.setEventTag(MONTH_SAVE_OK);
    	        	}
    	        }else{
    	        	Log.d("Goower","loadNew");
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
        	if(old == null){
        		save();
        	}else{
        		tip.showChoose(R.string.save_month_cover);
        		tip.setEventTag(MONTH_SAVE_COVER);
        	}
        	
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
		if(eventTag == MONTH_SAVE_COVER && which == Tip.CHOOSE_RIGHT){
			save();
		}
	}

	@Override
	public void onConfirm(int eventTag) {
		// TODO Auto-generated method stub
		
	}  
}