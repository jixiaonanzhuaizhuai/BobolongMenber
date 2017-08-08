package com.lgmember.activity.person;



import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.lgmember.activity.BaseActivity;
import com.lgmember.activity.R;
import com.lgmember.activity.person.NewPwdActivity;

public class PwdCodeActivity extends BaseActivity implements OnClickListener{

	private TextView phoneTxt,tv_time_num;
	private Button nextBtn;
	private String sms_capt_tokenTxt,phone;
	private EditText captEdt;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pwdcode);
		//从上个页面传过来的参数
		Bundle bundle = this.getIntent().getExtras();
		phone = bundle.getString("phone");
		sms_capt_tokenTxt = bundle.getString("sms_capt_tokenTxt");
		init();

	}
	public void init(){

		phoneTxt = (TextView)findViewById(R.id.phoneTxt);
		captEdt = (EditText)findViewById(R.id.codeEdt) ;
		nextBtn = (Button)findViewById(R.id.nextBtn);
		nextBtn.setOnClickListener(this);
		phoneTxt.setText(phone+"");

		tv_time_num = (TextView)findViewById(R.id.tv_time_num);
		TimeCount timeCount = new TimeCount(60000,1000);
		timeCount.start();

	}
	class TimeCount extends CountDownTimer {
		public TimeCount(long millisInFuture, long countDownInterval) {
			super(millisInFuture, countDownInterval);
		}
		public void onFinish() {
			tv_time_num.setText("60");
		}
		public void onTick(long millisUntilFinished) {
			tv_time_num.setText(""+millisUntilFinished / 1000 );
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()){
			case R.id.nextBtn:
				String capt = getText(captEdt);
				if (TextUtils.isEmpty(capt)){
					showToast("请输入验证码");
				}else {

					Intent intent = new Intent(PwdCodeActivity.this,NewPwdActivity.class);
					Bundle bundle = new Bundle();
					bundle.putString("sms_capt_tokenTxt",sms_capt_tokenTxt);
					bundle.putString("phone",phone);
					bundle.putString("capt",getText(captEdt));
					intent.putExtras(bundle);
					startActivity(intent);
					finish();
				}

		}

	}


}
