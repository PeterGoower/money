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
	
	public static final String DATA_TABLE_MONTH = "month_data";
	public static final String DATA_MONTH_DATE = "month_date";
	public static final String DATA_MONTH_TOTAL = "month_total";
	public static final String DATA_MONTH_OTHER = "other";
	public static final String DATA_MONTH_DESC = "desc";
	
	public static final String THEME_CURR = "theme_curr";  
	public static final String ITEM_EDIT_HINT_HIDE = "item_edit_hint_hide";
	
	public static void setTheme(Context con){
		con.setTheme(getCurrTheme(con));
	}
	
	public static int getCurrTheme(Context con){
		int currTheme = Setting.getInt(con, THEME_CURR);
		if(currTheme == -100){
			currTheme = R.style.Blue;
		}
		return currTheme;
	}
	
	public static void changeTheme(Context con, int theme){
		Setting.setInt(con, THEME_CURR, theme);
		setTheme(con);
	}
	
	public static int getIndexIcon(Context con){
		int icon = 0;
		int currTheme = getCurrTheme(con);
		switch(currTheme){
		case R.style.Blue:
			icon = R.drawable.ic_tab_index_blue;
			break;
		case R.style.Green:
			icon = R.drawable.ic_tab_index_green;
			break;
		case R.style.Red:
			icon = R.drawable.ic_tab_index_red;
			break;
		case R.style.Yellow:
			icon = R.drawable.ic_tab_index_yellow;
			break;
		case R.style.Glu:
			icon = R.drawable.ic_tab_index_glu;
			break;
		case R.style.Pink:
			icon = R.drawable.ic_tab_index_pink;
			break;
		}
		
		return icon;
	}
	
	public static int getGalleryIcon(Context con){
		int icon = 0;
		int currTheme = getCurrTheme(con);
		switch(currTheme){
		case R.style.Blue:
			icon = R.drawable.ic_tab_gallery_blue;
			break;
		case R.style.Green:
			icon = R.drawable.ic_tab_gallery_green;
			break;
		case R.style.Red:
			icon = R.drawable.ic_tab_gallery_red;
			break;
		case R.style.Yellow:
			icon = R.drawable.ic_tab_gallery_yellow;
			break;
		case R.style.Glu:
			icon = R.drawable.ic_tab_gallery_glu;
			break;
		case R.style.Pink:
			icon = R.drawable.ic_tab_gallery_pink;
			break;
		}
		
		return icon;
	}
	
	public static int getSettingIcon(Context con){
		int icon = 0;
		int currTheme = getCurrTheme(con);
		switch(currTheme){
		case R.style.Blue:
			icon = R.drawable.ic_tab_setting_blue;
			break;
		case R.style.Green:
			icon = R.drawable.ic_tab_setting_green;
			break;
		case R.style.Red:
			icon = R.drawable.ic_tab_setting_red;
			break;
		case R.style.Yellow:
			icon = R.drawable.ic_tab_setting_yellow;
			break;
		case R.style.Glu:
			icon = R.drawable.ic_tab_setting_glu;
			break;
		case R.style.Pink:
			icon = R.drawable.ic_tab_setting_pink;
			break;
		}
		
		return icon;
	}

}
