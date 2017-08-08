package com.lgmember.business;

import android.content.Context;
import android.graphics.Bitmap;

import com.lgmember.api.HttpHandler;
import com.lgmember.impl.ImgImpl;

import java.io.File;

/**
 * Created by Yanan_Wu on 2017/2/27.
 */

public class ShowNetworkImgBusiness {
    private String photoName;
    private Context context;
    private ImgImpl imgImpl;

    public ShowNetworkImgBusiness(Context context, String photoName) {
        super();
        this.context = context;
        this.photoName = photoName;
    }

    // 先验证参数的可发性，再登陆
    public void showNetworkImg() {
        imgImpl = new ImgImpl();
        imgImpl.showNetworkImg(photoName,handler,context);
    }

    private ShowNetworkImgResulHandler handler;

    public interface ShowNetworkImgResulHandler extends HttpHandler {

        public void onShowImgSuccess();

        public void onShowImgFailed(String s);

    }
    public void setHandler(ShowNetworkImgResulHandler handler){
        this.handler = handler;
    }

    }