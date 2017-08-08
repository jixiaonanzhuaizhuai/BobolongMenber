package com.lgmember.business.feedback;

import android.content.Context;

import com.lgmember.api.HttpHandler;
import com.lgmember.bean.FeedbackListBean;
import com.lgmember.impl.FeedbackImpl;

/**
 * Created by Yanan_Wu on 2017/3/6.
 */

public class FeedbackListBusiness {

    private int pageNo;
    private int pageSize;
    private Context context;
    private FeedbackImpl feedbackImpl;

    public FeedbackListBusiness(Context context, int pageNo,int pageSize) {
        super();
        this.context = context;
        this.pageNo = pageNo;
        this.pageSize = pageSize;
    }

    // 先验证参数的可发性，再登陆
    public void feedbackList() {
        // TODO 可能还要验证密码
        // 登陆
        feedbackImpl = new FeedbackImpl();
        feedbackImpl.feedbackList(pageNo,pageSize,handler,context);
    }

    private FeedbackListResultHandler handler;

    public interface FeedbackListResultHandler extends HttpHandler {
        //当参数为空
        public void onFeedbackListSuccess(FeedbackListBean feedbackListBean);

    }
    public void setHandler(FeedbackListResultHandler handler){
        this.handler = handler;
    }
}
