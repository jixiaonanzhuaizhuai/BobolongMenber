package com.lgmember.business;

import android.content.Context;

import com.lgmember.api.HttpHandler;
import com.lgmember.impl.ImgImpl;
import com.lgmember.impl.LoginImpl;

import java.io.File;

/**
 * Created by Yanan_Wu on 2017/2/27.
 */

public class VersionBusiness {
    private Context context;
    private LoginImpl loginImpl;

    public VersionBusiness(Context context) {
        super();
        this.context = context;
    }

    // 先验证参数的可发性，再登陆
    public void getVersion() {
        loginImpl = new LoginImpl();
        loginImpl.getVersion(handler,context);
    }
    private VersionResulHandler handler;
    public interface VersionResulHandler extends HttpHandler {

        public void onSuccess(String s);

    }
    public void setHandler(VersionResulHandler handler){
        this.handler = handler;
    }

    }