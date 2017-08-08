package com.lgmember.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import android.widget.EditText;
import android.widget.Toast;

import com.lgmember.activity.person.CertificationActivity;
import com.lgmember.business.SmsCodeBusiness;
import com.lgmember.business.RegisterBusiness;
import com.lgmember.util.StringUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Timer;
import java.util.TimerTask;

public class RegisterActivity extends BaseActivity implements OnClickListener,RegisterBusiness.RegisterResultHandler,
		SmsCodeBusiness.GetCodeResultHandler {

	private EditText phoneEdt, codeEdt;
	private Button requestCodeBtn, regBtn;
	private TimeCount timeCount;
	private String phoneTxt,codeTxt,sms_capt_tokenTxt;
	private String TAG = "------>RegisterActivity";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		init();
		showTipsDialog();

	}

	private void showTipsDialog() {

		AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
		builder.setTitle("提示");
		builder.setMessage("会员俱乐部采用实名会员管理，请确认信息与身份证信息相同，注册成功后，可凭本人身份证领取实体会员卡。");
		builder.setPositiveButton("确定",null);
		builder.show();
	}
	/**
	 * 初始化控件
	 */
	private void init() {
		phoneEdt = (EditText) findViewById(R.id.phoneEdt);
		codeEdt = (EditText) findViewById(R.id.codeEdt);
		requestCodeBtn = (Button) findViewById(R.id.requestCodeBtn);

		timeCount = new TimeCount(60000,1000);

		regBtn = (Button) findViewById(R.id.regBtn);
		requestCodeBtn.setOnClickListener(this);
		regBtn.setOnClickListener(this);

	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.requestCodeBtn:
				if (requestCodeBtn.isClickable()){
					getCode();
				}
				break;
			case R.id.regBtn:
				register();
				break;
		}
	}

	private void getCode(){
		phoneTxt = getText(phoneEdt);
		timeCount.start();
		SmsCodeBusiness getCodeBusiness = new SmsCodeBusiness(context,phoneTxt);
		//处理结果
		getCodeBusiness.setHandler(this);
		getCodeBusiness.getCode();
	}
	//activity只用来1.获取参数2.处理结果，中间业务放到business中去
	private void register(){
		phoneTxt = getText(phoneEdt);
		codeTxt = getText(codeEdt);
		RegisterBusiness registerBusiness = new RegisterBusiness(context,phoneTxt,codeTxt,sms_capt_tokenTxt);
		//处理结果
		registerBusiness.setHandler(this);
		registerBusiness.Register();
	}


	@Override
	public void onSuccess(JSONObject jsob) {
		// TODO 登录成功
		int code = 0;
		String card_num = "";
		try {
			code = jsob.getInt("code");

		} catch (JSONException e) {
			e.printStackTrace();
		}
		if (code == 0){
			try {
				card_num = jsob.getString("card_no");
				showDialog();
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}else {

			int codeArray[] = {R.string.code_0, R.string.code_1, R.string.code_2, R.string.code_3, R.string.code_4, R.string.code_5, R.string.code_6,
					R.string.code_7, R.string.code_8, R.string.code_9, R.string.code_10, R.string.code_11};
			for (int i = 0; i <= codeArray.length; i++) {
				if (i == code) {
					showToast(context.getString(codeArray[i]));
				}
			}
		}
	}

	//请求验证码成功时
	@Override
	public void onRequestCodeSuccess(String token) {

		sms_capt_tokenTxt = token;
	}
	//弹出对话框
	public void showDialog(){
		//注册成功后的业务逻辑
		AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
		builder.setTitle("自助实名认证");
		builder.setMessage("您已通过注册成为龙广会员，通过‘实名认证’即可享受龙广会员俱乐部的贴心服务。");
		builder.setPositiveButton("实名认证", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				//完成业务逻辑
				startIntent(CertificationActivity.class);
				finish();
			}
		});
		builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				//业务逻辑
			}
		});
		builder.show();

	}
    //点击验证码倒计时
	class TimeCount extends CountDownTimer {
		public TimeCount(long millisInFuture, long countDownInterval) {
			super(millisInFuture, countDownInterval);
		}
		public void onFinish() {
			requestCodeBtn.setText("获取验证码");
			requestCodeBtn.setClickable(true);
		}
		public void onTick(long millisUntilFinished) {
			requestCodeBtn.setClickable(false);
			requestCodeBtn.setText(millisUntilFinished / 1000 + "秒后点击重发");
		}
	}
}
