/**   
* @company:MeiZu
* @Title: NotificationActivity.java 
* @Package:com.example.flyme.apis.notification 
* @Description: TODO
* @author:  luocanguo
* @date:2014-11-8 下午2:30:46 
* @version V1.0   
*/
package com.example.flyme.apis.notification;

import java.util.ArrayList;
import java.util.List;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationCompat.BigPictureStyle;
import android.support.v4.app.NotificationCompat.BigTextStyle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import com.example.flyme.apis.R;
import com.example.flyme.apis.BaseActivity;
import com.meizu.flyme.reflect.NotificationProxy;


/**
 *@company:MeiZu
 *@author:LuoCanguo
 *@version:2014-11-8 下午2:30:46
 */
/** 
 * @ClassName: NotificationActivity 
 * @Description: TODO
 * @author:luocanguo
 * @date 2014-11-8 下午2:30:46 
 *  
 */
public class NotificationActivity extends BaseActivity {	
	private static final String TAG = NotificationActivity.class.getSimpleName();  
    private static final int NOTIFICATION_ID_1 = 0;
    private static final int NOTIFICATION_ID_2 = 2;
    private static final int NOTIFICATION_ID_3 = 3;
    private static final int NOTIFICATION_ID_4 = 4;    
    private static final int NOTIFICATION_ID_5 = 5;    
    private static final int NOTIFICATION_ID_6 = 6;     
    
