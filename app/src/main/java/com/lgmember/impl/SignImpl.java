package com.lgmember.impl;

import android.content.Context;
import android.util.Log;

import com.lgmember.api.HttpApi;
import com.lgmember.bean.MessageBean;
import com.lgmember.bean.SignListBean;
import com.lgmember.business.UploadImgBusiness;
import com.lgmember.business.message.MessageBusiness;
import com.lgmember.business.project.ActivityCodeBusiness;
import com.lgmember.business.sign.ScannerSignBusiness;
import com.lgmember.business.sign.SignListBusiness;
import com.lgmember.business.sign.UploadRecordBusiness;
import com.lgmember.util.Common;
import com.lgmember.util.JsonUtil;
import com.tsy.sdk.myokhttp.MyOkHttp;
import com.tsy.sdk.myokhttp.response.JsonResponseHandler;
import com.tsy.sdk.myokhttp.response.RawResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;

public class SignImpl extends HttpApi {

    private String TAG = "---SignImpl---";


    public void getSignList(int pageNo, int pageSize, final SignListBusiness.SignListResultHandler handler, Context context){
        //判断没有网络应该如何处理
        if (!app.isNetWorkEnable(context)) {
            handler.onNetworkDisconnect();
        }

        MyOkHttp mMyOkhttp = new MyOkHttp(okHttpClient());
        mMyOkhttp.get()
                .url(Common.URL_RECORD_LIST)
                .addParam("pageNo",String.valueOf(pageNo))
                .addParam("pageSize",String.valueOf(pageSize))
                .tag(this)
                .enqueue(new JsonResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, JSONObject response) {
                        Log.i(TAG,response.toString());

                        SignListBean signListBean = JsonUtil.parseJsonWithGson(response.toString(),SignListBean.class);

                        if (signListBean.getCode() == 0){
                            handler.onSuccess(signListBean);
                        }else {
                            handler.onError(signListBean.getCode());
                        }

                    }
                    @Override
                    public void onFailure(int statusCode, String error_msg) {
                        handler.onFailed(statusCode,error_msg);
                    }
                });
    }

    public void getProjectSign(String activitySignCode, final ActivityCodeBusiness.ActivityCodeResulHandler handler, Context context){
        //判断没有网络应该如何处理
        if (!app.isNetWorkEnable(context)) {
            handler.onNetworkDisconnect();
        }
        MyOkHttp mMyOkhttp = new MyOkHttp(okHttpClient());
        mMyOkhttp.get()
                .url(Common.URL_PROJECT_SIGN)
                .addParam("project_id",activitySignCode)
                .tag(this)
                .enqueue(new JsonResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, JSONObject response) {
                        Log.i(TAG, "onSuccess: ------"+response);

                        try {
                            int code = response.getInt("code");
                            String resultString = null;
                            if (code == 0){
                                int result = response.getInt("result");
                                if (result == 0){
                                    resultString = "签到成功";
                                }else if (result == 1){
                                    resultString = "已签到";
                                }else if (result == 2){
                                    resultString = "请报名后再签到";
                                }else {
                                    resultString = "活动码无效";
                                }
                                handler.onActivityCodeSuccess(resultString);
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

    public void getClubProjectSign(String activitySignCode, final ActivityCodeBusiness.ActivityCodeResulHandler handler, Context context){
        //判断没有网络应该如何处理
        if (!app.isNetWorkEnable(context)) {
            handler.onNetworkDisconnect();
        }
        MyOkHttp mMyOkhttp = new MyOkHttp(okHttpClient());
        mMyOkhttp.get()
                .url(Common.URL_CLUB_PROJECT_SIGN)
                .addParam("project_id",activitySignCode)
                .tag(this)
                .enqueue(new JsonResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, JSONObject response) {

                        try {
                            int code = response.getInt("code");
                            String resultString = null;
                            if (code == 0){
                                int result = response.getInt("result");
                                if (result == 0){
                                    resultString = "签到成功";
                                }else if (result == 1){
                                    resultString = "已签到";
                                }else if (result == 2){
                                    resultString = "请报名后再签到";
                                }else {
                                    resultString = "活动码无效";
                                }
                                handler.onActivityCodeSuccess(resultString);
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


    public void uploadRecond(String session_id, String path, String timestamp,final UploadRecordBusiness.UploadRecordResulHandler handler, Context context) {

        //判断没有网络应该如何处理
        if (!app.isNetWorkEnable(context)) {
            handler.onNetworkDisconnect();
        }

        File file = new File(path);

        Log.i(TAG, "uploadRecond: "+timestamp);

        MyOkHttp mMyOkhttp = new MyOkHttp(okHttpClient());
        mMyOkhttp.upload()
                .url(Common.URL_UPLOAD_RECORD + "?id=" +
                        session_id + "&timestamp="+ timestamp)
                .addFile("record", file)
                .tag(this)
                .enqueue(new JsonResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode,
                                          final JSONObject response) {
                        try {
                            int code = response.getInt("code");
                            if (code == 0) {
                                handler.onUploadRecordSuccess("签到成功");
                            } else {
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

    public void scannerSign(String url, final ScannerSignBusiness.ScannerSignResulHandler handler, Context context) {

        //判断没有网络应该如何处理
        if (!app.isNetWorkEnable(context)) {
            handler.onNetworkDisconnect();
        }

        MyOkHttp mMyOkhttp = new MyOkHttp(okHttpClient());
        mMyOkhttp.post()
                .url(url)
                .tag(this)
                .enqueue(new RawResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, String response) {
                        Log.i(TAG,"2222"+response.toString());
                    }

                    @Override
                    public void onFailure(int statusCode, String error_msg) {
                        Log.i(TAG,"1111"+statusCode+error_msg);
                    }
                });
    }
}
