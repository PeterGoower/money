package com.guu.money.utils;

import android.content.Context;

import com.guu.money.R;



public class Global {
	public static final String THEME_CURR = "theme_curr";  
	
	public static void setTheme(Context con){
		int currTheme = Setting.getInt(con, THEME_CURR);
		if(currTheme == -100){
			currTheme = R.style.Blue;
		}
		con.setTheme(currTheme);
	}
	
	public static void changeTheme(Context con, int theme){
		Setting.setInt(con, THEME_CURR, theme);
		setTheme(con);
	}

}
