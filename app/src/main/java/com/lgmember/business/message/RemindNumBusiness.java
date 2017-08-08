package com.lgmember.business.message;

import android.content.Context;

import com.lgmember.api.HttpHandler;
import com.lgmember.bean.RemindListResultBean;
import com.lgmember.impl.MessageImpl;

import java.util.List;

/**
 * Created by Yanan_Wu on 2017/3/17.
 */

public class RemindNumBusiness {

    private Context context;
    private MessageImpl messageImpl;

    public RemindNumBusiness(Context context) {
        super();
        this.context = context;
    }

    // 先验证参数的可发性，再登陆
    public void getRemindNum() {
        // TODO 可能还要验证密码
        // 登陆
        messageImpl = new MessageImpl();
        messageImpl.getRemindNum(handler,context);
    }

    private RemindNumResultHandler handler;

    public interface RemindNumResultHandler extends HttpHandler {
        //当参数为空
        public void onSuccess(List<Integer> list);
    }
    public void setHandler(RemindNumResultHandler handler){
        this.handler = handler;
    }
}
