package com.guu.money.pages;

import com.guu.money.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
public class BasePage extends Activity{
    
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         
        requestWindowFeature(Window.FEATURE_NO_TITLE); 
        
        setTheme(R.style.Blue);
    }
}