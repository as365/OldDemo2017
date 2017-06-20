package com.zfxc.ui;

import java.io.IOException;
import java.util.HashMap;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zfxc_wbx.R;
import com.zfxc.util.DataTable;
import com.zfxc.util.MyDbHelper;
import com.zfxc.util.MyDbInfo;
import com.zfxc.util.WebServiceJC;
import com.zfxc.wbx.MainActivity;

public class LoginActivity extends Activity {
	private TextView txtusername;
	private TextView txtpassword;
	MyDbHelper myDbHelper;

	private Handler handler;
	private ProgressBar xh_ProgressBar;
	private ProgressBar xh_ProgressBar2;
	private CheckBox chksaveps;

	private Button xh_Button;
	protected static final int GUI_STOP_NOTIFIER = 0x108;
	protected static final int GUI_THREADING_NOTIFIER = 0x109;
	public int intCounter = 0;

	int weberror = 0;
	private Button btnLogin;
	 String allQx;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_login);
		init();
	}

	private void init() {
		// TODO Auto-generated method stub

		weberror = 0;
		// 初始化控件
		btnLogin = (Button) findViewById(R.id.btnlogin);
		txtusername = (TextView) findViewById(R.id.txtusername);
		txtpassword = (TextView) findViewById(R.id.txtpassword);
		myDbHelper = MyDbHelper.getInstance(this);
		// 如果用户表里有数据则直接跳转至
		checkuser();
		// 添加监听器
		btnLogin.setOnClickListener(new OnClickListener() {
			Runnable runnable = new Runnable() {

				public void run() {

					boolean flag = checkUser();
					try {
						Thread.sleep(2000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					if (flag) {
						handler.sendEmptyMessage(1);// 曾想注掉这句话，直接调用setContentView(R.layout.main)，但报异常
					} else {

						handler.sendEmptyMessage(0);// 曾想注掉这句话，直接调用setContentView(R.layout.main)，但报异常
					}
				}
			};

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				// 需要用户验证和用户类型获取
				if (!checknetwork()) {
					dispToast("请确保网络通畅！");
					return;
				}

				xh_ProgressBar = (ProgressBar) findViewById(R.id.ProgressBar02);
				xh_ProgressBar.setVisibility(View.VISIBLE);

				TextView tView = (TextView) findViewById(R.id.textView2);
				tView.setVisibility(View.VISIBLE);
				Thread thread = new Thread(runnable);
				thread.start();
			}
		});

		handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				if (msg.what == 1) // handler接收到相关的消息后
				{

					Intent intent = new Intent(LoginActivity.this,
							MainActivity.class);
					startActivity(intent);
					finish();

				} else {
					if (weberror == 1) {
						dispToast("无法连接至服务器，请检查网络设置!");
					} else {
						dispToast("用户名密码错误,请重新输入!");
					}

					// 需要用户验证和用户类型获取
					xh_ProgressBar = (ProgressBar) findViewById(R.id.ProgressBar02);
					xh_ProgressBar.setVisibility(View.INVISIBLE);

					TextView tView = (TextView) findViewById(R.id.textView2);
					tView.setVisibility(View.INVISIBLE);
				}

			}
		};

		try {
			bindUser();
		} catch (Exception e) {
			// TODO: handle exception
		}

	}

	public boolean checknetwork() {

		ConnectivityManager cManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo info = cManager.getActiveNetworkInfo();

		if (info != null && info.isAvailable()) {
			// do something
			// 能联网
			return true;

		} else {
			// do something
			// 不能联网

			return false;
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.

		return true;
	}

	public void checkuser() {
		try {

			boolean flag = false;
			// Cursor c= myDbHelper
			// .Query(MyDbInfo.getTableNames()[2], new String[]
			// {MyDbInfo.getFieldNames()[4][0],MyDbInfo.getFieldNames()[4][1]},
			// null, null, null, null, null, null);
			String sql = "select * from " + MyDbInfo.getTableNames()[2];
			myDbHelper.open();
			Cursor c = myDbHelper.select(sql);

			c.moveToFirst();
			CharSequence[] list = new CharSequence[c.getCount()];

			if (list.length > 0 && c.getString(1) != null) {
				flag = true;
				c.getString(1);
				txtusername.setText(c.getString(1));
				txtpassword.setText(c.getString(2));

				WebServiceJC.susername = c.getString(1);
				;
				WebServiceJC.spassword = c.getString(2);
				;
				MyDbInfo.account = c.getString(1);
				MyDbInfo.password = c.getString(2);
				MyDbInfo.qx=c.getString(6);
			}

			c.close();
			myDbHelper.close();
			if (flag) {
				Intent intent = new Intent(LoginActivity.this,
						MainActivity.class);
				startActivity(intent);
				finish();
			}
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.toString());
			try {
				myDbHelper.close();
			} catch (Exception es) {

			}

		}
	}

	// 验证用户
	public boolean checkUser() {

		try {
			if (txtusername.getText().toString().equals("")
					|| txtusername.getText().toString() == null) {
//				Toast.makeText(LoginActivity.this, "账号不能为空！",
//						Toast.LENGTH_SHORT);
				weberror=2;
			} else if (txtpassword.getText().toString().equals("")
					|| txtpassword.getText().toString() == null) {
//				Toast.makeText(LoginActivity.this, "账号不能为空！",
//						Toast.LENGTH_SHORT);
				weberror=2;
			} else {
				HashMap<String, String> hm = new HashMap<String, String>();
				hm.put("zpmobilephone", txtusername.getText().toString());
				hm.put("zpenforcementnumber", txtpassword.getText().toString());
				Log.e("aaaaa", "=======" + txtusername.getText().toString()
						+ "====" + txtpassword.getText().toString());
				WebServiceJC.setUrl("inner", myDbHelper);
				String result = WebServiceJC.WorkJC("getIfyx", hm);
				String[] qx=result.split("\\|");
				if (qx[0].equals("1")) {
					allQx=qx[1];
					MyDbInfo.qx=qx[1];
					MyDbInfo.account = txtusername.getText().toString();
					MyDbInfo.password = txtpassword.getText().toString();
					insertUser();
					setVideoUrl();
					return true;

				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		return false;
	}

	private void setVideoUrl() {
		// TODO Auto-generated method stub
		boolean canconnect = checknetwork();
		if (canconnect) {
			// Toast.makeText(LoginActivity.this, "正在更新视频路径数据，请稍后。。。",
			// Toast.LENGTH_LONG).show();
			getvideocfgfromweb();
		} else {

			// Toast.makeText(LoginActivity.this, "请先联网！",
			// Toast.LENGTH_LONG).show();
		}

	}

	public void getvideocfgfromweb() {

		String result = "<dataset><table><sbasename>华明宅基地</sbasename><spengshiname>132号棚</spengshiname><sname>132号棚摄像头1</sname><svideourl>rtsp://admin:12345@www.tjhmqny.f3322.org:10401/h264/ch1/sub/av_stream</svideourl></table><table><sbasename>华明宅基地</sbasename><spengshiname>126号棚</spengshiname><sname>126号棚摄像头1</sname><svideourl>rtsp://admin:12345@www.tjhmqny.f3322.org:10301/h264/ch1/sub/av_stream </svideourl></table></dataset>";

		try {

			HashMap<String, String> hm = new HashMap<String, String>();
			hm.put("username", WebServiceJC.susername.toString());
			hm.put("password", WebServiceJC.spassword.toString());

			WebServiceJC.setUrl("inner", myDbHelper);
			result = WebServiceJC.WorkJC("getXMLVideoInfo", hm);
			if (!result.equals("")) {
				parseXml(result);

				// Toast.makeText(VideoCFGActivity.this, result,
				// Toast.LENGTH_LONG).show();

			}
		} catch (Exception e) {
			// TODO: handle exception

		}

	}

	public void parseXml(String xmlString) {
		try {

			DataTable dt = WebServiceJC.getChannelList(xmlString);

			deletedata();

			for (int i = 0; i < dt.getRow().size(); i++) {

				try {

					myDbHelper.open();
					String table = "TB_videocfg";
					String fields[] = { "TITLE", "URL", "TEMP1", "TEMP2" };
					String values[] = { "SS", "2015-09-12", "", "" };
					values[0] = dt.getRow().get(i).getColumn("sname")
							.toString();
					values[1] = dt.getRow().get(i).getColumn("svideourl")
							.toString();
					values[2] = dt.getRow().get(i).getColumn("sbasename")
							.toString();
					values[3] = dt.getRow().get(i).getColumn("spengshiname")
							.toString();

					myDbHelper.insert(table, fields, values);

					myDbHelper.close();

				} catch (Exception e) {

				} finally {
					try {
						myDbHelper.close();
					} catch (Exception es) {

					}

				}

			}

		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void deletedata() {
		// TODO Auto-generated method stub

		try {
			myDbHelper.open();

			myDbHelper.delete(MyDbInfo.getTableNames()[1], null, null);

			myDbHelper.close();

		} catch (Exception e) {

		} finally {
			myDbHelper.close();

		}
	}

	public void dispToast(String str) {
		Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
	}

	private void bindUser() {
		try {

			String sql = "select * from " + MyDbInfo.getTableNames()[2];
			myDbHelper.open();
			Cursor c = myDbHelper.select(sql);
			c.moveToFirst();
			CharSequence[] list = new CharSequence[c.getCount()];

			if (list.length > 0) {

				c.getString(1);
				txtusername.setText(c.getString(1));
				txtpassword.setText(c.getString(2));

				WebServiceJC.susername = c.getString(1);
				;
				WebServiceJC.spassword = c.getString(2);
				;

			}

			c.close();
			myDbHelper.close();
			// genNotification();
		} catch (Exception e) {
			try {
				myDbHelper.close();
			} catch (Exception es) {

			}
		}

	}

	public void insertUser() {

		myDbHelper.open();

		String fields[] = { "username", "password", "stype", "time1", "time2","uqx" };
		String values[] = { "", "", "", "", "",""};
		values[0] = txtusername.getText().toString();
		values[1] = txtpassword.getText().toString();
		values[5] =allQx;
		myDbHelper.insert(MyDbInfo.getTableNames()[2], fields, values);
		myDbHelper.close();

	}

}
