package com.okhttpdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.gson.Gson;
import com.okhttpdemo.NetworkUtils.OkUtils;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.IOException;

public class Main2Activity extends AppCompatActivity {
    private Button bAll;
    private Button bOne;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        EventBus.getDefault().register(this);
        bAll = (Button) findViewById(R.id.sendAll);
        bOne = (Button) findViewById(R.id.sendOne);
        bAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadNetworkDataByNewThread();
            }
        });
        bOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post("发送指定!");
            }
        });
    }

    private void loadNetworkDataByNewThread() {
        OkUtils.GET_NETWORK(Constants.RECENT_MOVIE_URL, new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                EventBus.getDefault().post("失败");
            }

            @Override
            public void onResponse(Response response) throws IOException {
                String string = response.body().string();
                Gson gson = new Gson();
                MovieBean bean = gson.fromJson(string, MovieBean.class);
                EventBus.getDefault().post(bean.getTitle());
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.POSTING)
    public void hello1(String s){
        Log.i("TAG", "hello1: POSTING2"+Thread.currentThread().getName());
        finish();
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void hello(String s){
        Log.i("TAG", "hello: MAIN2"+Thread.currentThread().getName());
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
