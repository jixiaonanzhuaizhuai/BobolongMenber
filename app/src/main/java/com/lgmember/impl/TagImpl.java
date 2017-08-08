package com.lgmember.impl;

import android.content.Context;
import android.util.Log;

import com.lgmember.api.HttpApi;
import com.lgmember.bean.CardListResultBean;
import com.lgmember.bean.TagsListResultBean;
import com.lgmember.business.card.CardListBusiness;
import com.lgmember.business.project.TagListBusiness;
import com.lgmember.util.Common;
import com.lgmember.util.JsonUtil;
import com.tsy.sdk.myokhttp.MyOkHttp;
import com.tsy.sdk.myokhttp.response.JsonResponseHandler;

import org.json.JSONObject;

/**
 * Created by Yanan_Wu on 2017/6/14.
 */

public class TagImpl extends HttpApi {

    public void getAllList(final int tab, final TagListBusiness.TagListResultHandler handler, Context context) {

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
                    public void onSuccess(int statusCode, final JSONObject response) {

                        Log.i("-----7777----", "onSuccess: "+response.toString());

                        TagsListResultBean tagsListResultBean = JsonUtil
                                .parseJsonWithGson(response.toString()
                                        ,TagsListResultBean.class);

                        if (tagsListResultBean.getCode() == 0){
                            handler.onTagListSuccess(tagsListResultBean);
                        }else {
                            handler.onError(tagsListResultBean.getCode());
                        }
                    }

                    @Override
                    public void onFailure(int statusCode, String error_msg) {
                        handler.onFailed(statusCode, error_msg);
                    }
                });

    }
}
