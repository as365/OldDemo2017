package com.zfxc.ui;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BaiduMap.OnMapClickListener;
import com.baidu.mapapi.map.BaiduMap.OnMarkerClickListener;
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
import com.baidu.mapapi.model.LatLng;
import com.example.zfxc_wbx.R;
import com.zfxc.util.DatePickerFragment;
import com.zfxc.util.MyDbHelper;
import com.zfxc.util.MyDbInfo;

public class ZFXCSTATActivity extends Activity {
	private MapView mMapView = null;

	MyDbHelper myDbHelper;
	private Button buttonSetStartDate;
	private Button buttonSetEndDate;
	private Button btnsearch;
	private EditText etstartddate;
	private EditText etendddate;
	BaiduMap mBaiduMap;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		SDKInitializer.initialize(getApplicationContext());
		setContentView(R.layout.activity_zfxcstat);
		initmap();
		init();
		showMapResult();
	}

	private void init() {
		// TODO Auto-generated method stub

		etstartddate = (EditText) findViewById(R.id.etstartddate);
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar rightNow = Calendar.getInstance();
		etendddate = (EditText) findViewById(R.id.etendddate);
		etendddate.setText(sf.format(rightNow.getTime()));
		rightNow.add(Calendar.MONTH, -1);
		etstartddate.setText(sf.format(rightNow.getTime()));

		buttonSetStartDate = (Button) findViewById(R.id.ButtonSetstartDate);
		buttonSetStartDate.setOnClickListener(new View.OnClickListener() {
			@SuppressLint("NewApi")
			@Override
			public void onClick(View v) {
				DatePickerFragment datePicker = new DatePickerFragment();
				datePicker.setEtdate(etstartddate);
				datePicker.setDateinitial(etstartddate.getText().toString());
				datePicker.show(getFragmentManager(), "etdate");

			}
		});

		buttonSetEndDate = (Button) findViewById(R.id.ButtonSetEndDate);
		buttonSetEndDate.setOnClickListener(new View.OnClickListener() {
			@SuppressLint("NewApi")
			@Override
			public void onClick(View v) {
				DatePickerFragment datePicker = new DatePickerFragment();
				datePicker.setEtdate(etendddate);
				datePicker.setDateinitial(etendddate.getText().toString());
				datePicker.show(getFragmentManager(), "etdate");

			}
		});

		btnsearch = (Button) findViewById(R.id.btnsearch);

		btnsearch.setOnClickListener(new View.OnClickListener() {
			@SuppressLint("NewApi")
			@Override
			public void onClick(View v) {
				
   showMapResult();
			}
		});

		//获取所有记录
		myDbHelper = MyDbHelper.getInstance(this);

		
	}
