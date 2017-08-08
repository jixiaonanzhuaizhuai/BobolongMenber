package com.lgmember.business;

import android.content.Context;

import com.lgmember.api.HttpHandler;
import com.lgmember.impl.LoginImpl;

import java.io.File;

/**
 * Created by Yanan_Wu on 2017/2/27.
 */

public class ApkBusiness {
    private Context context;
    private LoginImpl loginImpl;

    public ApkBusiness(Context context) {
        super();
        this.context = context;
    }

    public void getApkFile() {
        loginImpl = new LoginImpl();
        loginImpl.getApkFile(handler,context);
    }


    private ApkResulHandler handler;
    public interface ApkResulHandler extends HttpHandler {

        public void onApkSuccess(File file);
        public void onApkFailed(String s);
        public void onProgress(long currentBytes, long totalBytes);

    }
    public void setHandler(ApkResulHandler handler){
        this.handler = handler;
    }


    }