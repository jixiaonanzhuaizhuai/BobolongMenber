package com.lgmember.impl;

import android.content.Context;
import android.util.Log;

import com.lgmember.api.HttpApi;
import com.lgmember.business.person.CertificationBusiness;
import com.lgmember.model.Certification;
import com.lgmember.util.Common;
import com.tsy.sdk.myokhttp.MyOkHttp;
import com.tsy.sdk.myokhttp.response.JsonResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

public class CertificationImpl extends HttpApi {


    public void certificationMsg(Certification certification, final CertificationBusiness.CertificationResulHandler handler, final Context context) {

        //判断没有网络应该如何处理
        if (!app.isNetWorkEnable(context)) {
            handler.onNetworkDisconnect();
        }

        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("name",certification.getName());
            jsonObject.put("idno",certification.getIdno());
          //  jsonObject.put("gender",certification.getGender());
            jsonObject.put("nation",certification.getNation());
            jsonObject.put("upload_session_id",certification.getUpload_session_id());
            jsonObject.put("capt",certification.getCapt());
            jsonObject.put("capt_token",certification.getCapt_token());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        MyOkHttp mMyOkhttp = new MyOkHttp(okHttpClient());
        mMyOkhttp.post()
                .url(Common.URL_CERTIFICATION)
                .jsonParams(jsonObject.toString())
                .tag(this)
                .enqueue(new JsonResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, final JSONObject response) {
                        Log.i("=------6660--", "onSuccess: "+response.toString());
                        try {
                            int code = response.getInt("code");
                            if (code == 0){
                                handler.onSuccess();
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
