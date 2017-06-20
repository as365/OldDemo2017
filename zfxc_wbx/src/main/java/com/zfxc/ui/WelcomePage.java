package com.zfxc.ui;




import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Window;
import android.view.WindowManager;

import com.example.zfxc_wbx.R;
import com.zfxc.util.MyDbHelper;

public class WelcomePage extends Activity {
	 private Handler handler; 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		 requestWindowFeature(Window.FEATURE_NO_TITLE);
			getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
	                WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_welcome_page);
		MyDbHelper.getInstance(WelcomePage.this);
         // 初始化handler  
         handler = new Handler()  
         {  
                  @Override  
                  public void handleMessage(Message msg)  
                  {  
                      if(msg.what == 1) // handler接收到相关的消息后  
                           {
                    	//Intent intent=new Intent(WelcomePage.this,MainActivity.class);
                    	  Intent intent=new Intent(WelcomePage.this, LoginActivity.class);
                    	intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    	startActivity(intent);
                 finish();
                       //   setContentView(R.layout.activity_main);// 显示真正的应用界面  
                        
                           }  
                  }  
         };  
         // 新建一个线程，过5秒钟后向handler发送一个消息  
            Runnable runnable = new Runnable()  
         {  
                  public void run()  
                  {  
                           try  
                           {  
                                     Thread.sleep(2500);  
                           }   
                           catch (InterruptedException e)  
                           {  
                                     e.printStackTrace();  
                           }  
                           handler.sendEmptyMessage(1);//曾想注掉这句话，直接调用setContentView(R.layout.main)，但报异常  
                  }  
         };  
         Thread thread = new Thread(runnable);  
         thread.start();    

	}



}
