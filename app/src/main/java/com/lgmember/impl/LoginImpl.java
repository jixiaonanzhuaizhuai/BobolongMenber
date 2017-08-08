package com.lgmember.impl;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import com.lgmember.api.HttpApi;
import com.lgmember.bean.HttpResultBean;
import com.lgmember.business.ApkBusiness;
import com.lgmember.business.ShowNetworkImgBusiness;
import com.lgmember.business.VersionBusiness;
import com.lgmember.business.login.GuestLoginBusiness;
import com.lgmember.business.login.IfGuestLoginBusiness;
import com.lgmember.business.login.IfNeedCaptBusiness;
import com.lgmember.business.login.LoginBusiness;
import com.lgmember.business.person.ForgetPwdBusiness;
import com.lgmember.business.person.MemberNameBusiness;
import com.lgmember.util.Common;
import com.lgmember.util.JsonUtil;
import com.tsy.sdk.myokhttp.MyOkHttp;
import com.tsy.sdk.myokhttp.response.DownloadResponseHandler;
import com.tsy.sdk.myokhttp.response.JsonResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;

public class LoginImpl extends HttpApi {

    private String TAG = "---LoginImpl----";

    public void getApkFile(final ApkBusiness.ApkResulHandler handler, Context context) {

        //判断没有网络应该如何处理
        if (!app.isNetWorkEnable(context)) {
            handler.onNetworkDisconnect();
        }
        MyOkHttp mMyOkhttp = new MyOkHttp(okHttpClient());



        mMyOkhttp.download()
                .url(Common.URL_APK)
                /*.filePath(Environment.getExternalStorageDirectory() + "/LUGUANG.apk")*/
                .filePath(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath()+"/bobolong.apk")
                .tag(this)
                .enqueue(new DownloadResponseHandler() {
                    @Override
                    public void onStart(long totalBytes) {
                        /*Log.d(TAG, "doDownload onStart");*/
                    }

                    @Override
                    public void onFinish(File downloadFile) {
                        //Log.d(TAG, "doDownload onFinish:");
                        handler.onApkSuccess(downloadFile);
                    }

                    @Override
                    public void onProgress(long currentBytes, long totalBytes) {
                        handler.onProgress(currentBytes,totalBytes);
                    }

                    @Override
                    public void onFailure(String error_msg) {
                       handler.onApkFailed("Apk下载失败");
                    }
                });
    }



