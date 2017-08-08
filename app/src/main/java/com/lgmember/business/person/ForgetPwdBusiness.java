package com.lgmember.business.person;

import android.content.Context;
import android.text.TextUtils;

import com.lgmember.api.HttpHandler;
import com.lgmember.impl.CertificationImpl;
import com.lgmember.impl.LoginImpl;
import com.lgmember.model.Certification;
import com.lgmember.util.StringUtil;

/**
 * Created by Yanan_Wu on 2017/2/27.
 */

public class ForgetPwdBusiness {
    private String mobile,capt,sms_capt_token,password,confirmPwd;
    private Context context;
    private LoginImpl loginImpl;

    public ForgetPwdBusiness(Context context,String mobile,String capt,String sms_capt_token,String password,String confirmPwd) {
        super();
        this.context = context;
        this.mobile = mobile;
        this.capt = capt;
        this.sms_capt_token = sms_capt_token;
        this.password = password;
        this.confirmPwd = confirmPwd;
    }

    // 先验证参数的可发性，再登陆
    public void forgetPwd() {
        // 验证参数是否为空
        if (TextUtils.isEmpty(password)||TextUtils.isEmpty(confirmPwd)) {
            if (handler != null) {
                handler.onArgumentEmpty("参数不能为空");
            }
            return;
        }
        if (!StringUtil.isNewPwdEquallyConfirmPwd(password,confirmPwd)) {
            if (handler != null) {
                handler.onArgumentFormatError("两次密码不一致");
            }
            return;
        }


        // 判断活动码是否有效
        loginImpl = new LoginImpl();
        loginImpl.forgetPwd(mobile,capt,sms_capt_token,password,handler,context);
    }

    private ForgetPwdResulHandler handler;

    public interface ForgetPwdResulHandler extends HttpHandler {
        //当参数为空
        public void onArgumentEmpty(String string);

        //当参数不合法时
        public void onArgumentFormatError(String string);

        public void onSuccess(int code);

    }
    public void setHandler(ForgetPwdResulHandler handler){
        this.handler = handler;
    }

    }