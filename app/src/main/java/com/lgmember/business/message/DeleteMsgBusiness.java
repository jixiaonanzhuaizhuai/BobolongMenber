package com.lgmember.business.message;

import android.content.Context;

import com.lgmember.api.HttpHandler;
import com.lgmember.bean.MessageBean;
import com.lgmember.impl.MessageImpl;

/**
 * Created by Yanan_Wu on 2017/3/6.
 */

public class DeleteMsgBusiness {

    private int message_id;
    private int state;
    private Context context;
    private MessageImpl messageImpl;

    public DeleteMsgBusiness(Context context, int message_id, int state) {
        super();
        this.context = context;
        this.message_id = message_id;
        this.state = state;
    }

    // 先验证参数的可发性，再登陆
    public void deleteMessage() {
        // TODO 可能还要验证密码
        // 登陆
        messageImpl = new MessageImpl();
        messageImpl.deleteMessage(message_id,state,handler,context);
    }

    private DeleteMsgResultHandler handler;

    public interface DeleteMsgResultHandler extends HttpHandler {
        //当参数为空
        public void onDeleteMsgSuccess();

    }
    public void setHandler(DeleteMsgResultHandler handler){
        this.handler = handler;
    }
}
