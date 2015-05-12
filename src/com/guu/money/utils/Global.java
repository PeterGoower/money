package com.guu.money.utils;

import java.util.List;

import android.content.Context;

import com.avos.avoscloud.AVACL;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVUser;
import com.guu.money.R;



public class Global {
	public static AVUser currUser;//当前登录用户
	public static AVACL currAcl;//当前登录用户权限
	public static List<AVObject> currItemData;//当前用户的资产类型数据
	
	public static final String DATA_TABLE_ITEMS = "money_items";  //资产类型表
	
	
	public static final String DATA_NAME_ID = "money_id";  //字段：资产类型ID
	public static final String DATA_NAME_NAME = "money_name";  //字段：资产类型名称
	public static final String DATA_NAME_INDEX = "money_index";  //字段：资产类型序号
	
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
