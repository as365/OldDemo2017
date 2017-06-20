package com.zfxc.maputil;

import android.app.Application;
import android.os.Vibrator;
import android.widget.TextView;

import com.app.DemoApp;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.zfxc.entity.Positionet;
import com.zfxc.fragment.ZSYZFragment;

public class LocationApplication extends DemoApp {

	public LocationClient mLocationClient;// locationclient����
	public TextView mLocationresult, LogMSG;// ��ʾ���
	public Vibrator mVibrator;// ��
	public MyLocationListener mListener;// �����¼� ���ڱ����Ҷ�λ����Ϣ

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		mLocationClient = new LocationClient(this.getApplicationContext());
		mListener = new MyLocationListener();
		mLocationClient.registerLocationListener(mListener);
		// mVibrator = (Vibrator) getApplicationContext().getSystemService(
		// Service.VIBRATOR_SERVICE);

	}

	public class MyLocationListener implements BDLocationListener {

		@Override
		public void onReceiveLocation(BDLocation location) {
			// TODO Auto-generated method stub

			if (location == null) {
				return;
			}
			StringBuffer sBuffer = new StringBuffer(256);
			sBuffer.append("Time: ");
			sBuffer.append(location.getTime() + "\n");
			sBuffer.append("经度: ");
			sBuffer.append(location.getLatitude() + "\n");
			sBuffer.append("纬度: ");
			sBuffer.append(location.getLongitude() + "\n");
			sBuffer.append("详细位置信息: ");
			sBuffer.append(location.getAddrStr() + "\n");
			location.setLocType(BDLocation.TypeGpsLocation);
			// ////////////////////////////////////////////////////////////////////////
			if (location.getLocType() == BDLocation.TypeGpsLocation) {
				sBuffer.append("移动的速度: ");
				sBuffer.append(location.getSpeed());
				sBuffer.append("卫星的数目: ");
				sBuffer.append(location.getSatelliteNumber() + "\n");
				sBuffer.append("你手机的方向");
				sBuffer.append(location.getDirection());
				sBuffer.append("详细位置信息: ");

				sBuffer.append(location.getAddrStr() + "\n");
			
				

			} else if (location.getLocType() == BDLocation.TypeNetWorkLocation) {
				sBuffer.append("详细地址 : ");
				sBuffer.append(location.getAddrStr() + "\n");
				sBuffer.append("运营商信息 : ");
				sBuffer.append(location.getOperators());

			}
			
			Positionet.saddress=location.getAddrStr();
			Positionet.sgpsx=location.getLongitude()+"";
			Positionet.sgpsy=location.getLatitude()+"";
			LogMsg(sBuffer.toString());
			// Log.i("MyMapTest", sBuffer.toString());

		}

	}

	public void LogMsg(String str) {
		try {
			// mLocationresult��һ����̬��textview
			if (mLocationresult != null) {
				mLocationresult.setText(str);
			
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

}
