package com.lgmember.business;

import android.content.Context;

import com.lgmember.api.HttpHandler;
import com.lgmember.impl.ImgImpl;

import java.io.File;

/**
 * Created by Yanan_Wu on 2017/2/27.
 */

public class UpdatePhotoBusiness {
    private File file;
    private Context context;
    private ImgImpl uploadImgImpl;

    public UpdatePhotoBusiness(Context context, File file) {
        super();
        this.context = context;
        this.file = file;
    }

    // 先验证参数的可发性，再登陆
    public void updatePhoto() {
        uploadImgImpl = new ImgImpl();
        uploadImgImpl.updatePhoto(file,handler,context);
    }

    private UpdatePhotoResulHandler handler;

    public interface UpdatePhotoResulHandler extends HttpHandler {

        public void onSuccess();

    }
    public void setHandler(UpdatePhotoResulHandler handler){
        this.handler = handler;
    }

    }