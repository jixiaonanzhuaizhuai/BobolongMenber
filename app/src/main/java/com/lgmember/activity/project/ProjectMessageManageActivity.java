package com.lgmember.activity.project;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

import com.github.ikidou.fragmentBackHandler.BackHandlerHelper;
import com.lgmember.activity.R;


public class ProjectMessageManageActivity extends FragmentActivity  {
	// 定义FragmentTabHost对象
	private FragmentTabHost mTabHost;

	// 定义一个布局
	private LayoutInflater layoutInflater;

	// 定义数组来存放Fragment界面
	private Class fragmentArray[] = {
			FragmentAlreadyJoin.class, FragmentSoonJoin.class,
			FragmentHot.class, FragmentActivityList.class};

	// 定义数组来存放按钮图片
	private int mImageViewArray[] = {
			R.drawable.manage_tab_item_alreadyjoin,
			R.drawable.manage_tab_item_soonjoin,
			R.drawable.manage_tab_item_hot,
			R.drawable.manage_tab_item_activitylist};

	// Tab选项卡的文字
	private String mTextviewArray[] = { "以往报名", "即将参与", "热门活动", "活动列表" };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_manage);

		initView();
		Intent intent = getIntent();
		int tab_id = intent.getIntExtra("tab_id",99);
		mTabHost.setCurrentTab(tab_id);

	}

	/**
	 * 初始化组件
	 */
	private void initView() {
		// 实例化布局对象
		layoutInflater = LayoutInflater.from(this);

		// 实例化TabHost对象，得到TabHost
		mTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
		mTabHost.setup(ProjectMessageManageActivity.this, getSupportFragmentManager(),
				R.id.realtabcontent);

		// 得到fragment的个数
		int count = fragmentArray.length;

		for (int i = 0; i < count; i++) {
			// 为每一个Tab按钮设置图标、文字和内容
			TabHost.TabSpec tabSpec = mTabHost.newTabSpec(mTextviewArray[i])
					.setIndicator(getTabItemView(i));
			// 将Tab按钮添加进Tab选项卡中
			mTabHost.addTab(tabSpec, fragmentArray[i], null);
			// 设置Tab按钮的背景
			mTabHost.getTabWidget().getChildAt(i)
					.setBackgroundResource(R.drawable.manage_tab_item_bg);
		}
	}

	/**
	 * 给Tab按钮设置图标和文字
	 */
	private View getTabItemView(int index) {
		View view = layoutInflater.inflate(R.layout.manage_tab_item_view, null);

		ImageView imageView = (ImageView) view.findViewById(R.id.imageview);
		imageView.setImageResource(mImageViewArray[index]);

		TextView textView = (TextView) view.findViewById(R.id.textview);
		textView.setText(mTextviewArray[index]);

		return view;
	}

	/*
	* 重写返回键
	* */
	@Override
	public void onBackPressed() {
		if(!BackHandlerHelper.handleBackPress(this)){
			super.onBackPressed();
		}
	}
}