    private List<String> mListData = new ArrayList<String>();
    private ListView mListView;       
    private NotificationManager mManager;
    private Bitmap mIcon;
    private int mMsgNum;
    private final String strData[] = {"Normal Notification",
    		"Big Text Style Notification",
    		"Big Picture Style Notification",
    		"Inbox Style Notification",
    		//"Custom Notification",
    		"Normal ProgressBar",
    		"Circular ProgressBar"};
    		
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notification_layout);
        initNotification();
        
        mListView = (ListView)findViewById(R.id.Notification_ListView);        
        mListView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,getData()));               
        mListView.setOnItemClickListener(new  OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                    long arg3) {
                // TODO Auto-generated method stub   
            	
            	if(mListData.get(arg2).equals("Normal Notification")){            	
            		showNormalNotification();
            	}            	
            	if(mListData.get(arg2).equals("Big Text Style Notification")){
            		showBigTextStyleNotification();
            	}
            	if(mListData.get(arg2).equals("Big Picture Style Notification")){
            		showBigPictureStyleNotification();
            	}
            	if(mListData.get(arg2).equals("Inbox Style Notification")){
            		showInboxStyleNotification();
            	}
            	if(mListData.get(arg2).equals("Normal ProgressBar")){
            		showProgressBarNotification();
            	}
            	if(mListData.get(arg2).equals("Circular ProgressBar")){
            		showCircleProgressBarNotification();
            	}
            }            
        });
    }    
    
    
    @Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		clearNotification();
	}


	/** 
	* @Title: initNotification 
	* @Description: 初始化Notificaiton
	* @param: 
	* @return: void
	* @throws:
	*/
	private void initNotification(){
	    //获取大图标资源
        mIcon = BitmapFactory.decodeResource(getResources(),
                R.drawable.ic_launcher);
        // 获取通知服务
        mManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
    }       
    
    /** 
    * @Title: getData 
    * @Description: 获取ListView数据
    * @param: 
    * @return: mListData
    * @throws:
    */
    private List<String> getData(){       
    	for(int i = 0; i < strData.length; i++){
    		mListData.add(strData[i]);
    	}
        return mListData;
    }
    
    /** 
    * @Title: showNormalNotification 
    * @Description: 显示普通的Notification
    * @param: 
    * @return: void
    * @throws:
    */
    private void showNormalNotification(){    
        Notification notification = new NotificationCompat.Builder(NotificationActivity.this)
            .setLargeIcon(mIcon)                                                                                       //大图标
            .setSmallIcon(R.drawable.ic_launcher)                                                         //小图标
            .setTicker("showNormalNotification")                                                           //状态栏标题 
            .setContentTitle("ContentTitle")                                                                      //内容标题
            .setContentInfo("contentInfo")                                                                        //内容
            .setContentText("ContentText")                                                                       //内容附加信息   
            .setNumber(++mMsgNum) 
            .setAutoCancel(true)
            .setDefaults(Notification.DEFAULT_ALL)
            .build();
        mManager.notify(NOTIFICATION_ID_1, notification);
    }
    
    /** 
    * @Title: showBigTextStyleNotification 
    * @Description: 显示Big Text Style的Notification
    * @param: 
    * @return: void
    * @throws:
    */
    private void showBigTextStyleNotification(){
        NotificationCompat.BigTextStyle textStyle = new BigTextStyle();
        textStyle.setBigContentTitle("BigContentTitle")
            .setSummaryText("SummaryText")
            .bigText( "Can you see it? There is Big Text Style!");
        
        Notification notification = new NotificationCompat.Builder(NotificationActivity.this)
            .setLargeIcon(mIcon)                                                                                       //大图标
            .setSmallIcon(R.drawable.ic_launcher)                                                         //小图标
            .setTicker("Show Big Text Style")                                                                    //状态栏标题            
            .setContentTitle("ContentTitle")                                                                     //内容标题
            .setContentInfo("contentInfo")                                                                       //内容
            .setContentText("ContentText")                                                                      //内容附加信息  
            .setNumber(++mMsgNum) 
            .setStyle(textStyle)
            .setAutoCancel(true)
            .setDefaults(Notification.DEFAULT_ALL)
            .build();
        mManager.notify(NOTIFICATION_ID_2, notification);        
    }
    
    /** 
    * @Title: showBigPictureStyleNotification 
    * @Description: 显示Big Picture Style的Notification
    * @param: 
    * @return: void
    * @throws:
    */
    private void showBigPictureStyleNotification(){
        NotificationCompat.BigPictureStyle picStyle = new BigPictureStyle();
        picStyle.setBigContentTitle("BigContentTitle")
                .setSummaryText("SummaryText")
                .bigPicture(mIcon);
        Notification notification = new NotificationCompat.Builder(NotificationActivity.this)
                .setLargeIcon(mIcon)
                .setSmallIcon(R.drawable.ic_launcher)
                .setTicker("Show Big Picture Style")
                .setContentInfo("contentInfo")
                .setContentTitle("ContentTitle")
                .setContentText("ContentText")
                .setNumber(++mMsgNum) 
                .setStyle(picStyle)
                .setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_ALL)
                .build();
        mManager.notify(NOTIFICATION_ID_3, notification);
    }
    
    /** 
    * @Title: showInboxStyleNotification 
    * @Description: 显示Inbox Style的Notification
    * @param: 
    * @return: void
    * @throws:
    */
    private void showInboxStyleNotification(){
        NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle();
        inboxStyle.setBigContentTitle("BigContentTitle")
            .setSummaryText("SummaryText");
                
        for (int i = 0; i < 5; i++){
            inboxStyle.addLine("news:" + i);
        }            
        Notification notification = new NotificationCompat.Builder(NotificationActivity.this)
                .setLargeIcon(mIcon)
                .setSmallIcon(R.drawable.ic_launcher)
                .setTicker("Show Inbox Style Notification")
                .setContentInfo("contentInfo")
                .setContentTitle("ContentTitle")
                .setContentText("ContentText")
                .setNumber(++mMsgNum) 
                .setStyle(inboxStyle)
                .setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_ALL)
                .build();
        mManager.notify(NOTIFICATION_ID_4, notification);
    }
    
    
    /** 
    * @Title: showProgressBarNotification 
    * @Description: 显示普通的ProgressBar Notification
    * @param: 
    * @return: void
    * @throws:
    */
    private void showProgressBarNotification(){
        final NotificationCompat.Builder builder = new NotificationCompat.Builder(
        	NotificationActivity.this);
        builder.setLargeIcon(mIcon)
            .setSmallIcon(R.drawable.ic_launcher)
            .setTicker("showProgressBarNotification")
            .setContentTitle("ContentTitle")
            .setContentInfo("ContentInfo")       
            .setContentText("ContentText")
            .setNumber(++mMsgNum) 
            .setOngoing(true);
       
        //开启一个异步任务
        new AsyncTask<Integer, Integer, String>() {

            @Override
            protected String doInBackground(Integer... params) {
                // TODO Auto-generated method stub
                for(int i=0;i<=100;i++){
                    //builder.setProgress(100, i , false);
                    publishProgress(i);
                    try {
                        Thread.sleep(params[0]);
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
                return "OK";
            }
            
            @Override
            protected void onPreExecute() {
                // TODO Auto-generated method stub
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String result) {
                // TODO Auto-generated method stub
                super.onPostExecute(result);
                
                if(result == "OK"){
                    builder.setContentTitle("Download complete!")
                    .setProgress(0, 0, false)
                    .setOngoing(false);
                    mManager.notify(NOTIFICATION_ID_5 , builder.build());
                }                
            }

            @Override
            protected void onProgressUpdate(Integer... values) {
                // TODO Auto-generated method stub
                super.onProgressUpdate(values);
                builder.setProgress(100,values[0] , false);
                mManager.notify(NOTIFICATION_ID_5 ,builder.build());
            }       
        }.execute(100);
    }
    
    /** 
    * @Title: showCircleProgressBarNotification 
    * @Description: 显示圆环形的ProgressBar Notification
    * @param: 
    * @return: void
    * @throws:
    */
    private void showCircleProgressBarNotification(){        

        final Notification.Builder builder = new Notification.Builder(NotificationActivity.this);
        builder.setLargeIcon(mIcon)
             .setSmallIcon(R.drawable.ic_launcher)
            .setTicker("CirlceProgressBarNotification")
            .setContentTitle("ContentTitle")
            .setContentInfo("ContentInfo")       
            .setContentText("ContentText")   
            .setNumber(++mMsgNum) 
            .setWhen(0)
            .setOngoing(true);    	   
        //设置ProgressBar为圆环形
        NotificationProxy.setProgressBarStype(builder, true);         
        //开启一个异步任务
        new AsyncTask<Integer, Integer, String>() {

            @Override
            protected String doInBackground(Integer... params) {
                // TODO Auto-generated method stub
                for(int i=0;i<=100;i++){
                    //builder.setProgress(100, i , false);
                    publishProgress(i);
                    try {
                        Thread.sleep(params[0]);
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
                return "OK";
            }
            
            @Override
            protected void onPreExecute() {
                // TODO Auto-generated method stub
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String result) {
                // TODO Auto-generated method stub
                super.onPostExecute(result);
                
                if(result == "OK"){
                    builder.setContentTitle("Download complete!")
                    .setProgress(0, 0, false)
                    .setOngoing(false);
                    mManager.notify(NOTIFICATION_ID_6, builder.build());
                }                
            }

            @Override
            protected void onProgressUpdate(Integer... values) {
                // TODO Auto-generated method stub
                super.onProgressUpdate(values);
                builder.setProgress(100,values[0] , false);
                mManager.notify(NOTIFICATION_ID_6,builder.build());
            }       
        }.execute(100);
    }
    
    /** 
    * @Title: clearNotification 
    * @Description: 清除Notification通知栏
    * @param: 
    * @return: void
    * @throws:
    */
    private void clearNotification(){
    	mManager.cancelAll();
    }
}