public void showMapResult(){
	Cursor c = null;
	try {
		myDbHelper.open();
		// Cursor c = myDbHelper.select(MyDbInfo.getTableNames()[0],
		// MyDbInfo.getFieldNames()[0], null, null, null, null,
		// "TIME desc, id desc","0,9");
		String sql = "select * from " + MyDbInfo.getTableNames()[5]
				+ " where ddate>='" + etstartddate.getText().toString()+"' and ddate <='"+etendddate.getText().toString()+"'";
		c = myDbHelper.select(sql);
		mBaiduMap.clear();
		while(c.moveToNext()){
			try{
				
			
		String sgpsx=	c.getString(c.getColumnIndex("sgpsx"));
		String sgpsy=	c.getString(c.getColumnIndex("sgpsy"));
		String result="合格";
		if(c.getString(c.getColumnIndex("sscjlresult")).equals("否")){result="不合格";}
		if(c.getString(c.getColumnIndex("swzjlresult")).equals("否")){result="不合格";}
		if(c.getString(c.getColumnIndex("strpresult")).equals("否")){result="不合格";}
		if(c.getString(c.getColumnIndex("snysyresult")).equals("否")){result="不合格";}
		if(c.getString(c.getColumnIndex("sjgqresult")).equals("否")){result="不合格";}
		if(c.getString(c.getColumnIndex("sbzbsresult")).equals("否")){result="不合格";}
		if(c.getString(c.getColumnIndex("sjcresult")).equals("否")){result="不合格";}
		if(c.getString(c.getColumnIndex("stjjresult")).equals("否")){result="不合格";}
		if(c.getString(c.getColumnIndex("sjdccresult")).equals("否")){result="不合格";}
		LatLng point = new LatLng(Double.parseDouble(sgpsy) , Double.parseDouble(sgpsx) );
		Bundle bundle=new Bundle();
		bundle.putString("ddate", c.getString(c.getColumnIndex("ddate")));
		bundle.putString("scompanyname", c.getString(c.getColumnIndex("scompanyname")));
		bundle.putString("id", c.getString(c.getColumnIndex("id")));
		bundle.putString("sresult", result);
		// 构建Marker图标
		BitmapDescriptor bitmap=null;
		if(result.equals("合格")){
			 bitmap = BitmapDescriptorFactory
						.fromResource(R.drawable.maker);
		}else{
			 bitmap = BitmapDescriptorFactory
						.fromResource(R.drawable.myloc);
		}
		
		// 构建MarkerOption，用于在地图上添加Marker
		OverlayOptions option = new MarkerOptions().position(point)
				.icon(bitmap).extraInfo(bundle);

		// 在地图上添加Marker，并显示
		mBaiduMap.addOverlay(option);
		MapStatus mMapStatus = new MapStatus.Builder().target(point).zoom(13)
				.build();
		MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory
				.newMapStatus(mMapStatus);
		// 改变地图状态
		mBaiduMap.setMapStatus(mMapStatusUpdate);
}catch(Exception e0){
				
			}
		}
	}catch(Exception e){
		
		
	}finally{
		
		c.close();
		myDbHelper.close();
	}
	
	
}
	private void initmap() {
		mMapView = (MapView) findViewById(R.id.id_bmapView);
		 mBaiduMap = mMapView.getMap();
		// 开启交通图
		// mBaiduMap.setTrafficEnabled(true); open the traffic information

		LatLng point = new LatLng(23.150725, 113.257469);
		// 构建Marker图标
		BitmapDescriptor bitmap = BitmapDescriptorFactory
				.fromResource(R.drawable.maker);
		// 构建MarkerOption，用于在地图上添加Marker
		OverlayOptions option = new MarkerOptions().position(point)
				.icon(bitmap);

		// 在地图上添加Marker，并显示
		mBaiduMap.addOverlay(option);
		MapStatus mMapStatus = new MapStatus.Builder().target(point).zoom(13)
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
//				 Toast.makeText(
//				 ZFXCSTATActivity.this,
//				 arg0.getPosition().latitude + "sssss:"
//				 + arg0.getPosition().longitude+"ddd"+arg0.getExtraInfo().getString("ddate"),
//				 Toast.LENGTH_SHORT).show();
//				 
				final String iid=arg0.getExtraInfo().getString("id");
				AlertDialog dialog = new AlertDialog.Builder(
						ZFXCSTATActivity.this)
						.setTitle("查看详细").setMessage("巡检日期："+arg0.getExtraInfo().getString("ddate")+"\r\n巡检单位："+arg0.getExtraInfo().getString("scompanyname")+"\r\n巡检结果："+arg0.getExtraInfo().getString("sresult"))

						.setPositiveButton("确定",
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										
										// 点击“确认”后的操作
										Intent intent=new Intent(ZFXCSTATActivity.this, XCZFEditActivity.class);
										  intent.putExtra("id", iid);
										  startActivity(intent);

									}

								})
						.setNegativeButton("返回",
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										// 点击“返回”后的操作,这里不设置没有任何操作
									}
								}).create();
				dialog.show();
				 
				return false;
			}
		});

	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		// 在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
		mMapView.onDestroy();
		mMapView = null;
	}

	@Override
	protected void onResume() {
		super.onResume();
		// 在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
		mMapView.onResume();
	}

	@Override
	protected void onPause() {
		super.onPause();
		// 在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
		mMapView.onPause();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.zfxcstat, menu);
		return true;
	}

}
