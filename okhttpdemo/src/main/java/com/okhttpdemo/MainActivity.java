package com.okhttpdemo;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loadNetworkData();
        //loadNetworkDataByNewThread();
    }

    private void loadNetworkDataByNewThread() {
        OkUtils.loadDataByNewThread(Constants.URL, new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                Toast.makeText(MainActivity.this, "分享失败", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onResponse(Response response) throws IOException {
                Toast.makeText(MainActivity.this, "s= "+response.body().string(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void loadNetworkData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String s = OkUtils.loadStringFromUrl(Constants.URL);
                    if(s==null){

                    }else {
                        Message m = Message.obtain();
                        m.obj = s;
                        handler.sendMessage(m);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
