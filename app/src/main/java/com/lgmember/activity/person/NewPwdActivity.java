package com.lgmember.activity.person;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.lgmember.activity.BaseActivity;
import com.lgmember.activity.LoginActivity;
import com.lgmember.activity.R;
import com.lgmember.business.SmsCodeBusiness;
import com.lgmember.business.person.ForgetPwdBusiness;
import com.lgmember.util.StringUtil;

/**
 * Created by Yanan_Wu on 2016/12/29.
 */

public class NewPwdActivity extends BaseActivity implements OnClickListener,ForgetPwdBusiness.ForgetPwdResulHandler {

    private Button finishBtn;
    private TextView phoneTxt;
    private String phone,sms_capt_tokenTxt,capt;
    private EditText edt_pwd,edt_confirm_pwd;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newpwd);
        //从上个页面传过来的参数
        Bundle bundle = this.getIntent().getExtras();
        phone = bundle.getString("phone");
        sms_capt_tokenTxt = bundle.getString("sms_capt_tokenTxt");
        capt = bundle.getString("capt");
        init();


    }

    public void init(){

        edt_pwd = (EditText)findViewById(R.id.edt_pwd) ;
        edt_confirm_pwd = (EditText)findViewById(R.id.edt_confirm_pwd);
        phoneTxt = (TextView)findViewById(R.id.phoneTxt);
        phoneTxt.setText(phone+"");
        finishBtn = (Button)findViewById(R.id.finishBtn);
        finishBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.finishBtn:
                forgetPwd();
                break;
        }

    }

    private void forgetPwd() {
        ForgetPwdBusiness forgetPwdBusiness = new ForgetPwdBusiness(context,
                phone,capt,sms_capt_tokenTxt,getText(edt_pwd),getText(edt_confirm_pwd));
        //处理结果
        forgetPwdBusiness.setHandler(this);
        forgetPwdBusiness.forgetPwd();

    }

    @Override
    public void onSuccess(int code) {
        showToast("修改成功");
        startIntent(LoginActivity.class);
        finish();
    }
}
