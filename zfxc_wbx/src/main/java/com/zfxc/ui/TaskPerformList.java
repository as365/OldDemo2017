/**
 * 
 */
package com.zfxc.ui;

import java.util.ArrayList;
import java.util.List;

import com.example.zfxc_wbx.R;
import com.zfxc.adapter.TaskAdapter;
import com.zfxc.entity.Taskentity;
import com.zfxc.util.MyDbHelper;
import com.zfxc.util.MyDbInfo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;

/**
 * @author dell
 *
 */
public class TaskPerformList extends Activity {

	private ListView perLv;
	private List<Taskentity> tlist;
	private TaskAdapter adapter;
	private static String TaskMissId;
	private static String TaskNid;
	private static String TaskTitle;
	private static String TaskName;
	private static String TaskCounty;
	private static String TaskType;
	private Button btDelete;
	MyDbHelper myDbHelper;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_task_perform_list);
		perLv=(ListView) findViewById(R.id.task_lv);
		perLv.setOnItemClickListener(lvLisclick);
		myDbHelper = MyDbHelper.getInstance(this);
		tlist=new ArrayList<>();
		showList();
	}
	public OnItemClickListener lvLisclick=new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, View v, int position,
				long id) {
				TextView nid=(TextView) v.findViewById(R.id.task_item_id);
				String nnid=nid.getText().toString();
				Log.e("aaaaaa","==================="+nnid+"======="+TaskNid);
			if(TaskType.equals("农产品质量安全监管")){
				
				NCPZLZFEditActivity.startActivity(TaskPerformList.this,
						TaskMissId, nnid,TaskTitle,TaskName,
								TaskCounty);
			}else if(TaskType.equals("放心菜基地监督考评")){
				PFKHEditActivity.startActivity(TaskPerformList.this, TaskMissId,
						nnid, TaskTitle,
						TaskName, TaskCounty);
			}
		}
	};
	private void showList(){

		if(TaskType.equals("农产品质量安全监管")){
			myDbHelper.open();
			Cursor c = null;
			String sql = "select * from "
					+ MyDbInfo.getTableNames()[12]
					+ " where missionId= " + TaskMissId;
			c = myDbHelper.select(sql);
			while(c.moveToNext()){
				Taskentity taskty=new Taskentity();
				taskty.setTid(c.getString(0));
				taskty.setTunit(c.getString(1));
				taskty.setTdate(c.getString(2));
				taskty.setTaddress(c.getString(4));
				tlist.add(taskty);
			}
			adapter=new TaskAdapter(tlist, this,TaskType);
			perLv.setAdapter(adapter);
			if (c != null) {
				c.close();
			}

			myDbHelper.close();
		}else if(TaskType.equals("放心菜基地监督考评")){
			myDbHelper.open();
			Cursor c = null;
			String sql = "select * from "
					+ MyDbInfo.getTableNames()[13]
					+ " where missionId= " + TaskMissId;
			c = myDbHelper.select(sql);
			while(c.moveToNext()){
				Taskentity taskty=new Taskentity();
				taskty.setTid(c.getString(0));
				taskty.setTunit(c.getString(1));
				taskty.setTdate(c.getString(2));
				taskty.setTaddress(c.getString(5));
				tlist.add(taskty);
			} 
			adapter=new TaskAdapter(tlist, this,TaskType);
			perLv.setAdapter(adapter);
			if (c != null) {
				c.close();
			}

			myDbHelper.close();
		}
	}
	// 跳转
		public static void startActivity(Context context,String mId,String nId,String title,String name,String county,String type) {
			Intent in = new Intent(context, TaskPerformList.class);
			context.startActivity(in);
			Log.e("aaa","=======mId======="+mId+"====nId==="+nId+"===title===="+title+"===name===="+name+"===county===="+county+"====type==="+type);
			TaskMissId=mId;
			TaskNid=nId;
			TaskTitle=title;
			TaskName=name;
			TaskCounty=county;
			TaskType=type;
		}
}
