package com.lgmember.business.sign;

import android.content.Context;

import com.lgmember.api.HttpHandler;
import com.lgmember.impl.SignImpl;

/**
 * Created by Yanan_Wu on 2017/2/27.
 */

public class ScannerSignBusiness {
    private String url;
    private Context context;
    private SignImpl signImpl;

    public ScannerSignBusiness(Context context, String url) {
        super();
        this.context = context;
        this.url = url;
    }

    // 先验证参数的可发性，再登陆
    public void scannerSign() {
        signImpl = new SignImpl();
        signImpl.scannerSign(url,handler,context);
    }

    private ScannerSignResulHandler handler;

    public interface ScannerSignResulHandler extends HttpHandler {

        public void onSuccess(String s);

    }
    public void setHandler(ScannerSignResulHandler handler){
        this.handler = handler;
    }

    }