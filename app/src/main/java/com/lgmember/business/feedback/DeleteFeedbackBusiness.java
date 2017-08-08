package com.lgmember.business.feedback;

import android.content.Context;

import com.lgmember.api.HttpHandler;
import com.lgmember.bean.FeedbackListBean;
import com.lgmember.impl.FeedbackImpl;

import java.util.List;

/**
 * Created by Yanan_Wu on 2017/3/6.
 */

public class DeleteFeedbackBusiness {

    private List ids;
    private Context context;
    private FeedbackImpl feedbackImpl;

    public DeleteFeedbackBusiness(Context context, List ids) {
        super();
        this.context = context;
        this.ids = ids;
    }

    // 先验证参数的可发性，再登陆
    public void deleteFeedback() {
        // TODO 可能还要验证密码
        // 登陆
        feedbackImpl = new FeedbackImpl();
        feedbackImpl.deleteFeedback(ids,handler,context);
    }

    private DeleteFeedbackResultHandler handler;

    public interface DeleteFeedbackResultHandler extends HttpHandler {
        //当参数为空
        public void onDeleteFeedbackSuccess();

    }
    public void setHandler(DeleteFeedbackResultHandler handler){
        this.handler = handler;
    }
}
