package com.okhttpdemo;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;


import com.google.gson.Gson;
import com.okhttpdemo.NetworkUtils.OkUtils;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            Toast.makeText(MainActivity.this, msg.obj.toString(), Toast.LENGTH_LONG).show();
        }
    };
    private Message m = Message.obtain();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loadNetworkDataByNewThread();
    }

    private void loadNetworkDataByNewThread() {
        OkUtils.loadDataByNewThread(Constants.RECENT_MOVIE_URL, new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                m.obj = "请求网络失败";
                handler.sendMessage(m);
            }

            @Override
            public void onResponse(Response response) throws IOException {
                String string = response.body().string();
                Gson gson = new Gson();
                MovieBean bean = gson.fromJson(string, MovieBean.class);
                m.obj = bean.getSubjects().get(0).getTitle();
                handler.sendMessage(m);
            }
        });
    }
}