    public void getVersion(final VersionBusiness.VersionResulHandler handler, Context context) {
        //判断没有网络应该如何处理
        if (!app.isNetWorkEnable(context)) {
            handler.onNetworkDisconnect();
        }
        MyOkHttp mMyOkhttp = new MyOkHttp(okHttpClient());
        mMyOkhttp.post()
                .url(Common.URL_VERSION)
                .tag(this)
                .enqueue(new JsonResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, final JSONObject response) {

                        try {
                            int code = response.getInt("code");
                            String version = response.getString("version");
                            if (code == 0){
                                handler.onSuccess(version);
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

    public void getNeedCapt(final IfNeedCaptBusiness.IfNeedCaptResultHandler handler, Context context) {

        //判断没有网络应该如何处理
        if (!app.isNetWorkEnable(context)) {
            handler.onNetworkDisconnect();
        }
        MyOkHttp mMyOkhttp = new MyOkHttp(okHttpClient());
        mMyOkhttp.get()
                .url(Common.URL_LOGIN)
                //与 params 不共存 以 jsonParams 优先
                .tag(this)
                .enqueue(new JsonResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, final JSONObject response) {

                        try {
                            int code = response.getInt("code");
                            boolean need_capt = response.getBoolean("need_capt");
                            if (code == 0){
                                handler.onNeedCaptSuccess(need_capt);
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

    public void guestLogin(final GuestLoginBusiness.GUestLoginResultHandler handler, Context context) {

        //判断没有网络应该如何处理
        if (!app.isNetWorkEnable(context)) {
            handler.onNetworkDisconnect();
        }
        MyOkHttp mMyOkhttp = new MyOkHttp(okHttpClient());
        mMyOkhttp.get()
                .url(Common.URL_GUEST_LOGIN)
                .tag(this)
                .enqueue(new JsonResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, final JSONObject response) {

                        Log.i(TAG, "onSuccess111111: "+response.toString());
                        try {
                            int code = response.getInt("code");
                            if (code == 0){
                                handler.onGuestLoginSuccess();
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

    public void ifGuestLogin(final IfGuestLoginBusiness.IfGUestLoginResultHandler handler, Context context) {
        //判断没有网络应该如何处理
        if (!app.isNetWorkEnable(context)) {
            handler.onNetworkDisconnect();
        }
        MyOkHttp mMyOkhttp = new MyOkHttp(okHttpClient());
        mMyOkhttp.get()
                .url(Common.URL_IF_GUEST_LOGIN)
                .tag(this)
                .enqueue(new JsonResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, final JSONObject response) {
                        Log.i(TAG, "onSuccess: 666666"+response.toString());
                        try {
                            int code = response.getInt("code");
                            if (code == 0) {
                                String name = response.getString("name");

                                    handler.onIfGuestLoginSuccess(name);

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

    public void login(final String loginName, String password,String cpt,boolean need_capt, final LoginBusiness.LoginResultHandler handler, Context context) {

        //判断没有网络应该如何处理
        if (!app.isNetWorkEnable(context)) {
            handler.onNetworkDisconnect();
        }
        //http post的json数据格式：  {"name": "****","pwd": "******"}
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("name", loginName);
            jsonObject.put("pwd", password);
            jsonObject.put("cpt",cpt);
            jsonObject.put("need_capt",need_capt);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        MyOkHttp mMyOkhttp = new MyOkHttp(okHttpClient());
        mMyOkhttp.post()
                .url(Common.URL_LOGIN)
                .jsonParams(jsonObject.toString())
                //与 params 不共存 以 jsonParams 优先
                .tag(this)
                .enqueue(new JsonResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, final JSONObject response) {

                        try {
                            int state = response.getInt("state");
                            boolean need_capt = response.getBoolean("need_capt");
                            handler.onSuccess(state,need_capt);

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

    //忘记密码

    public void forgetPwd(String mobile, String capt, String sms_capt_token, String password, final ForgetPwdBusiness.ForgetPwdResulHandler handler, Context context) {


        //判断没有网络应该如何处理
        if (!app.isNetWorkEnable(context)) {
            handler.onNetworkDisconnect();
        }

        //http post的json数据格式：  {"name": "****","pwd": "******"}
        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("mobile", mobile);
            jsonObject.put("capt", capt);
            jsonObject.put("sms_capt_token",sms_capt_token);
            jsonObject.put("password",password);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        MyOkHttp mMyOkhttp = new MyOkHttp(okHttpClient());
        mMyOkhttp.post()
                .url(Common.URL_FORGET_PASSWORD)
                .jsonParams(jsonObject.toString())          //与 params 不共存 以 jsonParams 优先
                .tag(this)
                .enqueue(new JsonResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, final JSONObject response) {

                        HttpResultBean httpResultBean = JsonUtil.parseJsonWithGson(response.toString(),HttpResultBean.class);
                        if (httpResultBean.getCode() == 0){
                            handler.onSuccess(httpResultBean.getCode());
                        }else {
                            handler.onError(httpResultBean.getCode());
                        }
                    }

                    @Override
                    public void onFailure(int statusCode, String error_msg) {
                        handler.onFailed(statusCode, error_msg);

                    }
                });
    }

    //判断cookie是否有效
    public void getMemberName(final MemberNameBusiness.MemberNameResulHandler handler, Context context) {


        //判断没有网络应该如何处理
        if (!app.isNetWorkEnable(context)) {
            handler.onNetworkDisconnect();
        }

        MyOkHttp mMyOkhttp = new MyOkHttp(okHttpClient());
        mMyOkhttp.get()
                .url(Common.URL_GET_MEMBER_NAME)
                .tag(this)
                .enqueue(new JsonResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, final JSONObject response) {
                        HttpResultBean httpResultBean = JsonUtil.parseJsonWithGson(response.toString(),HttpResultBean.class);
                        int code = httpResultBean.getCode();
                        if (code == 0){
                            String name = null;
                            try {
                                name = response.getString("name");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            handler.onMemberNameSuccess(name);
                        }else if (code == 1){
                            handler.onMemberNameError(code);
                        }else {
                            handler.onError(code);
                        }
                    }

                    @Override
                    public void onFailure(int statusCode, String error_msg) {
                        handler.onFailed(statusCode, error_msg);

                    }
                });
    }
}
