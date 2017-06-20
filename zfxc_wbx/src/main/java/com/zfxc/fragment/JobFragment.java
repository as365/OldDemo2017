package com.zfxc.fragment;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout.LayoutParams;

import com.example.zfxc_wbx.R;
import com.zfxc.entity.NewsInfo;
import com.zfxc.ui.DBXCZFEditActivity;
import com.zfxc.ui.JCXCZFEditActivity;
import com.zfxc.ui.NYXCZFEditActivity;
import com.zfxc.ui.XCZFEditActivity;
import com.zfxc.ui.ZFXCSTATActivity;
import com.zfxc.ui.ZfxceditActivity;
import com.zfxc.util.DataTable;
import com.zfxc.util.MyDbHelper;
import com.zfxc.util.MyDbInfo;
import com.zfxc.util.WebServiceJC;

@SuppressLint("NewApi")
public class JobFragment extends Fragment implements OnScrollListener {

	private View view;// 缓存页面
	MyDbHelper myDbHelper;
	Handler handler1;
	String result;

	ListView myListView;
	List<NewsInfo> newsInfoListc = new ArrayList<NewsInfo>();
	MyAdapter mAdapter = new MyAdapter();
	private int visibleItemCount;
	private int visibleLastIndex;

	List<NewsInfo> newsInfoList;

	boolean canclickitem = true;
	Button btnadd;
	Button btnsearch;
	int page = 0;// 页数
	int pageSize = 10;// 页码
	LinearLayout loadingLayout;
	private Thread mThread;
	int maxcount;
	int maxpage;

	private LayoutParams mLayoutParams = new LinearLayout.LayoutParams(
			LinearLayout.LayoutParams.WRAP_CONTENT,
			LinearLayout.LayoutParams.WRAP_CONTENT);
	/**
	 * 设置布局显示目标最大化属性
	 */
	private LayoutParams FFlayoutParams = new LinearLayout.LayoutParams(
			LinearLayout.LayoutParams.FILL_PARENT,
			LinearLayout.LayoutParams.FILL_PARENT);

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.i("slide", "ActivityFragment--onCreate");
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		Log.i("slide", "ActivityFragment-onCreateView");
		if (view == null) {
			view = inflater.inflate(R.layout.job_fragment, container, false);

		}

		ViewGroup parent = (ViewGroup) view.getParent();
		if (parent != null) {
			parent.removeView(view);// 先移除
		}
		init();
		
		/***
		 * 添加记录代码部分
		 */
		// btnadd=(Button) view.findViewById(R.id.save);
		// btnadd.setOnClickListener(new View.OnClickListener() {
		// @Override
		// public void onClick(View v) {
		// Intent intent=new Intent(getActivity(), XCZFEditActivity.class);
		//
		// startActivity(intent);
		//
		//
		// }
		//
		//
		//
		// });
		/***
		 * 巡查记录查询代码部分
		 */
		// btnsearch=(Button) view.findViewById(R.id.btnsearch);
		// btnsearch.setOnClickListener(new View.OnClickListener() {
		// @Override
		// public void onClick(View v) {
		// Intent intent=new Intent(getActivity(), ZFXCSTATActivity.class);
		//
		// startActivity(intent);
		//
		//
		// }
		//
		//
		//
		// });

		myListView = (ListView) view.findViewById(R.id.listview);

		newsInfoList = new ArrayList<NewsInfo>();
		newsInfoListc = newsInfoList;

		// 线性布局
		LinearLayout layout = new LinearLayout(getActivity());
		// 设置布局 水平方向
		layout.setOrientation(LinearLayout.HORIZONTAL);
		// 进度条

		// 文本内容
		TextView textView = new TextView(getActivity());
		textView.setText("加载中...");
		textView.setGravity(Gravity.CENTER_VERTICAL);
		// 把文本加入到layout中
		layout.addView(textView, FFlayoutParams);
		// 设置layout的重力方向，即对齐方式是
		layout.setGravity(Gravity.CENTER);

		// 设置ListView的页脚layout
		if (loadingLayout == null) {
			loadingLayout = new LinearLayout(getActivity());
			loadingLayout.addView(layout, mLayoutParams);
			loadingLayout.setGravity(Gravity.CENTER);

			// 得到一个ListView用来显示条目

			// 添加到脚页显示
			myListView.addFooterView(loadingLayout);
			loadingLayout.setVisibility(View.INVISIBLE);
		}

		myListView.setAdapter(mAdapter);
		myListView.setOnScrollListener(JobFragment.this);

