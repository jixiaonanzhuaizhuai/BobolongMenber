package com.lgmember.business.login;

import android.content.Context;
import android.text.TextUtils;

import com.lgmember.api.HttpHandler;
import com.lgmember.impl.CodeImpl;
import com.lgmember.impl.LoginImpl;

/**
 * Created by Yanan_Wu on 2017/2/27.
 */

public class ImgCptBusiness {
    private Context context;
    private CodeImpl codeImpl;

    public ImgCptBusiness(Context context) {
        super();
        this.context = context;
    }

    // 先验证参数的可发性，再登陆
    public void getImgCpt() {

        // TODO 可能还要验证密码
        // 登陆
        codeImpl = new CodeImpl();
        codeImpl.getImgCpt(handler,context);
    }

    private ImgCptResultHandler handler;

    public interface ImgCptResultHandler extends HttpHandler {
        public void onImgCptSuccess();
        public void onImgCptFailed(String s);

    }
    public void setHandler(ImgCptResultHandler handler){
        this.handler = handler;
    }

    }