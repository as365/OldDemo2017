package com.demo;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;

import com.allenliu.versionchecklib.AVersionService;
import com.allenliu.versionchecklib.HttpRequestMethod;
import com.allenliu.versionchecklib.VersionParams;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button sendMessage;
    TabLayout tabLayout;
    ViewPager viewPager;
    private List<Fragment> fragmentsList = new ArrayList<>();
    private String url = "http://123.57.166.165:8080/api/books/update";
    private String path = Environment.getExternalStorageDirectory().getAbsolutePath() +"/demo/";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sendMessage = (Button) findViewById(R.id.sendMessage);
        tabLayout = (TabLayout) findViewById(R.id.tab);
        viewPager = (ViewPager) findViewById(R.id.vp);
        String[] arrTabTitles = new String[]{"书架", "笔记"};
        Fragment f1 = new Fragment();
        Fragment f2 = new Fragment();
        fragmentsList.add(f1);
        fragmentsList.add(f2);
        MyPagerAdapter adapter = new MyPagerAdapter(getSupportFragmentManager(), fragmentsList, arrTabTitles);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        createFile(path);
        sendMessage.setOnClickListener(this);
    }

    private void createFile(String path) {
        File file = new File(path);
        if(!file.exists()){
            file.mkdirs();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.sendMessage:
                File file = new File(path);
                if(!file.exists()){
                    file.mkdirs();
                }
                VersionParams versionParams = new VersionParams().setRequestUrl(url)
                        .setDownloadAPKPath(path)
                        .setRequestMethod(HttpRequestMethod.GET)
                        .setCustomDownloadActivityClass(Main2Activity.class);
                Intent intent = new Intent(this, VersionService.class);
                intent.putExtra(AVersionService.VERSION_PARAMS_KEY, versionParams);
                startService(intent);
                break;
        }
    }
}
