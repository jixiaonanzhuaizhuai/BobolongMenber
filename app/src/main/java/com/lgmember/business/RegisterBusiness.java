package com.lgmember.business;

import android.content.Context;
import android.text.TextUtils;

import com.lgmember.api.HttpApi;
import com.lgmember.api.HttpHandler;
import com.lgmember.impl.RegisterImpl;
import com.lgmember.util.StringUtil;

import org.json.JSONObject;

/**
 * Created by Yanan_Wu on 2017/2/28.
 */

public class RegisterBusiness {
    private String phoneTxt;
    private String codeTxt;
    private String sms_capt_tokenTxt;
    private Context context;
    private RegisterImpl registerImpl;

    public RegisterBusiness(Context context, String phoneTxt, String codeTxt, String sms_capt_tokenTxt) {
        super();
        this.context = context;
        this.phoneTxt = phoneTxt;
        this.codeTxt = codeTxt;
        this.sms_capt_tokenTxt = sms_capt_tokenTxt;
    }

    // 先验证参数的可发性，再登陆
    public void Register() {
        // 验证参数是否为空
        if (TextUtils.isEmpty(phoneTxt)) {
            if (handler != null) {
                handler.onArgumentEmpty("请输入手机号");
            }
            return;
        }
        if (TextUtils.isEmpty(codeTxt)) {
            if (handler != null) {
                handler.onArgumentEmpty("验证码不能为空");
            }
            return;
        }
        // 如果是手机号
        if (!StringUtil.isPhone(phoneTxt)) {
            if (handler != null) {
                handler.onArgumentFormatError("手机号格式不正确");
            }
            return;
        }
        // TODO 可能还要验证密码
        // 登陆
        registerImpl = new RegisterImpl();
        registerImpl.register(phoneTxt,codeTxt,sms_capt_tokenTxt,handler,context);
    }

    private RegisterResultHandler handler;

    public interface RegisterResultHandler extends HttpHandler {
        //当参数为空
        public void onArgumentEmpty(String s);

        //当参数不合法时
        public void onArgumentFormatError(String s);

        public void onSuccess(JSONObject jsob);

    }

    public void setHandler(RegisterResultHandler handler){
        this.handler = handler;
    }

}