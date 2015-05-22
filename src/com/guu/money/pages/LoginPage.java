package com.guu.money.pages;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import com.avos.avoscloud.AVACL;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.FindCallback;
import com.avos.avoscloud.LogInCallback;
import com.avos.avoscloud.RequestPasswordResetCallback;
import com.avos.avoscloud.SaveCallback;
import com.avos.avoscloud.SignUpCallback;
import com.guu.money.R;
import com.guu.money.listener.TipEvent;
import com.guu.money.utils.Cloud;
import com.guu.money.utils.Global;
import com.guu.money.utils.Utily;
import com.guu.money.views.Tip;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;



public class LoginPage extends BasePage implements OnClickListener, TipEvent{
	public static final int ANI_SHOW_USER = 0;
	public static final int ANI_SHOW_PSW = 1;
	public static final int ANI_SHOW_LOGIN = 2;
	public static final int ANI_SHOW_MORE = 3;
	public static final int ANI_SHOW_SWITCH = 4;
	public static final int TIMEOUT_HINT = 5;
	
	public static final int EVENT_REGI_OK = 0;
	public static final int EVENT_FIND_OK = 1;
	public static final int EVENT_TIMEOUT = 2;
	
	public static final int BTN_LOGIN = 0;
	public static final int BTN_REGI = 1;
	public static final int BTN_FORGET = 2;
	
	private int currAniStatus = 0;
	private int btnSwitch = BTN_LOGIN;
	private Timer timer;
	
	private boolean loginYet = false;
	private boolean timeFull = false;
	private boolean dataGot = false;
	private int gohomeType = 0;  //0普通 1登录 2注册
	private boolean active = true;
	
	
	
	private String[] itemNameIni;
	private int itemCountInit;
	private int itemSaveIndexInit = 0;
	
	private Button login;
	private EditText user;
	private EditText email;
	private EditText psw;
	private RelativeLayout userZone;
	private RelativeLayout emailZone;
	private RelativeLayout pswZone;
	private LinearLayout moreZone;
	private TextView intro;
	private TextView regi;
	private TextView forget;
	private Tip tip = new Tip(this,this);
	
