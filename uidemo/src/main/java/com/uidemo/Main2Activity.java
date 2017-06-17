package com.uidemo;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.lljjcoder.citypickerview.widget.CityPicker;

public class Main2Activity extends AppCompatActivity {
    private Button button;
    private CityPicker cityPicker;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        initCityPicker(Main2Activity.this);
        initView();
    }

    private void initView() {
        button = (Button) findViewById(R.id.choose);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cityPicker.show();
            }
        });
    }

    private void initCityPicker(final Context context) {
        cityPicker = new CityPicker.Builder(context)
                .textSize(20)
                .backgroundPop(0xa0000000)
                .title("地址选择")
                .titleBackgroundColor("#d9ead3")
                .titleTextColor("#000000")
                .confirTextColor("#00ff00")
                .cancelTextColor("#ff0000")
                .province("北京市")
                .city("北京市")
                .district("海淀区")
                .textColor(Color.parseColor("#000000"))
                .provinceCyclic(true)
                .cityCyclic(false)
                .districtCyclic(false)
                .visibleItemsCount(7)
                .itemPadding(10)
                .onlyShowProvinceAndCity(false)
                .build();
        cityPicker.setOnCityItemClickListener(new CityPicker.OnCityItemClickListener() {
            @Override
            public void onSelected(String... citySelected) {
                //省份
                String province = citySelected[0];
                //城市
                String city = citySelected[1];
                //区县（如果设定了两级联动，那么该项返回空）
                String district = citySelected[2];
                //邮编
                String code = citySelected[3];
            }
            @Override
            public void onCancel() {
                Toast.makeText(context, "已取消", Toast.LENGTH_LONG).show();
            }
        });
    }
}
