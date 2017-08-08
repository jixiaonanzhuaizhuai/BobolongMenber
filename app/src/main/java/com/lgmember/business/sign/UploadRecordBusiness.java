package com.lgmember.business.sign;

import android.content.Context;

import com.lgmember.api.HttpHandler;
import com.lgmember.impl.ImgImpl;
import com.lgmember.impl.SignImpl;

import java.io.File;

/**
 * Created by Yanan_Wu on 2017/2/27.
 */

public class UploadRecordBusiness {
    private String path,timestamp;
    private String session_id;
    private Context context;
    private SignImpl signImpl;

    public UploadRecordBusiness(Context context, String session_id, String path,String timestamp) {
        super();
        this.context = context;
        this.session_id = session_id;
        this.path = path;
        this.timestamp = timestamp ;
    }

    // 先验证参数的可发性，再登陆
    public void uploadRecord() {
        signImpl = new SignImpl();
        signImpl.uploadRecond(session_id,path,timestamp,handler,context);
    }

    private UploadRecordResulHandler handler;

    public interface UploadRecordResulHandler extends HttpHandler {

        public void onUploadRecordSuccess(String s);

    }
    public void setHandler(UploadRecordResulHandler handler){
        this.handler = handler;
    }

    }