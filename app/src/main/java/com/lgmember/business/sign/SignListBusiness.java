package com.lgmember.business.sign;

import android.content.Context;

import com.lgmember.api.HttpHandler;
import com.lgmember.bean.MessageBean;
import com.lgmember.bean.SignListBean;
import com.lgmember.impl.MessageImpl;
import com.lgmember.impl.SignImpl;

/**
 * Created by Yanan_Wu on 2017/3/6.
 */

public class SignListBusiness {

    private int pageNo;
    private int pageSize;
    private Context context;
    private SignImpl signImpl;

    public SignListBusiness(Context context, int pageNo, int pageSize) {
        super();
        this.context = context;
        this.pageNo = pageNo;
        this.pageSize = pageSize;
    }

    // 先验证参数的可发性，再登陆
    public void getSignList() {
        // TODO 可能还要验证密码
        // 登陆
        signImpl = new SignImpl();
        signImpl.getSignList(pageNo,pageSize,handler,context);
    }

    private SignListResultHandler handler;

    public interface SignListResultHandler extends HttpHandler {
        //当参数为空
        public void onSuccess(SignListBean signListBean);

    }
    public void setHandler(SignListResultHandler handler){
        this.handler = handler;
    }
}
