package com.lgmember.business.login;

import android.content.Context;
import android.text.TextUtils;

import com.lgmember.api.HttpApi;
import com.lgmember.api.HttpHandler;
import com.lgmember.impl.LoginImpl;
import com.lgmember.util.StringUtil;

import org.json.JSONObject;

/**
 * Created by Yanan_Wu on 2017/2/27.
 */

public class LoginBusiness {
    private String loginName;
    private String loginPass;
    private String cpt;
    private boolean need_capt;
    private Context context;
    private LoginImpl loginImpl;

    public LoginBusiness(Context context, String loginName, String loginPass,String cpt,boolean need_capt) {
        super();
        this.context = context;
        this.loginName = loginName;
        this.loginPass = loginPass;
        this.cpt = cpt;
        this.need_capt = need_capt;
    }

    // 先验证参数的可发性，再登陆
    public void Login() {
        // 验证参数是否为空
        if (TextUtils.isEmpty(loginName) || TextUtils.isEmpty(loginPass)) {
            if (handler != null) {
                handler.onArgumentEmpty("参数不能为空");
            }
            return;
        }
        // 如果是手机号
//        if (!StringUtil.isPhone(loginName)) {
//            if (handler != null) {
//                handler.onArgumentFormatError("手机号格式不正确");
//            }
//            return;
//        }
        // TODO 可能还要验证密码
        // 登陆
        loginImpl = new LoginImpl();
        loginImpl.login(loginName,loginPass,cpt,need_capt,handler,context);
    }

    private LoginResultHandler handler;

    public interface LoginResultHandler extends HttpHandler {
        //当参数为空
        public void onArgumentEmpty(String s);


        public void onSuccess(int state,boolean need_capt);
    }
    public void setHandler(LoginResultHandler handler){
        this.handler = handler;
    }

    }