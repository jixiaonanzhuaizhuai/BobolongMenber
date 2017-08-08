package com.lgmember.impl;

import android.content.Context;

import com.lgmember.api.HttpApi;
import com.lgmember.api.HttpHandler;
import com.lgmember.business.RegisterBusiness;
import com.lgmember.util.Common;
import com.tsy.sdk.myokhttp.MyOkHttp;
import com.tsy.sdk.myokhttp.response.JsonResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

public class RegisterImpl extends HttpApi {

    //登录
    public void register(String phoneTxt, String codeTxt, String sms_capt_tokenTxt, final RegisterBusiness.RegisterResultHandler handler, Context context) {
        //判断没有网络应该如何处理
        if (!app.isNetWorkEnable(context)) {
            handler.onNetworkDisconnect();
        }

        //http post的json数据格式：  {"name": "****","pwd": "******"}
        JSONObject jsonObject = new JSONObject();;
        try {
            jsonObject.put("mobile", phoneTxt);
            jsonObject.put("capt", codeTxt);
            jsonObject.put("sms_capt_token",sms_capt_tokenTxt);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        MyOkHttp mMyOkhttp = new MyOkHttp(okHttpClient());
        mMyOkhttp.post()
                .url(Common.URL_REGISTER)
                .jsonParams(jsonObject.toString())          //与 params 不共存 以 jsonParams 优先
                .tag(this)
                .enqueue(new JsonResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, final JSONObject response) {
                        handler.onSuccess(response);
                    }

                    @Override
                    public void onFailure(int statusCode, String error_msg) {
                        handler.onFailed(statusCode,error_msg);

                    }
                });
    }
}
