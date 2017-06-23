package com.basedemo;

import android.widget.TextView;

public class MainActivity extends BaseActivity {
    private TextView tv;
    @Override
    public int bindView() {
        return R.layout.activity_main;
    }

    @Override
    public void initView() {
        tv = (TextView) findViewById(R.id.tv);
    }

    @Override
    public void setListener() {

    }

    @Override
    public void doLogic() {
        showProgressDialog(this);
    }
}
