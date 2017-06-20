package com.zfxc.fragment;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.WildcardType;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.TreeSet;

import javax.xml.parsers.ParserConfigurationException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xml.sax.SAXException;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.RotateAnimation;
import android.view.animation.TranslateAnimation;
import android.view.animation.Animation.AnimationListener;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.ExpandableListView.OnGroupExpandListener;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;
import android.widget.ViewSwitcher.ViewFactory;

import com.baidu.platform.comapi.d.c;
import com.example.zfxc_wbx.R;
import com.zfxc.entity.MissDetailed;
import com.zfxc.entity.NewsInfo;
import com.zfxc.entity.ZFgePerson;
import com.zfxc.entity.ZfscDB;
import com.zfxc.fragment.ActivityFragment.ExAdapter.ChildViewHolder;
import com.zfxc.service.DownloadConFileService;
import com.zfxc.service.DownloadService;
import com.zfxc.ui.DBXCZFEditActivity;
import com.zfxc.ui.FXCQuery;
import com.zfxc.ui.LoginActivity;
import com.zfxc.ui.NCPZLZFEditActivity;
import com.zfxc.ui.NYXCZFEditActivity;
import com.zfxc.ui.PFKHEditActivity;
import com.zfxc.ui.SJfbrw;
import com.zfxc.ui.SpaceImageDetailActivity;
import com.zfxc.ui.TaskPerformList;
import com.zfxc.ui.ZfxceditActivity;
import com.zfxc.util.DataTable;
import com.zfxc.util.MyDbHelper;
import com.zfxc.util.MyDbInfo;
import com.zfxc.util.WebServiceJC;
import com.zfxc.wbx.MainActivity;


//任务公告
public class ActivityFragment extends Fragment implements OnScrollListener {

	public static int STATE = 0;
	private View view;// 缓存页面
	MyDbHelper myDbHelper;

	String result;
	private Context context;
	private List<String> ls;
	ListView myListView;
	List<NewsInfo> newsInfoListc = new ArrayList<NewsInfo>();
	List<List<MissDetailed>> mdList = new ArrayList<>();
	List<MissDetailed> mdsList = new ArrayList<>();
	// MyAdapter mAdapter = new MyAdapter();
	ExAdapter exAdapter = new ExAdapter();
	private int visibleItemCount;
	private int visibleLastIndex;
	DataTable dt;
	private ZFgePerson zfPS = null;
	public static String MissionId;
	List<NewsInfo> newsInfoList;
	boolean canclickitem = true;
	Button btnadd;
	int page = 0;// 页数
	int pageSize = 10;// 页码
	LinearLayout loadingLayout;
	private Thread mThread;
	int maxcount;
	int maxpage;
	private int lastClick = -1;
	private ExpandableListView exListView;
	private String sfilename = "";
	private ViewFlipper mFlipper;
	private View scrollTitleView;
	private String stt;
	private String rwqx;
	private Button btFb;
	private Button btizf;
	private Button btlzf;
	private Button btzf;
	private Animation animationTranslate, animationRotate, animationScale;
	private static int width, height;
	private static Boolean isClick = false;
	
