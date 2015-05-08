package com.guu.money.pages;
import com.avos.avoscloud.AVUser;
import com.guu.money.R;
import com.meizu.flyme.reflect.ActionBarProxy;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.ActionBar.Tab;
import android.os.Bundle;

public class HomePage extends BasePage {
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        final ActionBar bar = getActionBar();
        bar.addTab(bar.newTab().setIcon(R.drawable.ic_tab_index)
				.setTabListener(new MyTabListener<IndexPage>(this, "index", IndexPage.class)));
		bar.addTab(bar.newTab().setIcon(R.drawable.ic_tab_gallery)
				.setTabListener(new MyTabListener<GalleryPage>(this, "gallery", GalleryPage.class)));
		bar.addTab(bar.newTab().setIcon(R.drawable.ic_tab_setting)
				.setTabListener(new MyTabListener<SettingPage>(this, "setting", SettingPage.class)));
        
        bar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        
		ActionBarProxy.setActionBarTabsShowAtBottom(bar, true);
    }
    
    public static class MyTabListener<T extends Fragment> implements ActionBar.TabListener {
        private final Activity mActivity;
        private final String mTag;
        private final Class<T> mClass;
        private final Bundle mArgs;
        private Fragment mFragment;

        public MyTabListener(Activity activity, String tag, Class<T> clz) {
            this(activity, tag, clz, null);
        }

        public MyTabListener(Activity activity, String tag, Class<T> clz, Bundle args) {
            mActivity = activity;
            mTag = tag;
            mClass = clz;
            mArgs = args;

            // Check to see if we already have a fragment for this tab, probably
            // from a previously saved state.  If so, deactivate it, because our
            // initial state is that a tab isn't shown.
            mFragment = mActivity.getFragmentManager().findFragmentByTag(mTag);
            if (mFragment != null && !mFragment.isDetached()) {
                FragmentTransaction ft = mActivity.getFragmentManager().beginTransaction();
                ft.detach(mFragment);
                ft.commit();
            }
        }

        public void onTabSelected(Tab tab, FragmentTransaction ft) {
            if (mFragment == null) {
                mFragment = Fragment.instantiate(mActivity, mClass.getName(), mArgs);
                ft.add(android.R.id.content, mFragment, mTag);
            } else {
                ft.attach(mFragment);
            }
            
            mActivity.getActionBar().setTitle(mTag);
        }

        public void onTabUnselected(Tab tab, FragmentTransaction ft) {
            if (mFragment != null) {
                ft.detach(mFragment);
            }
        }

        public void onTabReselected(Tab tab, FragmentTransaction ft) {
        }
    }
}