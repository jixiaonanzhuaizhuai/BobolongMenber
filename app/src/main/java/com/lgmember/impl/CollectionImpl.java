package com.lgmember.impl;

import android.content.Context;

import com.lgmember.api.HttpApi;
import com.lgmember.bean.ProjectMessageBean;
import com.lgmember.business.collection.CollectionAddBusiness;
import com.lgmember.business.collection.CollectionListBusiness;
import com.lgmember.util.Common;
import com.lgmember.util.JsonUtil;
import com.tsy.sdk.myokhttp.MyOkHttp;
import com.tsy.sdk.myokhttp.response.JsonResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

public class CollectionImpl extends HttpApi {

    public void getCollectionList(int pageNo, int pageSize, int tag,final CollectionListBusiness.CollectionListResulHandler handler, Context context) {
        //判断没有网络应该如何处理
        if (!app.isNetWorkEnable(context)) {
            handler.onNetworkDisconnect();
        }

        MyOkHttp mMyOkhttp = new MyOkHttp(okHttpClient());
        mMyOkhttp.get()
                .url(Common.URL_COLLECTION_LIST)
                .addParam("pageNo", String.valueOf(pageNo))
                .addParam("pageSize", String.valueOf(pageSize))
                .addParam("tag", String.valueOf(tag))
                .tag(this)
                .enqueue(new JsonResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, JSONObject response) {
                        ProjectMessageBean projectMessageBean = JsonUtil.parseJsonWithGson(response.toString(), ProjectMessageBean.class);

                        if (projectMessageBean.getCode() == 0) {
                            handler.onSuccess(projectMessageBean);
                        } else {
                            handler.onError(projectMessageBean.getCode());
                        }
                    }

                    @Override
                    public void onFailure(int statusCode, String error_msg) {
                        handler.onFailed(statusCode, error_msg);
                    }
                });
    }

    public void addCollection(int id, final CollectionAddBusiness.CollectionResulHandler handler, Context context) {
        //判断没有网络应该如何处理
        if (!app.isNetWorkEnable(context)) {
            handler.onNetworkDisconnect();
        }

        //Log.i("----666888---",id+"");
        MyOkHttp mMyOkhttp = new MyOkHttp(okHttpClient());
        mMyOkhttp.get()
                .url(Common.URL_ADD_COLLECTION)
                .addParam("message_id", String.valueOf(id))
                .tag(this)
                .enqueue(new JsonResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, JSONObject response) {
                        try {
                            int code = response.getInt("code");
                            if (code == 0) {
                                handler.onCollectionSuccess("收藏成功");
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


    public void deleteCollection(int id, final CollectionAddBusiness.CollectionResulHandler handler, Context context){
        //判断没有网络应该如何处理
        if (!app.isNetWorkEnable(context)) {
            handler.onNetworkDisconnect();
        }

        MyOkHttp mMyOkhttp = new MyOkHttp(okHttpClient());
        mMyOkhttp.get()
                .url(Common.URL_DELETE_COLLECTION)
                .addParam("message_id",String.valueOf(id))
                .tag(this)
                .enqueue(new JsonResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, JSONObject response) {
                        try {
                            int code = response.getInt("code");
                            if (code == 0){
                                handler.onCollectionSuccess("取消收藏");
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

