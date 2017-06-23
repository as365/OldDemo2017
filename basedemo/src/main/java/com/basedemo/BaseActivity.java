package com.basedemo;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

/**
 * Created by Administrator on 2017/6/23.
 */

public abstract class BaseActivity extends AppCompatActivity {
    /**
     * 是否允许旋转屏幕
     **/
    private boolean isAllowScreenRoate = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (!isAllowScreenRoate) {
            //要求屏幕方向是竖屏
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }

        setScreenRoate(false);
        bindView();
        initView();
    }

    public abstract void bindView();

    public abstract void initView();

    /**
     * [页面跳转]不携带数据
     *
     * @param clz
     */
    public void startActivity(Class<?> clz) {
        startActivity(new Intent(BaseActivity.this, clz));
    }

    /**
     * [携带数据的页面回调跳转]
     *
     * @param clz
     * @param intent 携带参数
     */
    public void startActivity(Class<?> clz, Intent intent) {
        intent.setClass(this, clz);
        startActivity(intent);
    }

    /**
     * 携带数据并且有返回
     * @param clz
     * @param intent
     * @param requestCode
     */
    public void startActivity(Class<?> clz, Intent intent, int requestCode) {
        intent.setClass(this, clz);
        startActivityForResult(intent, requestCode);
    }

    /**
     * [简化Toast]
     *
     * @param msg
     */
    protected void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    /**
     * [是否允许屏幕旋转]
     *
     * @param isAllowScreenRoate
     */
    public void setScreenRoate(boolean isAllowScreenRoate) {
        this.isAllowScreenRoate = isAllowScreenRoate;
    }


    /**
     * 简单Dialog
     *
     * @param title 标题
     * @param message 内容
     * @param positive 右
     * @param negative 左
     */
    public void showSimpleDialog(String title, String message, String positive, String negative) {
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
}
