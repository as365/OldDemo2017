package com.zfxc.ui;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.zfxc_wbx.R;
import com.zfxc.util.MyDbHelper;
import com.zfxc.util.MyDbInfo;

public class ZFSCDetailActivity extends Activity {
	MyDbHelper myDbHelper;
	String id="";
	
	private EditText txtsname;
	private EditText txtddate;
	private EditText txtsperson;
	private EditText txtscomment;
	private Button btnopen;
	private Button delftrecord;
	private
	String sfilename="";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_zfscdetail);
	init();	
		binddata();
	}

	private void init() {
		// TODO Auto-generated method stub
		
		myDbHelper=MyDbHelper.getInstance(this);
		id = getIntent().getStringExtra("id");
		txtsname=(EditText)findViewById(R.id.txtsname);
		
	
		
		txtddate=(EditText)findViewById(R.id.txtddate);
		txtsperson=(EditText)findViewById(R.id.txtsperson);
		txtscomment=(EditText)findViewById(R.id.txtscomment);
		
		btnopen=(Button)findViewById(R.id.btnopen);		
		
		
		btnopen.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					openfile();
					
					
				}


				
				
			});
		 
		delftrecord=(Button)findViewById(R.id.delftrecord);
		
		delftrecord.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				deleterecord();
				
				
			}

		

			
			
		});
	}

	private void deleterecord() {
		// TODO Auto-generated method stub
		AlertDialog dialog=		new AlertDialog.Builder(ZFSCDetailActivity.this).setTitle("确认删除吗吗？")  
			     
			    .setPositiveButton("确定", new DialogInterface.OnClickListener() {  
			  
			        @Override  
			        public void onClick(DialogInterface dialog, int which) {  
			        // 点击“确认”后的操作  

			    		try{
			    			myDbHelper.open();
			    			
			    		
			    		String []wherevalue={id};
			    		myDbHelper.delete(MyDbInfo.getTableNames()[6], "ID=?", wherevalue);
			    		
			    		 
			    			myDbHelper.close();
			    			
			    			 String path= Environment.getExternalStorageDirectory().getPath()+"/zfxc_wbx/"+sfilename;
			    			 File f=new File(path);
			    			 if(f.exists()){
			    				 f.delete();
			    			 }
			    			
			    			Toast.makeText(ZFSCDetailActivity.this, "删除成功", Toast.LENGTH_LONG)
			    			.show();
			    		}catch(Exception e){
			    			Toast.makeText(ZFSCDetailActivity.this, "删除失败", Toast.LENGTH_LONG)
			    			.show();
			    		}finally{
			    			myDbHelper.close();
			    			
			    		}
			    		
			    		finish();
			          
			        }							

					
			    })  
			    .setNegativeButton("返回", new DialogInterface.OnClickListener() {  
			  
			        @Override  
			        public void onClick(DialogInterface dialog, int which) {  
			        // 点击“返回”后的操作,这里不设置没有任何操作  
			        }  
			    }).create();  
				dialog.show();
		
	}

	private void openfile() {
		// TODO Auto-generated method stub
		
		
		 String path= Environment.getExternalStorageDirectory().getPath()+"/zfxc_wbx/"+sfilename;
		 File f=new File(path);
		 if(f.exists()){
			 Intent intent=getWordFileIntent(path);
			 try{
				 startActivity(intent);
			 }catch(Exception e){
				 
			 }
		 }else{
			 Toast.makeText(this, "正在下载请稍后。。。", Toast.LENGTH_LONG).show();
			 
		 }
	}
	 public static Intent getWordFileIntent( String param )

	  {

	    Intent intent = new Intent("android.intent.action.VIEW");

	    intent.addCategory("android.intent.category.DEFAULT");

	    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

	    Uri uri = Uri.fromFile(new File(param ));

	    intent.setDataAndType(uri, "application/msword");

	    return intent;

	  }
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.zfscdetail, menu);
		return true;
	}

	
	private void binddata() {
		// TODO Auto-generated method stub
		Cursor c =null;
		try {
			myDbHelper.open();
			// Cursor c = myDbHelper.select(MyDbInfo.getTableNames()[0],
			// MyDbInfo.getFieldNames()[0], null, null, null, null,
			// "TIME desc, id desc","0,9");
			String sql = "select * from " + MyDbInfo.getTableNames()[6]
					+ " where ID=" + id;
		 c=myDbHelper.select(sql);
			String filename = "";
			c.moveToFirst();
			String result = "";

			// Toast.makeText(this, "sdsdsd"+id, Toast.LENGTH_SHORT).show();
			
			
			txtsname.setText(c.getString(1));
			
			txtsperson.setText(c.getString(4));
			txtscomment.setText(c.getString(3));
			sfilename=c.getString(3);
			SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd");
		
			try{
				
			
				txtddate.setText(	sf.format(Date.parse(c.getString(2))));
			}catch(Exception e){
				txtddate.setText(c.getString(2));
			}
			c.close();
			myDbHelper.close();

		} catch (Exception e) {

		}finally{
			
			c.close();
			myDbHelper.close();
		}
	}
}
