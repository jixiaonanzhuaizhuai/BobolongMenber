package com.lgmember.activity.project;

import android.content.ComponentName;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lgmember.activity.BaseActivity;
import com.lgmember.activity.R;
import com.lgmember.business.collection.CollectionAddBusiness;
import com.lgmember.business.project.JoinActivityBusiness;
import com.lgmember.model.ProjectMessage;
import com.lgmember.model.Tag;
import com.lgmember.util.Common;
import com.lgmember.util.DataLargeHolder;
import com.lgmember.util.StringUtil;
import com.lgmember.view.TopBarView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class ProjectMessageDetailActivity extends BaseActivity implements View.OnClickListener, JoinActivityBusiness.JoinActivityResulHandler,CollectionAddBusiness.CollectionResulHandler,TopBarView.onTitleBarClickListener {

	private TextView tv_title,tv_content,tv_hyperlink,
			tv_create_time,tv_count,tv_checkin_end_time;
	private ImageView imageView;
	private Button joinBtn;
	private int projectMessage_id;
	private ProjectMessage projectMessage;
	private TopBarView topBar;
	//private RightPopupMenu rightPopupMenu;

	private LinearLayout ll_collection,ll_share;
	private ImageView iv_collection;
	private TextView tv_collection;
	private boolean is_collection ;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_activitydetail);

		Intent intent = this.getIntent();
		int id =
				intent.getIntExtra("id",0);
		projectMessage = (ProjectMessage) DataLargeHolder.getInstance().retrieve(id);
		init();
	}

	private void addCollection() {
		CollectionAddBusiness collectionAddBusiness = new CollectionAddBusiness(context,projectMessage_id);
		collectionAddBusiness.setHandler(this);
		collectionAddBusiness.addCollection();

	}
	private void deleteCollection() {
		CollectionAddBusiness collectionAddBusiness = new CollectionAddBusiness(context,projectMessage_id);
		collectionAddBusiness.setHandler(this);
		collectionAddBusiness.deleteCollection();
	}

	private void init(){

		ll_collection = (LinearLayout)findViewById(R.id.ll_collection);
		iv_collection = (ImageView)findViewById(R.id.iv_collection) ;
		tv_collection = (TextView)findViewById(R.id.tv_collection);
		ll_collection.setOnClickListener(this);
		is_collection = projectMessage.getSaved();
		if (is_collection){
			iv_collection.setImageResource(R.mipmap.collection_checked);
			tv_collection.setText("取消收藏");
		}

		ll_share = (LinearLayout)findViewById(R.id.ll_share);
		ll_share.setOnClickListener(this);
		topBar = (TopBarView)findViewById(R.id.topbar);
		topBar.setClickListener(this);

		tv_title = (TextView) findViewById(R.id.tv_title);
		tv_content = (TextView) findViewById(R.id.tv_content);
		tv_hyperlink = (TextView)findViewById(R.id.tv_hyperlink);
		tv_create_time = (TextView)findViewById(R.id.tv_create_time);
		tv_count = (TextView)findViewById(R.id.tv_count);
		tv_checkin_end_time = (TextView)findViewById(R.id.tv_checkin_end_time);

		imageView = (ImageView)findViewById(R.id.imageView);
		joinBtn = (Button)findViewById(R.id.joinBtn);
		joinBtn.setOnClickListener(this);

		projectMessage_id = projectMessage.getId();

		List<Tag> tagList = projectMessage.getTags();

		List<String> tags = new ArrayList<>();

		for (int i = 0;i<tagList.size();i++){
			tags.add(tagList.get(i).getTag());
		}
		String pictureUrl = Common.URL_IMG_BASE+projectMessage.getPicture();
		StringUtil.setNetworkBitmap(this,pictureUrl,imageView);

		tv_title.setText(projectMessage.getTitle());
		tv_content.setText(Html.fromHtml(projectMessage.getContent()));
		tv_hyperlink.setText(projectMessage.getHyperlink());
		tv_hyperlink.setOnClickListener(this);
		tv_create_time.setText(projectMessage.getStart_time());
		tv_count.setText(""+projectMessage.getCount());
		tv_checkin_end_time.setText(projectMessage.getEnd_time());
		is_collection = projectMessage.getSaved();

		SimpleDateFormat df =
				new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
		String current_time = df.format(new Date());
		int compare_date = StringUtil.compare_date(projectMessage.getCheckin_end_time(),current_time);
		int state = projectMessage.getState();
		if (compare_date == -1){
			joinBtn.setEnabled(false);
			joinBtn.setText("报名时间已截止");
		}else if (state != -1 ){
			joinBtn.setEnabled(false);
			joinBtn.setText(StringUtil.numToJoinState(state));
		}else {
			joinBtn.setText("报名参与");
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()){
			case R.id.tv_hyperlink:
				Uri uri = Uri.parse("https://www.baidu.com/");
				Intent intent = new Intent(Intent.ACTION_VIEW,uri);
				startActivity(intent);
				break;
			case R.id.joinBtn:
				joinActivity();
				break;
			case R.id.ll_collection:
				if (is_collection){
					deleteCollection();
					is_collection = false;
				}else {
					addCollection();
					is_collection = true;
				}
				break;
			case R.id.ll_share:
				shareText(null, null,
						"这是一个有趣的活动：https://www.baidu.com/", "分享标题", "分享主题");
				break;
		}
	}

	public void joinActivity(){
		JoinActivityBusiness joinActivityBusiness = new JoinActivityBusiness(context,projectMessage_id);
		joinActivityBusiness.setHandler(this);
		joinActivityBusiness.join();
	}

	//一键分享
	public void shareText(String packageName, String className, String content, String title, String subject){
		Intent intent =new Intent();
		intent.setAction(Intent.ACTION_SEND);
		intent.setType("text/plain");
		if(stringCheck(className) && stringCheck(packageName)){
			ComponentName componentName = new ComponentName(packageName, className);
			intent.setComponent(componentName);
		}else if(stringCheck(packageName)){
			intent.setPackage(packageName);
		}

		intent.putExtra(Intent.EXTRA_TEXT, content);
		if(null != title && !TextUtils.isEmpty(title)){
			intent.putExtra(Intent.EXTRA_TITLE, title);
		}
		if(null != subject && !TextUtils.isEmpty(subject)){
			intent.putExtra(Intent.EXTRA_SUBJECT, subject);
		}
		intent.putExtra(Intent.EXTRA_TITLE, title);
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		Intent chooserIntent = Intent.createChooser(intent, "分享到：");
		startActivity(chooserIntent);
	}

	public static boolean stringCheck(String str){
		if(null != str && !TextUtils.isEmpty(str)){
			return true;
		}else {
			return false;
		}
	}

	@Override
	public void onSuccess(String string) {
		joinBtn.setText(string);
		joinBtn.setEnabled(false);
	}

	@Override
	public void onCollectionSuccess(String str) {
		if (is_collection){
			iv_collection.setImageResource(R.mipmap.collection_checked);
			tv_collection.setText("取消收藏");
		}else {
			iv_collection.setImageResource(R.mipmap.collection_define);
			tv_collection.setText("收藏");
		}
	}

	@Override
	public void onBackClick() {
		finish();
	}

	@Override
	public void onRightClick() {
	}
}
