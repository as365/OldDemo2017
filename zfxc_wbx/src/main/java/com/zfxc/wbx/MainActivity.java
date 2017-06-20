package com.zfxc.wbx;

import java.lang.reflect.Field;
import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;

import com.example.zfxc_wbx.R;
import com.zfxc.adapter.MyViewPagerAdapter;
import com.zfxc.fragment.ActivityFragment;
import com.zfxc.fragment.FriendFragment;
import com.zfxc.fragment.PersonFragment;
import com.zfxc.fragment.StatisticalFragment;
import com.zfxc.fragment.TongjiFragment;
import com.zfxc.fragment.ZSYZFragment;
import com.zfxc.fragment.JobFragment;
import com.zfxc.fragment.XTSZFragment;
import com.zfxc.fragment.ZFSCFragment;
import com.zfxc.ui.ZFXCSTATActivity;

public class MainActivity extends FragmentActivity implements
		OnPageChangeListener {
	private ViewPager pager;
	private PagerAdapter mAdapter;

	private ArrayList<Fragment> fragments;
	private ArrayList<RadioButton> title = new ArrayList<RadioButton>();// 三个标题
	private FragmentTabHost mTabHost;

	private LayoutInflater mInflater;

	private Class mFragmentArray[] = { ActivityFragment.class,
			StatisticalFragment.class, TongjiFragment.class,
			ZFSCFragment.class, PersonFragment.class, XTSZFragment.class };

	private int mImageViewArray[] = { R.drawable.tab_activity_selector,
			R.drawable.zxjk_tab_activity_selector,
			R.drawable.zsyz_tab_activity_selector,
			R.drawable.zfsc_tab_activity_selector,
			R.drawable.xczf_tab_activity_selector,
			R.drawable.xtsz_tab_activity_selector };

	private String mTextArray[] = { "任务公告", "巡查轨迹", "统计分析", "执法依据", "人员信息",
			"系统设置" };

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);// /slidingmenu里面重写了
		// initView();// 初始化控件
		// initTitle();
		// initViewPager();
		initView();
	}

	/**
	 * 初始化视图
	 */
	// private void initView() {
	// pager = (ViewPager) findViewById(R.id.pager);// 初始化控件
	// fragments = new ArrayList<Fragment>();// 初始化数据
	// fragments.add(new ActivityFragment());
	// fragments.add(new JobFragment());
	// // fragments.add(new FriendFragment()); 在线监控
	// // fragments.add(new ZSYZFragment()); 追溯验证
	//
	// fragments.add(new StatisticalFragment());
	// fragments.add(new ZFSCFragment());
	// fragments.add(new XTSZFragment());
	// }

	// /**
	// * 初始化ViewPager
	// */
	// private void initViewPager() {
	// mAdapter = new MyViewPagerAdapter(getSupportFragmentManager(),
	// fragments);
	// pager.setAdapter(mAdapter);
	// pager.setOnPageChangeListener(this);
	// pager.setCurrentItem(0);// 设置成当前第一个
	// }
	//
	// /**
	// * 初始化几个用来显示title的RadioButton
	// */
	// private void initTitle() {
	// title.add((RadioButton) findViewById(R.id.title1));// 三个title标签
	// title.add((RadioButton) findViewById(R.id.title2));
	// title.add((RadioButton) findViewById(R.id.title3));
	// // title.add((RadioButton) findViewById(R.id.title4));
	// title.add((RadioButton) findViewById(R.id.title5));
	// title.add((RadioButton) findViewById(R.id.title6));
	// title.get(0).setOnClickListener(new MyOnClickListener(0));// 设置响应
	// title.get(1).setOnClickListener(new MyOnClickListener(1));
	// title.get(2).setOnClickListener(new MyOnClickListener(2));
	// title.get(3).setOnClickListener(new MyOnClickListener(3));
	// title.get(4).setOnClickListener(new MyOnClickListener(4));
	// // title.get(5).setOnClickListener(new MyOnClickListener(5));
	// }

	// @Override
	// public boolean onCreateOptionsMenu(Menu menu) {
	// // Inflate the menu; this adds items to the action bar if it is present.
	// getMenuInflater().inflate(R.menu.main, menu);
	// return true;
	// }

	/**
	 * 重写OnClickListener的响应函数，主要目的就是实现点击title时，pager会跟着响应切换
	 * 
	 * @author llb
	 * 
	 */
	// private class MyOnClickListener implements OnClickListener {
	// private int index;
	//
	// public MyOnClickListener(int index) {
	// this.index = index;
	// }
	//
	// @Override
	// public void onClick(View v) {
	// pager.setCurrentItem(index);// 把viewpager的视图切过去，实现捏造title跟pager的联动
	// title.get(index).setChecked(true);// 设置被选中，否则布局里面的背景不会切换
	// }
	//
	// }

	/**
	 * 下面三个是OnPageChangeListener的接口函数
	 */
	// @Override
	// public void onPageScrollStateChanged(int arg0) {
	// }
	//
	// @Override
	// public void onPageScrolled(int arg0, float arg1, int arg2) {
	// }
	//
	// @Override
	// public void onPageSelected(int arg0) {
	// Log.i("slide", "onPageSelected+agr0=" + arg0);
	// title.get(arg0).setChecked(true);// 保持页面跟按钮的联动
	// }
	private void initView() {
		mInflater = LayoutInflater.from(this);
		mTabHost = (FragmentTabHost) findViewById(R.id.activity_tabhost);
		mTabHost.setup(this, getSupportFragmentManager(),
				R.id.activity_realtabcontent);

		int count = mFragmentArray.length;
		for (int i = 0; i < count; i++) {
			TabSpec tSpec = mTabHost.newTabSpec(mTextArray[i]).setIndicator(
					getTabItemView(i));
			mTabHost.addTab(tSpec, mFragmentArray[i], null);
			mTabHost.getTabWidget().getChildAt(i)
					.setBackgroundResource(R.color.white);

		}
		mTabHost.getCurrentTab();

	}

	private View getTabItemView(int index) {

		View v = mInflater.inflate(R.layout.layout_tab_item_view, null);
		ImageView imgTab = (ImageView) v.findViewById(R.id.tab_imageview);
		TextView tvTab = (TextView) v.findViewById(R.id.tab_textview);
		imgTab.setImageResource(mImageViewArray[index]);
		tvTab.setText(mTextArray[index]);
		// Log.e("aaaaa","================"+mTextArray[index]);
		return v;
	}

	@Override
	public void onPageScrollStateChanged(int arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onPageSelected(int arg0) {
		// TODO Auto-generated method stub

	}

}
