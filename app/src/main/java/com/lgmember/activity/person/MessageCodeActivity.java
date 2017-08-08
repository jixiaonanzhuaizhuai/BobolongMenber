package com.lgmember.activity.person;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.view.View.OnClickListener;
import android.widget.EditText;

import com.lgmember.activity.BaseActivity;
import com.lgmember.activity.R;
import com.lgmember.activity.person.PwdCodeActivity;
import com.lgmember.business.SmsCodeBusiness;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Yanan_Wu on 2016/12/29.
 */

//通过验证码找回密码

public class MessageCodeActivity extends BaseActivity implements OnClickListener,SmsCodeBusiness.GetCodeResultHandler {

    private Button nextBtn;
    private EditText phoneEdt;
    private String phoneTxt;
    private static String TAG ="------MessageCodeActivity------";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messagecode);
        init();
    }
    public void init(){
        phoneEdt = (EditText)findViewById(R.id.phoneEdt);
        nextBtn = (Button)findViewById(R.id.nextBtn);
        nextBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.nextBtn:
                getCode();
        }
    }

    public void getCode(){
        phoneTxt = getText(phoneEdt);
        SmsCodeBusiness getCodeBusiness = new SmsCodeBusiness(context,phoneTxt);
        //处理结果
        getCodeBusiness.setHandler(this);
        getCodeBusiness.getCode();
    }

    // 如果嫌方法太多，可以使用适配器模式，减少方法的覆写

    @Override
    public void onRequestCodeSuccess(String token) {

                Intent intent = new Intent(MessageCodeActivity.this,PwdCodeActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("sms_capt_tokenTxt",token);
                bundle.putString("phone",phoneTxt);
                intent.putExtras(bundle);
                startActivity(intent);
                finish();
    }
}
