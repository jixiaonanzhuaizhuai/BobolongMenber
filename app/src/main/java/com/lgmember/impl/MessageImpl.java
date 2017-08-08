package com.lgmember.impl;

import android.content.Context;

import com.lgmember.api.HttpApi;
import com.lgmember.bean.HttpResultBean;
import com.lgmember.bean.MessageBean;
import com.lgmember.bean.MessageDetailResultBean;
import com.lgmember.bean.RemindListResultBean;
import com.lgmember.bean.RemindNumResultBean;
import com.lgmember.business.message.DeleteMsgBusiness;
import com.lgmember.business.message.MessageBusiness;
import com.lgmember.business.message.UpdateMessageStateBusiness;
import com.lgmember.business.message.RemindMessageListBusiness;
import com.lgmember.business.message.RemindNumBusiness;
import com.lgmember.business.message.UploadIfReadBusiness;
import com.lgmember.util.Common;
import com.lgmember.util.JsonUtil;
import com.tsy.sdk.myokhttp.MyOkHttp;
import com.tsy.sdk.myokhttp.response.JsonResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

public class MessageImpl extends HttpApi {

    private String TAG = "----MessageImpl----";


    public void getRemindNum(final RemindNumBusiness.RemindNumResultHandler handler, Context context){
        //判断没有网络应该如何处理
        if (!app.isNetWorkEnable(context)) {
            handler.onNetworkDisconnect();
        }
        MyOkHttp mMyOkhttp = new MyOkHttp(okHttpClient());
        mMyOkhttp.get()
                .url(Common.URL_NOREAD_MESSAGE_NUM)
                .tag(this)
                .enqueue(new JsonResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, JSONObject response) {

                        RemindNumResultBean remindNumResultBean = JsonUtil.parseJsonWithGson(response.toString(),RemindNumResultBean.class);

                        if (remindNumResultBean.getCode() == 0){
                            handler.onSuccess(remindNumResultBean.getData());
                        }else {
                            handler.onError(remindNumResultBean.getCode());
                        }
                    }
                    @Override
                    public void onFailure(int statusCode, String error_msg) {
                        handler.onFailed(statusCode,error_msg);
                    }
                });
    }

    public void getRemindList(int pageNo, int pageSize, int read_state, final RemindMessageListBusiness.RemindListResultHandler handler, Context context){
        //判断没有网络应该如何处理
        if (!app.isNetWorkEnable(context)) {
            handler.onNetworkDisconnect();
        }
        MyOkHttp mMyOkhttp = new MyOkHttp(okHttpClient());
        mMyOkhttp.get()
                .url(Common.URL_REMIND_LIST)
                .addParam("pageNo",String.valueOf(pageNo))
                .addParam("pageSize",String.valueOf(pageSize))
                .addParam("read_state",String.valueOf(read_state))
                .tag(this)
                .enqueue(new JsonResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, JSONObject response) {

                        RemindListResultBean remindListResultBean = JsonUtil.parseJsonWithGson(response.toString(),RemindListResultBean.class);

                        if (remindListResultBean.getCode() == 0){
                            handler.onSuccess(remindListResultBean);
                        }else {
                            handler.onError(remindListResultBean.getCode());
                        }

                    }
                    @Override
                    public void onFailure(int statusCode, String error_msg) {
                        handler.onFailed(statusCode,error_msg);
                    }
                });
    }

    public void getSysMessage(int pageNo, int pageSize, final MessageBusiness.MessageResultHandler handler, Context context){
        //判断没有网络应该如何处理
        if (!app.isNetWorkEnable(context)) {
            handler.onNetworkDisconnect();
        }

        MyOkHttp mMyOkhttp = new MyOkHttp(okHttpClient());
        mMyOkhttp.get()
                .url(Common.URL_MESSAGE_LIST)
                .addParam("pageNo",String.valueOf(pageNo))
                .addParam("pageSize",String.valueOf(pageSize))
                .tag(this)
                .enqueue(new JsonResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, JSONObject response) {

                        MessageBean messageBean = JsonUtil
                                .parseJsonWithGson(response.toString(),MessageBean.class);

                        if (messageBean.getCode() == 0){
                            handler.onSuccess(messageBean);
                        }else {
                            handler.onError(messageBean.getCode());
                        }

                    }
                    @Override
                    public void onFailure(int statusCode, String error_msg) {
                        handler.onFailed(statusCode,error_msg);
                    }
                });
    }

    public void ifRead(int remind_id, int unread, final UploadIfReadBusiness.UploadIfReadResultHandler handler, Context context){
        //判断没有网络应该如何处理
        if (!app.isNetWorkEnable(context)) {
            handler.onNetworkDisconnect();
        }
        MyOkHttp mMyOkhttp = new MyOkHttp(okHttpClient());
        mMyOkhttp.get()
                .url(Common.URL_IF_READ)
                .addParam("id",String.valueOf(remind_id))
                .addParam("unread",String.valueOf(unread))
                .tag(this)
                .enqueue(new JsonResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, JSONObject response) {
                        try {
                            int code = response.getInt("code");
                            if (code != 0){
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

    public void deleteMessage(int message_id, int state, final DeleteMsgBusiness.DeleteMsgResultHandler handler, Context context){
        //判断没有网络应该如何处理
        if (!app.isNetWorkEnable(context)) {
            handler.onNetworkDisconnect();
        }

        //http post的json数据格式：  {"name": "****","pwd": "******"}
        final JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("message_id",message_id);
            jsonObject.put("state", state);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        MyOkHttp mMyOkhttp = new MyOkHttp(okHttpClient());
        mMyOkhttp.post()
                .url(Common.URL_DELETE_MESSAGE)
                .jsonParams(jsonObject.toString())
                .tag(this)
                .enqueue(new JsonResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, JSONObject response) {
                        try {
                            int code = response.getInt("code");
                            if (code == 0){
                                handler.onDeleteMsgSuccess();
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

    public void updateMsgState(int message_id, int state, final UpdateMessageStateBusiness.UpdateMessageStateResultHandler handler, Context context){
        //判断没有网络应该如何处理
        if (!app.isNetWorkEnable(context)) {
            handler.onNetworkDisconnect();
        }

        //http post的json数据格式：  {"name": "****","pwd": "******"}
        final JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("message_id",message_id);
            jsonObject.put("state", state);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        MyOkHttp mMyOkhttp = new MyOkHttp(okHttpClient());
        mMyOkhttp.post()
                .url(Common.URL_UPDATE_MESSAGE_STATE)
                .jsonParams(jsonObject.toString())
                .tag(this)
                .enqueue(new JsonResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, JSONObject response) {
                        HttpResultBean httpResultBean = JsonUtil
                                .parseJsonWithGson(response.toString(),MessageBean.class);
                        if (httpResultBean.getCode() == 0){
                            handler.onUpdateMessageStateSuccess();
                        }else {
                            handler.onError(httpResultBean.getCode());
                        }
                    }
                    @Override
                    public void onFailure(int statusCode, String error_msg) {
                        handler.onFailed(statusCode,error_msg);
                    }
                });
    }
}
