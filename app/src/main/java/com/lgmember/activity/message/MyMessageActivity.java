package com.lgmember.activity.message;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lgmember.activity.BaseActivity;
import com.lgmember.activity.R;
import com.lgmember.adapter.MessageListAdapter;
import com.lgmember.adapter.RemindListAdapter;
import com.lgmember.bean.MessageBean;
import com.lgmember.bean.RemindListResultBean;
import com.lgmember.business.message.MessageBusiness;
import com.lgmember.business.message.RemindMessageListBusiness;
import com.lgmember.business.message.RemindNumBusiness;
import com.lgmember.model.Message;
import com.lgmember.model.Remind;
import com.lgmember.view.BadgeView;
import com.lgmember.view.TopBarView;

import java.util.ArrayList;
import java.util.List;

import me.hwang.widgets.SmartPullableLayout;


public class MyMessageActivity extends BaseActivity implements RemindNumBusiness.RemindNumResultHandler ,TopBarView.onTitleBarClickListener,MessageBusiness.MessageResultHandler,View.OnClickListener,RemindMessageListBusiness.RemindListResultHandler{

	private TopBarView topBar;
	private BadgeView badgeViewLeft,badgeViewRight;
	private ListView lv_message;
	private List<Message> messageList;
	private MessageListAdapter messageListAdapter;
	private List<Remind> remindList;
	private RemindListAdapter remindListAdapter;
	private int pageNo = 1;
	private int pageSize = 5;
	private int read_state = 3;    // 1 未读 2 已读 3 所有

	private LinearLayout ll_loading;
	private ProgressBar progressBar;
	private TextView loadDesc;

	private int total;
	private boolean isLoading;
	private RadioButton rb_left,rb_right;
	private SmartPullableLayout mPullableLayout;
	private static final int ON_REFRESH = 1;
	private static final int ON_LOAD_MORE = 2;

	private int radioTag = 0;
	private int requestCode = 0;

