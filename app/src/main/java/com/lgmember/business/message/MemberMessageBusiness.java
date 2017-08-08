package com.lgmember.business.message;

import android.content.Context;
import android.graphics.Bitmap;

import com.lgmember.api.HttpHandler;
import com.lgmember.impl.MemberImpl;
import com.lgmember.model.Member;

/**
 * Created by Yanan_Wu on 2017/3/17.
 */

public class MemberMessageBusiness {

    private Context context;
    private MemberImpl memberImpl;

    public MemberMessageBusiness(Context context) {
        super();
        this.context = context;
    }

    // 先验证参数的可发性，再登陆
    public void getMemberMessage() {

        // 判断活动码是否有效
        memberImpl = new MemberImpl();
        memberImpl.getMemberMessage(handler,context);
    }

    private MemberMessageResulHandler handler;

    public interface MemberMessageResulHandler extends HttpHandler {

        public void onSuccess(Member member);


    }
    public void setHandler(MemberMessageResulHandler handler){
        this.handler = handler;
    }

}
