package com.lgmember.business;

import android.content.Context;
import android.text.TextUtils;

import com.lgmember.api.HttpHandler;
import com.lgmember.impl.CodeImpl;
import com.lgmember.util.StringUtil;

/**
 * Created by Yanan_Wu on 2017/2/27.
 */

public class SmsCodeIfCorrectBusiness {
    private String token,capt;
    private Context context;
    private CodeImpl codeImpl;

    public SmsCodeIfCorrectBusiness(Context context, String token,String capt) {
        super();
        this.context = context;
        this.token = token;
        this.capt = capt;
    }

    // 先验证参数的可发性，再登陆
    public void smsCode() {
        codeImpl = new CodeImpl();
        codeImpl.smsCodeIfCorrect(token,capt,handler,context);
    }

    private ValidateSmsCodeResultHandler handler;

    public interface ValidateSmsCodeResultHandler extends HttpHandler {

        //当请求验证码返回成功
        public void onValidateSmsSuccess();
    }
    public void setHandler(ValidateSmsCodeResultHandler handler){
        this.handler = handler;
    }

    }