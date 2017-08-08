package com.lgmember.business.login;

import android.content.Context;

import com.lgmember.api.HttpHandler;
import com.lgmember.impl.CodeImpl;
import com.lgmember.impl.LoginImpl;

/**
 * Created by Yanan_Wu on 2017/2/27.
 */

public class IfNeedCaptBusiness {
    private Context context;
    private LoginImpl loginImpl;

    public IfNeedCaptBusiness(Context context) {
        super();
        this.context = context;
    }

    // 先验证参数的可发性，再登陆
    public void getNeedCapt() {

        // TODO 可能还要验证密码
        // 登陆
        loginImpl = new LoginImpl();
        loginImpl.getNeedCapt(handler,context);
    }

    private IfNeedCaptResultHandler handler;

    public interface IfNeedCaptResultHandler extends HttpHandler {
        public void onNeedCaptSuccess(boolean need_capt);
    }
    public void setHandler(IfNeedCaptResultHandler handler){
        this.handler = handler;
    }

    }