package com.lgmember.activity.person;



import android.app.AlertDialog;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import com.lgmember.activity.BaseActivity;
import com.lgmember.activity.R;
import com.lgmember.activity.score.ExchangeGiftDetailActivity;
import com.lgmember.business.SmsCodeBusiness;
import com.lgmember.business.SmsCodeIfCorrectBusiness;
import com.lgmember.business.person.EditMemberMsgBusiness;
import com.lgmember.business.message.MemberMessageBusiness;
import com.lgmember.model.Member;
import com.lgmember.util.StringUtil;
import com.lgmember.view.TopBarView;

public class EditPersonalActivity extends BaseActivity implements
		MemberMessageBusiness.MemberMessageResulHandler,
		EditMemberMsgBusiness.EditMemberMessageResulHandler,
		TopBarView.onTitleBarClickListener,SmsCodeBusiness.GetCodeResultHandler,SmsCodeIfCorrectBusiness.ValidateSmsCodeResultHandler {

	private TopBarView topBar;
	private EditText edt_mobile,edt_addr,edt_company,edt_job_title,
					edt_month_income,edt_month_outcome;
	private Spinner sp_education;
	private int nation,education;

	private String name,idno,phoneNum,token;
	private boolean gender;


	private ArrayAdapter<String> nationAdapt,educationAdapter;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_editpersonal);
		init();
		getData();

	}
	private void getData() {
		MemberMessageBusiness memberMessage = new MemberMessageBusiness(context);
		memberMessage.setHandler(this);
		memberMessage.getMemberMessage();
	}

	public void init(){
		showToast("根据完善资料的程度，会赠送您相应的积分");
		topBar = (TopBarView)findViewById(R.id.topbar);
		topBar.setClickListener(this);
		nationAdapt = new
				ArrayAdapter<>(this,android.R.layout.simple_spinner_item,StringUtil.NATIONS);
		nationAdapt.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		sp_education = (Spinner)findViewById(R.id.sp_education);
		educationAdapter = new
				ArrayAdapter<>(this,
				android.R.layout.simple_spinner_item,StringUtil.EDUCATIONS);
		educationAdapter.setDropDownViewResource(
				android.R.layout.simple_spinner_dropdown_item);
		sp_education.setAdapter(educationAdapter);

		sp_education.setOnItemSelectedListener(
				new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent,
									   View view, int position, long id) {
				education = position;
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
			}
		});


		edt_mobile = (EditText)findViewById(R.id.edt_mobile);
		edt_addr = (EditText)findViewById(R.id.edt_addr);
		edt_company = (EditText)findViewById(R.id.edt_company);
		edt_job_title = (EditText)findViewById(R.id.edt_job_title);
		edt_month_income = (EditText)findViewById(R.id.edt_month_income);
		edt_month_outcome = (EditText)findViewById(R.id.edt_month_outcome);

	}

	private void submitData() {
		String addr = getText(edt_addr);
		String mobile = getText(edt_mobile);
		String company = getText(edt_company);
		String job_title = getText(edt_job_title);
		int month_income = Integer.parseInt(getText(edt_month_income));
		int month_outcome = Integer.parseInt(getText(edt_month_outcome));

		Member member = new Member();
		member.setName(name);
		member.setIdno(idno);
		member.setGender(gender);
		member.setMobile(mobile);
		member.setAddr(addr);
		member.setCompany(company);
		member.setJob_title(job_title);
		member.setNation(nation);
		member.setEducation(education);
		member.setMonth_income(month_income);
		member.setMonth_outcome(month_outcome);

		EditMemberMsgBusiness editMemberMsgBusiness = new EditMemberMsgBusiness(context,member);
		editMemberMsgBusiness.setHandler(this);
		editMemberMsgBusiness.editMemberMessage();

	}

	@Override
	public void onEditMemberMsgSuccess() {
		showToast("操作成功");
		startIntent(PersonalActivity.class);
	}

	@Override
	public void onSuccess(Member member) {
		name = member.getName();
		idno = member.getIdno();
		gender = member.getGender();
		phoneNum = member.getMobile();
		edt_mobile.setText(member.getMobile());
		edt_mobile.setSelection(member.getMobile().length());
		edt_addr.setText(member.getAddr());
		edt_company.setText(member.getCompany());
		edt_job_title.setText(member.getJob_title());
		nation = member.getNation();
		sp_education.setSelection(member.getEducation());
		edt_month_income.setText(member.getMonth_income()+"");
		edt_month_outcome.setText(member.getMonth_outcome()+"");
	}


	@Override
	public void onBackClick() {
		finish();
	}

	@Override
	public void onRightClick() {
		if (!StringUtil.isPhone(getText(edt_mobile))){
			showToast("手机号格式不正确！");
		}else if (!getText(edt_mobile).equals(phoneNum)){
			DialogPhoneCode();
		}else {
			submitData();
		}

	}
	private AlertDialog dialog;
	public void DialogPhoneCode() {
		AlertDialog.Builder adb = new AlertDialog.Builder(this);
		dialog = adb.create();
		View view = getLayoutInflater().inflate(
				R.layout.dialog_exchange_gift_phone_code, null);
		TextView txt_phone = (TextView) view.findViewById(R.id.txt_phone);
		txt_phone.setText(getText(edt_mobile));
		final Button btn_request_code = (Button) view.findViewById(R.id.btn_request_code);

		class TimeCount extends CountDownTimer {
			public TimeCount(long millisInFuture, long countDownInterval) {
				super(millisInFuture, countDownInterval);
			}
			public void onFinish() {
				btn_request_code.setText("获取验证码");
				btn_request_code.setClickable(true);
			}
			public void onTick(long millisUntilFinished) {
				btn_request_code.setClickable(false);
				btn_request_code.setText(millisUntilFinished / 1000 + "秒后点击重发");
			}
		}
		final TimeCount timeCount = new TimeCount(60000,1000);
		btn_request_code.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (btn_request_code.isClickable()) {
					timeCount.start();
					getCode();
				}
			}
		});
		final EditText edt_code = (EditText)view.findViewById(R.id.edt_code);
		Button btn_ok = (Button) view.findViewById(R.id.btn_ok);
		Button btn_cancle = (Button) view.findViewById(R.id.btn_cancle);
		btn_ok.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (getText(edt_code).equals("")){
					showToast("请输入验证码");
				}else {
					//先判断验证码是否正确，然后再提交修改的信息
					ifSmsCodeCorrect(getText(edt_code));
					dialog.dismiss();
				}

			}
		});
		btn_cancle.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}
		});
		dialog.setView(view, 0, 0, 0, 0);
		dialog.show();
	}

	private void ifSmsCodeCorrect(String capt) {
		SmsCodeIfCorrectBusiness smsCodeIfCorrectBusiness =
				new SmsCodeIfCorrectBusiness(context,token,capt);
		//处理结果
		smsCodeIfCorrectBusiness.setHandler(this);
		smsCodeIfCorrectBusiness.smsCode();
	}
	private void getCode(){
		SmsCodeBusiness getCodeBusiness =
				new SmsCodeBusiness(context,getText(edt_mobile));
		//处理结果
		getCodeBusiness.setHandler(this);
		getCodeBusiness.getCode();
	}
	//请求验证码，反悔的string是一个“token”
	@Override
	public void onRequestCodeSuccess(String string) {
		token = string;
	}

	@Override
	public void onValidateSmsSuccess() {
		submitData();
	}
}
