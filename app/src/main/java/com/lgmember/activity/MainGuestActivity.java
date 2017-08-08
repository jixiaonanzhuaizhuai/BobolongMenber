package com.lgmember.activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.lgmember.activity.project.ProjectMessageDetailActivity;
import com.lgmember.activity.project.ProjectMessageManageActivity;
import com.lgmember.adapter.TagsListAdapter;
import com.lgmember.bean.ProjectMessageBean;
import com.lgmember.bean.TagsListResultBean;
import com.lgmember.business.project.ProjectMessageListBusiness;
import com.lgmember.business.project.TagListBusiness;
import com.lgmember.model.ProjectMessage;
import com.lgmember.model.Tag;
import com.lgmember.util.Common;
import com.lgmember.util.DataLargeHolder;
import com.lgmember.util.StringUtil;
import com.stx.xhb.xbanner.XBanner;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import me.hwang.widgets.SmartPullableLayout;


public class MainGuestActivity extends BaseActivity implements OnClickListener,
		ProjectMessageListBusiness.ProjectMessageListResultHandler,TagListBusiness.TagListResultHandler{

    private TextView menuTxt,tv_login,tv_register,top_tv_login,top_tv_exit,moreactivity,rmoreactivity;
	private XBanner allBanner,recommendBanner;//轮播控件
	private ArrayList<String> immediatelyImages ;
	private ArrayList<String> recommendImages;
	private int pageNo = 1;
	private int pageSize = 3;

    private List<Tag> tagList ;
    private RecyclerView recyclerView;
    private TagsListAdapter adapter;

	private String TAG = "-MianActivity-";
	private SmartPullableLayout mPullableLayout;
	private static final int ON_REFRESH = 1;
	private static final int ON_LOAD_MORE = 2;

	private Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
				case ON_REFRESH:
					//adapter.notifyDataSetChanged();
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
		setContentView(R.layout.activity_main_guest);
		initView();
	}

    @Override
    protected void onResume() {
        super.onResume();
        getInitData();
    }

	private void getInitData(){
		pageNo = 1;
		tagList.clear();
		immediatelyImages.clear();
		recommendImages.clear();
		getProjectMessage();
		getHotProjectMessage();
		getTagsList();
		allBanner.startAutoPlay();
		recommendBanner.startAutoPlay();
	}

	private void initView() {
        tagList = new ArrayList<>();
		immediatelyImages = new ArrayList<>();
		recommendImages = new ArrayList<>();

		top_tv_login = (TextView)findViewById(R.id.top_tv_login);
		top_tv_login.setOnClickListener(this);
		top_tv_exit = (TextView)findViewById(R.id.top_tv_exit);
		top_tv_exit.setOnClickListener(this);

		tv_login = (TextView)findViewById(R.id.tv_login);
		tv_login.setOnClickListener(this);
		tv_register = (TextView)findViewById(R.id.tv_register);
		tv_register.setOnClickListener(this);

		menuTxt = (TextView) findViewById(R.id.menu_txt);
		moreactivity = (TextView)findViewById(R.id.moreactivity);
		rmoreactivity = (TextView)findViewById(R.id.rmoreactivity);
		allBanner = (XBanner) findViewById(R.id.allBanner);
		recommendBanner = (XBanner) findViewById(R.id.recommendBanner);
		menuTxt.setOnClickListener(this);
		moreactivity.setOnClickListener(this);
		rmoreactivity.setOnClickListener(this);
        recyclerView = (RecyclerView)findViewById(R.id.rc_tags_list);
        RecyclerView.LayoutManager layoutManager =new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new TagsListAdapter(tagList);
        recyclerView.setAdapter(adapter);

		mPullableLayout = (SmartPullableLayout)findViewById(R.id.layout_pullable);
		mPullableLayout.setOnPullListener(new SmartPullableLayout.OnPullListener() {
			@Override
			public void onPullDown() {
				new Thread(new Runnable() {
					@Override
					public void run() {
						try {
							getInitData();
							//开始自动翻页
							Thread.sleep(2000);
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
							Thread.sleep(3000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						mHandler.sendEmptyMessage(ON_LOAD_MORE);
					}
				}).start();
			}

		});
	}


	//监听处理返回键
	@Override
	public void onBackPressed() {
		startIntent(WelcomActivity.class);
	}

	//即将参与的活动
	private void getProjectMessage() {
		int tag = 0;
		ProjectMessageListBusiness projectMessageListBusiness = new ProjectMessageListBusiness(context, pageNo, pageSize,tag);
		projectMessageListBusiness.setHandler(this);
		projectMessageListBusiness.getProjectMessageAllList();

	}
	//推荐活动活动
	private void getHotProjectMessage() {
		int tag = 0;
		ProjectMessageListBusiness projectMessageListBusiness = new ProjectMessageListBusiness(context, pageNo, pageSize,tag);
		projectMessageListBusiness.setHandler(this);
		projectMessageListBusiness.getHotProjectMessage();

	}

    // 开始自动翻页
    private void getTagsList() {
        int tab = 3;
        TagListBusiness tagListBusiness = new TagListBusiness(context,tab);
        tagListBusiness.setHandler(this);
        tagListBusiness.getAllTagList();

    }

	private void initBanner(XBanner xBanner , final List<String> imgList,final List<ProjectMessage> bannerprojectMessageList) {
		//加载广告图片
		xBanner.setmAdapter(new XBanner.XBannerAdapter() {
			@Override
			public void loadBanner(XBanner banner, Object model, View view, int position) {
				Glide.with(MainGuestActivity.this).load(imgList.get(position)).into((ImageView) view);
			}
		});
		xBanner.setOnItemClickListener(new XBanner.OnItemClickListener() {
			@Override
			public void onItemClick(XBanner banner, int position) {
				ProjectMessage projectMessage =
						bannerprojectMessageList.get(position);
				DataLargeHolder.getInstance()
						.save(projectMessage.getId(),projectMessage);
				Intent intent = new
						Intent(MainGuestActivity.this,
						ProjectMessageDetailActivity.class);
				Bundle bundle = new Bundle();
				bundle.putInt("id",projectMessage.getId());
				intent.putExtras(bundle);
				startActivity(intent);
			}
		});

	}
	//这个是菜单、信息、签到、编辑、更多信息点击事件
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.menu_txt:
			showPopupMenu(v);
			break;
		case R.id.top_tv_login:
			startIntent(LoginActivity.class);
			break;
		case R.id.top_tv_exit:
			Intent intent = new
					Intent(this,WelcomActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|
					Intent.FLAG_ACTIVITY_NEW_TASK);
			startActivity(intent);
			break;
			case R.id.tv_login:
				startIntent(LoginActivity.class);
				break;
			case R.id.tv_register:
				startIntent(RegisterActivity.class);
				break;
		case R.id.moreactivity:
			Intent intent2 = new
					Intent(MainGuestActivity.this,ProjectMessageManageActivity.class);
			intent2.putExtra("tab_id",3);
			startActivity(intent2);
			break;
		case R.id.rmoreactivity:
			Intent intent1 = new
					Intent(MainGuestActivity.this,ProjectMessageManageActivity.class);
			intent1.putExtra("tab_id",2);
			startActivity(intent1);
			break;
		default:
			break;
		}
	}
	//菜单按钮中的各个子页面点击事件
	private void showPopupMenu(View view) {

        PopupMenu popup = new PopupMenu(MainGuestActivity.this, view, Gravity.CENTER_HORIZONTAL);

        popup.getMenuInflater().inflate(R.menu.main, popup.getMenu());

        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.personal:
                        startIntent(LoginActivity.class);
                        return true;
                    case R.id.activity:
                    	startIntent(LoginActivity.class);
                        return true;
                     case R.id.scores:
						startIntent(LoginActivity.class);
						return true;
					case R.id.exchange:
						startIntent(LoginActivity.class);
						return true;
					case R.id.card:
						startIntent(LoginActivity.class);
						return true;
					case R.id.sign:
						//recordPermission();
						//recordAudio();
						//recordAudio();
						startIntent(LoginActivity.class);
						return true;
					case R.id.messages:
						startIntent(LoginActivity.class);
						return true;
					case R.id.collection:
						startIntent(LoginActivity.class);
						return true;
					case R.id.question:
						startIntent(LoginActivity.class);
						return true;
					case R.id.setting:
						startIntent(LoginActivity.class);
						return true;
                }
                return false;
            }
        });

        popup.show();
    }
	// 开始自动翻页

	// 停止自动翻页
	@Override
	protected void onPause() {
		super.onPause();
		//停止翻页
		allBanner.stopAutoPlay();
		recommendBanner.stopAutoPlay();
	}

	private List<ProjectMessage> projectMessageList = new ArrayList<>();
	List<String> allTitleList = new ArrayList<>();
	@Override
	public void onSuccess(ProjectMessageBean bean) {
		projectMessageList = bean.getList();
		if (projectMessageList.size() == 0){
			allBanner.stopAutoPlay();
		}else {
			for (int i = 0; i < projectMessageList.size(); i++) {
				String pictureUrl = Common.URL_BASE + "project_img/" + projectMessageList.get(i).getPicture();
				immediatelyImages.add(pictureUrl);
				allTitleList.add(projectMessageList.get(i).getTitle());

			}
			allBanner.setData(immediatelyImages,allTitleList);
			initBanner(allBanner,immediatelyImages,projectMessageList);
		}
	}
	private List<ProjectMessage> hotProjectMessageList = new ArrayList<>();
	List<String> recommendTitleList = new ArrayList<>();
	@Override
	public void onHotSuccess(ProjectMessageBean bean) {

		hotProjectMessageList = bean.getList();
		if (projectMessageList.size() == 0){
			recommendBanner.stopAutoPlay();
		}else {
			for (int i = 0; i < hotProjectMessageList.size(); i++) {
				String pictureUrl = Common.URL_IMG_BASE + projectMessageList.get(i).getPicture();
				recommendImages.add(pictureUrl);
				recommendTitleList.add(hotProjectMessageList.get(i).getTitle());
			}
			recommendBanner.setData(recommendImages,recommendTitleList);
			initBanner(recommendBanner,recommendImages,hotProjectMessageList);
		}
	}

    @Override
    public void onTagListSuccess(TagsListResultBean tagsListResultBean) {
        tagList.addAll(tagsListResultBean.getData());
        adapter.notifyDataSetChanged();
    }
}
