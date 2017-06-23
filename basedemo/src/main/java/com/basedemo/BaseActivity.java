package com.basedemo;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

/**
 * Created by Administrator on 2017/6/23.
 */

public abstract class BaseActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(bindView());
        initView();
        doLogic();
    }


    public abstract int bindView();//绑定一个View

    public abstract void initView();//初始化所有的View
    public abstract void setListener();//设置监听

    public abstract void doLogic();//做逻辑操作

    /**
     * [页面跳转]不携带数据
     *
     * @param clz
     */
    public void startMyAc(Class<?> clz) {
        startActivity(new Intent(BaseActivity.this, clz));
    }

    /**
     * [携带数据的页面回调跳转]
     *
     * @param clz
     * @param intent 携带参数
     */
    public void startMyAc(Class<?> clz, Intent intent) {
        intent.setClass(this, clz);
        startActivity(intent);
    }

    /**
     * 携带数据并且有返回
     * @param clz
     * @param intent
     * @param requestCode
     */
    public void startMyAc(Class<?> clz, Intent intent, int requestCode) {
        intent.setClass(this, clz);
        startActivityForResult(intent, requestCode);
    }

    protected void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    /**
     * 普通Dialog
     * @param title 标题
     * @param message 内容
     * @param positive 右
     * @param negative 左
     */
    public void showNormalDialog(String title, String message, String positive, String negative) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        if (title.length() > 0) {
            builder.setTitle(title);
        }
        if (message.length() > 0) {
            builder.setMessage(message);
        }
        if (positive.length() > 0) {
            builder.setPositiveButton(positive, null);
        }
        if (negative.length() > 0) {
            builder.setNegativeButton(negative, null);
        }
        builder.create().show();
    }


    /**
     * 设置进度条对话框
     * @param context
     */
    public void showProgressDialog(Context context,String title) {
        final int MAX_PROGRESS = 100;
        final ProgressDialog progressDialog =
                new ProgressDialog(context);
        progressDialog.setProgress(0);
        progressDialog.setTitle(title);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setMax(MAX_PROGRESS);
        progressDialog.show();
    /* 模拟进度增加的过程
     * 新开一个线程，每个100ms，进度增加1
     */
        new Thread(new Runnable() {
            @Override
            public void run() {
                int progress= 0;
                while (progress < MAX_PROGRESS){
                    try {
                        Thread.sleep(100);
                        progress++;
                        progressDialog.setProgress(progress);
                    } catch (InterruptedException e){
                        e.printStackTrace();
                    }
                }
                // 进度达到最大值后，窗口消失
                progressDialog.cancel();
            }
        }).start();
    }
}
