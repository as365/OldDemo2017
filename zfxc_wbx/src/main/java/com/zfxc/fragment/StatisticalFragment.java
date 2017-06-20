/**
 * 
 */
package com.zfxc.fragment;

import java.io.IOException;
import java.io.Serializable;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.BaiduMap.OnMapClickListener;
import com.baidu.mapapi.map.BaiduMap.OnMarkerClickListener;
import com.baidu.mapapi.map.PolygonOptions;
import com.baidu.mapapi.map.Stroke;
import com.baidu.mapapi.model.LatLng;
import com.example.zfxc_wbx.R;
import com.zfxc.adapter.TongjiAdapter;
import com.zfxc.entity.GPS;
import com.zfxc.entity.MissTongJi;
import com.zfxc.pulltorefesh.XListView;
import com.zfxc.pulltorefesh.XListView.IXListViewListener;
import com.zfxc.ui.XCZFEditActivity;
import com.zfxc.ui.ZFXCSTATActivity;
import com.zfxc.util.DataTable;
import com.zfxc.util.DatePickerFragment;
import com.zfxc.util.MyDbHelper;
import com.zfxc.util.MyDbInfo;
import com.zfxc.util.WebServiceJC;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

/**
 * @author dell
 * 
 */

public class StatisticalFragment extends Fragment implements IXListViewListener {

	private View v;
	private MapView mMapView = null;
	MyDbHelper myDbHelper;
	private Button buttonSetStartDate;
	private Button buttonSetEndDate;
	private Button btnsearch;
	private Button btnshow;
	private EditText etstartddate;
	private EditText etendddate;
	private ListView lvStatis;
	private TongjiAdapter tjAdapter;
	private List<MissTongJi> mList;
	private List<String> missId;
	private List<GPS> gpsList;
	private String person;
	BaiduMap mBaiduMap;
	private Bundle bundle;
	private int page = 0;
	private int pageSize = 10;
	private int lastPage;
	private XListView mListView;
	private int start = 0;
	private static int refreshCnt = 0;
	Handler mHandler;
//	Handler mHandler = new Handler() {
//		@Override
//		public void handleMessage(Message msg) {
//			// TODO Auto-generated method stub
//			switch (msg.what) {
//			case 1:
//				QueryMiss();
//				tjAdapter.notifyDataSetChanged();
//				onLoad();
//				break;
//
//			case 2:
//
//				break;
//			case 3:
//
//				break;
//			case -1:
//				break;
//			}
//		}
//
//	};

