package com.uidemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.lljjcoder.citylist.CityListSelectActivity;
import com.lljjcoder.citylist.bean.CityInfoBean;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //首先跳转到列表页面，通过startActivityForResult实现页面跳转传值
       /* Intent intent = new Intent(MainActivity.this, CityListSelectActivity.class);
        startActivityForResult(intent, CityListSelectActivity.CITY_SELECT_RESULT_FRAG);*/
       startActivity(new Intent(MainActivity.this,Main2Activity.class));
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
                String latitude = cityInfoBean.getLongitude();
                //经度
                String longitude = cityInfoBean.getLatitude();

                //获取到城市名称，经纬度值后可自行使用...
            }
        }
    }
}
