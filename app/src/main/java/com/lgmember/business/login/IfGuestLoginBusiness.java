package com.lgmember.business.login;

import android.content.Context;

import com.lgmember.api.HttpHandler;
import com.lgmember.impl.LoginImpl;

/**
 * Created by Yanan_Wu on 2017/2/27.
 */

public class IfGuestLoginBusiness {
    private Context context;
    private LoginImpl loginImpl;

    public IfGuestLoginBusiness(Context context) {
        super();
        this.context = context;
    }
    public void ifGuestLogin() {
        loginImpl = new LoginImpl();
        loginImpl.ifGuestLogin(handler,context);
    }

    private IfGUestLoginResultHandler handler;
    public interface IfGUestLoginResultHandler extends HttpHandler {
        public void onIfGuestLoginSuccess(String s);
    }
    public void setHandler(IfGUestLoginResultHandler handler){
        this.handler = handler;
    }


    }