package com.zfxc.ui;





import android.annotation.SuppressLint;
import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.zfxc_wbx.R;
import com.zfxc.util.MyDbHelper;
import com.zfxc.util.MyDbInfo;

public class WebConfigActivity extends Activity {



	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		//getMenuInflater().inflate(R.menu.web_config, menu);
		return true;
	}

	
	private  MyDbHelper myDbHelper;
	
	private Button btnupdatews;//buttondownloadinfo
	private EditText etOutterWs;

	
	
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_web_config);
		// Show the Up button in the action bar.
		//getActionBar().setDisplayHomeAsUpEnabled(true);
		
		
		
		btnupdatews=(Button)findViewById(R.id.btnupdatewescfg);
		btnupdatews.setOnClickListener(new UpdateWSListenr());
		
		etOutterWs=(EditText)findViewById(R.id.etouterws);
		
		myDbHelper=MyDbHelper.getInstance(this);
		 initial();
	}
	
	
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		
		initial();
	}



	public void initial(){
		
		try {
			myDbHelper.open();
			// Cursor c = myDbHelper.select(MyDbInfo.getTableNames()[0],
			// MyDbInfo.getFieldNames()[0], null, null, null, null,
			// "TIME desc, id desc","0,9");
			String sql = "select * from " + MyDbInfo.getTableNames()[3];
			Cursor c = myDbHelper.select(sql);
			String filename = "";
			c.moveToFirst();
			String result = "";

			etOutterWs= ((EditText)findViewById(R.id.etouterws));
			etOutterWs.setText(c.getString(0));
			c.close();
			myDbHelper.close();

		} catch (Exception e) {

		}finally{
			
			myDbHelper.close();
		}
		
		try{
			
		
		
			
}catch(Exception e){
			
		}
	}
	
	class UpdateWSListenr implements OnClickListener{

		@Override
		public void onClick(View v) {
			try {
				
				
				
				myDbHelper.open();
				String table =MyDbInfo.getTableNames()[3];
				String fields[] = {"sinnerws","soutterws","time1","time2"};
				String values[] = { "", "", "", "" };
				values[0] = etOutterWs.getText().toString();
			
				
				myDbHelper.delete(table, null, null);
				myDbHelper.insert(table, fields, values);
				myDbHelper.close();
				Toast.makeText(WebConfigActivity.this, "保存成功", Toast.LENGTH_LONG)
				.show();
				} catch (Exception e) {
					Toast.makeText(WebConfigActivity.this, "保存失败", Toast.LENGTH_LONG)
					.show();
				}finally{
					myDbHelper.close();
				}
		
		}
		}
	



	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
