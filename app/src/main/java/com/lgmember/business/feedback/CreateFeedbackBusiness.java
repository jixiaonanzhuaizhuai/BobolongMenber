package com.lgmember.business.feedback;

import android.content.Context;

import com.lgmember.api.HttpHandler;
import com.lgmember.impl.CardImpl;
import com.lgmember.impl.FeedbackImpl;
import com.lgmember.model.Card;

/**
 * Created by Yanan_Wu on 2017/3/6.
 */

public class CreateFeedbackBusiness {

    private String content;
    private Context context;
    private FeedbackImpl feedbackImpl;

    public CreateFeedbackBusiness(Context context, String content) {
        super();
        this.context = context;
        this.content = content;
    }

    // 先验证参数的可发性，再登陆
    public void createFeedback() {
        // TODO 可能还要验证密码
        // 登陆
        feedbackImpl = new FeedbackImpl();
        feedbackImpl.createFeedback(content,handler,context);
    }

    private CreateFeedbackResultHandler handler;

    public interface CreateFeedbackResultHandler extends HttpHandler {
        //当参数为空
        public void onCreateFeedbackSuccess();

    }
    public void setHandler(CreateFeedbackResultHandler handler){
        this.handler = handler;
    }
}
