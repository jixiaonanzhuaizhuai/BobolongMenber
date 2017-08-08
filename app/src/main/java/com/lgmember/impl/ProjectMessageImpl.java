package com.lgmember.impl;

import android.content.Context;
import android.util.Log;

import com.lgmember.api.HttpApi;
import com.lgmember.bean.HttpResultBean;
import com.lgmember.bean.ProjectMessageBean;
import com.lgmember.bean.TagsListResultBean;
import com.lgmember.business.project.JoinActivityBusiness;
import com.lgmember.business.project.ProjectMessageDetailBusiness;
import com.lgmember.business.project.ProjectMessageListBusiness;
import com.lgmember.business.project.TagsListBusiness;
import com.lgmember.model.ProjectMessage;
import com.lgmember.util.Common;
import com.lgmember.util.JsonUtil;
import com.tsy.sdk.myokhttp.MyOkHttp;
import com.tsy.sdk.myokhttp.response.JsonResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

public class ProjectMessageImpl extends HttpApi {

    private String TAG = "--ProjectMessageImpl--";

    public void getProjectMessageDetail(final int id, final ProjectMessageDetailBusiness.ProjectMessageDetailResultHandler handler, Context context){
        //判断没有网络应该如何处理
        if (!app.isNetWorkEnable(context)) {
            handler.onNetworkDisconnect();
        }

        MyOkHttp mMyOkhttp = new MyOkHttp(okHttpClient());
        mMyOkhttp.get()
                .url(Common.URL_PROJECT_MESSAGE_DETAIL)
                .addParam("id",String.valueOf(id))
                .tag(this)
                .enqueue(new JsonResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, JSONObject response) {
                        try {
                            int code = response.getInt("code");
                            if (code == 0){
                                JSONObject jsob = response.getJSONObject("data");
                                ProjectMessage projectMessage = JsonUtil.parseJsonWithGson(jsob.toString(),ProjectMessage.class);
                                handler.onProjectMessageDetailSuccess(projectMessage);
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


    public void join(int project_id, final JoinActivityBusiness.JoinActivityResulHandler handler, Context context){
        //判断没有网络应该如何处理
        if (!app.isNetWorkEnable(context)) {
            handler.onNetworkDisconnect();
        }

        MyOkHttp mMyOkhttp = new MyOkHttp(okHttpClient());
        mMyOkhttp.get()
                .url(Common.URL_ACTIVITY_JOIN)
                .addParam("id",String.valueOf(project_id))
                .tag(this)
                .enqueue(new JsonResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, JSONObject response) {

                        HttpResultBean httpResultBean = JsonUtil
                                .parseJsonWithGson(response.toString(),
                                        HttpResultBean.class);

                        if (httpResultBean.getCode() == 0){
                            handler.onSuccess("报名成功");
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

    public void getTagsList(int tab, final TagsListBusiness.TagsListResulHandler handler, Context context){
        //判断没有网络应该如何处理
        if (!app.isNetWorkEnable(context)) {
            handler.onNetworkDisconnect();
        }

        MyOkHttp mMyOkhttp = new MyOkHttp(okHttpClient());
        mMyOkhttp.get()
                .url(Common.URL_TAGS_LIST)
                .addParam("tab",String.valueOf(tab))
                .tag(this)
                .enqueue(new JsonResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, JSONObject response) {

                        Log.i(TAG,"8888"+response.toString());

                        TagsListResultBean tagsListResultBean = JsonUtil.parseJsonWithGson(response.toString(),TagsListResultBean.class);
                        if (tagsListResultBean.getCode() == 0){
                            handler.onSuccess(tagsListResultBean);
                        }else {
                            handler.onError(tagsListResultBean.getCode());
                        }
                    }
                    @Override
                    public void onFailure(int statusCode, String error_msg) {
                        handler.onFailed(statusCode,error_msg);
                    }
                });
    }



    public void getAlreadJoinList(int pageNo,int pageSize,int tag, final ProjectMessageListBusiness.ProjectMessageListResultHandler handler, Context context){
        //判断没有网络应该如何处理
        if (!app.isNetWorkEnable(context)) {
            handler.onNetworkDisconnect();
        }
        MyOkHttp mMyOkhttp = new MyOkHttp(okHttpClient());
        mMyOkhttp.get()
                .url(Common.URL_ALREAD_JOIN)
                .addParam("pageNo",String.valueOf(pageNo))
                .addParam("pageSize",String.valueOf(pageSize))
                .addParam("search","")
                .addParam("tag",String.valueOf(tag))
                .tag(this)
                .enqueue(new JsonResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, JSONObject response) {

                        Log.i(TAG, "onSuccess: getAlreadJoinList"+response.toString());
                        ProjectMessageBean projectMessageBean =
                                JsonUtil.parseJsonWithGson(response.toString(),ProjectMessageBean.class);
                        if (projectMessageBean.getCode() == 0){
                            handler.onSuccess(projectMessageBean);
                        }else {
                            handler.onError(projectMessageBean.getCode());
                        }

                    }
                    @Override
                    public void onFailure(int statusCode, String error_msg) {
                        handler.onFailed(statusCode,error_msg);
                    }
                });
    }
    public void getSoonJoinList(int pageNo,int pageSize,int tag, final ProjectMessageListBusiness.ProjectMessageListResultHandler handler, Context context){
        //判断没有网络应该如何处理
        if (!app.isNetWorkEnable(context)) {
            handler.onNetworkDisconnect();
        }
        MyOkHttp mMyOkhttp = new MyOkHttp(okHttpClient());
        mMyOkhttp.get()
                .url(Common.URL_SOON_JOIN)
                .addParam("pageNo",String.valueOf(pageNo))
                .addParam("pageSize",String.valueOf(pageSize))
                .addParam("search","")
                .addParam("tag",String.valueOf(tag))
                .tag(this)
                .enqueue(new JsonResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, JSONObject response) {

                        Log.i(TAG, "onSuccess: "+response.toString());
                        ProjectMessageBean projectMessageBean =
                                JsonUtil.parseJsonWithGson(response.toString(),ProjectMessageBean.class);
                        if (projectMessageBean.getCode() == 0){
                            handler.onSuccess(projectMessageBean);
                        }else {
                            handler.onError(projectMessageBean.getCode());
                        }

                    }
                    @Override
                    public void onFailure(int statusCode, String error_msg) {
                        handler.onFailed(statusCode,error_msg);
                    }
                });
    }
    public void getAllList(int pageNo,int pageSize,int tag, final ProjectMessageListBusiness.ProjectMessageListResultHandler handler, Context context){
        //判断没有网络应该如何处理
        if (!app.isNetWorkEnable(context)) {
            handler.onNetworkDisconnect();
        }
        MyOkHttp mMyOkhttp = new MyOkHttp(okHttpClient());
        mMyOkhttp.get()
                .url(Common.URL_PROJECT_MESSAGE_ALLLIST)
                .addParam("pageNo",String.valueOf(pageNo))
                .addParam("pageSize",String.valueOf(pageSize))
                .addParam("search","")
                .addParam("tag",String.valueOf(tag))
                .tag(this)
                .enqueue(new JsonResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, JSONObject response) {
                        ProjectMessageBean projectMessageBean =
                                JsonUtil.parseJsonWithGson(response.toString(),ProjectMessageBean.class);
                        if (projectMessageBean.getCode() == 0){
                            handler.onSuccess(projectMessageBean);
                        }else {
                            handler.onError(projectMessageBean.getCode());
                        }

                    }
                    @Override
                    public void onFailure(int statusCode, String error_msg) {


                        handler.onFailed(statusCode,error_msg);

                    }
                });
    }
    public void getHotList(int pageNo,int pageSize, int tag,final ProjectMessageListBusiness.ProjectMessageListResultHandler handler, Context context){
        //判断没有网络应该如何处理
        if (!app.isNetWorkEnable(context)) {
            handler.onNetworkDisconnect();
        }

        MyOkHttp mMyOkhttp = new MyOkHttp(okHttpClient());
        mMyOkhttp.get()
                .url(Common.URL_HOT)
                .addParam("pageNo",String.valueOf(pageNo))
                .addParam("pageSize",String.valueOf(pageSize))
                .addParam("search","")
                .addParam("tag",String.valueOf(tag))
                .tag(this)
                .enqueue(new JsonResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, JSONObject response) {

                        Log.i(TAG, "onSuccess---99: "+response.toString());

                        ProjectMessageBean projectMessageBean =
                                JsonUtil.parseJsonWithGson(response.toString(),ProjectMessageBean.class);
                        if (projectMessageBean.getCode() == 0){
                            handler.onHotSuccess(projectMessageBean);
                        }else {
                            handler.onError(projectMessageBean.getCode());
                        }

                    }
                    @Override
                    public void onFailure(int statusCode, String error_msg) {
                        handler.onFailed(statusCode,error_msg);
                    }
                });
    }
}