	private Handler mHandler = new Handler() {
		@Override
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
				case ON_REFRESH:
					if (radioTag == 0){
						getRemindData();
					}else {
						getData();
					}
					mPullableLayout.stopPullBehavior();
					break;
				case ON_LOAD_MORE:
					mPullableLayout.stopPullBehavior();
					break;
			}
		}
	};


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_message_all);
		initView();
	}

	@Override
	protected void onResume() {
		super.onResume();
		//请求数据
		getInitData();

	}
	private void getInitData() {
		messageList.clear();
		remindList.clear();
		pageNo = 1;
		ll_loading.setVisibility(View.VISIBLE);
		progressBar.setVisibility(View.VISIBLE);
		loadDesc.setText("正在拼命加载");
		getUnreadNum();
		if (radioTag == 0){
			getRemindData();
		}else {
			getData();
		}

	}

	private void getUnreadNum() {
		RemindNumBusiness remindNumBusiness = new RemindNumBusiness(this);
		remindNumBusiness.setHandler(this);
		remindNumBusiness.getRemindNum();
	}

	private void getData() {
		MessageBusiness messageBusiness = new MessageBusiness(MyMessageActivity.this, pageNo, pageSize);
		messageBusiness.setHandler(this);
		messageBusiness.getSysMessage();
	}

	/**
	 * 初始化组件
	 */
	private void initView() {

		ll_loading = (LinearLayout) findViewById(R.id.ll_loading);
		progressBar = (ProgressBar) findViewById(R.id.progressBar1);
		loadDesc = (TextView) findViewById(R.id.tv_loading_desc);

		rb_left = (RadioButton)findViewById(R.id.rb_left);
		rb_right = (RadioButton)findViewById(R.id.rb_right);
		rb_left.setOnClickListener(this);
		rb_right.setOnClickListener(this);
		badgeViewLeft = new BadgeView(MyMessageActivity.this);
		badgeViewRight = new BadgeView(MyMessageActivity.this);

		badgeViewLeft.setTargetView(rb_left);
		badgeViewRight.setTargetView(rb_right);
		badgeViewLeft.setBadgeMargin(80,5,5,5);
		badgeViewRight.setBadgeMargin(80,5,5,5);
		topBar = (TopBarView)findViewById(R.id.topbar);
		topBar.setClickListener(this);
		lv_message = (ListView)findViewById(R.id.lv_message);
		messageList = new ArrayList<>();
		messageListAdapter = new MessageListAdapter(MyMessageActivity.this,messageList);
		remindList = new ArrayList<>();
		remindListAdapter = new RemindListAdapter(MyMessageActivity.this,remindList);
		lv_message.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Intent intent;
				if (messageList.size() == 0){
					intent = new Intent(MyMessageActivity.this,RemindDetailActivity.class);
					intent.putExtra("remind",
							new Gson().toJson(remindList.get(position)));
				}else {
					intent = new Intent(MyMessageActivity.this,MessageDetailActivity.class);
					intent.putExtra("memberMsg",
							new Gson().toJson(messageList.get(position)));
				}
				//startActivity(intent);
				startActivityForResult(intent, requestCode);
			}
		});

		lv_message.setOnScrollListener(new AbsListView.OnScrollListener() {
			//滑动状态改变的时候，回调
			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
			}
			//在滑动的时候不断的回调
			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
								 int visibleItemCount, int totalItemCount) {
				if (firstVisibleItem+visibleItemCount ==
						totalItemCount&&!isLoading) {
					isLoading = true;
					if (totalItemCount< total){
						pageNo++;
						if (messageList.size() == 0){
							getRemindData();
						}else {
							getData();
						}

					}
				}
			}
		});
		mPullableLayout = (SmartPullableLayout)findViewById(R.id.layout_pullable);
		mPullableLayout.setOnPullListener(new SmartPullableLayout.OnPullListener() {
			@Override
			public void onPullDown() {
				new Thread(new Runnable() {
					@Override
					public void run() {
						try {
							Thread.sleep(1000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						mHandler.sendEmptyMessage(ON_REFRESH);
					}
				}).start();
			}

			@Override
			public void onPullUp() {
				new Thread(new Runnable() {
					@Override
					public void run() {
						try {
							Thread.sleep(1000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						mHandler.sendEmptyMessage(ON_LOAD_MORE);
					}
				}).start();
			}

		});
	}

	public void onBackClick() {
		finish();
	}

	@Override
	public void onSuccess(List<Integer> list) {
		badgeViewLeft.setBadgeCount(list.get(1));
		badgeViewRight.setBadgeCount(list.get(0));
	}
	@Override
	public void onRightClick() {
	}

	@Override
	public void onSuccess(MessageBean bean) {
		messageList.clear();
		total = bean.getTotal();
		if (total == 0){
			progressBar.setVisibility(View.GONE);
			loadDesc.setText("当前还没有数据");
		}else {
			ll_loading.setVisibility(View.GONE);
			messageList.addAll(bean.getData());
			lv_message.setAdapter(messageListAdapter);
			messageListAdapter.notifyDataSetChanged();
			isLoading = false;
		}

	}

	@Override
	public void onClick(View view) {
		switch (view.getId()){
			case R.id.rb_left:
				radioTag = 0;
				getRemindData();
				break;
			case R.id.rb_right:
				radioTag = 1;
				getData();
				break;
		}
	}

	private void getRemindData() {
		RemindMessageListBusiness messageBusiness = new
				RemindMessageListBusiness(
				MyMessageActivity.this, pageNo, pageSize,read_state);
		messageBusiness.setHandler(this);
		messageBusiness.getRemindListMessage();
	}

	@Override
	public void onSuccess(RemindListResultBean bean) {
		remindList.clear();
		total = bean.getTotal();
		if (total == 0){
			progressBar.setVisibility(View.GONE);
			loadDesc.setText("当前还没有数据");
		}else {
			ll_loading.setVisibility(View.GONE);
			remindList.addAll(bean.getData());
			lv_message.setAdapter(remindListAdapter);
			remindListAdapter.notifyDataSetChanged();
			isLoading = false;
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (resultCode){
			case 99:
				radioTag = 0;
				break;
			case 100:
				radioTag = 1;
				break;
		}

	}
}
