package com.uidemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.RadioButton;

import com.lljjcoder.citylist.CityListSelectActivity;
import com.lljjcoder.citylist.bean.CityInfoBean;


public class MainActivity extends AppCompatActivity {
    private RadioButton message;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        message = (RadioButton) findViewById(R.id.message);
        Intent intent = new Intent(MainActivity.this, CityListSelectActivity.class);
        startActivityForResult(intent, CityListSelectActivity.CITY_SELECT_RESULT_FRAG);
    }
    //接收选择器选中的结果：
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CityListSelectActivity.CITY_SELECT_RESULT_FRAG) {
            if (resultCode == RESULT_OK) {
                if (data == null) {
                    return;
                }
                Bundle bundle = data.getExtras();
                CityInfoBean cityInfoBean = (CityInfoBean) bundle.getParcelable("cityinfo");
                if (null == cityInfoBean)
                    return;
                //城市名称
                String cityName = cityInfoBean.getName();
                //纬度
                String longitude = cityInfoBean.getLongitude();
                //经度
                String latitude = cityInfoBean.getLatitude();

                //获取到城市名称，经纬度值后可自行使用...
            }
        }
    }
}
