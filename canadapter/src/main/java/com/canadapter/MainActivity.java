package com.canadapter;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.zhy.adapter.recyclerview.wrapper.HeaderAndFooterWrapper;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private List<String> list = new ArrayList<>();
    private MyAdapter myAdapter;
    private RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = (RecyclerView) findViewById(R.id.rc);
        list=initData();//初始化数据
        myAdapter = new MyAdapter(this,R.layout.item,list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        HeaderAndFooterWrapper mHeaderAndFooterWrapper = new HeaderAndFooterWrapper(myAdapter);

        TextView t1 = new TextView(this);
        t1.setText("Header 1");
        TextView t2 = new TextView(this);
        t2.setText("Header 2");
        mHeaderAndFooterWrapper.addHeaderView(t1);
        mHeaderAndFooterWrapper.addFootView(t2);
        recyclerView.setAdapter(mHeaderAndFooterWrapper);

    }

    private List<String> initData() {
        for(int i=0;i<20;i++){
            list.add("第" + i + "个数据");
        }
        return list;
    }
}
