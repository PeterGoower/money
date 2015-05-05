package com.guu.money.pages;
import java.util.Timer;
import java.util.TimerTask;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.SignUpCallback;
import com.guu.money.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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



public class LoginPage extends Activity implements OnClickListener{
	public static final int ANI_SHOW_USER = 0;
	public static final int ANI_SHOW_PSW = 1;
	public static final int ANI_SHOW_LOGIN = 2;
	public static final int ANI_SHOW_MORE = 3;
	public static final int ANI_SHOW_SWITCH = 4;
	
	private int currAniStatus = 0;
	private boolean btnSwitch = true;
	private Timer timer;
	
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
	private ProgressDialog progressDialog;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); 
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,   
                WindowManager.LayoutParams.FLAG_FULLSCREEN); 
        
        setTheme(R.style.Blue); 
        
        setContentView(R.layout.page_login);
        login = (Button)this.findViewById(R.id.btn_login);
        user = (EditText)this.findViewById(R.id.user);
        email = (EditText)this.findViewById(R.id.email);
        psw = (EditText)this.findViewById(R.id.psw);
        userZone = (RelativeLayout)this.findViewById(R.id.user_zone);
        emailZone = (RelativeLayout)this.findViewById(R.id.email_zone);
        pswZone = (RelativeLayout)this.findViewById(R.id.psw_zone);
        moreZone = (LinearLayout)this.findViewById(R.id.more_zone);
        intro = (TextView)this.findViewById(R.id.intro);
        regi = (TextView)this.findViewById(R.id.btn_regi);
        regi.setOnClickListener(this);
        login.setOnClickListener(this);

        timer = new Timer(true);
		timer.schedule(task, 2000, 150); 
    }
    
    private TimerTask task = new TimerTask(){  
		public void run() { 
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
		
		ani.setInterpolator(this, android.R.anim.accelerate_interpolator);//ÉèÖÃ¶¯»­²åÈëÆ÷
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

	@Override
	public void onClick(View v) {
		int id = v.getId();
		if(id == R.id.btn_regi){
			showLoginAni(ANI_SHOW_SWITCH);
			if(btnSwitch == true){
				btnSwitch = false;
				regi.setText(R.string.login);
				login.setText(R.string.register);
				emailZone.setVisibility(View.VISIBLE);
			}else{
				btnSwitch = true;
				regi.setText(R.string.register);
				login.setText(R.string.login);
				emailZone.setVisibility(View.GONE);
			}
		}else if(id == R.id.btn_login){
			if (!psw.getText().toString().isEmpty()) {
				if (!user.getText().toString().isEmpty()) {
					if (!email.getText().toString().isEmpty()) {
						progressDialogShow();
						register();
					} else {
						showError(LoginPage.this.getString(R.string.error_register_email_address_null));
					}
				} else {
					showError(LoginPage.this.getString(R.string.error_register_user_name_null));
				}
			} else {
				showError(LoginPage.this.getString(R.string.error_register_password_null));
			}
		}
		
	}
	
	public void register() {
	    SignUpCallback signUpCallback = new SignUpCallback() {
	      public void done(AVException e) {
	        progressDialogDismiss();
	        if (e == null) {
	          showRegisterSuccess();
	          
	        } else {
	          switch (e.getCode()) {
	            case 202:
	              showError(LoginPage.this.getString(R.string.error_register_user_name_repeat));
	              break;
	            case 203:
	              showError(LoginPage.this.getString(R.string.error_register_email_repeat));
	              break;
	            default:
	              showError(LoginPage.this.getString(R.string.network_error));
	              break;
	          }
	        }
	      }
	    };
	    String username = user.getText().toString();
	    String password = psw.getText().toString();
	    String emailaddr = email.getText().toString();

	    signUp(username, password, emailaddr, signUpCallback);
		}
	
	private void signUp(String username, String password, String email, SignUpCallback signUpCallback) {
	    AVUser user = new AVUser();
	    user.setUsername(username);
	    user.setPassword(password);
	    user.setEmail(email);
	    user.signUpInBackground(signUpCallback);
	  }
	
	 private void showError(String errorMessage) {
		    new AlertDialog.Builder(this)
		        .setTitle(
		            this.getResources().getString(
		                R.string.dialog_message_title))
		        .setMessage(errorMessage)
		        .setNegativeButton(android.R.string.ok,
		            new DialogInterface.OnClickListener() {
		              public void onClick(DialogInterface dialog,
		                                  int which) {
		                dialog.dismiss();
		              }
		    }).show();
     }
	 
	 private void progressDialogShow() {
			progressDialog = ProgressDialog
					.show(this,
							this.getResources().getText(
									R.string.dialog_message_title),
									this.getResources().getText(
									R.string.dialog_text_wait), true, false);
		}
	 private void progressDialogDismiss() {
			if (progressDialog != null)
				progressDialog.dismiss();
		}

		private void showRegisterSuccess() {
			new AlertDialog.Builder(this)
					.setTitle(
							this.getResources().getString(
									R.string.dialog_message_title))
					.setMessage(
							this.getResources().getString(
									R.string.success_register_success))
					.setNegativeButton(android.R.string.ok,
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int which) {
									dialog.dismiss();
								}
							}).show();
		}
}