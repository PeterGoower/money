package com.guu.money.pages;

import java.util.ArrayList;
import java.util.List;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.SaveCallback;
import com.guu.money.R;
import com.guu.money.adapter.ItemsAdapter;
import com.guu.money.listener.TipEvent;
import com.guu.money.utils.Global;
import com.guu.money.utils.Setting;
import com.guu.money.views.EditListView;
import com.guu.money.views.Tip;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.RelativeLayout;

public class ItemsPage extends BasePage implements TipEvent{
	private final static int COMMIT_OK = 0;
	private EditListView list = null;
	private RelativeLayout hintArea;
	private Button tipHide;
	private ItemsAdapter infoAdapter;
	private List<AVObject> data;
	private int dataCount;
	private int dataCommitPos;
	private Tip tip = new Tip(this,this);
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page_items);
        
        hintArea = (RelativeLayout)findViewById(R.id.tip);
        tipHide = (Button)findViewById(R.id.tip_hide);
        manageHint();
       
        
        list = (EditListView)findViewById(R.id.list);
        initData();
        infoAdapter = new ItemsAdapter(this, data);
		list.setAdapter(infoAdapter);
    }
    
    private void manageHint(){
    	 tipHide.setOnClickListener(new OnClickListener() {
 			@Override
 			public void onClick(View v) {
 				AlphaAnimation ani = new AlphaAnimation(1, 0);  
 				
 				ani.setInterpolator(ItemsPage.this, android.R.anim.accelerate_decelerate_interpolator);
 				ani.setFillAfter(true);
 		        ani.setDuration(1000);
 		        hintArea.startAnimation(ani);
 				Setting.setBool(ItemsPage.this, Global.ITEM_EDIT_HINT_HIDE, true);
 			}
 		});
    	 
        if(Setting.getBool(ItemsPage.this, Global.ITEM_EDIT_HINT_HIDE)){
         	hintArea.setVisibility(View.INVISIBLE);
 		}else{
 			AlphaAnimation ani = new AlphaAnimation(0, 1);  
 			
 			ani.setInterpolator(this, android.R.anim.accelerate_decelerate_interpolator);
 			ani.setFillAfter(true);
 	        ani.setDuration(2000);
 	        hintArea.startAnimation(ani);
 		}
    }
    
    private void initData(){
    	int count = Global.currItemData.size();
    	data = new ArrayList<AVObject>();
    	for(int i=0; i<count; i++){
    		AVObject av = new AVObject(Global.DATA_TABLE_ITEMS);
    		AVObject old =  Global.currItemData.get(i);
    		av.put(Global.DATA_NAME_ID, old.get(Global.DATA_NAME_ID));
    		av.put(Global.DATA_NAME_NAME, old.get(Global.DATA_NAME_NAME));
        	av.put(Global.DATA_NAME_INDEX, old.get(Global.DATA_NAME_INDEX));
        	av.setObjectId(old.getObjectId());
        	av.setACL(Global.currAcl);
        	data.add(av);
    	}
    }
    
    @Override  
    public boolean onCreateOptionsMenu(Menu menu) {  
        MenuItem edit = menu.add(0,0,0, this.getResources().getString(R.string.save));
        edit.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
        
        return true;  
    }  
    
    private void commit(){
    	dataCount = data.size();
    	dataCommitPos = 0;
    	tip.showWaitting();
    	commitEach();
    }
   
    private void commitEach(){
    	AVObject curr = data.get(dataCommitPos);
    	curr.saveInBackground(new SaveCallback() {
    	   @Override
    	   public void done(AVException e) {
    	        if (e == null) {
    	            if(dataCommitPos < dataCount -1){
    	            	dataCommitPos++;
    	            	commitEach();
    	            }else if(dataCommitPos == dataCount -1){
    	            	tip.dismissWaitting();
    	            	tip.showHint(R.string.item_save_ok);
    	            	tip.setEventTag(COMMIT_OK);
    	            	Global.currItemData = data;
    	            }
    	        } else {
    	        	tip.dismissWaitting();
    	        	tip.showHint(R.string.item_save_fail);
    	        }
    	    }
    	});	
    }
  
    @Override  
    public boolean onOptionsItemSelected(MenuItem item) {  
        switch (item.getItemId()) {  
        case 0:  
            commit();
            break;  
        
        default:  
            break;  
        }  
        return super.onOptionsItemSelected(item);  
    }

	@Override
	public void onHintDismiss(int eventTag) {
		if(COMMIT_OK == eventTag){
			ItemsPage.this.finish();
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