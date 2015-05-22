package com.guu.money.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

/**
 * 保存或�?�获取一些设置�??
 */

public class Setting {
	
	/**
     * 设置�?个int�?
     */
    public static void setInt(Context con, String name, int value){
    	Editor edit = con.getSharedPreferences("oldhelper", 0).edit();  
		edit.putInt(name, value);
		edit.commit(); 
    }
    
    /**
     * 获取�?个已保存的int值，如果不存在则返回-100
     */
    public static int getInt(Context con, String name){
    	SharedPreferences sharedata = con.getSharedPreferences("oldhelper", 0);  
		int value = sharedata.getInt(name, -100);
		return value; 
    }
    
    /**
     * 设置�?个字符串�?
     */
    public static void setString(Context con, String name, String value){
    	Editor edit = con.getSharedPreferences("oldhelper", 0).edit();  
		edit.putString(name, value);
		edit.commit(); 
    }
    
    /**
     * 获取�?个已保存的字符串值，如果不存在则返回"null"
     */
    public static String getString(Context con, String name){
    	SharedPreferences sharedata = con.getSharedPreferences("oldhelper", 0);  
    	String value = sharedata.getString(name, "null");
		return value; 
    }
    
    /**
     * 设置�?个布尔�??
     */
    public static void setBool(Context con, String name, boolean value){
    	Editor edit = con.getSharedPreferences("oldhelper", 0).edit();  
		edit.putBoolean(name, value);
		edit.commit(); 
    }
    
    /**
     * 获取�?个已保存的布尔�?�，如果不存在则返回false
     */
    public static boolean getBool(Context con, String name){
    	SharedPreferences sharedata = con.getSharedPreferences("oldhelper", 0);  
		boolean value = sharedata.getBoolean(name, false);
		return value; 
    }
}
