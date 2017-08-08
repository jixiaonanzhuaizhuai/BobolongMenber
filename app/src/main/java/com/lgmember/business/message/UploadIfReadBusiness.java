package com.lgmember.business.message;

import android.content.Context;

import com.lgmember.api.HttpHandler;
import com.lgmember.impl.MessageImpl;

/**
 * Created by Yanan_Wu on 2017/3/6.
 */

public class UploadIfReadBusiness {

    private int remind_id;
    private int unread;
    private Context context;
    private MessageImpl messageImpl;

    public UploadIfReadBusiness(Context context, int remind_id, int unread) {
        super();
        this.context = context;
        this.remind_id = remind_id;
        this.unread = unread;
    }

    // 先验证参数的可发性，再登陆
    public void ifRead() {
        // TODO 可能还要验证密码
        // 登陆
        messageImpl = new MessageImpl();
        messageImpl.ifRead(remind_id,unread,handler,context);
    }

    private UploadIfReadResultHandler handler;

    public interface UploadIfReadResultHandler extends HttpHandler {

    }
    public void setHandler(UploadIfReadResultHandler handler){
        this.handler = handler;
    }
}
