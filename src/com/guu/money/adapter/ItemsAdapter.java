package com.guu.money.adapter;

import java.util.List;

import com.avos.avoscloud.AVObject;
import com.guu.money.R;
import com.guu.money.utils.Global;

import android.content.Context;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;

public class ItemsAdapter extends BaseAdapter{
    private LayoutInflater mInflater;
    private List<AVObject> data;
    private Context context;
    
    private EditText name;
    private Button delete;
    private Button add;
    
    public ItemsAdapter(Context context, List<AVObject> data){
    	this.context = context;
        this.data = data;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    
    public int getCount() {
    	if(data == null ){
    		return 0;
    	}else{
    		return data.size();
    	}
    }

    public AVObject getItem(int position) {
    	return (AVObject)data.get(position);
    }

    public long getItemId(int position) {
        return position;
    }
    
    public void setData(List<AVObject> data){
    	this.data = data;
    }
    
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
    	final int pos = position;
    	
		convertView = mInflater.inflate(R.layout.view_items_item, null);
		name = (EditText)convertView.findViewById(R.id.name);
		delete = (Button)convertView.findViewById(R.id.delete);
		add = (Button)convertView.findViewById(R.id.add);
		
		final AVObject currAv = data.get(position);
		name.setText(currAv.get(Global.DATA_NAME_NAME).toString());
		name.addTextChangedListener(new TextWatcher() {
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
			    	currAv.put(Global.DATA_NAME_NAME, input);
			    	data.set(pos, currAv);
			    }
			}
		});
		return convertView;
    }
}
