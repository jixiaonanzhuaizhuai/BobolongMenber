package com.lgmember.impl;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import com.lgmember.api.HttpApi;
import com.lgmember.business.SmsCodeIfCorrectBusiness;
import com.lgmember.business.login.ImgCptBusiness;
import com.lgmember.business.SmsCodeBusiness;
import com.lgmember.util.Common;
import com.tsy.sdk.myokhttp.MyOkHttp;
import com.tsy.sdk.myokhttp.response.DownloadResponseHandler;
import com.tsy.sdk.myokhttp.response.JsonResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class CodeImpl extends HttpApi {

    private String TAG = "---CodeImpl---";

    public void smsCodeIfCorrect(String token, String capt, final SmsCodeIfCorrectBusiness.ValidateSmsCodeResultHandler handler, Context context){
        //判断没有网络应该如何处理
        if (!app.isNetWorkEnable(context)) {
            handler.onNetworkDisconnect();
        }

        //http post的json数据格式：  {"name": "****","pwd": "******"}
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("token", token);
            jsonObject.put("capt", capt);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        MyOkHttp mMyOkhttp = new MyOkHttp(okHttpClient());
        mMyOkhttp.post()
                .url(Common.URL_VALIDATE_SMS_CAPT)
                .jsonParams(jsonObject.toString())
                .tag(this)
                .enqueue(new JsonResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, JSONObject response) {
                        try {
                            int code = response.getInt("code");
                            if (code == 0){
                                handler.onValidateSmsSuccess();
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

    public void getCode(String phoneTxt, final SmsCodeBusiness.GetCodeResultHandler handler, Context context){
        //判断没有网络应该如何处理
        if (!app.isNetWorkEnable(context)) {
            handler.onNetworkDisconnect();
        }
        MyOkHttp mMyOkhttp = new MyOkHttp(okHttpClient());
        mMyOkhttp.get()
                .url(Common.URL_REQUEST_CODE)
                .addParam("mobile",phoneTxt)
                .tag(this)
                .enqueue(new JsonResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, JSONObject response) {
                        try {
                            int code = response.getInt("code");
                            if (code == 0){
                                handler.onRequestCodeSuccess(response.getString("token"));
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

    //手机验证码

    public void getImgCpt(final ImgCptBusiness.ImgCptResultHandler handler, Context context){
        //判断没有网络应该如何处理
        if (!app.isNetWorkEnable(context)) {
            handler.onNetworkDisconnect();
        }

        String url = Common.URL_CPT;

        MyOkHttp mMyOkhttp = new MyOkHttp(okHttpClient());

        mMyOkhttp.download()
                .url(url)
                .filePath(Environment.getExternalStorageDirectory() + "/cpt.jpg")
                .tag(this)
                .enqueue(new DownloadResponseHandler() {
                    @Override
                    public void onStart(long totalBytes) {
                        /*Log.d(TAG, "doDownload onStart");*/

                    }

                    @Override
                    public void onFinish(File downloadFile) {
                        //Log.d(TAG, "doDownload onFinish:");
                        handler.onImgCptSuccess();
                    }

                    @Override
                    public void onProgress(long currentBytes, long totalBytes) {
                        /*Log.d(TAG, "doDownload onProgress:" + currentBytes + "/" + totalBytes);*/
                    }

                    @Override
                    public void onFailure(String error_msg) {
                        handler.onImgCptFailed("加载网络图片失败");
                    }
                });

    }
}
