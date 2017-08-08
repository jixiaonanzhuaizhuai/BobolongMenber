package com.lgmember.business.message;

import android.content.Context;

import com.lgmember.api.HttpHandler;
import com.lgmember.bean.HistoryScoresBean;
import com.lgmember.bean.MessageBean;
import com.lgmember.impl.MessageImpl;
import com.lgmember.impl.ScoresImpl;
import com.lgmember.model.Message;

/**
 * Created by Yanan_Wu on 2017/3/6.
 */

public class MessageBusiness {

    private int pageNo;
    private int pageSize;
    private Context context;
    private MessageImpl messageImpl;

    public MessageBusiness(Context context, int pageNo, int pageSize) {
        super();
        this.context = context;
        this.pageNo = pageNo;
        this.pageSize = pageSize;
    }

    // 先验证参数的可发性，再登陆
    public void getSysMessage() {
        // TODO 可能还要验证密码
        // 登陆
        messageImpl = new MessageImpl();
        messageImpl.getSysMessage(pageNo,pageSize,handler,context);
    }

    private MessageResultHandler handler;

    public interface MessageResultHandler extends HttpHandler {
        //当参数为空
        public void onSuccess(MessageBean bean);

    }
    public void setHandler(MessageResultHandler handler){
        this.handler = handler;
    }
}