		myListView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// if(canclickitem)
				Cursor c=null;
				myDbHelper.open();
				String sql="select senforcementtype from  tbmission where serverid='"+newsInfoList.get(position).getMissId()+"'";
				c=myDbHelper.select(sql);
				while(c.moveToNext()){
					String missType=c.getString(c.getColumnIndex("senforcementtype"));
					if(missType.equals("农业执法")){
						Intent intent = new Intent(getActivity(),
								NYXCZFEditActivity.class);
						intent.putExtra("id", newsInfoList.get(position)
								.getRealid());
						startActivity(intent);
					}else if(missType.equals("检查员")){
						Intent intent = new Intent(getActivity(),
								JCXCZFEditActivity.class);
						intent.putExtra("id", newsInfoList.get(position)
								.getRealid());
						startActivity(intent);
					}else if(missType.equals("地理标志")){
						Intent intent = new Intent(getActivity(),
								DBXCZFEditActivity.class);
						intent.putExtra("id", newsInfoList.get(position)
								.getRealid());
						startActivity(intent);
					}
				}
					c.close();
					myDbHelper.close();
					
			}
		});
		// addrenwu();
		// setMaxPage();
		loadNewData(0);

		return view;
	}

	class MyAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return newsInfoListc.size();
		}

		@Override
		public Object getItem(int arg0) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int arg0) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(int arg0, View arg1, ViewGroup arg2) {
			// TODO Auto-generated method stub
			NewsInfo newsInfo = newsInfoListc.get(arg0);
			LayoutInflater inflater = LayoutInflater.from(getActivity());
			View view = inflater.inflate(R.layout.listview_item, null);
			TextView tvTitle = (TextView) view
					.findViewById(R.id.iv_listview_title);
			TextView tvDetails = (TextView) view
					.findViewById(R.id.iv_listview_detail);
			ImageView ivImage = (ImageView) view
					.findViewById(R.id.iv_listview_item);
			TextView txtddate = (TextView) view.findViewById(R.id.iv_ddate);
			// ivImage.setImageResource(newsInfo.getIconID());
			txtddate.setText(newsInfo.getDdate());
			tvTitle.setText(newsInfo.getTitle());
			tvDetails.setText(newsInfo.getDetail());
			return view;
		}

	}

	@SuppressLint("NewApi")
	private void init() {
		// TODO Auto-generated method stub
		myDbHelper = MyDbHelper.getInstance(getActivity());
		// getActivity().getActionBar().hide();
		addrenwu();

	}

	public void addrenwu() {

		try {
			// myDbHelper.open();
			// String table = MyDbInfo.getTableNames()[4];
			// String fields[] =
			// {"sname","scontent","ddate","sstatus","scomment","sresult","sperson","suser"};
			// String values[] = { "执法监督", "前往**农场进行执法监督", "2015-09-19", "已接受",
			// "" , "", "", "guangzhou"};
			//
			//
			// myDbHelper.insert(table, fields, values);
			//
			// myDbHelper.close();
			// Toast.makeText(getActivity(), "保存成功", Toast.LENGTH_LONG)
			// .show();
		} catch (Exception e) {
			Toast.makeText(getActivity(), "保存失败", Toast.LENGTH_LONG).show();
		} finally {

			if (myDbHelper != null) {
				try {
					myDbHelper.close();
				} catch (Exception e0) {

				}

			}

		}

	}

	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		Log.i("slide", "ActivityFragment--onPause");
	}

	@Override
	public void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		Log.i("slide", "ActivityFragment--onStop");
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		Log.i("slide", "ActivityFragment--onDestroy");
	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
		// TODO Auto-generated method stub
		// Toast.makeText(getApplicationContext(), "sdsdsd"+totalItemCount,
		// Toast.LENGTH_SHORT).show();
		this.visibleItemCount = visibleItemCount;
		visibleLastIndex = firstVisibleItem + visibleItemCount - 1;

		try {
			if (visibleItemCount == totalItemCount) {
				loadingLayout.setVisibility(View.INVISIBLE);
			} else {

				loadingLayout.setVisibility(View.VISIBLE);
			}
			if (page >= maxpage) {
				loadingLayout.setVisibility(View.INVISIBLE);
			}

		} catch (Exception e) {

		}
	}

	// 查找最大页数
	public void setMaxPage() {

		try {
			myDbHelper.open();
			// Cursor c = myDbHelper.select(MyDbInfo.getTableNames()[0],
			// MyDbInfo.getFieldNames()[0], null, null, null, null,
			// "TIME desc, id desc","0,9");
			String sql = "select sname from " + MyDbInfo.getTableNames()[5];
			Cursor c = myDbHelper.select(sql);

			c.moveToFirst();

			maxcount = c.getCount();
			maxpage = maxcount / pageSize;

			c.close();
			myDbHelper.close();

		} catch (Exception e) {
			myDbHelper.close();
		} finally {
			myDbHelper.close();
		}
	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		// TODO Auto-generated method stub
		// Toast.makeText(getApplicationContext(), "sdsdsd"+mAdapter.getCount(),
		// Toast.LENGTH_SHORT).show();
		final int itemsLastIndex = mAdapter.getCount() - 1; // 数据集最后一项的索引
		int lastIndex = itemsLastIndex; // 加上底部的loadMoreView项

		if (visibleLastIndex == (lastIndex + 1)) {
			// 如果是自动加载,可以在这里放置异步加载数据的代码
			Log.i("LOADMORE", "loading...");

			canclickitem = false;
			// 开线程去下载网络数据
			if (mThread == null || !mThread.isAlive()) {
				mThread = new Thread() {
					@Override
					public void run() {
						try {
							// 这里放你网络数据请求的方法，我在这里用线程休眠5秒方法来处理
							Thread.sleep(5000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}

						Message message = new Message();
						message.what = 1;
						handler.sendMessage(message);
					}
				};
				mThread.start();
			}

			loadNewData(itemsLastIndex);

		} else {

		}

	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		newsInfoList.clear();
		page = 0;
		loadNewData(0);

	}

	public void loadNewData(int itemsLastIndex) {
		// page= itemsLastIndex;
		NewsInfo newsInfo;
		Cursor c = null;
		Cursor cjc = null;
		Cursor cdb = null;
		try {
			myDbHelper.open();
			// Cursor c = myDbHelper.select(MyDbInfo.getTableNames()[0],
			// MyDbInfo.getFieldNames()[0], null, null, null, null,
			// "TIME desc, id desc","0,9");
			String sql = "select * from " + MyDbInfo.getTableNames()[9]
					+ " order by nydate desc, id desc";
			c = myDbHelper.select(sql);
			String jcsql = "select * from " + MyDbInfo.getTableNames()[10]
					+ " order by edate desc, Id desc";
			cjc = myDbHelper.select(jcsql);
			String dbsql = "select * from " + MyDbInfo.getTableNames()[11]
					+ " order by dbdate desc, Id desc";
			cdb = myDbHelper.select(dbsql);
			if(c.getCount()!=0){
				
			c.moveToFirst();
			String result = "";
			newsInfo = new NewsInfo(R.drawable.icc_launcher, c.getString(1),
					c.getString(3), c.getString(2), c.getString(0),
					c.getString(38));
			newsInfoList.add(newsInfo);
			while (c.moveToNext()) {
				result = result + c.getString(0) + c.getString(1)
						+ c.getString(2) + "/r/n";

				newsInfo = new NewsInfo(R.drawable.icc_launcher,
						c.getString(1), c.getString(3), c.getString(2),
						c.getString(0), c.getString(38));
				newsInfoList.add(newsInfo);
			}
			}
			if(cjc.getCount()!=0){
				
			//下面是检查员的list
			
			cjc.moveToFirst();
			String jcresult = "";
			newsInfo = new NewsInfo(R.drawable.icc_launcher, cjc.getString(1),
					cjc.getString(9), cjc.getString(28), cjc.getString(0),
					cjc.getString(25));
			newsInfoList.add(newsInfo);
			while (cjc.moveToNext()) {
				jcresult = result + cjc.getString(0) + cjc.getString(1)
						+ cjc.getString(2) + "/r/n";

				newsInfo = new NewsInfo(R.drawable.icc_launcher,
						cjc.getString(1), cjc.getString(9), cjc.getString(28),
						cjc.getString(0), cjc.getString(25));
				newsInfoList.add(newsInfo);
			}
			}
		if(cdb.getCount()!=0){
				
			//下面是地标的list
			
			cdb.moveToFirst();
			String dbresult = "";
			newsInfo = new NewsInfo(R.drawable.icc_launcher, cdb.getString(1),
					cdb.getString(3), cdb.getString(2), cdb.getString(0),
					cdb.getString(41));
			newsInfoList.add(newsInfo);
			while (cdb.moveToNext()) {
				dbresult = result + cdb.getString(0) + cdb.getString(1)
						+ cdb.getString(2) + "/r/n";

				newsInfo = new NewsInfo(R.drawable.icc_launcher,
						cdb.getString(1), cdb.getString(3), cdb.getString(2),
						cdb.getString(0), cdb.getString(41));
				newsInfoList.add(newsInfo);
			}
			}
			c.close();
			cjc.close();
			cdb.close();
			myDbHelper.close();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (c != null) {
				c.close();
				
			}
			if(cjc!=null){
				cjc.close();
				
			}
			if(cdb!=null){
				cdb.close();
			}

			myDbHelper.close();

		}
		mAdapter.notifyDataSetChanged();

		page++;

	}

	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			switch (msg.what) {
			case 1:

				// 重新刷新Listview的adapter里面数据
				mAdapter.notifyDataSetChanged();
				canclickitem = true;

				break;
			default:
				break;
			}
		}

	};
}
