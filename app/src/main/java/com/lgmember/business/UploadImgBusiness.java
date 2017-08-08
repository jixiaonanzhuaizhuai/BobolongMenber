package com.lgmember.business;

import android.content.Context;


import com.lgmember.api.HttpHandler;
import com.lgmember.impl.ImgImpl;


import java.io.File;

/**
 * Created by Yanan_Wu on 2017/2/27.
 */

public class UploadImgBusiness {
    private File file;
    private String session_id;
    private Context context;
    private ImgImpl uploadImgImpl;

    public UploadImgBusiness(Context context, String session_id,File file) {
        super();
        this.context = context;
        this.session_id = session_id;
        this.file = file;
    }

    // 先验证参数的可发性，再登陆
    public void uploadImg() {
        uploadImgImpl = new ImgImpl();
        uploadImgImpl.uploadImg(session_id,file,handler,context);
    }

    private UploadImgResulHandler handler;

    public interface UploadImgResulHandler extends HttpHandler {

        public void onUploadImgSuccess();

    }
    public void setHandler(UploadImgResulHandler handler){
        this.handler = handler;
    }

    }