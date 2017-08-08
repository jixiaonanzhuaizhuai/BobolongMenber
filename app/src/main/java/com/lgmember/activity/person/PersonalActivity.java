package com.lgmember.activity.person;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lgmember.activity.BaseActivity;
import com.lgmember.activity.R;
import com.lgmember.business.message.MemberMessageBusiness;
import com.lgmember.model.Member;
import com.lgmember.util.StringUtil;
import com.lgmember.view.TopBarView;
import com.lgmember.view.TopBarView.onTitleBarClickListener;


public class PersonalActivity extends BaseActivity implements onTitleBarClickListener,MemberMessageBusiness.MemberMessageResulHandler{

	private TopBarView topbar;
	private TextView tv_name,tv_idno,tv_mobile,tv_gender,tv_addr,tv_company,tv_job_title,tv_nation, tv_source,tv_create_time,tv_education,tv_month_income,tv_month_outcome,tv_level, tv_authorized,tv_point, tv_card_no;

	private LinearLayout ll_name,ll_idno,ll_mobile,ll_gender,ll_addr,ll_company,ll_job_title,ll_nation, ll_source,ll_create_time,ll_education,ll_month_income,ll_month_outcome,ll_level, ll_authorized,ll_point, ll_card_no;



	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_personal);
		initView();
		getData();

	}

	private void getData() {
		MemberMessageBusiness memberMessage = new MemberMessageBusiness(context);
		memberMessage.setHandler(this);
		memberMessage.getMemberMessage();
	}

	private void initView() {
		topbar = (TopBarView)findViewById(R.id.topbar);
		topbar.setClickListener(this);
		tv_name = (TextView)findViewById(R.id.tv_name);
		tv_idno = (TextView)findViewById(R.id.tv_idno);
		tv_mobile = (TextView)findViewById(R.id.tv_mobile);
		tv_gender = (TextView)findViewById(R.id.tv_gender);
		tv_addr = (TextView)findViewById(R.id.tv_addr);
		tv_company = (TextView)findViewById(R.id.tv_company);
		tv_job_title = (TextView)findViewById(R.id.tv_job_title);
		tv_nation = (TextView)findViewById(R.id.tv_nation);
		tv_source = (TextView)findViewById(R.id.tv_source);
		tv_create_time = (TextView)findViewById(R.id.tv_create_time);
		tv_education = (TextView)findViewById(R.id.tv_education);
		tv_month_income = (TextView)findViewById(R.id.tv_month_income);
		tv_month_outcome = (TextView)findViewById(R.id.tv_month_outcome);
		tv_level = (TextView)findViewById(R.id.tv_level);
		tv_authorized = (TextView)findViewById(R.id.tv_authorized);
		tv_point = (TextView)findViewById(R.id.tv_point);
		tv_card_no = (TextView)findViewById(R.id.tv_card_no);

		ll_name = (LinearLayout) findViewById(R.id.ll_name);
		ll_idno = (LinearLayout)findViewById(R.id.ll_idno);
		ll_mobile = (LinearLayout)findViewById(R.id.ll_mobile);
		ll_gender = (LinearLayout)findViewById(R.id.ll_gender);
		ll_addr = (LinearLayout)findViewById(R.id.ll_addr);
		ll_company = (LinearLayout)findViewById(R.id.ll_company);
		ll_job_title = (LinearLayout)findViewById(R.id.ll_job_title);
		ll_nation = (LinearLayout)findViewById(R.id.ll_nation);
		ll_source = (LinearLayout)findViewById(R.id.ll_source);
		ll_create_time = (LinearLayout)findViewById(R.id.ll_create_time);
		ll_education = (LinearLayout)findViewById(R.id.ll_education);
		ll_month_income = (LinearLayout)findViewById(R.id.ll_month_income);
		ll_month_outcome = (LinearLayout)findViewById(R.id.ll_month_outcome);
		ll_level = (LinearLayout)findViewById(R.id.ll_level);
		ll_authorized = (LinearLayout)findViewById(R.id.ll_authorized);
		ll_point = (LinearLayout)findViewById(R.id.ll_point);
		ll_card_no = (LinearLayout)findViewById(R.id.ll_card_no);

	}



	@Override
	public void onSuccess(Member member) {
		empty2hide(member.getName(),ll_name,tv_name);
		empty2hide(member.getIdno(),ll_idno,tv_idno);
		empty2hide(member.getMobile(),ll_mobile,tv_mobile);
		empty2hide(StringUtil.numToGender(member.getGender()),ll_gender,tv_gender);
		empty2hide(member.getAddr(),ll_addr,tv_addr);
		empty2hide(member.getCompany(),ll_company,tv_company);
		empty2hide(member.getJob_title(),ll_job_title,tv_job_title);
		empty2hide(StringUtil.numToNation(member.getNation()),ll_nation,tv_nation);
		empty2hide(StringUtil.numToSoure(member.getSource()),ll_source,tv_source);
		empty2hide(member.getCreate_time(),ll_create_time,tv_create_time);
		empty2hide(StringUtil.numToEducation(member.getEducation()),ll_education,tv_education);
		empty2hide(""+member.getMonth_income(),ll_month_income,tv_month_income);
		empty2hide(""+member.getMonth_outcome(),ll_month_outcome,tv_month_outcome);
		empty2hide(StringUtil.numToLevels(member.getLevel()),ll_level,tv_level);
		empty2hide(StringUtil.authorized2String(member.getAuthorized()),ll_authorized,tv_authorized);
		empty2hide(""+member.getPoint(),ll_point,tv_point);
		empty2hide(member.getCard_no(),ll_card_no,tv_card_no);
	}

	private void empty2hide(String str,LinearLayout ll,TextView tv) {
		if (!TextUtils.isEmpty(str)){
			tv.setText(str+"");
		}else {
			ll.setVisibility(View.GONE);
		}

	}


	@Override
	public void onBackClick() {
		finish();
	}

	@Override
	public void onRightClick() {
		startIntent(EditPersonalActivity.class);
	}



}
