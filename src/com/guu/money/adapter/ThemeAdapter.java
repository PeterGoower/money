package com.guu.money.adapter;

import java.util.ArrayList;
import java.util.List;

import com.avos.avoscloud.LogUtil.log;
import com.guu.money.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ThemeAdapter extends BaseAdapter{
	private LayoutInflater mInflater;

	public ThemeAdapter(Context context) {
		super();
		mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {
		return 6;
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}
	
	private int getColorByIndex(int index){
		int[] themes = {R.color.theme_blue, R.color.theme_glu, R.color.theme_green, R.color.theme_pink, R.color.theme_red, R.color.theme_yellow};
		return themes[index];
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder vh = null;
		View view = convertView;
		if(view == null){
			view = mInflater.inflate(R.layout.view_theme_item, null);
			vh = new ViewHolder();
			vh.show = (View)view.findViewById(R.id.color_show);
			
			view.setTag(vh);
		}else{
			vh = (ViewHolder) view.getTag();
		}
		int color = getColorByIndex(position);
		log.d("Goower","color:"+color);
		vh.show.setBackgroundResource(color);
		return view;
	}

	private class ViewHolder {
		View show;
	}

}
