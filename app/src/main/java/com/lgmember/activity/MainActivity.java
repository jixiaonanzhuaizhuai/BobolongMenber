package com.lgmember.activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.lgmember.AudioRecorder.AndroidAudioRecorder;
import com.lgmember.AudioRecorder.model.AudioChannel;
import com.lgmember.AudioRecorder.model.AudioSampleRate;
import com.lgmember.AudioRecorder.model.AudioSource;
import com.lgmember.activity.card.MyCardActivity;
import com.lgmember.activity.collection.CollectionActivity;
import com.lgmember.activity.feedback.ReportProblemActivity;
import com.lgmember.activity.message.MyMessageActivity;
import com.lgmember.activity.person.EditPersonalActivity;
import com.lgmember.activity.person.PersonalAllActivity;
import com.lgmember.activity.project.ProjectMessageDetailActivity;
import com.lgmember.activity.project.ProjectMessageManageActivity;
import com.lgmember.activity.score.ExchangeScoresActivity;
import com.lgmember.activity.score.MyScoresActivity;
import com.lgmember.activity.setting.SettingActivity;
import com.lgmember.adapter.TagsListAdapter;
import com.lgmember.bean.ProjectMessageBean;
import com.lgmember.bean.TagsListResultBean;
import com.lgmember.business.ApkBusiness;
import com.lgmember.business.ShowNetworkImgBusiness;
import com.lgmember.business.VersionBusiness;
import com.lgmember.business.message.MemberMessageBusiness;
import com.lgmember.business.message.RemindNumBusiness;
import com.lgmember.business.project.ProjectMessageListBusiness;
import com.lgmember.business.project.TagListBusiness;
import com.lgmember.model.Member;
import com.lgmember.model.ProjectMessage;
import com.lgmember.model.Tag;
import com.lgmember.util.ActivityCollector;
import com.lgmember.util.Common;
import com.lgmember.util.DataLargeHolder;
import com.lgmember.util.QRCodeUtil;
import com.lgmember.util.StringUtil;
import com.lgmember.view.BadgeView;
import com.stx.xhb.xbanner.XBanner;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.PermissionListener;
import com.yanzhenjie.permission.Rationale;
import com.yanzhenjie.permission.RationaleListener;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import me.hwang.widgets.SmartPullableLayout;


