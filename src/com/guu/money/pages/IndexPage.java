package com.guu.money.pages;

import java.util.Timer;
import java.util.TimerTask;
import com.guu.money.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;


public class IndexPage extends Activity {
	private Timer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        requestWindowFeature(Window.FEATURE_NO_TITLE); 
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,   
                WindowManager.LayoutParams.FLAG_FULLSCREEN); 
        
        setContentView(R.layout.page_index);
        
        timer = new Timer(true);
		timer.schedule(task, 1500, 1500); 
    }

    private TimerTask task = new TimerTask(){  
		public void run() {  
			timer.cancel();
			Intent intent = new Intent();
			intent.setClass(IndexPage.this, LoginPage.class);
			startActivity(intent);
			IndexPage.this.finish();
	    }  
	};
    
}
