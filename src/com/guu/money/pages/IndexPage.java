package com.guu.money.pages;


import com.avos.avoscloud.AVUser;
import com.guu.money.R;
import com.guu.money.listener.MyTabListener;
import com.guu.money.utils.Global;
import com.guu.money.utils.Setting;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;

public class IndexPage extends Fragment {
	private Button setBtn;
	private Button setBtn2;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    	View indexView = inflater.inflate(R.layout.page_home, container, false);
    	
    	setBtn = (Button) indexView.findViewById(R.id.btn1);
		setBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				
				int[] themes = {R.style.Blue, R.style.Glu, R.style.Green, R.style.Pink, R.style.Red, R.style.Yellow};
				int currTheme = Setting.getInt(IndexPage.this.getActivity(), Global.THEME_CURR);
				int currIndex = 0;
				for(int i=0; i<6; i++){
					if(currTheme == themes[i]){
						currIndex = i;
						break;
					}
				}
				currIndex++;
				if(currIndex == 6){
					currIndex = 0;
				}
				
				Global.changeTheme(IndexPage.this.getActivity(), themes[currIndex]);
				
				//activity.finish();  
				  
		        //activity.startActivity(new Intent(activity, activity.getClass()));  
				
				Activity activity = IndexPage.this.getActivity();
				ActionBar bar = IndexPage.this.getActivity().getActionBar();
				bar.removeTab(bar.getTabAt(0));
				bar.addTab(bar.newTab().setIcon(R.drawable.ic_tab_index)
						.setTabListener(new MyTabListener<IndexPage>(activity, "index", IndexPage.class)),0,true);
				
			
			}
		});
		AVUser currentUser = AVUser.getCurrentUser();
		setBtn.setText(currentUser.getUsername());
		
		setBtn2 = (Button) indexView.findViewById(R.id.btn2);
		setBtn2.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				AVUser.logOut();   
				setBtn.setText("未登录");
			}
		});
        
        return indexView;
    }

}
