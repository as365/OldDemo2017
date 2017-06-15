package com.bk;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class MainActivity extends AppCompatActivity {
    @BindView(R.id.start)
    Button start;
    @BindView(R.id.end)
    Button end;
    @BindView(R.id.tv)
    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.start, R.id.end})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.start:
                tv.setText("开始");
                break;
            case R.id.end:
                tv.setText("结束");
                break;
        }
    }

}
