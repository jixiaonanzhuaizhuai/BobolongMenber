package com.lgmember.activity.score;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TabHost;
import android.widget.TextView;

import com.lgmember.activity.R;
import com.lgmember.view.TopBarView;


public class MyScoresActivity extends FragmentActivity implements TopBarView.onTitleBarClickListener {

	private TopBarView topBar;

	// 定义FragmentTabHost对象
	private FragmentTabHost mTabHost;

	// 定义一个布局
	private LayoutInflater layoutInflater;

	// 定义数组来存放Fragment界面
	private Class fragmentArray[] = {
			HistoryScoresActivity.class, ScoresRuleActicity.class};

	// 定义数组来存放按钮图片
	private int mImageViewArray[] = {
			R.drawable.manage_tab_item_alreadyjoin,
			R.drawable.manage_tab_item_soonjoin};

	// Tab选项卡的文字
	private String mTextviewArray[] = { "历史积分", "积分规则"};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_myscores);
		initView();
	}

	/**
	 * 初始化组件
	 */
	private void initView() {

		topBar = (TopBarView)findViewById(R.id.topbar);
		topBar.setClickListener(this);
		// 实例化布局对象
		layoutInflater = LayoutInflater.from(this);

		// 实例化TabHost对象，得到TabHost
		mTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
		mTabHost.setup(MyScoresActivity.this, getSupportFragmentManager(),
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

	@Override
	public void onBackClick() {
		finish();
	}

	@Override
	public void onRightClick() {

	}
	}