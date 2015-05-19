package com.guu.money.adapter;

import java.util.List;

import com.guu.money.R;
import com.guu.money.utils.Items;
import com.guu.money.utils.Utily;

import android.content.Context;
import android.text.method.DigitsKeyListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MonthAdapter extends BaseAdapter{
    private LayoutInflater mInflater;
    private List<Items> data;
    private Context context;
    
    private TextView name;
    private TextView content;
    private int count = 0;
    
    public MonthAdapter(Context context, List<Items> data){
    	this.context = context;
        this.data = data;
        count = data.size();
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    
    public int getCount() {
    	if(data == null ){
    		return 0;
    	}else{
    		return data.size();
    	}
    }

    public Items getItem(int position) {
    	return (Items)data.get(position);
    }

    public long getItemId(int position) {
        return position;
    }
    
    public void setData(List<Items> data){
    	this.data = data;
    	count = data.size();
    }
    
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
		convertView = mInflater.inflate(R.layout.view_month_item, null);
		name = (TextView)convertView.findViewById(R.id.name);
		content = (TextView)convertView.findViewById(R.id.content);
		
		Items it = data.get(position);
		name.setText(it.name);
		content.setText(it.content);
		if(position == count - 1){
			content.setHint(context.getResources().getString(R.string.nil));
			content.setTextSize(20);
			content.setPadding(content.getPaddingLeft(), Utily.dip2px(context, 20), 
					content.getPaddingRight(), Utily.dip2px(context, 10));
			
		}else{
			
			content.setKeyListener(new
					DigitsKeyListener(false,true));
		}
		
		return convertView;
    }
}
