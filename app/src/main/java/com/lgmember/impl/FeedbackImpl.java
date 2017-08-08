package com.lgmember.impl;

import android.content.Context;
import android.util.Log;

import com.lgmember.api.HttpApi;
import com.lgmember.bean.FeedbackListBean;
import com.lgmember.business.feedback.CreateFeedbackBusiness;
import com.lgmember.business.feedback.DeleteFeedbackBusiness;
import com.lgmember.business.feedback.FeedbackListBusiness;
import com.lgmember.util.Common;
import com.lgmember.util.JsonUtil;
import com.tsy.sdk.myokhttp.MyOkHttp;
import com.tsy.sdk.myokhttp.response.JsonResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class FeedbackImpl extends HttpApi {

    private String TAG = "---FeedbackImpl---";

    public void createFeedback(String content, final CreateFeedbackBusiness.CreateFeedbackResultHandler handler, Context context) {
        //判断没有网络应该如何处理
        if (!app.isNetWorkEnable(context)) {
            handler.onNetworkDisconnect();
        }

        //http post的json数据格式：  {"name": "****","pwd": "******"}
        JSONObject jsonObject = new JSONObject();;
        try {
            jsonObject.put("content", content);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        MyOkHttp mMyOkhttp = new MyOkHttp(okHttpClient());
        mMyOkhttp.post()
                .url(Common.URL_CREATE_FEEDBACK)
                .jsonParams(jsonObject.toString())          //与 params 不共存 以 jsonParams 优先
                .tag(this)
                .enqueue(new JsonResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, final JSONObject response) {

                        try {
                            int code = response.getInt("code");
                            if (code == 0){
                                handler.onCreateFeedbackSuccess();
                            }else {
                                handler.onError(code);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    @Override
                    public void onFailure(int statusCode, String error_msg) {
                        handler.onFailed(statusCode,error_msg);

                    }
                });
    }
    public void feedbackList(int pageNo, int pageSize, final FeedbackListBusiness.FeedbackListResultHandler handler, Context context) {
        //判断没有网络应该如何处理
        if (!app.isNetWorkEnable(context)) {
            handler.onNetworkDisconnect();
        }
        MyOkHttp mMyOkhttp = new MyOkHttp(okHttpClient());
        mMyOkhttp.get()
                .url(Common.URL_FEEDBACK_LIST)
                .addParam("pageNo",String.valueOf(pageNo))
                .addParam("pageSize",String.valueOf(pageSize))
                .tag(this)
                .enqueue(new JsonResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, final JSONObject response) {
                        FeedbackListBean feedbackListBean = JsonUtil.parseJsonWithGson(response.toString(),FeedbackListBean.class);
                        if (feedbackListBean.getCode() == 0){
                            handler.onFeedbackListSuccess(feedbackListBean);
                        }else {
                            handler.onError(feedbackListBean.getCode());
                        }
                    }
                    @Override
                    public void onFailure(int statusCode, String error_msg) {
                        handler.onFailed(statusCode,error_msg);

                    }
                });
    }
    public void deleteFeedback(List ids, final DeleteFeedbackBusiness.DeleteFeedbackResultHandler handler, Context context) {
        //判断没有网络应该如何处理
        if (!app.isNetWorkEnable(context)) {
            handler.onNetworkDisconnect();
        }

        //http post的json数据格式：  {"name": "****","pwd": "******"}
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("id", ids);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        MyOkHttp mMyOkhttp = new MyOkHttp(okHttpClient());
        mMyOkhttp.post()
                .url(Common.URL_DELETE_FEEDBACK)
                .jsonParams(jsonObject.toString())          //与 params 不共存 以 jsonParams 优先
                .tag(this)
                .enqueue(new JsonResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, final JSONObject response) {

                        try {
                            int code = response.getInt("code");
                            if (code == 0){
                                handler.onDeleteFeedbackSuccess();
                            }else {
                                handler.onError(code);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    @Override
                    public void onFailure(int statusCode, String error_msg) {
                        handler.onFailed(statusCode,error_msg);

                    }
                });
    }
}
