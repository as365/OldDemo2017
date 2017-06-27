package com.canadapter;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.canadapter.item.Left;
import com.canadapter.item.Right;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;

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
        myAdapter = new MyAdapter(this,list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        myAdapter.addItemViewDelegate(new Left());
        myAdapter.addItemViewDelegate(new Right());
        recyclerView.setAdapter(myAdapter);
    }

    private List<String> initData() {
        for(int i=0;i<20;i++){
            list.add("第" + i + "个数据");
        }
        return list;
    }
}
