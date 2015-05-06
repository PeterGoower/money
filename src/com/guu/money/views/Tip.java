package com.guu.money.views;

import java.util.Timer;
import java.util.TimerTask;

import com.guu.money.R;
import com.guu.money.listener.TipEvent;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Tip {
	private static final int HINT_DIS = 0;
	private static final int HINT_DIS_DELAY = 1000;
	
	public static final int CHOOSE_LEFT = 0;
	public static final int CHOOSE_RIGHT = 1;
	
	private Activity context;
	private TipEvent event;
	private int eventTag;
	private AlertDialog waittingDialog;
	private AlertDialog hintDialog;
	private AlertDialog confirmDialog;
	private AlertDialog chooseDialog;
	private Timer timer;
	
	public Tip(Activity context, TipEvent event){
		this.context = context;
		this.event = event;
	}
	
	public void setEventTag(int tag){
		eventTag = tag;
	}

	/********************加载框*************************/
	public void showWaitting() {
		eventTag = -100;
		Context mContext = context.getBaseContext();
		LayoutInflater mInflater = LayoutInflater.from(mContext);
		View view = mInflater.inflate(R.layout.view_waitting_dialog, null);
		waittingDialog = new AlertDialog.Builder(context).create();
		waittingDialog.show();
		waittingDialog.getWindow().setContentView(view);
	}

	public void dismissWaitting() {

		if(waittingDialog != null)
			waittingDialog.dismiss();
	}
	
	public boolean isWaittingShow(){
		
		if(waittingDialog != null)
			return waittingDialog.isShowing();
		
		return false;
	}
	
	/********************提示框*************************/
    public void showHint(String hint) {
    	showHint(hint, HINT_DIS_DELAY);
	}
    
    public void showHint(int hint) {
    	showHint(context.getResources().getString(hint), HINT_DIS_DELAY);
	}
    
    public void showHint(String hint, int showtime) {
    	eventTag = -100;
		Context mContext = context.getBaseContext();
		LayoutInflater mInflater = LayoutInflater.from(mContext);
		View view = mInflater.inflate(R.layout.view_hint_dialog, null);
		hintDialog = new AlertDialog.Builder(context).create();
		hintDialog.show();
		
		TextView title = (TextView) view.findViewById(R.id.title);
		title.setText(hint);
		
		hintDialog.getWindow().setContentView(view);
		timer = new Timer(true);
		TimerTask task = new TimerTask(){  
			public void run() { 
				Message msg = Message.obtain();
				msg.what = HINT_DIS;
				msg.obj = null;
				mHandler.sendMessage(msg);
				
				timer.cancel();
		    }  
		};
		timer.schedule(task, showtime); 
	}
	
    private Handler mHandler = new Handler(){
		
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			if(msg.what == HINT_DIS){
				hintDialog.dismiss();
				event.onHintDismiss(eventTag);
            	return;
            }
		}
	};
	
	
	/********************确认框*************************/
	public void showConfirm(int infoResId) {
		showConfirm(context.getResources().getString(infoResId), null);
	}
	
	public void showConfirm(String infoString) {
		showConfirm(infoString, null);
	}
	
	public void showConfirm(int infoResId, int btnResId) {
		showConfirm(context.getResources().getString(infoResId), context.getResources().getString(btnResId));
	}
	
	public void showConfirm(String infoString, String btnString) {
    	eventTag = -100;
		Context mContext = context.getBaseContext();
		LayoutInflater mInflater = LayoutInflater.from(mContext);
		View view = mInflater.inflate(R.layout.view_confirm_dialog, null);
		confirmDialog = new AlertDialog.Builder(context).create();
		confirmDialog.show();
		
		TextView info = (TextView) view.findViewById(R.id.info);
		info.setText(infoString);
		Button confirm = (Button) view.findViewById(R.id.btn_confirm);
		if (btnString != null){
			confirm.setText(btnString);
		}
		confirm.setOnClickListener(new View.OnClickListener(){
		    @Override
	        public void onClick(View v) {
		    	confirmDialog.dismiss();
		    	event.onConfirm(eventTag);
	        }
		});
		
		confirmDialog.getWindow().setContentView(view);
	}
	
	
	/********************选择框*************************/
	public void showChoose(int infoResId) {
		showChoose(context.getResources().getString(infoResId), null, null);
	}
	
	public void showChoose(String infoString) {
		showChoose(infoString, null, null);
	}
	
	public void showChoose(int infoResId, int leftResId, int rightResId) {
		showChoose(context.getResources().getString(infoResId), 
				context.getResources().getString(leftResId),
				context.getResources().getString(rightResId));
	}
	
	public void showChoose(String infoString, String leftString, String rightString) {
    	eventTag = -100;
		Context mContext = context.getBaseContext();
		LayoutInflater mInflater = LayoutInflater.from(mContext);
		View view = mInflater.inflate(R.layout.view_choose_dialog, null);
		chooseDialog = new AlertDialog.Builder(context).create();
		chooseDialog.show();
		
		TextView info = (TextView) view.findViewById(R.id.info);
		info.setText(infoString);
		Button left = (Button) view.findViewById(R.id.left);
		Button right = (Button) view.findViewById(R.id.right);
		if (leftString != null){
			left.setText(leftString);
			right.setText(rightString);
		}
		left.setOnClickListener(new View.OnClickListener(){
		    @Override
	        public void onClick(View v) {
		    	chooseDialog.dismiss();
		    	event.onChoose(CHOOSE_LEFT, eventTag);
	        }
		});
		
		right.setOnClickListener(new View.OnClickListener(){
		    @Override
	        public void onClick(View v) {
		    	chooseDialog.dismiss();
		    	event.onChoose(CHOOSE_RIGHT, eventTag);
	        }
		});
		
		chooseDialog.getWindow().setContentView(view);
	}
}
