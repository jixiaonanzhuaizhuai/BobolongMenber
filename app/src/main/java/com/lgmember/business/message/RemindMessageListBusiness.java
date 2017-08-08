package com.lgmember.business.message;

import android.content.Context;

import com.lgmember.api.HttpHandler;
import com.lgmember.bean.MessageBean;
import com.lgmember.bean.RemindListResultBean;
import com.lgmember.impl.MemberImpl;
import com.lgmember.impl.MessageImpl;
import com.lgmember.model.Member;

/**
 * Created by Yanan_Wu on 2017/3/17.
 */

public class RemindMessageListBusiness {

    private int pageNo;
    private int pageSize;
    private int read_state;
    private Context context;
    private MessageImpl messageImpl;

    public RemindMessageListBusiness(Context context, int pageNo, int pageSize,int read_state) {
        super();
        this.context = context;
        this.pageNo = pageNo;
        this.pageSize = pageSize;
        this.read_state = read_state;
    }

    // 先验证参数的可发性，再登陆
    public void getRemindListMessage() {
        // TODO 可能还要验证密码
        // 登陆
        messageImpl = new MessageImpl();
        messageImpl.getRemindList(pageNo,pageSize,read_state,handler,context);
    }

    private RemindListResultHandler handler;

    public interface RemindListResultHandler extends HttpHandler {
        //当参数为空
        public void onSuccess(RemindListResultBean bean);

    }
    public void setHandler(RemindListResultHandler handler){
        this.handler = handler;
    }
}
