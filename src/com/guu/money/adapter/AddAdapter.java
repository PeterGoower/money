package com.guu.money.adapter;

import java.util.List;

import com.guu.money.R;
import com.guu.money.listener.ContentChange;
import com.guu.money.utils.Items;
import com.guu.money.utils.Utily;

import android.content.Context;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.DigitsKeyListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class AddAdapter extends BaseAdapter{
    private LayoutInflater mInflater;
    private List<Items> data;
    private Context context;
    
    private TextView name;
    private EditText content;
    private int count = 0;
    private ContentChange lis;
    
    public AddAdapter(Context context, List<Items> data, ContentChange lis){
    	this.context = context;
        this.data = data;
        this.lis = lis;
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
    	final int pos = position;
    	
		convertView = mInflater.inflate(R.layout.view_add_item, null);
		name = (TextView)convertView.findViewById(R.id.name);
		content = (EditText)convertView.findViewById(R.id.content);
		
		Items it = data.get(position);
		name.setText(it.name);
		content.setText(it.content);
		if(position == count - 1){
			content.setHint(context.getResources().getString(R.string.desc_hint));
			content.setTextSize(20);
			content.setPadding(content.getPaddingLeft(), content.getPaddingTop(), 
					content.getPaddingRight(), Utily.dip2px(context, 10));
			AbsListView.LayoutParams lp = new AbsListView.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, Utily.dip2px(context, 100));
            convertView.setLayoutParams(lp);
		}else{
			
			content.setKeyListener(new
					DigitsKeyListener(false,true));
			AbsListView.LayoutParams lp = new AbsListView.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, Utily.dip2px(context, 60));
            convertView.setLayoutParams(lp);
		}
		
		content.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
			// TODO Auto-generated method stub
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
			int after) {
			// TODO Auto-generated method stub
			}

			@Override
			public void afterTextChanged(Editable s) {
			    String input= s.toString();
			    if(!TextUtils.isEmpty(input)){
			    	lis.dataChaneg(pos, input);
			    }
			}
		});
		
		return convertView;
    }
}