	private String userString;
	private String pswString;
	private String emailString;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE); 
        
        super.onCreate(savedInstanceState);
        
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,   
                WindowManager.LayoutParams.FLAG_FULLSCREEN); 
        
        setContentView(R.layout.page_login);
        
        user = (EditText)this.findViewById(R.id.user);
        email = (EditText)this.findViewById(R.id.email);
        psw = (EditText)this.findViewById(R.id.psw);
        
        userZone = (RelativeLayout)this.findViewById(R.id.user_zone);
        emailZone = (RelativeLayout)this.findViewById(R.id.email_zone);
        pswZone = (RelativeLayout)this.findViewById(R.id.psw_zone);
        moreZone = (LinearLayout)this.findViewById(R.id.more_zone);
        
        intro = (TextView)this.findViewById(R.id.intro);
        login = (Button)this.findViewById(R.id.btn_login);
        regi = (TextView)this.findViewById(R.id.btn_regi);
        forget = (TextView)this.findViewById(R.id.btn_forget);
        regi.setOnClickListener(this);
        login.setOnClickListener(this);
        forget.setOnClickListener(this);
        
        AVUser currentUser = AVUser.getCurrentUser();
        if (currentUser != null) {
        	loginYet = true;
        	login.setEnabled(false);
        	initData(true);
        } 
        timer = new Timer(true);
		timer.schedule(task, 1000, 150); 
    }
    
    private TimerTask task = new TimerTask(){  
		public void run() { 
			if(loginYet == true){
				timeFull = true;
				timer.cancel();
				if(dataGot && gohomeType == 0){
					goHome();
				}
			    return;
			}
			Message msg = Message.obtain();
			msg.what = currAniStatus;
			msg.obj = null;
			mHandler.sendMessage(msg);
			currAniStatus = currAniStatus + 1;
			if(currAniStatus > ANI_SHOW_MORE){
				timer.cancel();
			}
	    }  
	};
	
	private Handler mHandler = new Handler(){
		
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			if(msg.what <= ANI_SHOW_MORE){
				showLoginAni(msg.what);
				
            	return;
            }else if(msg.what == TIMEOUT_HINT){
            	if(active){
            		tip.showHint(R.string.timeout, 5000);
                	tip.setEventTag(EVENT_TIMEOUT);
            	}
            }
		}
	};
	
	private void initData(boolean first){
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
    	
    	if(first){
    		timer = new Timer(true);
    		timer.schedule(timeoutTask, 10000); 
    	}
    	
    }
	
	private TimerTask timeoutTask = new TimerTask(){  
		public void run() { 
			timer.cancel();
			Message msg = Message.obtain();
			msg.what = TIMEOUT_HINT;
			msg.obj = null;
			mHandler.sendMessage(msg);
	    }  
	};
    
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
    	        		initData(false);
    	        	}
    	        }
    	    }
    	});
    }
    
    private void fetchAllData(){
    	AVQuery<AVObject> query = new AVQuery<AVObject>(Global.DATA_TABLE_MONTH);
    	query.whereNotEqualTo(Global.DATA_MONTH_DATE, "NULL");
    	query.findInBackground(new FindCallback<AVObject>() {
    		@Override
    	    public void done(List<AVObject> avObjects, AVException e) {
    	        if (e == null) {
    	        	int count = avObjects.size();
    	        	if(count != 0){
    	        		Global.currMonthData = avObjects;
    	        	}
    	        }
    	    }
    	});
    	
    	manageDataGot();
    }
    
    private void manageDataGot(){
        dataGot = true;
    	
    	if(gohomeType == 0){
    		if(timeFull){
    			goHome();
    		}
    	}else if(gohomeType == 1){
    		tip.dismissWaitting();
            goHome();
    	}else if(gohomeType == 2){
    		tip.dismissWaitting();
    		tip.showHint(R.string.success_register_success);
        	tip.setEventTag(EVENT_REGI_OK);
    	}
    }
	
	private void showLoginAni(int aniStatus){
		TranslateAnimation ani = new TranslateAnimation(1000, 0,-200,0);  
		
		ani.setInterpolator(this, android.R.anim.accelerate_interpolator);
		ani.setFillAfter(true);
        if(aniStatus == ANI_SHOW_USER){
        	ani.setDuration(150);
            userZone.startAnimation(ani);
            userZone.setVisibility(View.VISIBLE);
            intro.setVisibility(View.INVISIBLE);
        }
        
        if(aniStatus == ANI_SHOW_PSW){
        	ani.setDuration(150);
            pswZone.startAnimation(ani);
            pswZone.setVisibility(View.VISIBLE);
        }
        
        if(aniStatus == ANI_SHOW_LOGIN){
        	ani.setDuration(150);
            login.startAnimation(ani);
            login.setVisibility(View.VISIBLE);
        }

        if(aniStatus == ANI_SHOW_MORE){
        	ani.setDuration(150);
            moreZone.startAnimation(ani);
            moreZone.setVisibility(View.VISIBLE);
        }
        if(aniStatus == ANI_SHOW_SWITCH){
        	ani.setInterpolator(this, android.R.anim.anticipate_interpolator);
        	ani.setDuration(150);
        	login.startAnimation(ani);
        }
	}
	
	private void switchBtnStatus(){
		showLoginAni(ANI_SHOW_SWITCH);
		if(btnSwitch == BTN_LOGIN){
			btnSwitch = BTN_REGI;
			regi.setText(R.string.login);
			login.setText(R.string.register);
			emailZone.setVisibility(View.VISIBLE);
		}else if(btnSwitch == BTN_REGI){
			btnSwitch = BTN_LOGIN;
			regi.setText(R.string.register);
			login.setText(R.string.login);
			emailZone.setVisibility(View.GONE);
		}
	} 
	
	private void showForget(){
		
		btnSwitch = BTN_FORGET;
		login.setText(R.string.psw_forget_btn);
		regi.setText(R.string.login);
		emailZone.setVisibility(View.VISIBLE);
		
        TranslateAnimation ani = new TranslateAnimation(0, 1000,0,-200);  
		
		ani.setInterpolator(this, android.R.anim.accelerate_interpolator);
		ani.setFillAfter(true);
		ani.setDuration(150);
		userZone.startAnimation(ani);
		pswZone.startAnimation(ani);
		userZone.setVisibility(View.GONE);
		pswZone.setVisibility(View.GONE);
	}
	
	private void hideForget(){
		btnSwitch = BTN_LOGIN;
		login.setText(R.string.login);
		regi.setText(R.string.register);
		userZone.setVisibility(View.VISIBLE);
		emailZone.setVisibility(View.GONE);
		pswZone.setVisibility(View.VISIBLE);
		
        TranslateAnimation ani = new TranslateAnimation(1000, 0, -200, 0);  
		
		ani.setInterpolator(this, android.R.anim.accelerate_interpolator);
		ani.setFillAfter(true);
		ani.setDuration(150);
		userZone.startAnimation(ani);
		pswZone.startAnimation(ani);
	}

	@Override
	public void onClick(View v) {
		int id = v.getId();
		if(id == R.id.btn_regi){
			if(btnSwitch == BTN_FORGET){
				hideForget();
			}else{
				switchBtnStatus();
			}
		}else if(id == R.id.btn_forget){
			if(btnSwitch != BTN_FORGET){
				showForget();
			}
		}else if(id == R.id.btn_login){
			userString = user.getText().toString();
		    pswString = psw.getText().toString();
		    emailString = email.getText().toString().trim();
		    
			if(btnSwitch == BTN_LOGIN){
				if(userString.isEmpty()){
					tip.showHint(R.string.error_register_user_name_null);
					return;
				}
				if(pswString.isEmpty()){
					tip.showHint(R.string.error_register_password_null);
					return;
				}
				tip.showWaitting();
				login();
			} else if(btnSwitch == BTN_REGI) {
				if(userString.isEmpty()){
					tip.showHint(R.string.error_register_user_name_null);
					return;
				}
				if(pswString.isEmpty()){
					tip.showHint(R.string.error_register_password_null);
					return;
				}
				if(emailString.isEmpty()){
					tip.showHint(R.string.error_register_email_address_null);
					return;
				}
				if(!Utily.isEmail(emailString)){
					tip.showHint(R.string.error_register_email_address_wrong);
					return;
				}
				
				tip.showWaitting();
				register();
			} else if(btnSwitch == BTN_FORGET) {
				if(emailString.isEmpty()){
					tip.showHint(R.string.error_register_email_address_null);
					return;
				}
				if(!Utily.isEmail(emailString)){
					tip.showHint(R.string.error_register_email_address_wrong);
					return;
				}
				
				tip.showWaitting();
				findPsw();
			}
		}
	}
	
	private void goHome(){
		timer.cancel();
		Intent intent = new Intent(LoginPage.this, HomePage.class);
        startActivity(intent); 
        this.finish();
	}
	
	private void register() {
		gohomeType = 2;
	    SignUpCallback signUpCallback = new SignUpCallback() {
	        public void done(AVException e) {
	        	
	            if (e == null) {
	            	initData(true);
	            } else {
	            	tip.dismissWaitting();
	                switch (e.getCode()) {
	                    case 202:
	                    	tip.showHint(R.string.error_register_user_name_repeat);
	                    break;
	                    
	                    case 203:
	                    	tip.showHint(R.string.error_register_email_repeat);
	                    break;
	                    
	                    default:
	                    	tip.showHint(R.string.network_error);
	                    break;
	                }
	            }
	        }
	    };

	    Cloud.signUp(userString, pswString, emailString, signUpCallback);
	}
	
	private void login() {
		gohomeType = 1;
		LogInCallback loginCallback = new LogInCallback() {
			public void done(AVUser user, AVException e) {
	        	
	            if (e == null) {
	            	initData(true);
	            } else {
	            	tip.dismissWaitting();
	                tip.showHint(R.string.login_fail);
	            }
	        }
	    };

	    Cloud.login(userString, pswString, loginCallback);
	}
	
	private void findPsw() {
		RequestPasswordResetCallback resetCallback = new RequestPasswordResetCallback() {
			public void done(AVException e) {
	        	tip.dismissWaitting();
	        	
	            if (e == null) {
	            	tip.showHint(R.string.find_psw_ok);
	            	tip.setEventTag(EVENT_FIND_OK);
	            } else {
	                tip.showHint(R.string.find_psw_fail);
	            }
	        }
	    };

	    Cloud.resetPsw(emailString, resetCallback);
	}

	@Override
	public void onHintDismiss(int eventTag) {
		if(eventTag == EVENT_REGI_OK){
			goHome();
		}else if(eventTag == EVENT_FIND_OK){
			hideForget();
		}
	}

	@Override
	public void onChoose(int which, int eventTag) {
		Log.d("Goower", "btn"+which+"/eventTag:"+eventTag);
	}

	@Override
	public void onConfirm(int eventTag) {
		Log.d("Goower", "eventTag:"+eventTag);
	}
	
	@Override
	public void onStop() {
		super.onStop();
        active = false;
     }
}