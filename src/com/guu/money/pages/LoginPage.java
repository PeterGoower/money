package com.guu.money.pages;
import java.util.Timer;
import java.util.TimerTask;

import com.guu.money.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;



public class LoginPage extends Activity implements OnClickListener{
	public static final int ANI_SHOW_USER = 0;
	public static final int ANI_SHOW_PSW = 1;
	public static final int ANI_SHOW_LOGIN = 2;
	public static final int ANI_SHOW_MORE = 3;
	public static final int ANI_SHOW_SWITCH = 4;
	
	private int currAniStatus = -1;
	private boolean btnSwitch = true;
	private Timer timer;
	
	private Button login;
	private RelativeLayout userZone;
	private RelativeLayout pswZone;
	private LinearLayout moreZone;
	private TextView intro;
	private TextView regi;
	
	
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); 
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,   
                WindowManager.LayoutParams.FLAG_FULLSCREEN); 
        
        setContentView(R.layout.page_login);
        login = (Button)this.findViewById(R.id.btn_login);
        userZone = (RelativeLayout)this.findViewById(R.id.user_zone);
        pswZone = (RelativeLayout)this.findViewById(R.id.psw_zone);
        moreZone = (LinearLayout)this.findViewById(R.id.more_zone);
        intro = (TextView)this.findViewById(R.id.intro);
        regi = (TextView)this.findViewById(R.id.btn_regi);
        regi.setOnClickListener(this);

        timer = new Timer(true);
		timer.schedule(task, 2000, 150); 
    }
    
    private TimerTask task = new TimerTask(){  
		public void run() {  
			
			currAniStatus = currAniStatus + 1;
			Message msg = Message.obtain();
			msg.what = currAniStatus;
			msg.obj = null;
			mHandler.sendMessage(msg);
			
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
				showLoginAni();
            	return;
            }
		}
	};
	
	private void showLoginAni(){
		TranslateAnimation ani = new TranslateAnimation(1000, 0,0,0);  
		
		ani.setInterpolator(this, android.R.anim.accelerate_interpolator);//ÉèÖÃ¶¯»­²åÈëÆ÷
		ani.setFillAfter(true);
        if(currAniStatus == ANI_SHOW_USER){
        	ani.setDuration(150);
            userZone.startAnimation(ani);
            userZone.setVisibility(View.VISIBLE);
            intro.setVisibility(View.INVISIBLE);
        }
        
        if(currAniStatus == ANI_SHOW_PSW){
        	ani.setDuration(150);
            pswZone.startAnimation(ani);
            pswZone.setVisibility(View.VISIBLE);
        }
        
        if(currAniStatus == ANI_SHOW_LOGIN){
        	ani.setDuration(150);
            login.startAnimation(ani);
            login.setVisibility(View.VISIBLE);
        }

        if(currAniStatus == ANI_SHOW_MORE){
        	ani.setDuration(150);
            moreZone.startAnimation(ani);
            moreZone.setVisibility(View.VISIBLE);
        }
        if(currAniStatus == ANI_SHOW_SWITCH){
        	ani.setInterpolator(this, android.R.anim.anticipate_interpolator);
        	ani.setDuration(150);
        	login.startAnimation(ani);
        }
	}

	@Override
	public void onClick(View v) {
		int id = v.getId();
		if(id == R.id.btn_regi){
			
			currAniStatus = ANI_SHOW_SWITCH;
			showLoginAni();
			if(btnSwitch == true){
				btnSwitch = false;
				regi.setText(R.string.login);
				login.setText(R.string.register);
			}else{
				btnSwitch = true;
				regi.setText(R.string.register);
				login.setText(R.string.login);
			}
		}
		
	}
}