	ChildViewHolder clHolder;
	Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			switch (msg.what) {
			case 1:
				bindNotices();
				break;

			case 2:
				myDbHelper.open();
				String[] wherevalue = { MyDbInfo.account };
				myDbHelper.delete(MyDbInfo.getTableNames()[8], "Mobilephone=?",
						wherevalue);
				myDbHelper.delete(MyDbInfo.getTableNames()[4], "suser=?",
						wherevalue);
				myDbHelper.close();
				download123();
				loadNewData(0);
				// exAdapter.notifyDataSetChanged();

				break;
			case 3:
				myDbHelper.open();
				String[] wherevalueup = { MyDbInfo.account };
				myDbHelper.delete(MyDbInfo.getTableNames()[8], "Mobilephone=?",
						wherevalueup);
				myDbHelper.delete(MyDbInfo.getTableNames()[4], "suser=?",
						wherevalueup);
				myDbHelper.close();
				download123();
				loadNewData(0);
				// exAdapter.notifyDataSetChanged();

				break;
			case -1:
				break;
			}
		}

	};

	private static final Integer[] images = { R.drawable.redtimepoint,
			R.drawable.yellowtimepoint,

	};

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
		Log.e("slide", "ActivityFragment--onCreate");
	}

	@Override
	public void onStart() {
		super.onStart();
		myDbHelper.open();
		String[] wherevalue = { MyDbInfo.account };
		myDbHelper.delete(MyDbInfo.getTableNames()[8], "Mobilephone=?",
				wherevalue);
		myDbHelper.delete(MyDbInfo.getTableNames()[4], "suser=?", wherevalue);
		myDbHelper.close();
		ls.clear();
		download123();
		Log.e("aaaaa", "=========onStart==========" + STATE);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		Log.i("slide", "ActivityFragment-onCreateView");
		if (view == null) {
			view = inflater.inflate(R.layout.activity_fragment, container,
					false);
		}
		ViewGroup parent = (ViewGroup) view.getParent();
		if (parent != null) {
			parent.removeView(view);// 先移除
		}
		
		ls = new ArrayList<>();
		init();
		
		// myListView = (ListView) view.findViewById(R.id.listview);
		exListView = (ExpandableListView) view.findViewById(R.id.exlistview);
		mFlipper = (ViewFlipper) view.findViewById(R.id.flipper);
		mFlipper.setInAnimation(AnimationUtils.loadAnimation(getActivity(),
				android.R.anim.slide_in_left));
		mFlipper.setOutAnimation(AnimationUtils.loadAnimation(getActivity(),
				android.R.anim.slide_out_right));
		btFb=(Button) view.findViewById(R.id.faburenwu);
		rwqx=MyDbInfo.qx;
		if(rwqx.equals("市无公害中心")){
			btFb.setVisibility(View.VISIBLE);
			btFb.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					SJfbrw.startActivity(getActivity());
				}
			});
		}
		mFlipper.startFlipping();
		newsInfoList = new ArrayList<NewsInfo>();
		newsInfoListc = newsInfoList;
		exListView.setAdapter(exAdapter);
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
			// myListView.addFooterView(loadingLayout);
			loadingLayout.setVisibility(View.INVISIBLE);
		}

		// myListView.setAdapter(mAdapter);
		exListView.setAdapter(exAdapter);
		// myListView.setOnScrollListener(ActivityFragment.this);
		//
		exListView.setOnGroupClickListener(new OnGroupClickListener() {

			@Override
			public boolean onGroupClick(ExpandableListView parent, View v,
					int groupPosition, long id) {

				if (lastClick == -1) {
					exListView.expandGroup(groupPosition);
					if (mdsList != null) {
						mdsList.clear();
						mdList.clear();
					}
					Cursor c = null;
					Cursor c1 = null;

					String missid = "";
					// String st = ls.get(position);
					NewsInfo newsInfo = newsInfoListc.get(groupPosition);
					String st = newsInfo.getStatus();

					if (st.equals("未完成")) {
						TextView mid2 = (TextView) v
								.findViewById(R.id.iv_listview_missid2);
						stt = mid2.getText().toString();
						missid = stt;
						// String updatefields2[] = { "Status"};
						// String[] updatevalues2 = {"正在执行"};
						// String[] wherevalue = { missid};
						// myDbHelper.update(MyDbInfo.getTableNames()[8],updatefields2,
						// updatevalues2, "MissionId=?", wherevalue );
						Updata(stt, zfPS.getMobilePhone(), "正在执行");
						if (newsInfoList != null) {
							newsInfoList.clear();
							newsInfoListc.clear();
						}
						mHandler.sendEmptyMessage(2);
					} else if (st.equals("正在执行")) {
						TextView mid3 = (TextView) v
								.findViewById(R.id.iv_listview_missid3);
						missid = mid3.getText().toString();
					} else {
						TextView mid = (TextView) v
								.findViewById(R.id.iv_listview_missid);
						missid = mid.getText().toString();
					}
					Log.e("aaaaa", "====================" + missid);
					MissionId = missid;
					myDbHelper.open();
					String sql = "select * from " + MyDbInfo.getTableNames()[4]
							+ " where serverid='" + missid + "'";
					c = myDbHelper.select(sql);
					String sql1 = "select * from "
							+ MyDbInfo.getTableNames()[8]
							+ " where MissionId='" + missid + "'";
					c1 = myDbHelper.select(sql1);
					c.moveToFirst();
					c1.moveToFirst();
					int Uname = c1.getColumnIndex("Uname");
					String name = c1.getString(Uname);
					int Status = c1.getColumnIndex("Status");
					String status = c1.getString(Status);
					MissDetailed mds = new MissDetailed();
					mds.setMissStatus(status);
					mds.setMissContent(c.getString(2));
					mds.setMissContentfile(c.getString(13));
					mds.setMissDate(c.getString(3));
					mds.setMissType(c.getString(12));
					mds.setMissUser(name);
					mdsList.add(mds);
					mdList.add(mdsList);
					exAdapter.notifyDataSetChanged();
					sfilename = c.getString(13);

					if (c != null || c1 != null) {
						c.close();
						c1.close();
						myDbHelper.close();
					}

				}

				if (lastClick != -1 && lastClick != groupPosition) {
					exListView.collapseGroup(lastClick);
					exListView.expandGroup(groupPosition);
					if (mdsList != null) {
						mdsList.clear();
						mdList.clear();
					}
					Cursor c = null;
					Cursor c1 = null;

					String missid = "";
					// String st = ls.get(position);
					NewsInfo newsInfo = newsInfoListc.get(groupPosition);
					String st = newsInfo.getStatus();

					if (st.equals("未完成")) {
						TextView mid2 = (TextView) v
								.findViewById(R.id.iv_listview_missid2);
						stt = mid2.getText().toString();
						missid = stt;
						// Updata(st,zfPS.getMobilePhone(), "正在执行");
						// download123();
						// exAdapter.notify();
						Updata(stt, zfPS.getMobilePhone(), "正在执行");
						if (newsInfoList != null) {
							newsInfoList.clear();
							newsInfoListc.clear();
						}
						// download123();
						// loadNewData(0);
						mHandler.sendEmptyMessage(2);
					} else if (st.equals("正在执行")) {
						TextView mid3 = (TextView) v
								.findViewById(R.id.iv_listview_missid3);
						missid = mid3.getText().toString();
					} else {
						TextView mid = (TextView) v
								.findViewById(R.id.iv_listview_missid);
						missid = mid.getText().toString();
					}
					Log.e("aaaaa", "====================" + missid);
					MissionId = missid;
					myDbHelper.open();
					String sql = "select * from " + MyDbInfo.getTableNames()[4]
							+ " where serverid='" + missid + "'";
					c = myDbHelper.select(sql);
					String sql1 = "select * from "
							+ MyDbInfo.getTableNames()[8]
							+ " where MissionId='" + missid + "'";
					c1 = myDbHelper.select(sql1);
					c.moveToFirst();
					c1.moveToFirst();
					int Uname = c1.getColumnIndex("Uname");
					String name = c1.getString(Uname);
					int Status = c1.getColumnIndex("Status");
					String status = c1.getString(Status);
					MissDetailed mds = new MissDetailed();
					mds.setMissStatus(status);
					mds.setMissContent(c.getString(2));
					mds.setMissContentfile(c.getString(13));
					mds.setMissDate(c.getString(3));
					mds.setMissType(c.getString(12));
					mds.setMissUser(name);
					mdsList.add(mds);
					mdList.add(mdsList);
					sfilename = c.getString(13);
					exAdapter.notifyDataSetChanged();
					if (c != null || c1 != null) {
						c.close();
						c1.close();
						myDbHelper.close();
					}
				} else if (lastClick == groupPosition) {
					if (exListView.isGroupExpanded(groupPosition)) {
						exListView.collapseGroup(groupPosition);

					} else if (!exListView.isGroupExpanded(groupPosition)) {
						exListView.expandGroup(groupPosition);
					}
				}
				lastClick = groupPosition;

				return true;
			}
		});
		// addrenwu();
		setMaxPage();
		loadNewData(0);
		return view;
	}

	private TextView getTextView(String tongzhi) {
		TextView tv = new TextView(getActivity());
		tv.setGravity(Gravity.LEFT);
		tv.setTextSize(20);
		tv.setSingleLine(true);
		tv.setGravity(Gravity.CENTER_VERTICAL);
		tv.setText(tongzhi);
		return tv;
	}

	@Override
	public void setUserVisibleHint(boolean isVisibleToUser) {
		// TODO Auto-generated method stub
		super.setUserVisibleHint(isVisibleToUser);
		// if(isVisibleToUser){
		// try{
		// init();
		// page=0;
		// newsInfoListc.clear();
		// loadNewData(0);
		//
		// myListView=(ListView) view.findViewById(R.id.listview);
		//
		// newsInfoList=new ArrayList<NewsInfo>();
		// //newsInfoListc=newsInfoList;
		//
		// // 线性布局
		// LinearLayout layout =new LinearLayout(getActivity());
		// // 设置布局 水平方向
		// layout.setOrientation(LinearLayout.HORIZONTAL);
		// // 进度条
		//
		//
		//
		// // 文本内容
		// TextView textView =new TextView(getActivity());
		// textView.setText("加载中...");
		// textView.setGravity(Gravity.CENTER_VERTICAL);
		// // 把文本加入到layout中
		// layout.addView(textView, FFlayoutParams);
		// // 设置layout的重力方向，即对齐方式是
		// layout.setGravity(Gravity.CENTER);
		//
		// // 设置ListView的页脚layout
		// if(loadingLayout==null){
		// loadingLayout =new LinearLayout(getActivity());
		// loadingLayout.addView(layout, mLayoutParams);
		// loadingLayout.setGravity(Gravity.CENTER);
		//
		// // 得到一个ListView用来显示条目
		//
		// // 添加到脚页显示
		// myListView.addFooterView(loadingLayout);
		// loadingLayout.setVisibility(View.INVISIBLE);
		// }
		//
		//
		//
		//
		// myListView.setAdapter(mAdapter);
		// myListView.setOnScrollListener(ActivityFragment.this);
		//
		//
		//
		// myListView.setOnItemClickListener(new OnItemClickListener() {
		// public void onItemClick(AdapterView<?> parent, View view, int
		// position, long id) {
		// //if(canclickitem)
		// {
		//
		//
		// // Toast.makeText(getActivity(), "sdsdsd"+position,
		// Toast.LENGTH_SHORT).show();
		// Intent intent=new Intent(getActivity(), ZfxceditActivity.class);
		// intent.putExtra("id", newsInfoList.get(position).getRealid());
		// startActivity(intent);
		// }
		// }
		// });
		// // addrenwu();
		// setMaxPage();
		// loadNewData(0);
		// }catch(Exception e){
		//
		// }
		//
		// }
	}

	private void init() {
		myDbHelper = MyDbHelper.getInstance(getActivity());
	}

	public void addrenwu() {

		try {
			myDbHelper.open();
			String table = MyDbInfo.getTableNames()[4];
			String fields[] = { "sname", "scontent", "ddate", "sstatus",
					"scomment", "sresult", "sperson", "suser", "serverid" };
			String values[] = { "执法监督", "前往**农场进行执法监督", "2015-09-19", "已接受",
					"", "", "", "guangzhou", "serverid" };

			values[0] = dt.getRow().get(0).getColumn("sname").toString();
			values[1] = dt.getRow().get(0).getColumn("scontent").toString();

			SimpleDateFormat dFormat = new SimpleDateFormat("yyyy-MM-dd");

			values[2] = dFormat.format(Date.parse(dt.getRow().get(0)
					.getColumn("ddate").toString()));
			values[3] = dt.getRow().get(0).getColumn("sstatus").toString();
			values[4] = dt.getRow().get(0).getColumn("scomment").toString();
			values[5] = dt.getRow().get(0).getColumn("sresult").toString();
			values[6] = dt.getRow().get(0).getColumn("sperson").toString();
			values[7] = dt.getRow().get(0).getColumn("suser").toString();
			values[8] = dt.getRow().get(0).getColumn("id").toString();
			myDbHelper.insert(table, fields, values);

			myDbHelper.close();
			Toast.makeText(getActivity(), "保存成功", Toast.LENGTH_LONG).show();

		} catch (Exception e) {

		} finally {
			try {
				myDbHelper.close();

			} catch (Exception e2) {
				// TODO: handle exception
			}

		}
		loadNewData(0);

	}

	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		STATE = 0;
		Log.e("slide", "ActivityFragment--onPause");
	}

	@Override
	public void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		Log.e("slide", "ActivityFragment--onStop");
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		Log.e("slide", "ActivityFragment--onDestroy");
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
			String sql = "select sname from " + MyDbInfo.getTableNames()[4];
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
		final int itemsLastIndex = exListView.getCount() - 1; // 数据集最后一项的索引
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
							Thread.sleep(100);
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

			// loadNewData(itemsLastIndex);

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
		Cursor c1 = null;
		try {
			myDbHelper.open();
			// Cursor c = myDbHelper.select(MyDbInfo.getTableNames()[0],
			// MyDbInfo.getFieldNames()[0], null, null, null, null,
			// "TIME desc, id desc","0,9");
			String sql = "select * from " + MyDbInfo.getTableNames()[4]
					+ " order by ddate desc, id desc";
			c = myDbHelper.select(sql);

			// c.moveToFirst();
			String result = "";
			// newsInfo = new NewsInfo(R.drawable.icc_launcher, c.getString(1),
			// c.getString(2), c.getString(3), c.getString(0),
			// c.getString(10));
			// newsInfoList.add(newsInfo);
			while (c.moveToNext()) {
				result = result + c.getString(0) + c.getString(1)
						+ c.getString(2) + "/r/n";
				String sql1 = "select Status from "
						+ MyDbInfo.getTableNames()[8] + " where MissionId='"
						+ c.getString(10) + "'";
				c1 = myDbHelper.select(sql1);
				while (c1.moveToNext()) {
					int fnameIndex = c1.getColumnIndex("Status");
					String fname = c1.getString(fnameIndex);
					newsInfo = new NewsInfo(R.drawable.icc_launcher,
							c.getString(1), c.getString(2), c.getString(3),
							c.getString(0), c.getString(10), fname);
					newsInfoList.add(newsInfo);

				}
				Message msg = new Message();
				msg.what = 1;
				mHandler.sendMessageDelayed(msg, 500);

				c1.close();
			}
			c.close();
			myDbHelper.close();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (c != null) {
				c.close();
			}

			myDbHelper.close();

		}
		exAdapter.notifyDataSetChanged();

		page++;

	}

	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			switch (msg.what) {
			case 1:

				// 重新刷新Listview的adapter里面数据
				exAdapter.notifyDataSetChanged();
				canclickitem = true;

				break;
			default:
				break;
			}
		}

	};

	@SuppressWarnings("unused")
	public void download123() {
		String status = "";
		DataTable dt = null;
		String result = "";
		String enforcementtype = "";
		String sqlPS = "";

		HashMap<String, String> hm = new HashMap<String, String>();
		String susername = MyDbInfo.account;
		String spassword = MyDbInfo.password;
		WebServiceJC.susername = MyDbInfo.account;
		// String
		// sql="select zpenforcementtype from tbzfPerson where zpmobilephone='"+WebServiceJC.susername+"'";
		// hm.put("username",susername);
		hm.put("zpmobilephone", susername);
		// hm.put("sql",sql);
		WebServiceJC.setUrl("inner", myDbHelper);
		if (hm != null) {
			enforcementtype = WebServiceJC.WorkJC("getUserType", hm);
			MyDbInfo.enforType = WebServiceJC.WorkJC("getUserType", hm);
		}

		sqlPS = "select * from tbzfgrPerson where Mobilephone='"
				+ WebServiceJC.susername
				+ "' and Status <> '已完成' order by Id desc";
		hm.put("sql", sqlPS);
		WebServiceJC.setUrl("inner", myDbHelper);
		if (hm != null) {
			String result1 = WebServiceJC.WorkJC("Down_Person_json", hm);
			try {

				JSONObject json = new JSONObject(result1);
				JSONArray jsonArray = json.getJSONArray("root");
				for (int i = 0; i < jsonArray.length(); i++) {
					zfPS = new ZFgePerson();
					JSONObject jsonObjectSon = (JSONObject) jsonArray.opt(i);
					zfPS.setId(jsonObjectSon.getInt("id"));
					zfPS.setMissionId(jsonObjectSon.getInt("MissionId"));
					zfPS.setMobilePhone(jsonObjectSon.getString("Mobilephone"));
					zfPS.setUname(jsonObjectSon.getString("Uname"));
					zfPS.setStatus(jsonObjectSon.getString("Status"));
					Log.e("aaaaaa",
							"=========" + zfPS.getId() + "==========="
									+ zfPS.getMissionId() + "=========="
									+ zfPS.getMobilePhone());
					String sql1 = "select * from tbmission where sstatus <> '已完成' and id='"
							+ zfPS.getMissionId() + "'";
					hm.put("username", susername);
					hm.put("password", spassword);
					hm.put("sql", sql1);
					WebServiceJC.setUrl("inner", myDbHelper);
					if (hm != null) {

						result = WebServiceJC.WorkJC("downloadinfo", hm);
					}
					try {
						dt = WebServiceJC.getChannelListzfxc(result);
						// String s1 = zfPS.getStatus();
						// ls.add(s1);

						addrenwu(dt);

					} catch (ParserConfigurationException e) {
						e.printStackTrace();
					} catch (SAXException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}
					boolean flag = false;
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}

	}

	@SuppressWarnings("unused")
	public void addrenwu(DataTable dt) {
		try {
			myDbHelper.open();
			String table = MyDbInfo.getTableNames()[4];
			String fields[] = { "sname", "scontent", "ddate", "sstatus",
					"scomment", "sresult", "sperson", "suser", "serverid",
					"senforcementtype", "contentFile", "county" };
			String field2s[] = { "MissionId", "Mobilephone", "Uname", "Status" };
			String values2[] = { "", "", "", "" };
			String values[] = { "执法监督", "前往**农场进行执法监督", "2015-09-19", "已接受",
					"", "", "", "guangzhou", "serverid", "", "contentFile",
					"county" };
			for (int i = 0; i < dt.getRow().size(); i++) {
				values[0] = dt.getRow().get(i).getColumn("sname").toString();
				values[1] = dt.getRow().get(i).getColumn("scontent").toString();

				SimpleDateFormat dFormat = new SimpleDateFormat("yyyy-MM-dd");

				values[2] = dt.getRow().get(0).getColumn("ddate").toString()
						.split(" ")[0];
				try {

					values[2] = dFormat.format(dFormat.parse(values[2]));
				} catch (Exception e111) {

				}

				values[3] = dt.getRow().get(i).getColumn("sstatus").toString();
				values[4] = dt.getRow().get(i).getColumn("scomment").toString();
				values[5] = dt.getRow().get(i).getColumn("sresult").toString();
				values[6] = zfPS.getUname();
				values[7] = zfPS.getMobilePhone();
				values[8] = dt.getRow().get(i).getColumn("id").toString();
				values[9] = dt.getRow().get(i).getColumn("senforcementtype")
						.toString();
				values[10] = dt.getRow().get(i).getColumn("contentFile")
						.toString();
				values[11] = dt.getRow().get(i).getColumn("sperson").toString();
				values2[0] = zfPS.getMissionId().toString();
				values2[1] = zfPS.getMobilePhone();
				values2[2] = zfPS.getUname();
				values2[3] = zfPS.getStatus();
				Log.e("aaaaaaa", "==============" + zfPS.getStatus());
				myDbHelper.insert(table, fields, values);
				myDbHelper
						.insert(MyDbInfo.getTableNames()[8], field2s, values2);
			}
			myDbHelper.close();

			// Toast.makeText(getActivity(), "保存成功", Toast.LENGTH_LONG)
			// .show();

		} catch (Exception e) {
			System.out.println(e.getMessage());

		} finally {
			try {
				myDbHelper.close();

			} catch (Exception e2) {
				// TODO: handle exception
			}

		}

	}

	public static void startActivity(Context context) {
		Intent in = new Intent(context, ActivityFragment.class);
		context.startActivity(in);
	}

	class ExAdapter extends BaseExpandableListAdapter {

		private static final int TYPE_ITEM = 0;

		private static final int TYPE_SEPARATOR = 1;

		private static final int TYPE_MAX_COUNT = 1 + 1 + 1;
		private int type;

		public int getItemViewType(String p) {

			if (p.equals("未完成")) {
				return type = 0;
			} else if (p.equals("正在执行")) {
				return type = 1;
			} else {
				return type = 2;
			}

		}

		@Override
		public int getGroupCount() {
			return newsInfoListc != null ? newsInfoListc.size() : 0;
		}

		@Override
		public NewsInfo getGroup(int groupPosition) {
			return newsInfoListc.get(groupPosition);
		}

		@Override
		public long getGroupId(int groupPosition) {
			return groupPosition;
		}

		@Override
		public View getGroupView(int groupPosition, boolean isExpanded,
				View convertView, ViewGroup parent) {
			View comsumView = null;
			View chargeView = null;
			View chargView = null;

			NewsInfo newsInfo = newsInfoListc.get(groupPosition);
			LayoutInflater inflater = LayoutInflater.from(getActivity());
			Log.e("aaaaaa", "=======groupPosition=========" + groupPosition);
			String s = newsInfo.getStatus();
			int t = getItemViewType(s);
			switch (t) {

			case TYPE_ITEM:
				ViewHolder2 holder2 = null;
				if (chargeView == null) {
					holder2 = new ViewHolder2();
					chargeView = inflater
							.inflate(R.layout.listview_item2, null);
					holder2.tvTitle = (TextView) chargeView
							.findViewById(R.id.iv_listview_title2);
					holder2.tvDetails = (TextView) chargeView
							.findViewById(R.id.iv_listview_detail2);
					ImageView ivImage = (ImageView) chargeView
							.findViewById(R.id.iv_listview_item2);
					holder2.icon = (ImageView) chargeView
							.findViewById(R.id.iv_listview_item2_2);
					holder2.txtddate = (TextView) chargeView
							.findViewById(R.id.iv_ddate2);
					holder2.tvMissId = (TextView) chargeView
							.findViewById(R.id.iv_listview_missid2);
					holder2.btzf = (Button) chargeView
							.findViewById(R.id.iv_zf_missbt2);
					chargeView.setTag(holder2);
					convertView = chargeView;

				} else {
					holder2 = (ViewHolder2) convertView.getTag();
				}

				holder2.txtddate.setText(newsInfo.getDdate());
				holder2.tvTitle.setText(newsInfo.getTitle());
				holder2.tvDetails.setText(newsInfo.getDetail());
				holder2.tvMissId.setText(newsInfo.getMissId());
				// holder2.icon.setImageResource(R.drawable.yellowtimepoint);
				holder2.btzf.setOnClickListener(new lvButtonListener(
						groupPosition));
				break;
			case TYPE_SEPARATOR:
				ViewHolder3 holder3 = null;
				if (chargView == null) {
					holder3 = new ViewHolder3();
					chargView = inflater.inflate(R.layout.listview_item3, null);
					holder3.tvTitle = (TextView) chargView
							.findViewById(R.id.iv_listview_title3);
					holder3.tvDetails = (TextView) chargView
							.findViewById(R.id.iv_listview_detail3);
					ImageView ivImage = (ImageView) chargView
							.findViewById(R.id.iv_listview_item3);
					holder3.txtddate = (TextView) chargView
							.findViewById(R.id.iv_ddate3);
					ImageView icon = (ImageView) chargView
							.findViewById(R.id.iv_listview_item2_3);
					holder3.tvMissId = (TextView) chargView
							.findViewById(R.id.iv_listview_missid3);
					holder3.btzf = (Button) chargView
							.findViewById(R.id.iv_zf_missbt3);
					holder3.btup = (Button) chargView
							.findViewById(R.id.iv_up_missbt3);
					holder3.btlistzf = (Button) chargView
							.findViewById(R.id.iv_listzf_missbt3);
					chargView.setTag(holder3);
					convertView = chargView;

				} else {
					holder3 = (ViewHolder3) convertView.getTag();
				}

				holder3.txtddate.setText(newsInfo.getDdate());
				holder3.tvTitle.setText(newsInfo.getTitle());
				holder3.tvDetails.setText(newsInfo.getDetail());
				holder3.tvMissId.setText(newsInfo.getMissId());
				holder3.btzf.setOnClickListener(new lvButtonListener(
						groupPosition));
				holder3.btup.setOnClickListener(new lvButtonListener(
						groupPosition));
				holder3.btlistzf.setOnClickListener(new lvButtonListener(
						groupPosition));
				break;
			default:
				ViewHolder1 holder1 = null;
				if (comsumView == null) {
					holder1 = new ViewHolder1();
					comsumView = inflater.inflate(R.layout.listview_item, null);
					holder1.tvTitle = (TextView) comsumView
							.findViewById(R.id.iv_listview_title);
					holder1.tvDetails = (TextView) comsumView
							.findViewById(R.id.iv_listview_detail);
					ImageView ivImage = (ImageView) comsumView
							.findViewById(R.id.iv_listview_item);
					holder1.txtddate = (TextView) comsumView
							.findViewById(R.id.iv_ddate);
					ImageView icon = (ImageView) comsumView
							.findViewById(R.id.iv_listview_item2);
					holder1.tvMissId = (TextView) comsumView
							.findViewById(R.id.iv_listview_missid);
					comsumView.setTag(holder1);
					convertView = comsumView;
				} else {
					holder1 = (ViewHolder1) convertView.getTag();
				}

				holder1.txtddate.setText(newsInfo.getDdate());
				holder1.tvTitle.setText(newsInfo.getTitle());
				holder1.tvDetails.setText(newsInfo.getDetail());
				holder1.tvMissId.setText(newsInfo.getMissId());
				break;
			}
			return convertView;
		}

		@Override
		public int getChildrenCount(int groupPosition) {
			return mdList != null ? mdList.size() : 0;
		}

		@Override
		public MissDetailed getChild(int groupPosition, int childPosition) {
			return mdList.get(0).get(childPosition);
		}

		@Override
		public long getChildId(int groupPosition, int childPosition) {
			return childPosition;
		}

		@Override
		public View getChildView(int groupPosition, int childPosition,
				boolean isLastChild, View convertView, ViewGroup parent) {
			View v = null;
			ChildViewHolder vHoler = null;
			LayoutInflater inflater = LayoutInflater.from(getActivity());
			MissDetailed md = mdList.get(0).get(childPosition);
			if (v == null) {
				vHoler = new ChildViewHolder();
				v = inflater.inflate(R.layout.layout_child_item, null);
				vHoler.txtMissStatus = (TextView) v
						.findViewById(R.id.child_mstatus);
				vHoler.txtMissContent = (TextView) v
						.findViewById(R.id.child_mcontent);
				vHoler.txtMissDate = (TextView) v
						.findViewById(R.id.child_mdate);
				vHoler.txtMissUser = (TextView) v
						.findViewById(R.id.child_muser);
				vHoler.txtMissType = (TextView) v
						.findViewById(R.id.child_mtype);
				vHoler.txtMissfilde = (TextView) v
						.findViewById(R.id.child_mcontent_filde);
				vHoler.btshow = (Button) v
						.findViewById(R.id.child_mcontent_show);
				v.setTag(vHoler);
				convertView = v;
			} else {
				vHoler = (ChildViewHolder) v.getTag();
			}
			vHoler.txtMissStatus.setText(md.getMissStatus());
			vHoler.txtMissContent.setText(md.getMissContent());
			vHoler.txtMissDate.setText(md.getMissDate());
			vHoler.txtMissUser.setText(md.getMissUser());
			vHoler.txtMissType.setText(md.getMissType());
			vHoler.txtMissfilde.setText(md.getMissContentfile());
			vHoler.btshow
					.setOnClickListener(new lvButtonListener(groupPosition));
			return convertView;
		}

		@Override
		public boolean hasStableIds() {
			return true;
		}

		@Override
		public boolean isChildSelectable(int groupPosition, int childPosition) {
			return true;
		}

		public class ViewHolder1 {

			TextView txtddate;
			TextView tvTitle;
			TextView tvDetails;
			TextView tvMissId;

		}

		public class ViewHolder2 {

			TextView txtddate;
			TextView tvTitle;
			TextView tvDetails;
			TextView tvMissId;
			ImageView icon;
			Button btzf;
		}

		public class ViewHolder3 {

			TextView txtddate;
			TextView tvTitle;
			TextView tvDetails;
			TextView tvMissId;
			Button btzf;
			Button btup;
			Button btlistzf;

		}

		public class ChildViewHolder {
			TextView txtMissStatus;
			TextView txtMissContent;
			TextView txtMissDate;
			TextView txtMissUser;
			TextView txtMissType;
			TextView txtMissfilde;
			Button btshow;

		}

		class lvButtonListener implements OnClickListener {
			private int position;
			private int cun=0;

			lvButtonListener(int pos) {
				position = pos;
			}

			@Override
			public void onClick(View v) {
				switch (v.getId()) {
				case R.id.iv_listzf_missbt3:
					NewsInfo newsInfoTask = newsInfoListc.get(position);
					String stTask = newsInfoTask.getMissId();
					Cursor ctypeTask = null;
					myDbHelper.open();
					String sqltypeTask = "select * from "
							+ MyDbInfo.getTableNames()[4] + " where serverid='"
							+ stTask + "'";
					ctypeTask = myDbHelper.select(sqltypeTask);
					ctypeTask.moveToFirst();
					String typeTask = ctypeTask.getString(ctypeTask
							.getColumnIndex("senforcementtype"));
					String countyTask = ctypeTask.getString(ctypeTask
							.getColumnIndex("county"));
					if (ctypeTask != null) {
						ctypeTask.close();
						myDbHelper.close();
					}
					Log.e("aaaa", "=========type==========" + type);
					if (typeTask.equals("农产品质量安全监管")) {
						Cursor czf = null;
						Cursor czf1 = null;
						myDbHelper.open();
						if (mdsList != null) {
							mdsList.clear();
							mdList.clear();
						}
						String sqlzf = "select * from "
								+ MyDbInfo.getTableNames()[4]
								+ " where serverid='" + stTask + "'";
						czf = myDbHelper.select(sqlzf);
						String sqlzf1 = "select * from "
								+ MyDbInfo.getTableNames()[8]
								+ " where MissionId='" + stTask + "'";
						czf1 = myDbHelper.select(sqlzf1);
						czf.moveToFirst();
						czf1.moveToFirst();
						int Uname = czf1.getColumnIndex("Uname");
						String name = czf1.getString(Uname);
						int Status = czf1.getColumnIndex("Status");
						String status = czf1.getString(Status);
						MissDetailed mds = new MissDetailed();
						mds.setMissStatus(status);
						mds.setMissContent(czf.getString(2));
						mds.setMissContentfile(czf.getString(13));
						mds.setMissDate(czf.getString(3));
						mds.setMissType(czf.getString(12));
						mds.setMissUser(name);
						mdsList.add(mds);
						mdList.add(mdsList);
						sfilename = czf.getString(13);
						if (czf != null || czf1 != null) {
							czf.close();
							czf1.close();
							myDbHelper.close();
						}
						myDbHelper.open();
						Cursor c = null;
						String sql = "select Id from "
								+ MyDbInfo.getTableNames()[12]
								+ " where missionId= " + stTask;
						c = myDbHelper.select(sql);
						c.moveToFirst();

						if (c.getCount() == 0) {
//							NCPZLZFEditActivity.startActivity(getActivity(),
//									stTask, "", newsInfoListc.get(position)
//											.getTitle(), zfPS.getUname(),
//											countyTask);
						Toast.makeText(getActivity(),"暂时没有数据！！！",Toast.LENGTH_SHORT).show();
						} else {
							int nyid = c.getColumnIndex("Id");
							String n = c.getString(nyid);
							Log.e("aaaa", "==========n=========" + n);
							TaskPerformList.startActivity(getActivity(),
									stTask, n, newsInfoListc.get(position)
											.getTitle(), zfPS.getUname(),
											countyTask,typeTask);
						}
						if (c != null) {
							c.close();
							myDbHelper.close();
						}

					} else if (typeTask.equals("放心菜基地监督考评")) {
						Cursor czf = null;
						Cursor czf1 = null;
						myDbHelper.open();
						if (mdsList != null) {
							mdsList.clear();
							mdList.clear();
						}
						String sqlzf = "select * from "
								+ MyDbInfo.getTableNames()[4]
								+ " where serverid='" + stTask + "'";
						czf = myDbHelper.select(sqlzf);
						String sqlzf1 = "select * from "
								+ MyDbInfo.getTableNames()[8]
								+ " where MissionId='" + stTask + "'";
						czf1 = myDbHelper.select(sqlzf1);
						czf.moveToFirst();
						czf1.moveToFirst();
						int Uname = czf1.getColumnIndex("Uname");
						String name = czf1.getString(Uname);
						int Status = czf1.getColumnIndex("Status");
						String status = czf1.getString(Status);
						MissDetailed mds = new MissDetailed();
						mds.setMissStatus(status);
						mds.setMissContent(czf.getString(2));
						mds.setMissContentfile(czf.getString(13));
						mds.setMissDate(czf.getString(3));
						mds.setMissType(czf.getString(12));
						mds.setMissUser(name);
						mdsList.add(mds);
						mdList.add(mdsList);
						sfilename = czf.getString(13);
						if (czf != null || czf1 != null) {
							czf.close();
							czf1.close();
							myDbHelper.close();
						}
						myDbHelper.open();
						Cursor c = null;
						String sql = "select Id from "
								+ MyDbInfo.getTableNames()[13]
								+ " where missionId= " + stTask;
						c = myDbHelper.select(sql);
						c.moveToFirst();

						if (c.getCount() == 0) {
							Toast.makeText(getActivity(),"暂时没有数据！！！",Toast.LENGTH_SHORT).show();
//							PFKHEditActivity.startActivity(getActivity(), stTask,
//									"", newsInfoListc.get(position).getTitle(),
//									zfPS.getUname(), countyTask);
						} else {
							int nyid = c.getColumnIndex("Id");
							String n = c.getString(nyid);
							Log.e("aaaa", "==========n=========" + n);
							TaskPerformList.startActivity(getActivity(),
									stTask, n, newsInfoListc.get(position)
											.getTitle(), zfPS.getUname(),
											countyTask,typeTask);
						}
						if (c != null) {
							c.close();
							myDbHelper.close();
						}

					}

					
					break;
				case R.id.iv_zf_missbt3:
					NewsInfo newsInfo = newsInfoListc.get(position);
					String st = newsInfo.getMissId();
					Cursor ctype = null;
					myDbHelper.open();
					String sqltype = "select * from "
							+ MyDbInfo.getTableNames()[4] + " where serverid='"
							+ st + "'";
					ctype = myDbHelper.select(sqltype);
					ctype.moveToFirst();
					String type = ctype.getString(ctype
							.getColumnIndex("senforcementtype"));
					String county = ctype.getString(ctype
							.getColumnIndex("county"));
					if (ctype != null) {
						ctype.close();
						myDbHelper.close();
					}
					Log.e("aaaa", "=========type==========" + type);
					if (type.equals("农产品质量安全监管")) {
						Cursor czf = null;
						Cursor czf1 = null;
						myDbHelper.open();
						if (mdsList != null) {
							mdsList.clear();
							mdList.clear();
						}
						String sqlzf = "select * from "
								+ MyDbInfo.getTableNames()[4]
								+ " where serverid='" + st + "'";
						czf = myDbHelper.select(sqlzf);
						String sqlzf1 = "select * from "
								+ MyDbInfo.getTableNames()[8]
								+ " where MissionId='" + st + "'";
						czf1 = myDbHelper.select(sqlzf1);
						czf.moveToFirst();
						czf1.moveToFirst();
						int Uname = czf1.getColumnIndex("Uname");
						String name = czf1.getString(Uname);
						int Status = czf1.getColumnIndex("Status");
						String status = czf1.getString(Status);
						MissDetailed mds = new MissDetailed();
						mds.setMissStatus(status);
						mds.setMissContent(czf.getString(2));
						mds.setMissContentfile(czf.getString(13));
						mds.setMissDate(czf.getString(3));
						mds.setMissType(czf.getString(12));
						mds.setMissUser(name);
						mdsList.add(mds);
						mdList.add(mdsList);
						sfilename = czf.getString(13);
						if (czf != null || czf1 != null) {
							czf.close();
							czf1.close();
							myDbHelper.close();
						}
						myDbHelper.open();
						Cursor c = null;
						String sql = "select Id from "
								+ MyDbInfo.getTableNames()[12]
								+ " where missionId= " + st;
						c = myDbHelper.select(sql);
						c.moveToFirst();

//						if (c.getCount() == 0) {
							NCPZLZFEditActivity.startActivity(getActivity(),
									st, "", newsInfoListc.get(position)
											.getTitle(), zfPS.getUname(),
									county);
//						} else {
//							int nyid = c.getColumnIndex("Id");
//							String n = c.getString(nyid);
//							Log.e("aaaa", "==========n=========" + n);
//							NCPZLZFEditActivity.startActivity(getActivity(),
//									st, n, newsInfoListc.get(position)
//											.getTitle(), zfPS.getUname(),
//									county);
//						}
						if (c != null) {
							c.close();
							myDbHelper.close();
						}

					} else if (type.equals("放心菜基地监督考评")) {
						Cursor czf = null;
						Cursor czf1 = null;
						myDbHelper.open();
						if (mdsList != null) {
							mdsList.clear();
							mdList.clear();
						}
						String sqlzf = "select * from "
								+ MyDbInfo.getTableNames()[4]
								+ " where serverid='" + st + "'";
						czf = myDbHelper.select(sqlzf);
						String sqlzf1 = "select * from "
								+ MyDbInfo.getTableNames()[8]
								+ " where MissionId='" + st + "'";
						czf1 = myDbHelper.select(sqlzf1);
						czf.moveToFirst();
						czf1.moveToFirst();
						int Uname = czf1.getColumnIndex("Uname");
						String name = czf1.getString(Uname);
						int Status = czf1.getColumnIndex("Status");
						String status = czf1.getString(Status);
						MissDetailed mds = new MissDetailed();
						mds.setMissStatus(status);
						mds.setMissContent(czf.getString(2));
						mds.setMissContentfile(czf.getString(13));
						mds.setMissDate(czf.getString(3));
						mds.setMissType(czf.getString(12));
						mds.setMissUser(name);
						mdsList.add(mds);
						mdList.add(mdsList);
						sfilename = czf.getString(13);
						if (czf != null || czf1 != null) {
							czf.close();
							czf1.close();
							myDbHelper.close();
						}
						myDbHelper.open();
						Cursor c = null;
						String sql = "select Id from "
								+ MyDbInfo.getTableNames()[13]
								+ " where missionId= " + st;
						c = myDbHelper.select(sql);
						c.moveToFirst();

//						if (c.getCount() == 0) {
							PFKHEditActivity.startActivity(getActivity(), st,
									"", newsInfoListc.get(position).getTitle(),
									zfPS.getUname(), county);
//						} else {
//							int nyid = c.getColumnIndex("Id");
//							String n = c.getString(nyid);
//							Log.e("aaaa", "==========n=========" + n);
//							PFKHEditActivity.startActivity(getActivity(), st,
//									n, newsInfoListc.get(position).getTitle(),
//									zfPS.getUname(), county);
//						}
						if (c != null) {
							c.close();
							myDbHelper.close();
						}

					}

					break;
				case R.id.iv_up_missbt3:
					dialog(position);

					// handler.sendEmptyMessage(1);
					break;
				case R.id.child_mcontent_show:
					MissDetailed mdd1 = mdList.get(0).get(0);
					String date = mdd1.getMissDate();
					NewsInfo newsIn = newsInfoListc.get(position);
					String missId = newsIn.getMissId();
					// 下载文件
					Intent intent = new Intent();
					intent.setClass(getActivity(), DownloadConFileService.class);
					intent.putExtra("missId", missId);
					getActivity().startService(intent);
					
					String path = Environment.getExternalStorageDirectory().getPath()
							+ "/zfxc_wbx/" + sfilename;
					String[] tp=sfilename.split("\\.");
					if(tp[1].equals("jpg")||tp[1].equals("png")||tp[1].equals("pdf")){
						Intent intent1 = new Intent(getActivity(),
								SpaceImageDetailActivity.class);
						View itemView = v.findViewById(R.id.child_mcontent_show);
						File f = new File(path);
						if(sfilename==null||!f.exists()){
							Toast.makeText(getActivity(), "正在下载请稍后。。。", Toast.LENGTH_LONG)
							.show();
						}
						if (sfilename != null && !sfilename.equals("") && f.exists()) {

							BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
							Bitmap bitmap = BitmapFactory.decodeFile(path, bitmapOptions);
							SpaceImageDetailActivity.bitmap = bitmap;
							intent1.putExtra("position", 1);
							int[] location = new int[2];
							itemView.getLocationOnScreen(location);
							intent1.putExtra("locationX", location[0]);
							intent1.putExtra("locationY", location[1]);

							intent1.putExtra("width", itemView.getWidth());
							intent1.putExtra("height", itemView.getHeight());
							startActivity(intent1);
							getActivity().overridePendingTransition(0, 0);
							
						}
					}else{
						openfile();
					}
					
					break;
				case R.id.iv_zf_missbt2:
					NewsInfo newsInfounfinish = newsInfoListc.get(position);
					String stunfinish = newsInfounfinish.getMissId();
					Cursor ctypeunfinish = null;
					myDbHelper.open();
					String sqltypeunfinish = "select * from "
							+ MyDbInfo.getTableNames()[4] + " where serverid='"
							+ stunfinish + "'";
					ctypeunfinish = myDbHelper.select(sqltypeunfinish);
					ctypeunfinish.moveToFirst();
					String typeunfinish = ctypeunfinish.getString(ctypeunfinish
							.getColumnIndex("senforcementtype"));
					String countyunfinish = ctypeunfinish
							.getString(ctypeunfinish.getColumnIndex("county"));
					if (ctypeunfinish != null) {
						ctypeunfinish.close();
						myDbHelper.close();
					}
					Log.e("aaaa", "=========type==========" + typeunfinish);
					if (typeunfinish.equals("农产品质量安全监管")) {
						Cursor czf = null;
						Cursor czf1 = null;
						myDbHelper.open();
						if (mdsList != null) {
							mdsList.clear();
							mdList.clear();
						}
						String sqlzf = "select * from "
								+ MyDbInfo.getTableNames()[4]
								+ " where serverid='" + stunfinish + "'";
						czf = myDbHelper.select(sqlzf);
						String sqlzf1 = "select * from "
								+ MyDbInfo.getTableNames()[8]
								+ " where MissionId='" + stunfinish + "'";
						czf1 = myDbHelper.select(sqlzf1);
						czf.moveToFirst();
						czf1.moveToFirst();
						int Uname = czf1.getColumnIndex("Uname");
						String name = czf1.getString(Uname);
						int Status = czf1.getColumnIndex("Status");
						String status = czf1.getString(Status);
						MissDetailed mds = new MissDetailed();
						mds.setMissStatus(status);
						mds.setMissContent(czf.getString(2));
						mds.setMissContentfile(czf.getString(13));
						mds.setMissDate(czf.getString(3));
						mds.setMissType(czf.getString(12));
						mds.setMissUser(name);
						mdsList.add(mds);
						mdList.add(mdsList);
						sfilename = czf.getString(13);
						if (czf != null || czf1 != null) {
							czf.close();
							czf1.close();
							myDbHelper.close();
						}
						myDbHelper.open();
						Cursor c = null;
						String sql = "select Id from "
								+ MyDbInfo.getTableNames()[12]
								+ " where missionId= " + stunfinish;
						c = myDbHelper.select(sql);
						c.moveToFirst();

						if (c.getCount() == 0) {
							NCPZLZFEditActivity.startActivity(getActivity(),
									stunfinish, "", newsInfoListc.get(position)
											.getTitle(), zfPS.getUname(),
									countyunfinish);
						} else {
							int nyid = c.getColumnIndex("Id");
							String n = c.getString(nyid);
							Log.e("aaaa", "==========n=========" + n);
							NCPZLZFEditActivity.startActivity(getActivity(),
									stunfinish, n, newsInfoListc.get(position)
											.getTitle(), zfPS.getUname(),
									countyunfinish);
						}
						if (c != null) {
							c.close();
							myDbHelper.close();
						}

					} else if (typeunfinish.equals("放心菜基地监督考评")) {
						Cursor czf = null;
						Cursor czf1 = null;
						myDbHelper.open();
						if (mdsList != null) {
							mdsList.clear();
							mdList.clear();
						}
						String sqlzf = "select * from "
								+ MyDbInfo.getTableNames()[4]
								+ " where serverid='" + stunfinish + "'";
						czf = myDbHelper.select(sqlzf);
						String sqlzf1 = "select * from "
								+ MyDbInfo.getTableNames()[8]
								+ " where MissionId='" + stunfinish + "'";
						czf1 = myDbHelper.select(sqlzf1);
						czf.moveToFirst();
						czf1.moveToFirst();
						int Uname = czf1.getColumnIndex("Uname");
						String name = czf1.getString(Uname);
						int Status = czf1.getColumnIndex("Status");
						String status = czf1.getString(Status);
						MissDetailed mds = new MissDetailed();
						mds.setMissStatus(status);
						mds.setMissContent(czf.getString(2));
						mds.setMissContentfile(czf.getString(13));
						mds.setMissDate(czf.getString(3));
						mds.setMissType(czf.getString(12));
						mds.setMissUser(name);
						mdsList.add(mds);
						mdList.add(mdsList);
						sfilename = czf.getString(13);
						if (czf != null || czf1 != null) {
							czf.close();
							czf1.close();
							myDbHelper.close();
						}
						myDbHelper.open();
						Cursor c = null;
						String sql = "select Id from "
								+ MyDbInfo.getTableNames()[13]
								+ " where missionId= " + stunfinish;
						c = myDbHelper.select(sql);
						c.moveToFirst();

						if (c.getCount() == 0) {
							PFKHEditActivity.startActivity(getActivity(),
									stunfinish, "", newsInfoListc.get(position)
											.getTitle(), zfPS.getUname(),
									countyunfinish);
						} else {
							int nyid = c.getColumnIndex("Id");
							String n = c.getString(nyid);
							Log.e("aaaa", "==========n=========" + n);
							PFKHEditActivity.startActivity(getActivity(),
									stunfinish, n, newsInfoListc.get(position)
											.getTitle(), zfPS.getUname(),
									countyunfinish);
						}
						if (c != null) {
							c.close();
							myDbHelper.close();
						}

					}

					break;
				default:
					break;
				}
			}
		}

	}

	private void openfile() {
		// TODO Auto-generated method stub

		String path = Environment.getExternalStorageDirectory().getPath()
				+ "/zfxc_wbx/" + sfilename;
		File f = new File(path);
		if (f.exists()) {
			Intent intent = getWordFileIntent(path);
			try {
				startActivity(intent);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			Toast.makeText(getActivity(), "正在下载请稍后。。。", Toast.LENGTH_LONG)
					.show();

		}
	}

	public static Intent getWordFileIntent(String param)

	{

		Intent intent = new Intent("android.intent.action.VIEW");

		intent.addCategory("android.intent.category.DEFAULT");

		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

		Uri uri = Uri.fromFile(new File(param));

		intent.setDataAndType(uri, "application/msword");

		return intent;

	}

	private void Updata(String missId, String uname, String status) {
		HashMap<String, String> hm = new HashMap<String, String>();
		hm.put("MissionId", missId);
		hm.put("Mobilephone", uname);
		hm.put("Status", status);
		WebServiceJC.WorkJC("changeStatus", hm);// 更新任务状态
	}

	private void ncpzf(String mId) throws Exception {
		// TODO Auto-generated method stub

		int rezult = 0;
		String sql = "select * from tbncpzf where missionId=" + mId;
		myDbHelper.open();
		Cursor c = myDbHelper.select(sql);

		// String sqldel = "";
		// sqldel = "delete from tbzfsc where username='"
		// + WebServiceJC.susername + "'";
		// executeSql(sqldel);
		try {

			while (c.moveToNext()) {

				int id = c.getInt(c.getColumnIndex("Id"));
				String date = c.getString(c.getColumnIndex("ncdate"));
				String nccompany = c.getString(c.getColumnIndex("nccompany"));
				String ncmembers = c.getString(c.getColumnIndex("ncmembers"));
				String ncaddress = c.getString(c.getColumnIndex("ncaddress"));
				String ncxnresult = c.getString(c.getColumnIndex("ncxnresult"));
				String ncxnproblem = c.getString(c
						.getColumnIndex("ncxnproblem"));
				String ncdwresult = c.getString(c.getColumnIndex("ncdwresult"));
				String ncdwproblem = c.getString(c
						.getColumnIndex("ncdwproblem"));
				String ncbcresult = c.getString(c.getColumnIndex("ncbcresult"));
				String ncbcproblem = c.getString(c
						.getColumnIndex("ncbcproblem"));
				String nczyresult = c.getString(c.getColumnIndex("nczyresult"));
				String nczyproblem = c.getString(c
						.getColumnIndex("nczyproblem"));
				String ncqjresult = c.getString(c.getColumnIndex("ncqjresult"));
				String ncqjproblem = c.getString(c
						.getColumnIndex("ncqjproblem"));
				String ncwrresult = c.getString(c.getColumnIndex("ncwrresult"));
				String ncwrproblem = c.getString(c
						.getColumnIndex("ncwrproblem"));
				String ncqqresult = c.getString(c.getColumnIndex("ncqqresult"));
				String ncqqproblem = c.getString(c
						.getColumnIndex("ncqqproblem"));
				String ncglresult = c.getString(c.getColumnIndex("ncglresult"));
				String ncglproblem = c.getString(c
						.getColumnIndex("ncglproblem"));
				String ncwzresult = c.getString(c.getColumnIndex("ncwzresult"));
				String ncwzproblem = c.getString(c
						.getColumnIndex("ncwzproblem"));
				String nczqresult = c.getString(c.getColumnIndex("nczqresult"));
				String nczqproblem = c.getString(c
						.getColumnIndex("nczqproblem"));
				String ncsxresult = c.getString(c.getColumnIndex("ncsxresult"));
				String ncsxproblem = c.getString(c
						.getColumnIndex("ncsxproblem"));
				String ncdaresult = c.getString(c.getColumnIndex("ncdaresult"));
				String ncdaproblem = c.getString(c
						.getColumnIndex("ncdaproblem"));
				String nczjresult = c.getString(c.getColumnIndex("nczjresult"));
				String nczjproblem = c.getString(c
						.getColumnIndex("nczjproblem"));
				String nccdresult = c.getString(c.getColumnIndex("nccdresult"));
				String nccdproblem = c.getString(c
						.getColumnIndex("nccdproblem"));
				String nccfresult = c.getString(c.getColumnIndex("nccfresult"));
				String nccfproblem = c.getString(c
						.getColumnIndex("nccfproblem"));
				String ncssresult = c.getString(c.getColumnIndex("ncssresult"));
				String ncssproblem = c.getString(c
						.getColumnIndex("ncssproblem"));
				String ncgfresult = c.getString(c.getColumnIndex("ncgfresult"));
				String ncgfproblem = c.getString(c
						.getColumnIndex("ncgfproblem"));
				String nczfproblem = c.getString(c
						.getColumnIndex("nczfproblem"));
				String ncsjyear = c.getString(c.getColumnIndex("ncsjyear"));
				String ncsjmonth = c.getString(c.getColumnIndex("ncsjmonth"));
				String ncsjday = c.getString(c.getColumnIndex("ncsjday"));
				String ncxcyear = c.getString(c.getColumnIndex("ncxcyear"));
				String ncxcmonth = c.getString(c.getColumnIndex("ncxcmonth"));
				String ncxcday = c.getString(c.getColumnIndex("ncxcday"));
				String ncgpsx = c.getString(c.getColumnIndex("ncgpsx"));
				String ncsgpsy = c.getString(c.getColumnIndex("ncsgpsy"));
				String missionId = c.getString(c.getColumnIndex("missionId"));
				String missname = c.getString(c.getColumnIndex("missname"));
				String missperson = c.getString(c.getColumnIndex("missperson"));

				WebServiceJC.setUrl("inner", myDbHelper);
				HashMap<String, String> hm = new HashMap<String, String>();
				String sql2 = "delete xc_11_1  where missionid=" + missionId
						+ " and zfPerson='" + missperson + "'";
				hm.put("username", MyDbInfo.account);
				hm.put("password", MyDbInfo.password);
				hm.put("sql", sql2);
				WebServiceJC.setUrl("inner", myDbHelper);
				WebServiceJC.WorkJC("downloadinfo", hm);
				// String isup = c.getString(c.getColumnIndex("sisup"));

				String sql1 = "insert into xc_11_1 (missionId , missionName , zfPerson, zfDate, [gpsX] , [gpsY] , "
						+ "   [v0_1] , [v0_2] , [v0_3], [v0_4]  , [v1_1] , [v1_2] , [v1_3] , "
						+ "  [v1_4] , [v1_5] , [v1_6] , [v1_7] , [v1_8] , [v2_1] , "
						+ "   [v2_2] , [v2_3] ,[v2_4] ,[v3_1] , [v3_2] , [v3_3] , [v3_4] , [v3_5] , [v3_6] , "
						+ "   [v3_7] , [v3_8] ,[v3_9] ,[v3_10] , [v3_11] , [v3_12] , [v4_1] , [v4_2] , [v4_3] , "
						+ "   [v4_4] , [v5_1] ,[v5_2] ,[v5_3] , [v5_4] , [v5_5] , [v5_6] , [v6_1] , [v7_1] , "
						+ "   [v7_2] , [v7_3] ,[v7_4] ,[v7_5] ,[v7_6])values ('"
						+ missionId
						+ "','"
						+ missname
						+ "','"
						+ missperson
						+ "','"
						+ date
						+ "','"
						+ ncgpsx
						+ "','"
						+ ncsgpsy
						+ "','"
						+ nccompany
						+ "','"
						+ ncaddress
						+ "','"
						+ missperson
						+ " "
						+ ncmembers
						+ "','"
						+ date
						+ "','"
						+ ncxnresult
						+ "','"
						+ ncxnproblem
						+ "','"
						+ ncdwresult
						+ "','"
						+ ncdwproblem
						+ "','"
						+ ncbcresult
						+ "','"
						+ ncbcproblem
						+ "','"
						+ nczyresult
						+ "','"
						+ nczyproblem
						+ "','"
						+ ncqjresult
						+ "','"
						+ ncqjproblem
						+ "','"
						+ ncwrresult
						+ "','"
						+ ncwrproblem
						+ "','"
						+ ncqqresult
						+ "','"
						+ ncqqproblem
						+ "','"
						+ ncglresult
						+ "','"
						+ ncglproblem
						+ "','"
						+ ncwzresult
						+ "','"
						+ ncwzproblem
						+ "','"
						+ nczqresult
						+ "','"
						+ nczqproblem
						+ "','"
						+ ncsxresult
						+ "','"
						+ ncsxproblem
						+ "','"
						+ ncdaresult
						+ "','"
						+ ncdaproblem
						+ "','"
						+ nczjresult
						+ "','"
						+ nczjproblem
						+ "','"
						+ nccdresult
						+ "','"
						+ nccdproblem
						+ "','"
						+ nccfresult
						+ "','"
						+ nccfproblem
						+ "','"
						+ ncssresult
						+ "','"
						+ ncssproblem
						+ "','"
						+ ncgfresult
						+ "','"
						+ ncgfproblem
						+ "','"
						+ nczfproblem
						+ "','"
						+ ncsjyear
						+ "','"
						+ ncsjmonth
						+ "','"
						+ ncsjday
						+ "','"
						+ ncxcyear + "','" + ncxcmonth + "','" + ncxcday + "')";

				boolean flag_i = executeSql(sql1);
				// sql = "update tbnyxczf set isup=1 where id=" + id;
				//
				// myDbHelper.open();
				// myDbHelper.execSQL(sql);
				System.out.println("flag_i=" + flag_i);
				if (flag_i == true) {
					rezult = 1;
					String[] wherevalue = { mId };
					Updata(mId, zfPS.getMobilePhone(), "已完成");
					myDbHelper.open();
					myDbHelper.delete(MyDbInfo.getTableNames()[12],
							"missionId=?", wherevalue);
					myDbHelper.close();
					if (newsInfoList != null) {
						newsInfoList.clear();
						newsInfoListc.clear();
					}
					// exAdapter.notifyDataSetChanged();
					mHandler.sendEmptyMessage(3);
					handler2.sendEmptyMessage(rezult);
				} else {
					rezult = 2;
					handler2.sendEmptyMessage(rezult);
				}
			}

			if (!c.moveToFirst()) {
				rezult = 2;
			}
		} catch (Exception e) {
			e.printStackTrace();

		}
		c.close();
		myDbHelper.close();
		return;

	}

	private void pfkhzf(String mId) throws Exception {
		// TODO Auto-generated method stub

		int rezult = 0;
		myDbHelper.open();
		String sql = "select * from tbpfkhzf where missionId=" + mId;
		Cursor c = myDbHelper.select(sql);

		// String sqldel = "";
		// sqldel = "delete from tbzfsc where username='"
		// + WebServiceJC.susername + "'";
		// executeSql(sqldel);
		try {

			while (c.moveToNext()) {
				int id = c.getInt(c.getColumnIndex("Id"));
				String pfdate = c.getString(c.getColumnIndex("pfdate"));
				String pfcompany = c.getString(c.getColumnIndex("pfcompany"));
				String pfexperts = c.getString(c.getColumnIndex("pfexperts"));
				String pftotalNum = c.getString(c.getColumnIndex("pftotalNum"));
				String pftxtadress = c.getString(c
						.getColumnIndex("pftxtadress"));
				String pfzhutiscore = c.getString(c
						.getColumnIndex("pfzhutiscore"));
				String pfzhutiproblem = c.getString(c
						.getColumnIndex("pfzhutiproblem"));
				String pfleixingscore = c.getString(c
						.getColumnIndex("pfleixingscore"));
				String pfleixingproblem = c.getString(c
						.getColumnIndex("pfleixingproblem"));
				String pfguimoscore = c.getString(c
						.getColumnIndex("pfguimoscore"));
				String pfguimoproblem = c.getString(c
						.getColumnIndex("pfguimoproblem"));
				String pfmeiguanscore = c.getString(c
						.getColumnIndex("pfmeiguanscore"));
				String pfmeiguanproblem = c.getString(c
						.getColumnIndex("pfmeiguanproblem"));
				String pffangbianscore = c.getString(c
						.getColumnIndex("pffangbianscore"));
				String pffangbianproblem = c.getString(c
						.getColumnIndex("pffangbianproblem"));
				String pfnongcanscore = c.getString(c
						.getColumnIndex("pfnongcanscore"));
				String pfnongcanproblem = c.getString(c
						.getColumnIndex("pfnongcanproblem"));
				String pffeiqiwuscore = c.getString(c
						.getColumnIndex("pffeiqiwuscore"));
				String pffeiqiwuproblem = c.getString(c
						.getColumnIndex("pffeiqiwuproblem"));
				String pfchuliscore = c.getString(c
						.getColumnIndex("pfchuliscore"));
				String pfchuliproblem = c.getString(c
						.getColumnIndex("pfchuliproblem"));
				String pftongzhiscore = c.getString(c
						.getColumnIndex("pftongzhiscore"));
				String pftongzhiproblem = c.getString(c
						.getColumnIndex("pftongzhiproblem"));
				String pfjishuscore = c.getString(c
						.getColumnIndex("pfjishuscore"));
				String pfjishuproblem = c.getString(c
						.getColumnIndex("pfjishuproblem"));
				String pfkongzhiscore = c.getString(c
						.getColumnIndex("pfkongzhiscore"));
				String pfkongzhiproblem = c.getString(c
						.getColumnIndex("pfkongzhiproblem"));
				String pffushescore = c.getString(c
						.getColumnIndex("pffushescore"));
				String pffusheproblem = c.getString(c
						.getColumnIndex("pffusheproblem"));
				String pfzhengshuscore = c.getString(c
						.getColumnIndex("pfzhengshuscore"));
				String pfzhengshuproblem = c.getString(c
						.getColumnIndex("pfzhengshuproblem"));
				String pfpeixunscore = c.getString(c
						.getColumnIndex("pfpeixunscore"));
				String pfpeixunproblem = c.getString(c
						.getColumnIndex("pfpeixunproblem"));
				String pfzhiduscore = c.getString(c
						.getColumnIndex("pfzhiduscore"));
				String pfzhiduproblem = c.getString(c
						.getColumnIndex("pfzhiduproblem"));
				String pfglshoucescore = c.getString(c
						.getColumnIndex("pfglshoucescore"));
				String pfglshouceproblem = c.getString(c
						.getColumnIndex("pfglshouceproblem"));
				String pfzhanshiscore = c.getString(c
						.getColumnIndex("pfzhanshiscore"));
				String pfzhanshiproblem = c.getString(c
						.getColumnIndex("pfzhanshiproblem"));
				String pfdaozescore = c.getString(c
						.getColumnIndex("pfdaozescore"));
				String pfdaozeproblem = c.getString(c
						.getColumnIndex("pfdaozeproblem"));
				String pfnongziscore = c.getString(c
						.getColumnIndex("pfnongziscore"));
				String pfnongziproblem = c.getString(c
						.getColumnIndex("pfnongziproblem"));
				String pftianjianscore = c.getString(c
						.getColumnIndex("pftianjianscore"));
				String pftianjianproblem = c.getString(c
						.getColumnIndex("pftianjianproblem"));
				String pfwangluoscore = c.getString(c
						.getColumnIndex("pfwangluoscore"));
				String pfwangluoproblem = c.getString(c
						.getColumnIndex("pfwangluoproblem"));
				String pfshangchuanscore = c.getString(c
						.getColumnIndex("pfshangchuanscore"));
				String pfshangchuanproblem = c.getString(c
						.getColumnIndex("pfshangchuanproblem"));
				String pfzhuisuscore = c.getString(c
						.getColumnIndex("pfzhuisuscore"));
				String pfzhuisuproblem = c.getString(c
						.getColumnIndex("pfzhuisuproblem"));
				String pfbiaozhiscore = c.getString(c
						.getColumnIndex("pfbiaozhiscore"));
				String pfbiaozhiproblem = c.getString(c
						.getColumnIndex("pfbiaozhiproblem"));
				String pfwendingscore = c.getString(c
						.getColumnIndex("pfwendingscore"));
				String pfwendingproblem = c.getString(c
						.getColumnIndex("pfwendingproblem"));
				String pftongyiscore = c.getString(c
						.getColumnIndex("pftongyiscore"));
				String pftongyiproblem = c.getString(c
						.getColumnIndex("pftongyiproblem"));
				String pfjiluscore = c.getString(c
						.getColumnIndex("pfjiluscore"));
				String pfjiluproblem = c.getString(c
						.getColumnIndex("pfjiluproblem"));
				String pfshangbiaoscore = c.getString(c
						.getColumnIndex("pfshangbiaoscore"));
				String pfshangbiaoproblem = c.getString(c
						.getColumnIndex("pfshangbiaoproblem"));
				String pfdymoshiscore = c.getString(c
						.getColumnIndex("pfdymoshiscore"));
				String pfdymoshiproblem = c.getString(c
						.getColumnIndex("pfdymoshiproblem"));
				String pfbaodaoscore = c.getString(c
						.getColumnIndex("pfbaodaoscore"));
				String pfbaodaoproblem = c.getString(c
						.getColumnIndex("pfbaodaoproblem"));
				String pfgpsx = c.getString(c.getColumnIndex("pfgpsx"));
				String pfsgpsy = c.getString(c.getColumnIndex("pfsgpsy"));
				String missionId = c.getString(c.getColumnIndex("missionId"));
				String missname = c.getString(c.getColumnIndex("missname"));
				String missperson = c.getString(c.getColumnIndex("missperson"));
				String pftxtjiditotalscore = c.getString(c
						.getColumnIndex("pftxtjiditotalscore"));
				String pftxtjiditotalproblem = c.getString(c
						.getColumnIndex("pftxtjiditotalproblem"));
				String pftxthuanjingtotalscore = c.getString(c
						.getColumnIndex("pftxthuanjingtotalscore"));
				String pftxthuanjingtotalproblem = c.getString(c
						.getColumnIndex("pftxthuanjingtotalproblem"));
				String pftxtshengchantotalscore = c.getString(c
						.getColumnIndex("pftxtshengchantotalscore"));
				String pftxtshengchantotalproblem = c.getString(c
						.getColumnIndex("pftxtshengchantotalproblem"));
				String pftxtguanlitotalscore = c.getString(c
						.getColumnIndex("pftxtguanlitotalscore"));
				String pftxtguanlitotalproblem = c.getString(c
						.getColumnIndex("pftxtguanlitotalproblem"));
				String pftxtjianguantotalscore = c.getString(c
						.getColumnIndex("pftxtjianguantotalscore"));
				String pftxtjianguantotalproblem = c.getString(c
						.getColumnIndex("pftxtjianguantotalproblem"));
				String pftxtjingyingtotalscore = c.getString(c
						.getColumnIndex("pftxtjingyingtotalscore"));
				String pftxtjingyingtotalproblem = c.getString(c
						.getColumnIndex("pftxtjingyingtotalproblem"));
				String pftxtxiaoshoutotalscore = c.getString(c
						.getColumnIndex("pftxtxiaoshoutotalscore"));
				String pftxtxiaoshoutotalproblem = c.getString(c
						.getColumnIndex("pftxtxiaoshoutotalproblem"));

				WebServiceJC.setUrl("inner", myDbHelper);
				HashMap<String, String> hm = new HashMap<String, String>();
				String sql2 = "delete xc_11_3  where missionid=" + missionId
						+ " and zfPerson='" + missperson + "'";
				hm.put("username", MyDbInfo.account);
				hm.put("password", MyDbInfo.password);
				hm.put("sql", sql2);
				WebServiceJC.setUrl("inner", myDbHelper);
				WebServiceJC.WorkJC("downloadinfo", hm);
				// String isup = c.getString(c.getColumnIndex("sisup"));

				String sql1 = "insert into xc_11_3 (missionId,missionName,zfPerson,zfDate,gpsX,gpsY,"
						+ "v0_1,v0_2,v0_3,v0_4,v1_1,v1_2,v1_3,"
						+ "v1_4,v1_5,v1_6,v1_7,v1_8,v2_1,"
						+ "v2_2,v2_3,v2_4,v2_5,v2_6,v2_7,v2_8,v2_9,v2_10,v2_11,v2_12,v3_1,v3_2,v3_3,v3_4,v3_5,v3_6,"
						+ "v3_7,v3_8,v3_9,v3_10,v3_11 ,v3_12,v3_13,v3_14,v4_1,v4_2,v4_3,"
						+ "v4_4,v4_5,v4_6,v4_7,v4_8,v4_9,v4_10,v4_11,v4_12,v5_1,v5_2,v5_3,v5_4,v5_5,v5_6,v5_7,v5_8,v5_9,v5_10,v5_11,v5_12,"
						+ "v6_1,v6_2,v6_3,v6_4,v6_5,v6_6,v6_7,v6_8,v7_1,"
						+ "v7_2,v7_3,v7_4,v7_5,v7_6,v7_7,v7_8)values ('"
						+ missionId
						+ "','"
						+ missname
						+ "','"
						+ missperson
						+ "','"
						+ pfdate
						+ "','"
						+ pfgpsx
						+ "','"
						+ pfsgpsy
						+ "','"
						+ pfcompany
						+ "','"
						+ pfexperts
						+ "','"
						+ pftxtadress
						+ "','"
						+ pftotalNum
						+ "','"
						+ pftxtjiditotalscore
						+ "','"
						+ pftxtjiditotalproblem
						+ "','"
						+ pfzhutiscore
						+ "','"
						+ pfzhutiproblem
						+ "','"
						+ pfleixingscore
						+ "','"
						+ pfleixingproblem
						+ "','"
						+ pfguimoscore
						+ "','"
						+ pfguimoproblem
						+ "','"
						+ pftxthuanjingtotalscore
						+ "','"
						+ pftxthuanjingtotalproblem
						+ "','"
						+ pfmeiguanscore
						+ "','"
						+ pfmeiguanproblem
						+ "','"
						+ pffangbianscore
						+ "','"
						+ pffangbianproblem
						+ "','"
						+ pfnongcanscore
						+ "','"
						+ pfnongcanproblem
						+ "','"
						+ pffeiqiwuscore
						+ "','"
						+ pffeiqiwuproblem
						+ "','"
						+ pfchuliscore
						+ "','"
						+ pfchuliproblem
						+ "','"
						+ pftxtshengchantotalscore
						+ "','"
						+ pftxtshengchantotalproblem
						+ "','"
						+ pftongzhiscore
						+ "','"
						+ pftongzhiproblem
						+ "','"
						+ pfjishuscore
						+ "','"
						+ pfjishuproblem
						+ "','"
						+ pfkongzhiscore
						+ "','"
						+ pfkongzhiproblem
						+ "','"
						+ pffushescore
						+ "','"
						+ pffusheproblem
						+ "','"
						+ pfzhengshuscore
						+ "','"
						+ pfzhengshuproblem
						+ "','"
						+ pfpeixunscore
						+ "','"
						+ pfpeixunproblem
						+ "','"
						+ pftxtguanlitotalscore
						+ "','"
						+ pftxtguanlitotalproblem
						+ "','"
						+ pfzhiduscore
						+ "','"
						+ pfzhiduproblem
						+ "','"
						+ pfglshoucescore
						+ "','"
						+ pfglshouceproblem
						+ "','"
						+ pfzhanshiscore
						+ "','"
						+ pfzhanshiproblem
						+ "','"
						+ pfdaozescore
						+ "','"
						+ pfdaozeproblem
						+ "','"
						+ pfnongziscore
						+ "','"
						+ pfnongziproblem
						+ "','"
						+ pftxtjianguantotalscore
						+ "','"
						+ pftxtjianguantotalproblem
						+ "','"
						+ pftianjianscore
						+ "','"
						+ pftianjianproblem
						+ "','"
						+ pfwangluoscore
						+ "','"
						+ pfwangluoproblem
						+ "','"
						+ pfshangchuanscore
						+ "','"
						+ pfshangchuanproblem
						+ "','"
						+ pfzhuisuscore
						+ "','"
						+ pfzhuisuproblem
						+ "','"
						+ pfbiaozhiscore
						+ "','"
						+ pfbiaozhiproblem
						+ "','"
						+ pftxtjingyingtotalscore
						+ "','"
						+ pftxtjingyingtotalproblem
						+ "','"
						+ pfwendingscore
						+ "','"
						+ pfwendingproblem
						+ "','"
						+ pftongyiscore
						+ "','"
						+ pftongyiproblem
						+ "','"
						+ pfjiluscore
						+ "','"
						+ pfjiluproblem
						+ "','"
						+ pftxtxiaoshoutotalscore
						+ "','"
						+ pftxtxiaoshoutotalproblem
						+ "','"
						+ pfshangbiaoscore
						+ "','"
						+ pfshangbiaoproblem
						+ "','"
						+ pfdymoshiscore
						+ "','"
						+ pfdymoshiproblem
						+ "','"
						+ pfbaodaoscore + "','" + pfbaodaoproblem + "')";

				boolean flag_i = executeSql(sql1);
				// sql = "update tbnyxczf set isup=1 where id=" + id;
				//
				// myDbHelper.open();
				// myDbHelper.execSQL(sql);
				System.out.println("flag_i=" + flag_i);
				if (flag_i == true) {
					rezult = 1;
					String[] wherevalue = { mId };
					myDbHelper.open();
					myDbHelper.delete(MyDbInfo.getTableNames()[13],
							"missionId=?", wherevalue);
					myDbHelper.close();
					Updata(mId, zfPS.getMobilePhone(), "已完成");
					if (newsInfoList != null) {
						newsInfoList.clear();
						newsInfoListc.clear();
					}
					mHandler.sendEmptyMessage(3);
					handler2.sendEmptyMessage(rezult);
				} else {
					rezult = 2;
					handler2.sendEmptyMessage(rezult);
				}
			}

			if (!c.moveToFirst()) {
				rezult = 2;
			}
			if (c != null) {
				c.close();
				myDbHelper.close();
			}
		} catch (Exception e) {
			e.printStackTrace();

		}

		return;

	}

	public static boolean executeSql(String sql) {
		WebServiceJC.setNetWorkCondition();
		// 命名空间
		String nameSpace = "http://tempuri.org/";
		// 调用的方法名称
		String methodName = "rsqlStr";
		// EndPoint
		// String endPoint = "";
		// SOAP Action
		String soapAction = "http://tempuri.org/rsqlStr";

		// 指定WebService的命名空间和调用的方法名
		SoapObject rpc = new SoapObject(nameSpace, methodName);

		// 设置需调用WebService接口需要传入的两个参数mobileCode、userId
		rpc.addProperty("sqlStr", sql);
		// rpc.addProperty("userId", "");

		// 生成调用WebService方法的SOAP请求信息,并指定SOAP的版本
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER10);

		envelope.bodyOut = rpc;
		// 设置是否调用的是dotNet开发的WebService
		envelope.dotNet = true;
		// 等价于envelope.bodyOut = rpc;
		envelope.setOutputSoapObject(rpc);

		HttpTransportSE transport = new HttpTransportSE(
				WebServiceJC.SERVICE_URL);
		try {
			// 调用WebService
			transport.call(soapAction, envelope);
		} catch (Exception e) {
			e.printStackTrace();
		}

		// 获取返回的数据
		SoapObject object = (SoapObject) envelope.bodyIn;
		// 获取返回的结果
		String result = object.getProperty(0).toString();

		// 将WebService返回的结果显示在TextView中
		System.out.println("result===" + result);

		return Boolean.parseBoolean(result);
	}

	Handler handler2 = new Handler() {

		@Override
		public void handleMessage(Message msg) {

			if (msg.what == 1) {

				Toast.makeText(getActivity(), "数据上传成功", Toast.LENGTH_LONG)
						.show();
			} else if (msg.what == 2) {

				Toast.makeText(getActivity(), "数据上传失败，请检查网络", Toast.LENGTH_LONG)
						.show();
			} else if (msg.what == 3) {

				Toast.makeText(getActivity(), "数据下载成功", Toast.LENGTH_LONG)
						.show();
			} else if (msg.what == 4) {

				Toast.makeText(getActivity(), "数据下载失败，请检查网络", Toast.LENGTH_LONG)
						.show();
			}

		}
	};

	// 加载滚动数据
	private void bindNotices() {
		mFlipper.removeAllViews();
		if (newsInfoList.size() != 0) {
			for (int i = 0; i < newsInfoListc.size(); i++) {
				mFlipper.addView(getTextView("  任务名称："
						+ newsInfoListc.get(i).getTitle().toString()
						// + "  任务内容："
						// + newsInfoListc.get(i).getDetail().toString()
						+ " —— 任务时间："
						+ newsInfoListc.get(i).getDdate().toString()
						+ " —— 任务状态："
						+ newsInfoListc.get(i).getStatus().toString()));
			}
		} else {
			mFlipper.addView(getTextView("没有数据显示！"));
		}

	}

	// dialog()
	protected void dialog(final int position) {
		// TODO Auto-generated method stub
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setTitle("提示");
		builder.setMessage("确定要上传数据吗！");
		builder.setCancelable(false);
		builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				Cursor ctype = null;
				NewsInfo newsInfo1 = newsInfoListc.get(position);
				String st1 = newsInfo1.getMissId();
				Log.e("aaaaa", "==========iv_up_missbt3=============" + st1);

				myDbHelper.open();
				String sqltypesc = "select senforcementtype from "
						+ MyDbInfo.getTableNames()[4] + " where serverid='"
						+ st1 + "'";
				ctype = myDbHelper.select(sqltypesc);
				ctype.moveToFirst();
				String type1 = ctype.getString(0);
				if (ctype != null) {
					ctype.close();
					myDbHelper.close();
				}
				if (type1.equals("农产品质量安全监管")) {
					try {
						myDbHelper.open();
						Cursor c = null;
						String sql = "select * from "
								+ MyDbInfo.getTableNames()[12]
								+ " where missionId= " + st1;
						c = myDbHelper.select(sql);
						c.moveToFirst();
						// String ncId=c.getString(0);
						// Log.e("aaaa", "==================="+ncId);
						if (c.getCount() != 0) {
							ncpzf(st1);

						} else {
							Toast.makeText(getActivity(), "请先执法！",
									Toast.LENGTH_SHORT).show();
						}
						if (c != null) {
							c.close();
							myDbHelper.close();
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				} else if (type1.equals("放心菜基地监督考评")) {
					try {
						myDbHelper.open();
						Cursor c = null;
						String sql = "select Id from "
								+ MyDbInfo.getTableNames()[13]
								+ " where missionId= " + st1;
						c = myDbHelper.select(sql);
						c.moveToFirst();

						if (c.getCount() != 0) {
							pfkhzf(st1);

						} else {
							Toast.makeText(getActivity(), "请先执法！",
									Toast.LENGTH_SHORT).show();
						}
						if (c != null) {
							c.close();
							myDbHelper.close();
						}
						//
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}

		});
		builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});

		builder.create().show();
	}
	
	
	protected Animation animRotate(float toDegrees, float pivotXValue, float pivotYValue)   
    {  
        // TODO Auto-generated method stub  
        animationRotate = new RotateAnimation(0, toDegrees, Animation.RELATIVE_TO_SELF, pivotXValue, Animation.RELATIVE_TO_SELF, pivotYValue);  
        animationRotate.setAnimationListener(new AnimationListener()   
        {  
              
            @Override  
            public void onAnimationStart(Animation animation)   
            {  
                // TODO Auto-generated method stub  
                  
            }  
              
            @Override  
            public void onAnimationRepeat(Animation animation)   
            {  
                // TODO Auto-generated method stub  
                  
            }  
              
            @Override  
            public void onAnimationEnd(Animation animation)   
            {  
                // TODO Auto-generated method stub  
                animationRotate.setFillAfter(true);  
            }  
        });  
        return animationRotate;  
    }  
    //移动的动画效果          
    /*   
     * TranslateAnimation(float fromXDelta, float toXDelta, float fromYDelta, float toYDelta)   
     *   
     * float fromXDelta:这个参数表示动画开始的点离当前View X坐标上的差值；  
     *  
　　       * float toXDelta, 这个参数表示动画结束的点离当前View X坐标上的差值；  
     *  
　　       * float fromYDelta, 这个参数表示动画开始的点离当前View Y坐标上的差值；  
     *  
　　       * float toYDelta)这个参数表示动画开始的点离当前View Y坐标上的差值；  
     */  
    protected Animation animTranslate(float toX, float toY, final int lastX, final int lastY,  
            final Button button, long durationMillis)   
    {  
        // TODO Auto-generated method stub  
        animationTranslate = new TranslateAnimation(0, toX, 0, toY);                  
        animationTranslate.setAnimationListener(new AnimationListener()  
        {  
                          
            public void onAnimationStart(Animation animation)  
            {  
                // TODO Auto-generated method stub  
                                  
            }  
                          
            public void onAnimationRepeat(Animation animation)   
            {  
                // TODO Auto-generated method stub  
                              
            }  
                          
            public void onAnimationEnd(Animation animation)  
            {  
            	RelativeLayout.LayoutParams lp=new RelativeLayout.LayoutParams(0,0); 
                // TODO Auto-generated method stub  
                lp.height =70;  
                lp.width = 170;                                            
                lp.setMargins(lastX, lastY, 0, 0);  
                button.setLayoutParams(lp);  
                button.clearAnimation();  
                          
            }  
        });                                                                                               
        animationTranslate.setDuration(durationMillis);  
        return animationTranslate;  
    }  
	
	
	
	
}