public class MainActivity extends BaseActivity implements OnClickListener,
		ProjectMessageListBusiness.ProjectMessageListResultHandler,
		MemberMessageBusiness.MemberMessageResulHandler,ShowNetworkImgBusiness.ShowNetworkImgResulHandler,RemindNumBusiness.RemindNumResultHandler,TagListBusiness.TagListResultHandler,VersionBusiness.VersionResulHandler,ApkBusiness.ApkResulHandler{
    private TextView moreInfo,menuTxt,sexTxt,ageTxt,nationTxt,birthdayTxt,editInfo,moreactivity,rmoreactivity,messageBtn,signBtn;
	private TextView tv_name,tv_card_no,tv_point,tv_level,tv_gender,tv_age,tv_nation,tv_birthday;
	private ImageView iv_photo;
	private XBanner xRecommendBanner,allBanner;
	private ArrayList<String> allImages ;
	private ArrayList<String> recommendImages;
    private boolean isButton = true;
	private String gender,nation;
	private String birthday;
	private int age;
	private int unReadNum;
	private int pageNo = 1;
	private int pageSize = 3;
	private BadgeView badgeView;
	private String currVersion;

    private String cardNum;
	private AlertDialog dialog;

	private ProgressDialog progressDialog;

	private SmartPullableLayout mPullableLayout;

	private static final int REQUEST_CODE_RECORD_AUDIO = 100;
	private static final int REQUEST_CODE_SETTING = 300;

	//录音签到
	private static final int REQUEST_RECORD_AUDIO = 0;
	private  String AUDIO_FILE_PATH = Environment.getExternalStorageDirectory() + "/recorded_audio.wav";
	private String TAG = "-MianActivity-";
	private SharedPreferences sp;
	private boolean if_auto_start;

	private List<Tag> tagList ;
	private RecyclerView recyclerView;
	private TagsListAdapter adapter;


	private static final int ON_REFRESH = 1;
	private static final int ON_LOAD_MORE = 2;

	private String oldStartTime;

	Message message;

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
				case 1000 :
					progressDialog.setProgress(msg.arg1);
					break;
			}
		}
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main1);
		initView();
	}
	@Override
	protected void onResume() {
		super.onResume();
		getInitData();

	}
	private void getInitData(){
		//录音
		sp = this.getSharedPreferences(Common.SP_NAME, MODE_PRIVATE);
		if_auto_start = sp.getBoolean(Common.SP_IF_RECORDER,true);
		versionNoUpdateTime();
		pageNo = 1;
		tagList.clear();
		allImages.clear();
		recommendImages.clear();
		getUnreadRemindNum();
		getMemberMsg();
		getProjectMessage();
		getHotProjectMessage();
		getTagsList();
		allBanner.startAutoPlay();
		xRecommendBanner.startAutoPlay();
}

	/*版本暂不更新，一天提示一次*/
	private void versionNoUpdateTime() {
		Date nowDate = new Date();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String nowTime = format.format(nowDate);
		oldStartTime = sp.getString(Common.SP_VERSION_UPDATE_TIME,"2008-08-08 18:28:28");
		int days = 0;
		try {
			Date nowDate1 = format.parse(nowTime);
			Date oldDate1 = format.parse(oldStartTime);
			days = StringUtil.differentDaysByMillisecond(oldDate1,nowDate1);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		if (days >= 1){
			//判断是否需要更新版本,获取服务器上的版本号
			getServiceVersion();
		}
	}
	private void initBanner(XBanner xBanner , final List<String> imgList,final List<ProjectMessage> bannerprojectMessageList) {
		//加载广告图片
		xBanner.setmAdapter(new XBanner.XBannerAdapter() {
			@Override
			public void loadBanner(XBanner banner, Object model, View view, int position) {
				Glide.with(MainActivity.this).load(imgList.get(position)).into((ImageView) view);
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
						Intent(MainActivity.this,
						ProjectMessageDetailActivity.class);
				Bundle bundle = new Bundle();
				bundle.putInt("id",projectMessage.getId());
				intent.putExtras(bundle);
				startActivity(intent);
			}
		});

	}

	private void getServiceVersion() {
		VersionBusiness versionBusiness = new VersionBusiness(context);
		versionBusiness.setHandler(this);
		versionBusiness.getVersion();
	}

	private void initView() {
		progressDialog = new ProgressDialog(this);
		progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
		progressDialog.setCancelable(true);
		progressDialog.setCanceledOnTouchOutside(false);
		progressDialog.setTitle("更新进度提示");

		tagList = new ArrayList<>();
		//获取标签列表数据
		allImages = new ArrayList<>();
		recommendImages = new ArrayList<>();
		iv_photo = (ImageView)findViewById(R.id.touxiang);
        iv_photo.setOnClickListener(this);
		tv_name = (TextView)findViewById(R.id.tv_name);
		tv_card_no = (TextView)findViewById(R.id.tv_card_no);
		tv_point = (TextView)findViewById(R.id.tv_point);
		tv_level = (TextView)findViewById(R.id.tv_level);
		tv_gender = (TextView)findViewById(R.id.tv_gender);
		tv_age = (TextView)findViewById(R.id.tv_age);
		tv_nation = (TextView)findViewById(R.id.tv_nation);
		tv_birthday = (TextView)findViewById(R.id.tv_birthday);
		menuTxt = (TextView) findViewById(R.id.menu_txt);
		messageBtn = (TextView) findViewById(R.id.messageBtn);
		signBtn = (TextView) findViewById(R.id.signBtn);
		moreInfo = (TextView) findViewById(R.id.moreInfo);
		sexTxt = (TextView)findViewById(R.id.sexTxt);
		ageTxt = (TextView)findViewById(R.id.ageTxt);
		nationTxt = (TextView)findViewById(R.id.nationTxt);
		birthdayTxt = (TextView)findViewById(R.id.birthdayTxt);
		editInfo = (TextView)findViewById(R.id.editInfo);
		moreactivity = (TextView)findViewById(R.id.moreactivity);
		rmoreactivity = (TextView)findViewById(R.id.rmoreactivity);
		allBanner = (XBanner) findViewById(R.id.allBanner);
		xRecommendBanner = (XBanner) findViewById(R.id.recommendBanner);
		menuTxt.setOnClickListener(this);
		messageBtn.setOnClickListener(this);
		signBtn.setOnClickListener(this);
		editInfo.setOnClickListener(this);
		moreInfo.setOnClickListener(this);
		moreactivity.setOnClickListener(this);
		rmoreactivity.setOnClickListener(this);

		badgeView = new BadgeView(this);
		badgeView.setTargetView(messageBtn);
		badgeView.setBadgeCount(unReadNum);
		badgeView.setBadgeMargin(5,5,5,5);

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

	public void recordAudio() {
		AndroidAudioRecorder.with(this)
				// Required
				.setSampleRate(AudioSampleRate.HZ_8000)
				.setFilePath(AUDIO_FILE_PATH)
				.setRequestCode(REQUEST_RECORD_AUDIO)
				// Optional
				.setSource(AudioSource.MIC)
				.setChannel(AudioChannel.MONO)
				.setAutoStart(if_auto_start)
				// Start recording
				.record();

	}

	private void getMemberMsg() {

		MemberMessageBusiness memberMessage = new MemberMessageBusiness(context);
		memberMessage.setHandler(this);
		memberMessage.getMemberMessage();
	}
	//获取未读消息的数目
	private void getUnreadRemindNum() {
		RemindNumBusiness remindNumBusiness = new RemindNumBusiness(this);
		remindNumBusiness.setHandler(this);
		remindNumBusiness.getRemindNum();
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
	//这个是菜单、信息、签到、编辑、更多信息点击事件
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.menu_txt:
			showPopupMenu(v);
			break;
		case R.id.messageBtn:
			startIntent(MyMessageActivity.class);
			break;
		case R.id.signBtn:
			recordPermission();
			//startIntent(SignActivity.class);
			break;
		case R.id.editInfo:
			startIntent(EditPersonalActivity.class);
			break;
		case R.id.moreactivity:
			Intent intent = new
					Intent(MainActivity.this,ProjectMessageManageActivity.class);
			intent.putExtra("tab_id",3);
			startActivity(intent);
			break;
		case R.id.rmoreactivity:
			Intent intent1 = new
					Intent(MainActivity.this,ProjectMessageManageActivity.class);
			intent1.putExtra("tab_id",2);
			startActivity(intent1);
			break;
	case R.id.moreInfo:
            if(isButton){
                sexTxt.setVisibility(View.VISIBLE);
				tv_gender.setVisibility(View.VISIBLE);
				tv_gender.setText(gender);
                ageTxt.setVisibility(View.VISIBLE);
				tv_age.setVisibility(View.VISIBLE);
				tv_age.setText(age+"");
                nationTxt.setVisibility(View.VISIBLE);
				tv_nation.setVisibility(View.VISIBLE);
				tv_nation.setText(nation);
                birthdayTxt.setVisibility(View.VISIBLE);
				tv_birthday.setVisibility(View.VISIBLE);
				tv_birthday.setText(birthday+"");
                moreInfo.setText("隐藏信息>>");
                isButton = false;
            }else {
                sexTxt.setVisibility(View.GONE);
				tv_gender.setVisibility(View.GONE);
                ageTxt.setVisibility(View.GONE);
				tv_age.setVisibility(View.GONE);
                nationTxt.setVisibility(View.GONE);
				tv_nation.setVisibility(View.GONE);
                birthdayTxt.setVisibility(View.GONE);
				tv_birthday.setVisibility(View.GONE);
                moreInfo.setText("更多信息>>");
                isButton = true;
            }
			break;
        case R.id.touxiang:
          final String filePath = Environment.getExternalStorageDirectory() + "/memberCard.jpg";
            //二维码图片较大时，生成图片、保存文件的时间可能较长，因此放在新线程中
            new Thread(new Runnable() {
                @Override
                public void run() {
                    boolean success = QRCodeUtil.createQRImage(cardNum, 800, 800,
                            true ? BitmapFactory.decodeResource(getResources(), R.mipmap.logo) : null,
                            filePath);

                    if (success) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                showMemberCard(filePath);
                            }
                        });
                    }
                }
            }).start();
            break;
		default:
			break;
		}
	}

	public void showMemberCard(String filePath) {
		AlertDialog.Builder adb = new AlertDialog.Builder(this);
		dialog = adb.create();
		View view = getLayoutInflater().inflate(R.layout.dialog_el_member_card, null);
		ImageView iv_member_card = (ImageView)view.findViewById(R.id.iv_member_card);
		iv_member_card.setImageBitmap(BitmapFactory.decodeFile(filePath));

		dialog.setView(view);
		dialog.show();
	}

	//菜单按钮中的各个子页面点击事件
	private void showPopupMenu(View view) {

        PopupMenu popup = new PopupMenu(MainActivity.this, view, Gravity.CENTER_HORIZONTAL);

        popup.getMenuInflater().inflate(R.menu.main, popup.getMenu());

        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.personal:
                        startIntent(PersonalAllActivity.class);
                        return true;
                    case R.id.activity:
                    	startIntent(ProjectMessageManageActivity.class);
                        return true;
                     case R.id.scores:
						startIntent(MyScoresActivity.class);
						return true;
					case R.id.exchange:
						startIntent(ExchangeScoresActivity.class);
						return true;
					case R.id.card:
						startIntent(MyCardActivity.class);
						return true;
					case R.id.sign:
						recordPermission();
						//recordAudio();
						//recordAudio();
						//startIntent(SignActivity.class);
						return true;
					case R.id.messages:
						startIntent(MyMessageActivity.class);
						return true;
					case R.id.collection:
						startIntent(CollectionActivity.class);
						return true;
					case R.id.question:
						startIntent(ReportProblemActivity.class);
						return true;
					case R.id.setting:
						startIntent(SettingActivity.class);
						return true;
                }
                return false;
            }
        });

        popup.show();
    }
	// 开始自动翻页
	private void getTagsList() {
		int tab = 3;
		TagListBusiness tagListBusiness = new TagListBusiness(context,tab);
		tagListBusiness.setHandler(this);
		tagListBusiness.getAllTagList();

	}

	// 停止自动翻页
	@Override
	protected void onPause() {

		super.onPause();
		//停止翻页
		allBanner.stopAutoPlay();
		xRecommendBanner.stopAutoPlay();
	}

	private List<ProjectMessage> projectMessageList = new ArrayList<>();
	List<String> allTitleList = new ArrayList<>();
	@Override
	public void onSuccess(ProjectMessageBean bean) {
		projectMessageList = bean.getList();
		if (projectMessageList.size() == 0){
			allBanner.stopAutoPlay();
		}else {
			for (int i=0;i<projectMessageList.size();i++){
				String pictureUrl = Common.URL_IMG_BASE+projectMessageList.get(i).getPicture();
				allImages.add(pictureUrl);
				allTitleList.add(projectMessageList.get(i).getTitle());
			}
			allBanner.setData(allImages,allTitleList);
			initBanner(allBanner,allImages,projectMessageList);
		}


	}
	private List<ProjectMessage> hotProjectMessageList = new ArrayList<>();
	List<String> recommendTitleList = new ArrayList<>();
	@Override
	public void onHotSuccess(ProjectMessageBean bean) {
		hotProjectMessageList = bean.getList();
		if (hotProjectMessageList.size() == 0){
			xRecommendBanner.stopAutoPlay();
		}else {
			for (int i=0;i<hotProjectMessageList.size();i++){
				String pictureUrl = Common.URL_BASE+"project_img/"+projectMessageList.get(i).getPicture();
				recommendImages.add(pictureUrl);
				recommendTitleList.add(projectMessageList.get(i).getTitle());
		}
			xRecommendBanner.setData(recommendImages,recommendTitleList);
			initBanner(xRecommendBanner,recommendImages,hotProjectMessageList);
		}

	}
	//会员个人信息
	@Override
	public void onSuccess(Member member) {

        cardNum = member.getCard_no();

		//后台传过来的图片为空，设置为默认的，否则，就用后台传过来的
		if (member.getAvatar() == null || member.getAvatar().isEmpty()){
			iv_photo.setImageResource(R.drawable.touxiang);
		}else {
			showNetworkImg(member.getAvatar());
		}
		tv_name.setText(member.getName()+"");
		tv_card_no.setText(member.getCard_no()+"");
		tv_point.setText(member.getPoint()+"");
		tv_level.setText(StringUtil.numToLevels(member.getLevel())+"");

		String IDcard = member.getIdno();

		birthday = IDcard.substring(6,10)+"-"+IDcard.substring(10,12)+"-"+IDcard.substring(12,14);
		age = StringUtil.IDcard2Age(IDcard);
		gender = StringUtil.numToGender(member.getGender());
		nation = StringUtil.numToNation(member.getNation());
	}
	private void showNetworkImg(String photoName) {
		ShowNetworkImgBusiness showNetworkImgBusiness = new ShowNetworkImgBusiness(context,photoName);
		showNetworkImgBusiness.setHandler(this);
		showNetworkImgBusiness.showNetworkImg();
	}
	@Override
	public void onShowImgSuccess() {

		String path = Environment.getExternalStorageDirectory() + "/network.jpg";
		Bitmap bm = BitmapFactory.decodeFile(path);
		iv_photo.setBackground(new BitmapDrawable(context.getResources(),bm));
	}
	@Override
	public void onShowImgFailed(String s) {
		iv_photo.setBackground(getResources().getDrawable(R.drawable.touxiang));
	}

	//提醒消息的数目
	@Override
	public void onSuccess(List<Integer> list) {
		 unReadNum = list.get(0)+list.get(1);
		badgeView.setBadgeCount(unReadNum);
	}

	/*
	* 录音权限
	* */
	private void recordPermission() {
		AndPermission.with(this)
				.requestCode(REQUEST_CODE_RECORD_AUDIO)
				.permission(android.Manifest.permission.RECORD_AUDIO)
				.callback(permissionListener)
				.rationale(new RationaleListener() {
					@Override
					public void showRequestPermissionRationale(
							int requestCode, Rationale rationale) {
						AndPermission.rationaleDialog(
								MainActivity.this, rationale).
								show();
					}
				})
				.start();
	}
	/**
	 * 回调监听。
	 */
	private PermissionListener permissionListener = new PermissionListener() {
		@Override
		public void onSucceed(int requestCode, @NonNull List<String> grantPermissions) {
			switch (requestCode) {
				case REQUEST_CODE_RECORD_AUDIO: {
					recordAudio();
					break;
				}
			}
		}
		@Override
		public void onFailed(int requestCode, @NonNull List<String> deniedPermissions) {
			switch (requestCode) {
				case REQUEST_CODE_RECORD_AUDIO: {
					showToast("请求权限失败了");
					break;
				}
			}
			// 用户否勾选了不再提示并且拒绝了权限，那么提示用户到设置中授权。
			if (AndPermission.hasAlwaysDeniedPermission(MainActivity.this, deniedPermissions)) {
				// 第一种：用默认的提示语。
                /*AndPermission.defaultSettingDialog(MainActivity.this, REQUEST_CODE_SETTING).show();*/

				// 第二种：用自定义的提示语。
				AndPermission.defaultSettingDialog(MainActivity.this, REQUEST_CODE_SETTING)
						.setTitle("权限申请失败")
						.setMessage("我们需要的一些权限被您拒绝或者系统发生错误申请失败，请您到设置页面手动授权，否则功能无法正常使用！")
						.setPositiveButton("好，去设置")
						.show();
			}
		}
	};

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
			case REQUEST_CODE_SETTING: {
				recordAudio();
				break;
			}
		}

	}

	@Override
	public void onTagListSuccess(TagsListResultBean tagsListResultBean) {

		tagList.addAll(tagsListResultBean.getData());
		adapter.notifyDataSetChanged();
	}

	//重写返回键
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0){
			ActivityCollector.finishAll();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	public void onSuccess(String s) {

		//获取手机版本号
		PackageManager pm = getPackageManager();
		try {
			PackageInfo pInfo = pm.getPackageInfo(getPackageName(),0);
			currVersion = String.valueOf(pInfo.versionCode);
		} catch (PackageManager.NameNotFoundException e) {
			e.printStackTrace();
		}
		if (!s.equals(currVersion)){
			showUpdateDialog();
		}
	}

	public void showUpdateDialog() {
		// 构造对话框
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("软件更新");
		builder.setMessage("有新版本,建议更新!");
		// 更新
		builder.setPositiveButton("更新", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {

				Intent intent = new Intent();
				intent.setAction("android.intent.action.VIEW");
				Uri content_url = Uri.parse(Common.URL_APK);
				intent.setData(content_url);
				startActivity(intent);

				/*downloadApk();
				progressDialog.show();*/
				dialog.dismiss();
			}


		});
		// 稍后更新
		builder.setNegativeButton("稍后更新", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {

				Date startDate = new Date();
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String startTime = format.format(startDate);
				sp.edit().putString(Common.SP_VERSION_UPDATE_TIME,startTime).commit();



				dialog.dismiss();
			}
		});
		Dialog noticeDialog = builder.create();
		noticeDialog.show();
	}


	private void downloadApk() {
		ApkBusiness apkBusiness = new ApkBusiness(context);
		apkBusiness.setHandler(this);
		apkBusiness.getApkFile();
	}

	@Override
	public void onApkSuccess(File file) {
		Intent intent = new Intent(Intent.ACTION_VIEW);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
		startActivity(intent);
		android.os.Process.killProcess(android.os.Process.myPid());
	}

	@Override
	public void onApkFailed(String s) {
		showToast(s);
	}


	@Override
	public void onProgress(final long currentBytes, long totalBytes) {

		int total = Integer.parseInt(String.valueOf(totalBytes/1024));
		int current = Integer.parseInt(String.valueOf(currentBytes/1024));

		progressDialog.setMax(total);

		message = Message.obtain();
		message.what = 1000;
		message.arg1 = current;
		mHandler.sendMessage(message);

	}



}
