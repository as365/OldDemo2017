package com.zfxc.ui;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zfxc_wbx.R;
import com.zfxc.entity.ZFgePerson;
import com.zfxc.fragment.ActivityFragment;
import com.zfxc.util.DatePickerFragment;
import com.zfxc.util.MyDbHelper;
import com.zfxc.util.MyDbInfo;
import com.zfxc.util.WebServiceJC;

public class ZfxceditActivity extends Activity {
	private String id;
	private TextView etdescrib;
	MyDbHelper myDbHelper;
	private Button buttonSetDate;
	private Button btndeleteftrecord;
	private Button btnsave;
	private Button btPerform;
	private EditText etddate;
	private EditText etsresult;
	private EditText etscomment;
	private EditText etsperson;
	private RadioGroup radioGroupsstatus;
	private RadioButton radiounfinish;
	private RadioButton radiofinish;
	private RadioButton radioother;
	String sstatusrb;
	private ActivityFragment af;
	private ZFgePerson zfPS;
	private static final int  TIME_DESC=0;
	private String enforcementtype=MyDbInfo.enforType;
	private String uname=MyDbInfo.account;
	private String missName;
	private String missPerson;
	private String missType;
	private  Message message;

	 private Handler handler=new Handler(){
	        @Override
	        public void handleMessage(Message msg) {
	            super.handleMessage(msg);
	        switch (message.what){
	            case TIME_DESC:
	            	 handler.sendEmptyMessage(TIME_DESC);
                     try {
                         Thread.sleep(1000);
                     } catch (InterruptedException e) {
                         e.printStackTrace();
                     }
	                break;
	        }
	 
	 
	        }
	    } ;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_zfxcedit);
		af=new ActivityFragment();
		handler=new Handler();
		init();
		binddata();
		Log.e("aaaa","================="+enforcementtype);
	}

	private void savedata() {
		// TODO Auto-generated method stub

		try {
			myDbHelper.open();
			// Cursor c = myDbHelper.select(MyDbInfo.getTableNames()[0],
			// MyDbInfo.getFieldNames()[0], null, null, null, null,
			// "TIME desc, id desc","0,9");

			String scomment = etscomment.getText().toString();
			String sresult = etsresult.getText().toString();  
			String sperson = etsperson.getText().toString();
			String sstatus = sstatusrb;
			String dexecutedate = etddate.getText().toString();
			sstatus = ((RadioButton) ZfxceditActivity.this
					.findViewById(radioGroupsstatus.getCheckedRadioButtonId()))
					.getText().toString();
			Log.e("aaaaaaaaa","=========sstatus============"+sstatus);
			String updatefields[] = { "sstatus", "scomment", "sresult",
					"sperson", "dexecutedate"};
			String[] updatevalues = { sstatus, scomment, sresult, sperson,
					dexecutedate};
			String updatefields2[] = { "Status"};
			String[] updatevalues2 = { sstatus};
			// String updatefields[] = {"sname"};
			// String []updatevalues={sstatus};
			String[] wherevalue = { af.MissionId };
			myDbHelper.update(MyDbInfo.getTableNames()[4], updatefields,
					updatevalues, "serverid=?", wherevalue);
			myDbHelper.update(MyDbInfo.getTableNames()[8],updatefields2,
					updatevalues2, "MissionId=?", wherevalue );
			Log.e("aaaa","=================="+sstatus);
			Updata(af.MissionId,uname,sstatus);
			if(sstatus.equals("已完成")){
				myDbHelper.delete(
						MyDbInfo.getTableNames()[9],
						"missionId=?", wherevalue);
				myDbHelper.delete(
						MyDbInfo.getTableNames()[10],
						"missionId=?", wherevalue);
				myDbHelper.delete(
						MyDbInfo.getTableNames()[11],
						"missionId=?", wherevalue);
			}
			myDbHelper.close();
			Toast.makeText(ZfxceditActivity.this, "确认成功", Toast.LENGTH_LONG)
					.show();
			
		} catch (Exception e) {
			Toast.makeText(ZfxceditActivity.this, "确认失败", Toast.LENGTH_LONG)
					.show();
		} finally {

			myDbHelper.close();
		}
		finish();
		
	}

	private void binddata() {
		// TODO Auto-generated method stub
		Cursor c = null;
		Cursor c1 = null;
		try {
			myDbHelper.open();
			// Cursor c = myDbHelper.select(MyDbInfo.getTableNames()[0],
			// MyDbInfo.getFieldNames()[0], null, null, null, null,
			// "TIME desc, id desc","0,9");
			String sql = "select * from " + MyDbInfo.getTableNames()[4]
					+ " where ID=" + id;
			c = myDbHelper.select(sql);
			String filename = "";
			c.moveToFirst();
			String result = "";
			String sql1 = "select * from " + MyDbInfo.getTableNames()[8]
					+ " where MissionId=" + af.MissionId;
			c1 = myDbHelper.select(sql1);
			c1.moveToFirst();
			// Toast.makeText(this, "sdsdsd"+id, Toast.LENGTH_SHORT).show();
			if (c.getString(9) == null || c.getString(9).equals("")) {
				Date date = new Date();
				SimpleDateFormat dataFomate = new SimpleDateFormat("yyyy-MM-dd");
				etddate.setText(dataFomate.format(date));
			} else {
				etddate.setText(c.getString(9));
			}
			if(c1.getString(4).equals("未完成")){
				
			Updata(af.MissionId,uname,"正在执行");
			}
			if (c1.getString(4) == null || c1.getString(4).equals("")
					|| c1.getString(4).equals("null")
					|| c1.getString(4).equals("未完成")) {
//				radiounfinish.setChecked(true);
				radiounfinish.setVisibility(radiounfinish.INVISIBLE);
				radiofinish.setChecked(false);
				radioother.setChecked(true);
				
				btPerform.setVisibility(btPerform.VISIBLE);
				btnsave.setVisibility(btPerform.VISIBLE);
			} else if (c1.getString(4).equals("已完成")) {
				                                      
				radiounfinish.setChecked(false);
				radiofinish.setChecked(true);
				radioother.setChecked(false);
				btPerform.setVisibility(btPerform.INVISIBLE);
				btnsave.setVisibility(btPerform.INVISIBLE);
			} else if (c1.getString(4).equals("正在执行")) {
//				radiounfinish.setChecked(false);
				radiounfinish.setVisibility(radiounfinish.INVISIBLE);
				radiofinish.setChecked(false);
				radioother.setChecked(true);
				btPerform.setVisibility(btPerform.VISIBLE);
				btnsave.setVisibility(btPerform.VISIBLE);
			} else {

				radiofinish.setChecked(true);
				radiounfinish.setChecked(false);
			}

			etsresult.setText(c.getString(6));
			etscomment.setText(c.getString(5));
			etsperson.setText(c.getString(7));
			missName=c.getString(1);
			missPerson=c.getString(7);
			missType=c.getString(12);
			etdescrib.setText("任务名称："
					+ c.getString(1)
					+ "\r\n任务内容："
					+ c.getString(2)
					+ "\r\n发布日期："
					+ c.getString(3)
					+ "\r\n任务状态："
					+ c1.getString(4)
					+ "\r\n备注："
					+ c.getString(5)
					+ "\r\n结果："
					+ c.getString(6)
					+ "\r\n执行人："
					+ c.getString(7)
					+ "\r\n人员类型："
					+ c.getString(12)
					+ "\r\n执行日期："
					+ (c.getString(9) == null ? "" : c.getString(9)));
			c.close();
			c1.close();
			myDbHelper.close();

		} catch (Exception e) {

		} finally {

			c.close();
			myDbHelper.close();
		}
	}

	@SuppressLint("NewApi")
	private void init() {
		// TODO Auto-generated method stub
		myDbHelper = MyDbHelper.getInstance(this);
		id = getIntent().getStringExtra("id");

		etdescrib = (TextView) findViewById(R.id.etdescrib);
		etdescrib.setTextColor(Color.GRAY);
		etddate = (EditText) findViewById(R.id.etddate);
		etscomment = (EditText) findViewById(R.id.etscomment);
		etsresult = (EditText) findViewById(R.id.etsresult);
		etsperson = (EditText) findViewById(R.id.etsperson);
		etddate = (EditText) findViewById(R.id.etddate);
		radiounfinish = (RadioButton) findViewById(R.id.radiounfinish);
		radiofinish = (RadioButton) findViewById(R.id.radiofinish);
		radioother = (RadioButton) findViewById(R.id.radioorther);
		btPerform = (Button) findViewById(R.id.zfxcedit_perform);
		btPerform.setOnClickListener(Lisclick);
		radioGroupsstatus = (RadioGroup) findViewById(R.id.rgstatus);

		radioGroupsstatus
				.setOnCheckedChangeListener(new OnCheckedChangeListener() {

					@Override
					public void onCheckedChanged(RadioGroup arg0, int arg1) {
						// TODO Auto-generated method stub
						// 获取变更后的选中项的ID
						int radioButtonId = arg0.getCheckedRadioButtonId();
						// 根据ID获取RadioButton的实例
						RadioButton rb = (RadioButton) ZfxceditActivity.this
								.findViewById(radioButtonId);
						sstatusrb = rb.getText().toString();
						Log.e("aaaaaaaaaaaaaaa","=================="+sstatusrb);
						// 更新文本内容，以符合选中项

					}
				});

		btnsave = (Button) findViewById(R.id.save);
		btnsave.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				int result=0;
				message=new Message();
				if(radiofinish.isChecked()){
					if(MyDbInfo.PuResult==null){
						Toast.makeText(ZfxceditActivity.this, "数据没有上传！！", Toast.LENGTH_SHORT).show();
						radioother.setChecked(true);
						return;
					}else{
						for(int i=0;i<MyDbInfo.PuResult.size();i++){
							if(MyDbInfo.PuResult.get(i).equals(af.MissionId)){
								result=1;
							}else{
								result=0;
							}
						}
						if(result==1){
							savedata();
						}else
						{
							Toast.makeText(ZfxceditActivity.this, "数据没有上传！！", Toast.LENGTH_SHORT).show();
							radioother.setChecked(true);
							result=0;
							return;
						}
					}
					
				}else{
					finish();
				}
				ActivityFragment.STATE=1;
			}				
			

		});
		 
		/***
		 * 删除任务 部分代码
		 */
		// // btndeleteftrecord = (Button) findViewById(R.id.delftrecord);
		// if (id == null || id.equals("")){
		// btndeleteftrecord.setVisibility(View.INVISIBLE);
		// }else{
		// btndeleteftrecord.setVisibility(View.VISIBLE);
		// }
		// btndeleteftrecord.setOnClickListener(new View.OnClickListener() {
		// @Override
		// public void onClick(View v) {
		//
		//
		//
		//
		// AlertDialog dialog= new
		// AlertDialog.Builder(ZfxceditActivity.this).setTitle("确认删除吗吗？")
		//
		// .setPositiveButton("确定", new DialogInterface.OnClickListener() {
		//
		// @Override
		// public void onClick(DialogInterface dialog, int which) {
		// // 点击“确认”后的操作
		// deletedata();
		//
		// }
		//
		//
		// })
		// .setNegativeButton("返回", new DialogInterface.OnClickListener() {
		//
		// @Override
		// public void onClick(DialogInterface dialog, int which) {
		// // 点击“返回”后的操作,这里不设置没有任何操作
		// }
		// }).create();
		// dialog.show();
		//
		//
		// }
		//
		//
		// });

		buttonSetDate = (Button) findViewById(R.id.ButtonSetDate);
		buttonSetDate.setOnClickListener(new View.OnClickListener() {
			@SuppressLint("NewApi")
			@Override
			public void onClick(View v) {
				DatePickerFragment datePicker = new DatePickerFragment();
				datePicker.setEtdate(etddate);
				datePicker.setDateinitial(etddate.getText().toString());
				datePicker.show(getFragmentManager(), "etdate");

			}
		});


   }
	private OnClickListener Lisclick=new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			Log.e("aaaa","================="+enforcementtype);
			
			if(missType.equals("农业执法")){
				myDbHelper.open();
				Cursor c=null;
				String sql="select Id from "+MyDbInfo.getTableNames()[9]+" where missionId="+af.MissionId;
				c=myDbHelper.select(sql);
				c.moveToFirst();
				
				if(c.getCount()==0){
					NYXCZFEditActivity.startActivity(ZfxceditActivity.this,af.MissionId,"",missName,missPerson);
				}else{
					int nyid=c.getColumnIndex("Id");
					String n=c.getString(nyid);
					NYXCZFEditActivity.startActivity(ZfxceditActivity.this,af.MissionId,n,missName,missPerson);
				}
				c.close();
				myDbHelper.close();
			}else if(missType.equals("检查员")){
				myDbHelper.open();
				Cursor c=null;
				String sql="select Id from "+MyDbInfo.getTableNames()[10]+" where missionId="+af.MissionId;
				c=myDbHelper.select(sql);
				c.moveToFirst();
				
				if(c.getCount()==0){
					JCXCZFEditActivity.startActivity(ZfxceditActivity.this,af.MissionId,"",missName,missPerson);
				}else{
					int nyid=c.getColumnIndex("Id");
					String n=c.getString(nyid);
					JCXCZFEditActivity.startActivity(ZfxceditActivity.this,af.MissionId,n,missName,missPerson);
				}
				c.close();
				myDbHelper.close();
			}else if(missType.equals("地理标志")){
				myDbHelper.open();
				Cursor c=null;
				String sql="select Id from "+MyDbInfo.getTableNames()[11]+" where missionId="+af.MissionId;
				c=myDbHelper.select(sql);
				c.moveToFirst();
				
				if(c.getCount()==0){
					DBXCZFEditActivity.startActivity(ZfxceditActivity.this,af.MissionId,"",missName,missPerson);
				}else{
					int nyid=c.getColumnIndex("Id");
					String n=c.getString(nyid);
					DBXCZFEditActivity.startActivity(ZfxceditActivity.this,af.MissionId,n,missName,missPerson);
				}
				c.close();
				myDbHelper.close();
			}
		}
	};
	private void deletedata() {
		// TODO Auto-generated method stub

		try {
			myDbHelper.open();

			String[] wherevalue = { id };
			myDbHelper.delete(MyDbInfo.getTableNames()[4], "ID=?", wherevalue);

			myDbHelper.close();
			Toast.makeText(ZfxceditActivity.this, "删除成功", Toast.LENGTH_LONG)
					.show();
		} catch (Exception e) {
			Toast.makeText(ZfxceditActivity.this, "删除失败", Toast.LENGTH_LONG)
					.show();
		} finally {
			myDbHelper.close();

		}

		finish();

	}
	private void Updata(String missId,String uname,String status){
		HashMap<String, String> hm = new HashMap<String, String>();
		hm.put("MissionId", missId);
		hm.put("Mobilephone", uname);
		hm.put("Status", status);
		WebServiceJC.WorkJC("changeStatus", hm);// 更新任务状态
	}
	@Override
	protected void onPause() {
		super.onPause();
		Log.e("aaaa","===============onPause=============");
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.zfxcedit, menu);
		return true;
	}

}
