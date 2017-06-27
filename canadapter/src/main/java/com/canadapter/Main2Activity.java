package com.canadapter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.canadapter.empty.EmptyAdapter;
import com.zhy.adapter.recyclerview.wrapper.EmptyWrapper;
import java.util.ArrayList;
import java.util.List;


public class Main2Activity extends AppCompatActivity {
    private List<String> list = new ArrayList<>();
    private EmptyAdapter myAdapter;
    private RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        list = initData();
        recyclerView = (RecyclerView) findViewById(R.id.rc);
        list=initData();//初始化数据
        myAdapter = new EmptyAdapter(this,R.layout.item_left,list);
        EmptyWrapper<String> emptyWrapper = new EmptyWrapper<>(myAdapter);
        emptyWrapper.setEmptyView(R.layout.empty);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(emptyWrapper);
    }

    private List<String> initData() {
        /*for(int i=0;i<20;i++){
            list.add("第" + i + "个数据");
        }*/
        return list;
    }
}