	@Override
	public void onStart() {
		super.onStart();
		page = 0;
		QueryMiss();
	};

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		SDKInitializer.initialize(getActivity().getApplicationContext());
		v = inflater.inflate(R.layout.activity_zfxcstat, null);
		mListView = (XListView) v.findViewById(R.id.statis_pull_refresh_list);
		mList = new ArrayList<>();
		mListView.setPullLoadEnable(true);
		initmap();
		init();
		// showMapResult();
		mListView.setXListViewListener(this);
		mListView.setOnItemClickListener(itemLisclick);
		mHandler = new Handler();
		tjAdapter = new TongjiAdapter(mList, getActivity());
		mListView.setAdapter(tjAdapter);
		return v;
	}

	private OnItemClickListener itemLisclick = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			TextView mid2 = (TextView) view.findViewById(R.id.tj_item_missId);
			String mId = mid2.getText().toString();
			getItemMap(mId);
		}
	};

	private void init() {
		// TODO Auto-generated method stub

		etstartddate = (EditText) v.findViewById(R.id.etstartddate);
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar rightNow = Calendar.getInstance();
		etendddate = (EditText) v.findViewById(R.id.etendddate);
		etendddate.setText(sf.format(rightNow.getTime()).toString());
		rightNow.add(Calendar.MONTH, -1);
		etstartddate.setText(sf.format(rightNow.getTime()).toString());
		lvStatis = (ListView) v.findViewById(R.id.statlv);
		btnshow = (Button) v.findViewById(R.id.btnshow);
		btnshow.setOnClickListener(Lisclick);
		buttonSetStartDate = (Button) v.findViewById(R.id.ButtonSetstartDate);
		buttonSetStartDate.setOnClickListener(new View.OnClickListener() {
			@SuppressLint("NewApi")
			@Override
			public void onClick(View v) {
				DatePickerFragment datePicker = new DatePickerFragment();
				datePicker.setEtdate(etstartddate);
				datePicker.setDateinitial(etstartddate.getText().toString());
				datePicker.show(getActivity().getFragmentManager(), "etdate");
				page=0;
			}
		});

		buttonSetEndDate = (Button) v.findViewById(R.id.ButtonSetEndDate);
		buttonSetEndDate.setOnClickListener(new View.OnClickListener() {
			@SuppressLint("NewApi")
			@Override
			public void onClick(View v) {
				DatePickerFragment datePicker = new DatePickerFragment();
				datePicker.setEtdate(etendddate);
				datePicker.setDateinitial(etendddate.getText().toString());
				datePicker.show(getActivity().getFragmentManager(), "etdate");
				page=0;
			}
		});

		btnsearch = (Button) v.findViewById(R.id.btnsearch);

		btnsearch.setOnClickListener(new View.OnClickListener() {
			@SuppressLint("NewApi")
			@Override
			public void onClick(View v) {
				if (mList != null) {
					mList.clear();
				}
				page = 0;
				QueryMiss();
				tjAdapter.notifyDataSetChanged();
				// showMapResult();
			}
		});

		// 获取所有记录
		myDbHelper = MyDbHelper.getInstance(getActivity());

	}

	public void showMapResult() {
		Cursor c = null;
		try {
			myDbHelper.open();
			// Cursor c = myDbHelper.select(MyDbInfo.getTableNames()[0],
			// MyDbInfo.getFieldNames()[0], null, null, null, null,
			// "TIME desc, id desc","0,9");
			String sql = "select * from " + MyDbInfo.getTableNames()[5]
					+ " where ddate>='" + etstartddate.getText().toString()
					+ "' and ddate <='" + etendddate.getText().toString() + "'";
			c = myDbHelper.select(sql);
			mBaiduMap.clear();
			while (c.moveToNext()) {
				try {

					String sgpsx = c.getString(c.getColumnIndex("sgpsx"));
					String sgpsy = c.getString(c.getColumnIndex("sgpsy"));
					String result = "合格";
					if (c.getString(c.getColumnIndex("sscjlresult"))
							.equals("否")) {
						result = "不合格";
					}
					if (c.getString(c.getColumnIndex("swzjlresult"))
							.equals("否")) {
						result = "不合格";
					}
					if (c.getString(c.getColumnIndex("strpresult")).equals("否")) {
						result = "不合格";
					}
					if (c.getString(c.getColumnIndex("snysyresult"))
							.equals("否")) {
						result = "不合格";
					}
					if (c.getString(c.getColumnIndex("sjgqresult")).equals("否")) {
						result = "不合格";
					}
					if (c.getString(c.getColumnIndex("sbzbsresult"))
							.equals("否")) {
						result = "不合格";
					}
					if (c.getString(c.getColumnIndex("sjcresult")).equals("否")) {
						result = "不合格";
					}
					if (c.getString(c.getColumnIndex("stjjresult")).equals("否")) {
						result = "不合格";
					}
					if (c.getString(c.getColumnIndex("sjdccresult"))
							.equals("否")) {
						result = "不合格";
					}
					LatLng point = new LatLng(Double.parseDouble(sgpsy),
							Double.parseDouble(sgpsx));
					Bundle bundle = new Bundle();
					bundle.putString("ddate",
							c.getString(c.getColumnIndex("ddate")));
					bundle.putString("scompanyname",
							c.getString(c.getColumnIndex("scompanyname")));
					bundle.putString("id", c.getString(c.getColumnIndex("id")));
					bundle.putString("sresult", result);
					// 构建Marker图标
					BitmapDescriptor bitmap = null;
					if (result.equals("合格")) {
						bitmap = BitmapDescriptorFactory
								.fromResource(R.drawable.maker);
					} else {
						bitmap = BitmapDescriptorFactory
								.fromResource(R.drawable.myloc);
					}

					// 构建MarkerOption，用于在地图上添加Marker
					OverlayOptions option = new MarkerOptions().position(point)
							.icon(bitmap).extraInfo(bundle);

					// 在地图上添加Marker，并显示
					mBaiduMap.addOverlay(option);
					MapStatus mMapStatus = new MapStatus.Builder()
							.target(point).zoom(13).build();
					MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory
							.newMapStatus(mMapStatus);
					// 改变地图状态
					mBaiduMap.setMapStatus(mMapStatusUpdate);
				} catch (Exception e0) {

				}
			}
		} catch (Exception e) {

		} finally {

			c.close();
			myDbHelper.close();
		}

	}

	private OnClickListener Lisclick = new OnClickListener() {

		@Override
		public void onClick(View v) {
			getMap();
		}
	};

	public void getMap() {
		mBaiduMap.clear();
		DataTable dtdb = null;
		DataTable dtjc = null;
		DataTable dtny = null;
		String dbresult = null;
		String jcresult = null;
		String nyresult = null;
		Marker marker = null;
		gpsList = new ArrayList<>();
		if (gpsList.size() != 0) {
			gpsList.clear();
		}
		for (int i = 0; i < mList.size(); i++) {
			GPS g = new GPS();
			HashMap<String, String> hm = new HashMap<String, String>();
			String susername = MyDbInfo.account;
			String spassword = MyDbInfo.password;
			WebServiceJC.susername = MyDbInfo.account;
			String sqldb = "select gpsX,gpsY from xc_11_1 where missionId='"
					+ mList.get(i).getMisId() + "' and zfPerson='" + person
					+ "'";
			hm.put("username", susername);
			hm.put("password", spassword);
			hm.put("sql", sqldb);
			WebServiceJC.setUrl("inner", myDbHelper);
			if (hm != null) {
				dbresult = WebServiceJC.WorkJC("downloadinfo", hm);
			}
			String sqljc = "select gpsX,gpsY from xc_11_3 where missionId='"
					+ mList.get(i).getMisId() + "' and zfPerson='" + person
					+ "'";
			hm.put("username", susername);
			hm.put("password", spassword);
			hm.put("sql", sqljc);
			WebServiceJC.setUrl("inner", myDbHelper);
			if (hm != null) {
				jcresult = WebServiceJC.WorkJC("downloadinfo", hm);
			}
			// String sqlny =
			// "select gpsX,gpsY from xc_wghzhjc_f2 where missionId='"
			// + mList.get(i).getMisId()+ "' and zfPerson='"+person+"'";
			// hm.put("username", susername);
			// hm.put("password", spassword);
			// hm.put("sql", sqlny);
			// WebServiceJC.setUrl("inner", myDbHelper);
			// if (hm != null) {
			// nyresult = WebServiceJC.WorkJC("downloadinfo", hm);
			// }
			try {
				dtdb = WebServiceJC.getChannelListzfxc(dbresult);
				if (dtdb.getRow().size() != 0) {
					g.setGpsx(dtdb.getRow().get(0).getStringColumn("gpsX"));
					g.setGpsy(dtdb.getRow().get(0).getStringColumn("gpsY"));
					gpsList.add(g);
				}
				dtjc = WebServiceJC.getChannelListzfxc(jcresult);
				if (dtjc.getRow().size() != 0) {
					g.setGpsx(dtjc.getRow().get(0).getStringColumn("gpsX"));
					g.setGpsy(dtjc.getRow().get(0).getStringColumn("gpsY"));
					gpsList.add(g);
				}
				// dtny = WebServiceJC.getChannelListzfxc(nyresult);
				// if(dtny.getRow().size()!=0){
				// g.setGpsx(dtny.getRow().get(0).getStringColumn("gpsX"));
				// g.setGpsy(dtny.getRow().get(0).getStringColumn("gpsY"));
				// gpsList.add(g);
				// }

			} catch (ParserConfigurationException e) {
				e.printStackTrace();
			} catch (SAXException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		for (int i = 0; i < gpsList.size(); i++) {
			Log.e("aaaa", "======================" + gpsList.get(i).getGpsx()
					+ "========" + gpsList.get(i).getGpsy());
			LatLng point = new LatLng(Double.parseDouble(gpsList.get(i)
					.getGpsy()), Double.parseDouble(gpsList.get(i).getGpsx()));
			// 构建Marker图标
			BitmapDescriptor bitmap = BitmapDescriptorFactory
					.fromResource(R.drawable.maker);
			// 构建MarkerOption，用于在地图上添加Marker
			bundle = new Bundle();
			bundle.putSerializable("info", (Serializable) mList.get(i));
			OverlayOptions option = new MarkerOptions().position(point)
					.icon(bitmap).extraInfo(bundle);
			// marker = (Marker) (mBaiduMap.addOverlay(option));

			// marker.setExtraInfo(bundle);
			// ======================116.293588========39.94921
			// 在地图上添加Marker，并显示
			mBaiduMap.addOverlay(option);
			MapStatus mMapStatus = new MapStatus.Builder().target(point)
					.zoom(12).build();
			MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory
					.newMapStatus(mMapStatus);
			// 改变地图状态
			mBaiduMap.setMapStatus(mMapStatusUpdate);
		}

	}

	public void getItemMap(String missId) {
		mBaiduMap.clear();
		DataTable dtdb = null;
		DataTable dtjc = null;
		DataTable dtny = null;
		String dbresult = null;
		String jcresult = null;
		String nyresult = null;
		Marker marker = null;
		gpsList = new ArrayList<>();
		if (gpsList.size() != 0) {
			gpsList.clear();
		}
		for (int i = 0; i < mList.size(); i++) {
			GPS g = new GPS();
			HashMap<String, String> hm = new HashMap<String, String>();
			String susername = MyDbInfo.account;
			String spassword = MyDbInfo.password;
			WebServiceJC.susername = MyDbInfo.account;
			String sqldb = "select gpsX,gpsY from xc_11_1 where missionId='"
					+ missId + "' and zfPerson='" + person + "'";
			hm.put("username", susername);
			hm.put("password", spassword);
			hm.put("sql", sqldb);
			WebServiceJC.setUrl("inner", myDbHelper);
			if (hm != null) {
				dbresult = WebServiceJC.WorkJC("downloadinfo", hm);
			}
			String sqljc = "select gpsX,gpsY from xc_11_3 where missionId='"
					+ missId + "' and zfPerson='" + person + "'";
			hm.put("username", susername);
			hm.put("password", spassword);
			hm.put("sql", sqljc);
			WebServiceJC.setUrl("inner", myDbHelper);
			if (hm != null) {
				jcresult = WebServiceJC.WorkJC("downloadinfo", hm);
			}
			// String sqlny =
			// "select gpsX,gpsY from xc_wghzhjc_f2 where missionId='"
			// + mList.get(i).getMisId()+ "' and zfPerson='"+person+"'";
			// hm.put("username", susername);
			// hm.put("password", spassword);
			// hm.put("sql", sqlny);
			// WebServiceJC.setUrl("inner", myDbHelper);
			// if (hm != null) {
			// nyresult = WebServiceJC.WorkJC("downloadinfo", hm);
			// }
			try {
				dtdb = WebServiceJC.getChannelListzfxc(dbresult);
				if (dtdb.getRow().size() != 0) {
					g.setGpsx(dtdb.getRow().get(0).getStringColumn("gpsX"));
					g.setGpsy(dtdb.getRow().get(0).getStringColumn("gpsY"));
					gpsList.add(g);
				}
				dtjc = WebServiceJC.getChannelListzfxc(jcresult);
				if (dtjc.getRow().size() != 0) {
					g.setGpsx(dtjc.getRow().get(0).getStringColumn("gpsX"));
					g.setGpsy(dtjc.getRow().get(0).getStringColumn("gpsY"));
					gpsList.add(g);
				}
				// dtny = WebServiceJC.getChannelListzfxc(nyresult);
				// if(dtny.getRow().size()!=0){
				// g.setGpsx(dtny.getRow().get(0).getStringColumn("gpsX"));
				// g.setGpsy(dtny.getRow().get(0).getStringColumn("gpsY"));
				// gpsList.add(g);
				// }

			} catch (ParserConfigurationException e) {
				e.printStackTrace();
			} catch (SAXException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		for (int i = 0; i < gpsList.size(); i++) {
			Log.e("aaaa", "======================" + gpsList.get(i).getGpsx()
					+ "========" + gpsList.get(i).getGpsy());
			LatLng point = new LatLng(Double.parseDouble(gpsList.get(i)
					.getGpsy()), Double.parseDouble(gpsList.get(i).getGpsx()));
			// 构建Marker图标
			BitmapDescriptor bitmap = BitmapDescriptorFactory
					.fromResource(R.drawable.maker);
			// 构建MarkerOption，用于在地图上添加Marker
			bundle = new Bundle();
			bundle.putSerializable("info", (Serializable) mList.get(i));
			OverlayOptions option = new MarkerOptions().position(point)
					.icon(bitmap).extraInfo(bundle);
			// marker = (Marker) (mBaiduMap.addOverlay(option));

			// marker.setExtraInfo(bundle);
			// ======================116.293588========39.94921
			// 在地图上添加Marker，并显示
			mBaiduMap.addOverlay(option);
			MapStatus mMapStatus = new MapStatus.Builder().target(point)
					.zoom(12).build();
			MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory
					.newMapStatus(mMapStatus);
			// 改变地图状态
			mBaiduMap.setMapStatus(mMapStatusUpdate);
		}
	}

	/***
	 * 查询任务
	 */
	 public void QueryMiss() {
		// if (mList != null) {
		// mList.clear();
		// }
		String result = null;
		String result1 = null;
		DataTable dt = null;
		DataTable dt1 = null;
		missId = new ArrayList<>();
		HashMap<String, String> hm = new HashMap<String, String>();
		String susername = MyDbInfo.account;
		String spassword = MyDbInfo.password;
		WebServiceJC.susername = MyDbInfo.account;
		// String sql1 =
		// "select MissionId,Uname from tbzfgrPerson where Mobilephone='"
		// + susername+ "'";
		String sql1 = "select top " + pageSize
				+ " MissionId,Uname from tbzfgrPerson "
				+ "where id NOT IN(SELECT TOP " + pageSize * page
				+ " id FROM tbzfgrPerson where 1=1 and Mobilephone='"
				+ susername + "' and Status='已完成'  order  by Id  desc) "
				+ "and Mobilephone='" + susername
				+ "' and Status='已完成'  order  by Id  desc";
		hm.put("username", susername);
		hm.put("password", spassword);
		hm.put("sql", sql1);
		WebServiceJC.setUrl("inner", myDbHelper);
		if (hm != null) {

			result = WebServiceJC.WorkJC("downloadinfo", hm);

		}
		try {
			dt1 = WebServiceJC.getChannelListzfxc(result);
			if (dt1.getRow().size() != 0) {
				person = dt1.getRow().get(0).getColumn("Uname").toString();
				for (int i = 0; i < dt1.getRow().size(); i++) {
					missId.add(dt1.getRow().get(i).getColumn("MissionId")
							.toString());
					String sql = "select * from tbmission where id='"
							+ missId.get(i)
							+ "'and sstatus ='已完成' and ddate  between '"
							+ etstartddate.getText().toString() + "' and '"
							+ etendddate.getText().toString() + "' ";
					Log.e("aaaa","=================="+etstartddate.getText().toString()+"======"+etendddate.getText().toString());
					// String
					// sql="select top 2 * from tbmission where id=( SELECT TOP 1 id FROM tbmission where id='"+missId.get(i)+"' and sstatus ='已完成' "
					// +
					// "and ddate  between '"+etstartddate.getText().toString()+"' and '"+etendddate.getText().toString()+"' order  by ddate desc )  "
					// +
					// "and sstatus ='已完成' and ddate  between '"+etstartddate.getText().toString()+"' and '"+etendddate.getText().toString()+"' order  by ddate  desc";
					hm.put("username", susername);
					hm.put("password", spassword);
					hm.put("sql", sql);
					WebServiceJC.setUrl("inner", myDbHelper);
					if (hm != null) {

						result1 = WebServiceJC.WorkJC("downloadinfo", hm);

					}
					dt = WebServiceJC.getChannelListzfxc(result1);
					MissTongJi mtj = new MissTongJi();
					for (int j = 0; j < dt.getRow().size(); j++) {
//						SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
//						Long t = null;
////						Log.e("aaaa","============"+dt.getRow().get(j).getColumn("ddate")
////								.toString()+"======");
////						
//						try {
//							t = sf.parse(dt.getRow().get(j).getColumn("ddate").toString()
//									);
//						} catch (ParseException e) {
//							e.printStackTrace();
//						}
//						String time = sf.format(t);
						String [] d=dt.getRow().get(j).getColumn("ddate").toString().split("\\ ");
						String []t=d[0].split("\\/");
						String time=t[0]+"-"+t[1]+"-"+t[2];
						mtj.setMisName(dt.getRow().get(j).getColumn("sname")
								.toString());
						mtj.setMisDate(time);
						mtj.setMisType(dt.getRow().get(j)
								.getColumn("senforcementtype").toString());
						mtj.setMisStatu(dt.getRow().get(j).getColumn("sstatus")
								.toString());
						mtj.setMisId(dt.getRow().get(j).getColumn("id")
								.toString());
						mList.add(mtj);
						// bundle = new Bundle();
						// bundle.putString("missName",mtj.getMisName());
						// bundle.putString("missType",mtj.getMisType());
						// bundle.putString("missStatu", mtj.getMisStatu());
					}

				}

			}

		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void initmap() {
		mMapView = (MapView) v.findViewById(R.id.id_bmapView);
		mBaiduMap = mMapView.getMap();
		// 开启交通图
		// mBaiduMap.setTrafficEnabled(true); open the traffic information
		// 天津左边117.210813092,39.1439299033
		LatLng point = new LatLng(39.1439299033, 117.210813092);
		// 构建Marker图标
		BitmapDescriptor bitmap = BitmapDescriptorFactory
				.fromResource(R.drawable.maker);
		// 构建MarkerOption，用于在地图上添加Marker
		OverlayOptions option = new MarkerOptions().position(point)
				.icon(bitmap);

		// 在地图上添加Marker，并显示
		mBaiduMap.addOverlay(option);
		MapStatus mMapStatus = new MapStatus.Builder().target(point).zoom(12)
				.build();
		MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory
				.newMapStatus(mMapStatus);
		// 改变地图状态
		mBaiduMap.setMapStatus(mMapStatusUpdate);
		mBaiduMap.setOnMapClickListener(new OnMapClickListener() {

			@Override
			public boolean onMapPoiClick(MapPoi arg0) {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public void onMapClick(LatLng arg0) {
				// TODO Auto-generated method stub
				// Toast.makeText(DisplayZFXCActivity.this,
				// arg0.latitude+":"+arg0.longitude, Toast.LENGTH_SHORT).show();

			}
		});

		mBaiduMap.setOnMarkerClickListener(new OnMarkerClickListener() {

			@Override
			public boolean onMarkerClick(Marker arg0) {
				// TODO Auto-generated method stub
				//
				// Toast.makeText(
				// ZFXCSTATActivity.this,
				// arg0.getPosition().latitude + "sssss:"
				// +
				// arg0.getPosition().longitude+"ddd"+arg0.getExtraInfo().getString("ddate"),
				// Toast.LENGTH_SHORT).show();
				//
				MissTongJi tj = (MissTongJi) arg0.getExtraInfo().get("info");
				AlertDialog dialog = new AlertDialog.Builder(getActivity())
						.setTitle("详细信息")
						.setMessage(
								"任务名称：" + tj.getMisName() + "\r\n任务类型："
										+ tj.getMisType() + "\r\n任务状态："
										+ tj.getMisStatu())

						.setPositiveButton("知道了！",
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int which) {

										// // 点击“确认”后的操作
										// Intent intent = new Intent(
										// getActivity(),
										// XCZFEditActivity.class);
										// intent.putExtra("id", iid);
										// startActivity(intent);
										dialog.dismiss();
									}

								}).create();
				dialog.show();
				// .setNegativeButton("返回",
				// new DialogInterface.OnClickListener() {
				//
				// @Override
				// public void onClick(DialogInterface dialog,
				// int which) {
				// // 点击“返回”后的操作,这里不设置没有任何操作
				// }
				// })

				return false;
			}
		});
		//
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		// 在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
		mMapView.onDestroy();
		mMapView = null;
	}

	@Override
	public void onResume() {
		super.onResume();
		Log.e("aaaa", "=======onResume=======");
		// 在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
		mMapView.onResume();
	}

	@Override
	public void onPause() {
		super.onPause();
		page = 0;
		Log.e("aaaa", "=======onPause=======");
		// 在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
		mMapView.onPause();
	}

	@Override
	public void onRefresh() {
		// page = page + 1;
		page = page + 1;
		if(page==1){
			mList.clear();
			page=0;
		}
		QueryMiss();
		mHandler.postDelayed(new Runnable() {
			@Override
			public void run() {
				// if (mList != null) {
				// mList.clear();
				// }
				// tjAdapter.addAll(mList);
				tjAdapter.notifyDataSetChanged();
				// tjAdapter=new TongjiAdapter(mList, getActivity());
				// mListView.setAdapter(tjAdapter);
				onLoad();
			}
		}, 2000);
//		page = page + 1;
	}

	@Override
	public void onLoadMore() {
		page = page + 1;
		if(page==1){
			mList.clear();
			page=0;
		}
		QueryMiss();
		mHandler.postDelayed(new Runnable() {
			@Override
			public void run() {
				tjAdapter.notifyDataSetChanged();
				onLoad();
			}
		}, 2000);
	}

	private void onLoad() {
		mListView.stopRefresh();
		mListView.stopLoadMore();
		mListView.setRefreshTime("刚刚");
	}

	// public boolean onCreateOptionsMenu(Menu menu) {
	// // Inflate the menu; this adds items to the action bar if it is present.
	// getMenuInflater().inflate(R.menu.zfxcstat, menu);
	// return true;
	// }

}
