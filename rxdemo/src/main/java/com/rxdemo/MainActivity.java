package com.rxdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

import static android.R.id.list;
import static android.os.Build.VERSION_CODES.M;

public class MainActivity extends AppCompatActivity {
    private TextView tv;
    private Button bt;
    private int count = 0;
    private Map<String,String> map;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv = (TextView) findViewById(R.id.tv);
        bt = (Button) findViewById(R.id.button);
        initData();
        setListener2();
    }

    private void initData() {
        map = new HashMap<>();
        map.put("name","lcj");
        map.put("pass","1234567");
    }

    /**setListener1
     * 基础使用语法
     */
    private void setListener1() {
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count++;
                Observable.create(new ObservableOnSubscribe<Integer>() {
                    @Override
                    public void subscribe(@NonNull ObservableEmitter<Integer> observableEmitter) throws Exception {
                        Thread.sleep(2000);
                        if(count<=5){
                            observableEmitter.onNext(count);
                        }else {
                            observableEmitter.onComplete();
                        }
                    }
                }).subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Consumer<Integer>() {
                            @Override
                            public void accept( Integer integer) throws Exception {
                                tv.setText(integer+"");
                            }

                        });

            }
        });
    }


    /**setListener2
     * just和flatMap(map)联合使用模拟网络请求
     */
    private void setListener2() {
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Observable.just(map)//用户名和密码的map
                        .map(new Function<Map<String,String>, Boolean>() {
                            @Override
                            public Boolean apply(@NonNull Map<String, String> map) throws Exception {
                                return checkMap(map);//执行网络请求返回是否登录成功!
                            }
                        })
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Consumer<Boolean>() {
                            @Override
                            public void accept(@NonNull Boolean aBoolean) throws Exception {
                                if(aBoolean){
                                    tv.setText("登录成功!");
                                }else {
                                    tv.setText("无效的用户名或者密码!");
                                }
                            }
                        });
            }
        });
    }
    private Boolean checkMap(Map<String, String> map) {
        if(!map.containsKey("name")||!map.containsKey("pass")){
            return false;
        }
        if(map.get("name").equals("lcj")&&map.get("pass").equals("123456")){
            return true;
        }
        return false;
    }

}
