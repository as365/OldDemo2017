package com.zfxc.enviroment;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.zfxc_wbx.R;
import com.zfxc.util.DataTable;
import com.zfxc.util.MyDbHelper;
import com.zfxc.util.MyDbInfo;
import com.zfxc.util.WebServiceJC;

@SuppressLint("HandlerLeak")
public class EnvironmentActivity extends Activity {
	private Button btnupload;
	MyDbHelper myDbHelper;
	int weberror = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_environment);
		inital();
	}

	public void inital() {

		myDbHelper = MyDbHelper.getInstance(EnvironmentActivity.this);
		btnupload = (Button) findViewById(R.id.btnupload);

		btnupload.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Toast.makeText(EnvironmentActivity.this, "正在获取请稍后！", Toast.LENGTH_SHORT)
				.show();
				Thread t=new Thread(rdownload);
				t.start();
				
			}

		});

		// 设置表格标题的背景颜色
		ViewGroup tableTitle = (ViewGroup) findViewById(R.id.table_title);
		tableTitle.setBackgroundColor(Color.rgb(255, 100, 10));
		Bind();
	}
Runnable rdownload=new Runnable() {
		
		@Override
		public void run() {
			// TODO Auto-generated method stub
			try{ 
				Looper.prepare();
			checkEnvironmentinfo();

		
				
			handler.sendEmptyMessage(0);
				
			}catch(Exception e){
				
			//	Log.i("sss",e.getMessage());
				handler.sendEmptyMessage(1);
			}
			Looper.loop();
		}
	};
	Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			
			 if(msg.what == 0) {
				 
				 Toast.makeText(EnvironmentActivity.this, "数据下载成功", Toast.LENGTH_SHORT)
					.show();
					Bind();
			 }else if(msg.what == 1){
				 
				 Toast.makeText(EnvironmentActivity.this, "数据下载失败，请检查网络", Toast.LENGTH_SHORT)
					.show();
			 }
			 
		}
	};

	public void Bind() {
		try {
			List<Environment> list = new ArrayList<Environment>();
			myDbHelper.open();
			String sql = "select * from " + MyDbInfo.getTableNames()[7];
			Cursor c = myDbHelper.select(sql);
			int irows = 0;
			while (c.moveToNext()) {
				String ip="";
				try{
					ip=c.getString(2).substring(c.getString(2).lastIndexOf(".")+1);
				}catch(Exception e0){
					
				}
				list.add(new Environment(ip,c.getString(1) , c
						.getString(3), c.getString(4), c.getString(5), c
						.getString(6), c.getString(7)));
			}
			ListView tableListView = (ListView) findViewById(R.id.listenvironment);
			EnvironmentAdapter adapter = new EnvironmentAdapter(this, list);
			tableListView.setAdapter(adapter);

			c.close();
			myDbHelper.close();

		} catch (Exception e) {
			myDbHelper.close();
		}
	}

	public boolean checkEnvironmentinfo() {
		try {

			HashMap<String, String> hm = new HashMap<String, String>();
			hm.put("username", "admin");
			hm.put("password", "gzncp");
			hm.put("sql",
					"SELECT * FROM tbenviorment WHERE (id IN (SELECT MAX(id) AS max_id FROM tbenviorment GROUP BY ip)) ORDER BY ddate DESC");

			// WebServiceJC.setUrl("inner", myDbHelper);
			//WebServiceJC.SERVICE_URL = "";
			String result = WebServiceJC.WorkJC("downloadinfo", hm);
			WebServiceJC.setUrl("inner", myDbHelper);
			if (!result.equals("<dataset></dataset>")) {
				DataTable dt = WebServiceJC.getChannelListzfxc(result);

				myDbHelper.open();
				String sqldel = "delete from " + MyDbInfo.getTableNames()[7];
				try {
					myDbHelper.execSQL(sqldel);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				myDbHelper.close();

				for (int i = 0; i < dt.getRow().size(); i++) {
					String ddate = dt.getRow().get(i).getColumn("ddate")
							.toString();
					String ip = dt.getRow().get(i).getColumn("ip").toString();
					String kqwd = dt.getRow().get(i).getColumn("kqwd")
							.toString();
					String kqsd = dt.getRow().get(i).getColumn("kqsd")
							.toString();
					String gz = dt.getRow().get(i).getColumn("gz").toString();
					String trwd = dt.getRow().get(i).getColumn("trwd")
							.toString();
					String trsd = dt.getRow().get(i).getColumn("trsd")
							.toString();
					insertEnvironmentinfo(ddate, ip, kqwd, kqsd, gz, trwd, trsd);
				}
				return true;

			}
		} catch (Exception e) {
			// TODO: handle exception
			weberror = 1;
		}

		return false;
	}

	public void insertEnvironmentinfo(String ddate, String ip, String kqwd,
			String kqsd, String gz, String trwd, String trsd) {
		try {

			myDbHelper.open();

			String fields[] = { "ddate", "ip", "kqwd", "kqsd", "gz", "trwd",
					"trsd" };
			String values[] = { "", "", "", "", "", "", "" };
			values[0] = ddate;
			values[1] = ip;
			values[2] = kqwd;
			values[3] = kqsd;
			values[4] = gz;
			values[5] = trwd;
			values[6] = trsd;

			myDbHelper.insert(MyDbInfo.getTableNames()[7], fields, values);
			myDbHelper.close();
			
		} catch (Exception e) {
			Toast.makeText(EnvironmentActivity.this, "保存失败", Toast.LENGTH_LONG)
					.show();
		} finally {
			myDbHelper.close();
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		
		return true;
	}

}
