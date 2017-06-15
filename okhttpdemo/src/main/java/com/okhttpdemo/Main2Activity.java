package com.okhttpdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;


public class Main2Activity extends AppCompatActivity {
    private int index=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        findViewById(R.id.post).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().postSticky("h"+(index++));
            }
        });
        findViewById(R.id.regist).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().register(Main2Activity.this);
            }
        });

        findViewById(R.id.unregist).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().unregister(Main2Activity.this);
            }
        });

    }

    @Subscribe(threadMode = ThreadMode.POSTING, sticky = true)
    public void onMessageEventPostThread(String s) {
        Log.i("TAG","POSTING "+ s);
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onMessageEventMainThread(String s) {
        Log.i("TAG","MAIN "+ s);
    }

    @Subscribe(threadMode = ThreadMode.BACKGROUND, sticky = true)
    public void onMessageEventBackgroundThread(String s) {
        Log.i("TAG", "BACKGROUND "+s);
    }

    @Subscribe(threadMode = ThreadMode.ASYNC, sticky = true)
    public void onMessageEventAsync(String s) {
        Log.i("TAG","ASYNC "+s);
    }

}
