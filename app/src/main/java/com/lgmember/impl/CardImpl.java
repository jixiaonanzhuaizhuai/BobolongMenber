package com.lgmember.impl;

import android.content.Context;

import com.lgmember.api.HttpApi;
import com.lgmember.bean.CardListResultBean;
import com.lgmember.business.card.CardDetailBusiness;
import com.lgmember.business.card.CardListBusiness;
import com.lgmember.business.card.ExchangeCardBusiness;
import com.lgmember.business.card.GetCardBusiness;
import com.lgmember.model.Card;
import com.lgmember.util.Common;
import com.lgmember.util.JsonUtil;
import com.tsy.sdk.myokhttp.MyOkHttp;
import com.tsy.sdk.myokhttp.response.JsonResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

public class CardImpl extends HttpApi {

    public void getCardList(final int pageNo, int pageSize, int state, final CardListBusiness.CardListResultHandler handler, Context context) {

        //判断没有网络应该如何处理
        if (!app.isNetWorkEnable(context)) {
            handler.onNetworkDisconnect();
        }
        MyOkHttp mMyOkhttp = new MyOkHttp(okHttpClient());
        mMyOkhttp.get()
                .url(Common.URL_CARD_LIST)
                .addParam("pageNo",String.valueOf(pageNo))
                .addParam("pageSize", String.valueOf(pageSize))
                .addParam("state",String.valueOf(state))
                .tag(this)
                .enqueue(new JsonResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, final JSONObject response) {

                        CardListResultBean cardListResultBean = JsonUtil
                                .parseJsonWithGson(response.toString()
                                        ,CardListResultBean.class);


                        if (cardListResultBean.getCode() == 0){
                            handler.onSuccess(cardListResultBean);
                        }else {
                            handler.onError(cardListResultBean.getCode());
                        }
                    }

                    @Override
                    public void onFailure(int statusCode, String error_msg) {
                        handler.onFailed(statusCode, error_msg);
                    }
                });

    }

    public void getCard(final int card_id, final GetCardBusiness.GetCardResultHandler handler, Context context) {

        //判断没有网络应该如何处理
        if (!app.isNetWorkEnable(context)) {
            handler.onNetworkDisconnect();
        }

        MyOkHttp mMyOkhttp = new MyOkHttp(okHttpClient());
        mMyOkhttp.get()
                .url(Common.URL_GET_CARD)
                .addParam("id",String.valueOf(card_id))
                .tag(this)
                .enqueue(new JsonResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, final JSONObject response) {

                        try {
                            int code = response.getInt("code");
                            if (code == 0){
                                int data = response.getInt("data");
                                handler.onGetCardSuccess(data);
                            }else{
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

    public void getRemindCard(final int remind_id, final GetCardBusiness.GetCardResultHandler handler, Context context) {

        //判断没有网络应该如何处理
        if (!app.isNetWorkEnable(context)) {
            handler.onNetworkDisconnect();
        }

        MyOkHttp mMyOkhttp = new MyOkHttp(okHttpClient());
        mMyOkhttp.get()
                .url(Common.URL_GET_REMIND_CARD)
                .addParam("id",String.valueOf(remind_id))
                .tag(this)
                .enqueue(new JsonResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, final JSONObject response) {

                        try {
                            int code = response.getInt("code");
                            if (code == 0){
                                int data = response.getInt("result");
                                handler.onGetCardSuccess(data);
                            }else{
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

    public void getCardDetail(final int card_id, final CardDetailBusiness.CardDetailResultHandler handler, Context context) {

        //判断没有网络应该如何处理
        if (!app.isNetWorkEnable(context)) {
            handler.onNetworkDisconnect();
        }

        MyOkHttp mMyOkhttp = new MyOkHttp(okHttpClient());
        mMyOkhttp.get()
                .url(Common.URL_GET_CARD)//卡券详情页面老师还没有给我
                .addParam("id",String.valueOf(card_id))
                .tag(this)
                .enqueue(new JsonResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, final JSONObject response) {

                        try {
                            int code = response.getInt("code");
                            if (code == 0){
                                JSONObject json = response.getJSONObject("data");
                                Card card = JsonUtil.parseJsonWithGson(json.toString(),Card.class);
                                handler.onCardDetailSuccess(card);
                            }else{
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
    public void exchangeCard(final int card_id, final ExchangeCardBusiness.ExchangeCardResultHandler handler, Context context) {

        //判断没有网络应该如何处理
        if (!app.isNetWorkEnable(context)) {
            handler.onNetworkDisconnect();
        }

        MyOkHttp mMyOkhttp = new MyOkHttp(okHttpClient());
        mMyOkhttp.get()
                .url(Common.URL_CARD_CODE)//卡券详情页面老师还没有给我
                .addParam("id",String.valueOf(card_id))
                .tag(this)
                .enqueue(new JsonResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, final JSONObject response) {

                        try {
                            int code = response.getInt("code");
                            if (code == 0){
                                JSONObject json = response.getJSONObject("data");
                                handler.onExchangeCardSuccess(json.toString());
                            }else{
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
