package com.guu.money.pages;
import java.util.Timer;
import java.util.TimerTask;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.LogInCallback;
import com.avos.avoscloud.RequestPasswordResetCallback;
import com.avos.avoscloud.SignUpCallback;
import com.guu.money.R;
import com.guu.money.listener.TipEvent;
import com.guu.money.utils.Cloud;
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
	
	public static final int EVENT_REGI_OK = 0;
	public static final int EVENT_FIND_OK = 1;
	
	public static final int BTN_LOGIN = 0;
	public static final int BTN_REGI = 1;
	public static final int BTN_FORGET = 2;
	
	private int currAniStatus = 0;
	private int btnSwitch = BTN_LOGIN;
	private Timer timer;
	private boolean loginYet = false;
	
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
        } 
        timer = new Timer(true);
		timer.schedule(task, 1000, 150); 
    }
    
    private TimerTask task = new TimerTask(){  
		public void run() { 
			if(loginYet == true){
				goHome();
				timer.cancel();
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
            }
		}
	};
	
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
		    emailString = email.getText().toString();
		    
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
		Intent intent = new Intent(LoginPage.this, HomePage.class);
        startActivity(intent); 
        this.finish();
	}
	
	private void register() {
	    SignUpCallback signUpCallback = new SignUpCallback() {
	        public void done(AVException e) {
	        	tip.dismissWaitting();
	            if (e == null) {
	            	tip.showHint(R.string.success_register_success);
	            	tip.setEventTag(EVENT_REGI_OK);
	            } else {
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
		LogInCallback loginCallback = new LogInCallback() {
			public void done(AVUser user, AVException e) {
	        	tip.dismissWaitting();
	            if (e == null) {
	            	goHome();
	            } else {
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
}