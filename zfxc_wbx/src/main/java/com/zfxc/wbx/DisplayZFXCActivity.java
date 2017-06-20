package com.zfxc.wbx;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
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
public class DisplayZFXCActivity extends Activity {  
   
	  private MapView mMapView = null;
    @Override  
    public void onCreate(Bundle savedInstanceState) {  
        super.onCreate(savedInstanceState);  
      
  
      

    	
    		requestWindowFeature(Window.FEATURE_NO_TITLE);
    		// 在使用SDK各组件之前初始化context信息，传入ApplicationContext
    		// 注意该方法要再setContentView方法之前实现
    		SDKInitializer.initialize(getApplicationContext());
    		  setContentView(R.layout.activity_display_zfxc);  
    	//	setContentView(R.layout.activity_main);
    		// 获取地图控件引用
    		mMapView = (MapView) findViewById(R.id.id_bmapView);
    BaiduMap	mBaiduMap = mMapView.getMap();  
    		//开启交通图   
    		
    		//mBaiduMap.setTrafficEnabled(true); open the traffic information
    		
    		LatLng point = new LatLng(23.150725, 113.257469);  
    		//构建Marker图标  
    		BitmapDescriptor bitmap = BitmapDescriptorFactory  
    		    .fromResource(R.drawable.maker);  
    		//构建MarkerOption，用于在地图上添加Marker  
    		OverlayOptions option = new MarkerOptions()  
    		    .position(point)  
    		    .icon(bitmap);
    		
    		//在地图上添加Marker，并显示  
    		mBaiduMap.addOverlay(option);
    		 MapStatus mMapStatus = new MapStatus.Builder()
    	        .target(point)
    	        .zoom(13)
    	        .build();
    		  MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mMapStatus);
    	        //改变地图状态
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
					//Toast.makeText(DisplayZFXCActivity.this, arg0.latitude+":"+arg0.longitude, Toast.LENGTH_SHORT).show();
					
				}
			});
    		mBaiduMap.setOnMarkerClickListener(new OnMarkerClickListener() {
				
				@Override
				public boolean onMarkerClick(Marker arg0) {
					// TODO Auto-generated method stub
					Toast.makeText(DisplayZFXCActivity.this, arg0.getPosition().latitude+"sssss:"+arg0.getPosition().longitude, Toast.LENGTH_SHORT).show();
					return false;
				}
			});
    	}

    	@Override
    	protected void onDestroy()
    	{
    		super.onDestroy();
    		// 在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
    		mMapView.onDestroy();
    		mMapView = null;
    	}

    	@Override
    	protected void onResume()
    	{
    		super.onResume();
    		// 在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
    		mMapView.onResume();
    	}

    	@Override
    	protected void onPause()
    	{
    		super.onPause();
    		// 在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
    		mMapView.onPause();
    	}

    }