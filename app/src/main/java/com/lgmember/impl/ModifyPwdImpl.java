package com.lgmember.impl;

import android.content.Context;
import android.util.Log;

import com.lgmember.api.HttpApi;
import com.lgmember.business.person.ModifyPwdBusiness;
import com.lgmember.util.Common;
import com.tsy.sdk.myokhttp.MyOkHttp;
import com.tsy.sdk.myokhttp.response.JsonResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

public class ModifyPwdImpl extends HttpApi {

    public void modifyPwd(String oldPwdTxt, String newPwdTxt, final ModifyPwdBusiness.ModifyResultHandler handler, Context context) {
        //判断没有网络应该如何处理
        if (!app.isNetWorkEnable(context)) {
            handler.onNetworkDisconnect();
        }
        //http post的json数据格式：  {"name": "****","pwd": "******"}
        JSONObject jsonObject = new JSONObject();
        ;
        try {
            jsonObject.put("old_pwd", oldPwdTxt);
            jsonObject.put("new_pwd", newPwdTxt);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        MyOkHttp mMyOkhttp = new MyOkHttp(okHttpClient());
        mMyOkhttp.post()
                .url(Common.URL_MODIFY_PWD)
                .jsonParams(jsonObject.toString())          //与 params 不共存 以 jsonParams 优先
                .tag(this)
                .enqueue(new JsonResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, final JSONObject response) {
                        try {
                            int code = response.getInt("code");
                            if (code == 0){
                                handler.onSuccess("修改成功");
                            }else {
                                handler.onError(code);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    @Override
                    public void onFailure(int statusCode, String error_msg) {
                        handler.onFailed(statusCode, error_msg);
                    }
                });

    }
}
