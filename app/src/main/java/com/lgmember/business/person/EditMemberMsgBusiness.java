package com.lgmember.business.person;

import android.content.Context;

import com.lgmember.api.HttpHandler;
import com.lgmember.impl.MemberImpl;
import com.lgmember.model.Member;

/**
 * Created by Yanan_Wu on 2017/3/17.
 */

public class EditMemberMsgBusiness {

    private Member member;
    private Context context;
    private MemberImpl memberImpl;

    public EditMemberMsgBusiness(Context context,Member member) {
        super();
        this.context = context;
        this.member = member;
    }

    // 先验证参数的可发性，再登陆
    public void editMemberMessage() {

        // 判断活动码是否有效
        memberImpl = new MemberImpl();
        memberImpl.editMemberMessage(member,handler,context);
    }

    private EditMemberMessageResulHandler handler;

    public interface EditMemberMessageResulHandler extends HttpHandler {

        public void onEditMemberMsgSuccess();

    }
    public void setHandler(EditMemberMessageResulHandler handler){
        this.handler = handler;
    }

}
