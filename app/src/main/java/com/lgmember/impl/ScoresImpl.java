package com.lgmember.impl;

import android.content.Context;
import android.util.Log;

import com.lgmember.api.HttpApi;
import com.lgmember.bean.HistoryScoresBean;
import com.lgmember.business.score.HistoryScoresBusiness;
import com.lgmember.business.score.ScoresRuleBusiness;
import com.lgmember.util.Common;
import com.lgmember.util.JsonUtil;
import com.tsy.sdk.myokhttp.MyOkHttp;
import com.tsy.sdk.myokhttp.response.JsonResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;


public class ScoresImpl extends HttpApi {

    public void getScoresRule(final ScoresRuleBusiness.ScoresRuleHandler handler, Context context){
        //判断没有网络应该如何处理
        if (!app.isNetWorkEnable(context)) {
            handler.onNetworkDisconnect();
        }
        MyOkHttp mMyOkhttp = new MyOkHttp(okHttpClient());
        mMyOkhttp.post()
                .url(Common.URL_SCORES_RULE)
                .tag(this)
                .enqueue(new JsonResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, JSONObject response) {
                       handler.onSuccess(response);

                    }
                    @Override
                    public void onFailure(int statusCode, String error_msg) {
                        handler.onFailed(statusCode,error_msg);
                    }
                });
    }
    public void getScoresInfo(final ScoresRuleBusiness.ScoresRuleHandler handler, Context context){
        //判断没有网络应该如何处理
        if (!app.isNetWorkEnable(context)) {
            handler.onNetworkDisconnect();
        }
        MyOkHttp mMyOkhttp = new MyOkHttp(okHttpClient());
        mMyOkhttp.post()
                .url(Common.URL_SCORES_INFORMATION)
                .tag(this)
                .enqueue(new JsonResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, JSONObject response) {
                        //Log.i("----999333----",response.toString());
                        handler.onSuccess(response);

                    }
                    @Override
                    public void onFailure(int statusCode, String error_msg) {
                        //Log.i("----666----",statusCode+error_msg);
                        handler.onFailed(statusCode,error_msg);
                    }
                });
    }


    public void getHistoryScores(int pageNo, int pageSize, final HistoryScoresBusiness.HistoryScoresResultHandler handler, Context context){
        //判断没有网络应该如何处理
        if (!app.isNetWorkEnable(context)) {
            handler.onNetworkDisconnect();
        }

        //http post的json数据格式：  {"name": "****","pwd": "******"}
        final JSONObject jsonObject = new JSONObject();
        ;
        try {
            jsonObject.put("pageNo", 1);
            jsonObject.put("pageSize", 10);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        MyOkHttp mMyOkhttp = new MyOkHttp(okHttpClient());
        mMyOkhttp.post()
                .url(Common.URL_HISTORY_SCORES)
                .jsonParams(jsonObject.toString())
                .tag(this)
                .enqueue(new JsonResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, JSONObject response) {


                        HistoryScoresBean historyScoresBean = JsonUtil.parseJsonWithGson(response.toString(),HistoryScoresBean.class);
                        if (historyScoresBean.getCode() == 0){
                            handler.onHisSuccess(historyScoresBean);
                        }else {
                            handler.onError(historyScoresBean.getCode());
                        }


                    }
                    @Override
                    public void onFailure(int statusCode, String error_msg) {
                        handler.onFailed(statusCode,error_msg);
                    }
                });
    }


}
