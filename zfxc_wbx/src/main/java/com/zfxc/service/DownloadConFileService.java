package com.zfxc.service;


import java.io.File;

import android.app.Service;
import android.content.Intent;
import android.database.Cursor;
import android.os.Environment;
import android.os.IBinder;
import android.widget.Toast;

import com.zfxc.entity.Mp3Info;
import com.zfxc.util.AppConstant;
import com.zfxc.util.HttpDownloader;
import com.zfxc.util.MyDbHelper;
import com.zfxc.util.MyDbInfo;

public class DownloadConFileService extends Service{
	MyDbHelper myDbHelper;
	String missId;
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	//ÿ���û����ListActivity���е�һ����Ŀʱ���ͻ���ø÷���
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		myDbHelper = MyDbHelper.getInstance(this);	
		 missId=intent.getStringExtra("missId");
		DownloadThread downloadThread = new DownloadThread(null);
		
		Thread thread = new Thread(downloadThread);
		thread.start();
		return super.onStartCommand(intent, flags, startId);
	}
	
	class DownloadThread implements Runnable{
		private Mp3Info mp3Info = null;
		public DownloadThread(Mp3Info mp3Info){
			this.mp3Info = mp3Info;
		}
		@Override
		public void run() {
			try{
				
				Cursor c=null;
				
				try {
					
					myDbHelper.open();
//					Cursor c = myDbHelper.select(MyDbInfo.getTableNames()[0],
//							MyDbInfo.getFieldNames()[0], null, null, null, null, "TIME desc, id desc","0,9");
					String sql="select * from "+MyDbInfo.getTableNames()[4]+" where serverid= "+missId;
					c = myDbHelper.select(sql);				
				
					
					while (c.moveToNext()) {
				
						String mp3Url = "";
						
						mp3Url=AppConstant.URL.BASE_URL+c.getString(13);
						 String path= Environment.getExternalStorageDirectory().getPath()+"/zfxc_wbx/"+c.getString(13);
						 File f=new File(path);
						 if(!f.exists()){
						HttpDownloader httpDownloader = new HttpDownloader();
					//	Toast.makeText(DownloadService.this, mp3Url, Toast.LENGTH_LONG).show();
						int mp3Result = httpDownloader.downFile(mp3Url, "zfxc_wbx/", c.getString(13));
						 }
						
//						mp3Url=AppConstant.URL.BASE_URL+"zfsc20151027022147.doc";
//						HttpDownloader httpDownloader = new HttpDownloader();
//					
//						int mp3Result = httpDownloader.downFile(mp3Url, "zfxc/", "zfsc20151027022147.doc");
					}
					
					c.close();
					myDbHelper.close();

				} catch (Exception e) {
					myDbHelper.close();
				}finally{
					if(c!=null){
						c.close();
					}
					
					myDbHelper.close();
					
				}
				
			//String mp3Url = AppConstant.URL.BASE_URL + mp3Info.getMp3Name();
			
			
			
			
	}catch(Exception e){
				
			}

		}
		
	}

}
