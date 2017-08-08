package com.lgmember.business.person;

import android.content.Context;
import android.text.TextUtils;

import com.lgmember.api.HttpHandler;
import com.lgmember.impl.LoginImpl;
import com.lgmember.util.StringUtil;

/**
 * Created by Yanan_Wu on 2017/2/27.
 */

public class MemberNameBusiness {
    private Context context;
    private LoginImpl loginImpl;

    public MemberNameBusiness(Context context) {
        super();
        this.context = context;
    }

    // 先验证参数的可发性，再登陆
    public void getMemberName() {


        // 判断活动码是否有效
        loginImpl = new LoginImpl();
        loginImpl.getMemberName(handler,context);
    }

    private MemberNameResulHandler handler;

    public interface MemberNameResulHandler extends HttpHandler {

        public void onMemberNameSuccess(String name);
        public void onMemberNameError(int code);


    }
    public void setHandler(MemberNameResulHandler handler){
        this.handler = handler;
    }

    }