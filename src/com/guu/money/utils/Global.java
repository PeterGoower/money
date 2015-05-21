package com.guu.money.utils;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;

import com.avos.avoscloud.AVACL;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVUser;
import com.guu.money.R;



public class Global {
	public static boolean dataChange = true;
	
	public static AVUser currUser;//当前登录用户
	public static AVACL currAcl;//当前登录用户权限
	public static List<AVObject> currItemData;//当前用户的资产类型数据
	public static List<AVObject> currMonthData;//当前用户的分月数据
	public static int currPlan = 10000;//当前存钱计划
	
	public static final String DATA_TABLE_ITEMS = "money_items";  //资产类型表
	public static final String DATA_NAME_ID = "money_id";  //字段：资产类型ID
	public static final String DATA_NAME_NAME = "money_name";  //字段：资产类型名称
	public static final String DATA_NAME_INDEX = "money_index";  //字段：资产类型序号
	
	public static final String DATA_TABLE_MONTH = "month_data";
	public static final String DATA_MONTH_DATE = "month_date";
	public static final String DATA_MONTH_TOTAL = "month_total";
	public static final String DATA_MONTH_OTHER = "other";
	public static final String DATA_MONTH_DESC = "desc";
	
	public static final String DATA_TABLE_SETTING = "setting_data";
	public static final String DATA_SETTING_INT = "int_value";
	public static final String DATA_SETTING_STRING = "string_value";
	public static final String DATA_SETTING_KEY = "key";
	public static final String DATA_SETTING_VALUE_PLAN = "plan";
	
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
	
	public static String getThemeColor(Context con){
		String color = null;
		int currTheme = getCurrTheme(con);
		switch(currTheme){
		case R.style.Blue:
			color = "#00a0e9";
			break;
		case R.style.Green:
			color = "#23ba8f";
			break;
		case R.style.Red:
			color = "#fea509";
			break;
		case R.style.Yellow:
			color = "#fc4324";
			break;
		case R.style.Glu:
			color = "#df4696";
			break;
		case R.style.Pink:
			color = "#9e4aac";
			break;
		}
		
		return color;
	}
	
	public static List<Items> getItemsData(Context con, boolean showTotal){
		List<Items> data = new ArrayList<Items>();
    	
    	int count = Global.currItemData.size();
    	for(int i=0; i<count; i++){
    		AVObject curr = Global.currItemData.get(i);
    		Items it = new Items();
    		it.id = curr.get(Global.DATA_NAME_ID).toString();
    		it.name = curr.getString(Global.DATA_NAME_NAME);
    		it.content = "";
    		data.add(it);
    	}
    	
    	Items other = new Items();
    	other.id = Global.DATA_MONTH_OTHER;
    	other.name = con.getResources().getString(R.string.other1);
    	other.content = "";
		data.add(other);
		
		if(showTotal){
			Items total = new Items();
			total.id = Global.DATA_MONTH_TOTAL;
			total.name = con.getResources().getString(R.string.total);
			total.content = "";
			data.add(total);
		}
		
		
		Items desc = new Items();
		desc.id = Global.DATA_MONTH_DESC;
		desc.name = con.getResources().getString(R.string.desc);
		desc.content = "";
		data.add(desc);
		
		return data;
	}
	
	public static List<Items> fillItemsWithAv(AVObject av, List<Items> data, boolean showTotal){
    	int nameValue = 0;
    	int total = av.getInt(Global.DATA_MONTH_TOTAL);
    	int count = data.size();
    	int normalOffset = 2;
    	if(showTotal){
    		normalOffset = 3;
    	}
    	for(int i=0; i<count-normalOffset; i++){
    		Items curr = data.get(i);
    		String id = curr.id;
    		if(av.get(id) != null){
    			String value = av.get(id).toString();
    			int valueInt = Integer.parseInt(value);
    			curr.content = value;
    			data.set(i, curr);
    			nameValue = nameValue + valueInt;
    		}
    	}
    	
    	int other = total - nameValue;
    	Items others = data.get(count-normalOffset);
    	others.content = String.valueOf(other);
    	data.set(count-normalOffset, others);
    	
    	if(showTotal){
    		Items t = data.get(count-2);
        	t.content = av.get(Global.DATA_MONTH_TOTAL).toString();
        	data.set(count-2, t);
    	}
    	
    	Items desc = data.get(count-1);
    	desc.content = av.getString(Global.DATA_MONTH_DESC);
    	data.set(count-1, desc);
    	
    	return data;
    }
	
	public static String[] getMonthDataIndexGroup(){
		String[] ret = null;
		if(Global.currMonthData != null){
    		int dataCount = Global.currMonthData.size();
    		ret = new String[dataCount];
        	for(int i=0; i<dataCount; i++){
        		ret[i] = Global.currMonthData.get(i).getString(Global.DATA_MONTH_DATE);
        	}
    	}
		
		return ret;
	}
	
	public static int getMonthDataIndex(String date){
    	int index = -1;
    	String[] valueDate = getMonthDataIndexGroup();
    	if(valueDate != null){
    		int dataCount = Global.currMonthData.size();
        	for(int i=0; i<dataCount; i++){
        		if(date.equals(valueDate[i])){
        			index = i;
        			break;
        		}
        	}
    	}
    	
    	return index;
    }
}
