package com.lgmember.business.login;

import android.content.Context;
import android.text.TextUtils;

import com.lgmember.api.HttpHandler;
import com.lgmember.impl.LoginImpl;

/**
 * Created by Yanan_Wu on 2017/2/27.
 */

public class GuestLoginBusiness {
    private Context context;
    private LoginImpl loginImpl;

    public GuestLoginBusiness(Context context) {
        super();
        this.context = context;
    }

    public void guestLogin() {
        loginImpl = new LoginImpl();
        loginImpl.guestLogin(handler,context);
    }

    private GUestLoginResultHandler handler;
    public interface GUestLoginResultHandler extends HttpHandler {
        public void onGuestLoginSuccess();
    }
    public void setHandler(GUestLoginResultHandler handler){
        this.handler = handler;
    }
    }