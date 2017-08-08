package com.lgmember.impl;

import android.content.Context;
import android.util.Log;

import com.lgmember.api.HttpApi;
import com.lgmember.bean.MemberResultBean;
import com.lgmember.business.person.EditMemberMsgBusiness;
import com.lgmember.business.message.MemberMessageBusiness;
import com.lgmember.model.Member;
import com.lgmember.util.Common;
import com.lgmember.util.JsonUtil;
import com.tsy.sdk.myokhttp.MyOkHttp;
import com.tsy.sdk.myokhttp.response.JsonResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

public class MemberImpl extends HttpApi {

    private String TAG = "---MemberImpl--";
    public void getMemberMessage(final MemberMessageBusiness.MemberMessageResulHandler handler, Context context) {

        //判断没有网络应该如何处理
        if (!app.isNetWorkEnable(context)) {
            handler.onNetworkDisconnect();
        }

        MyOkHttp mMyOkhttp = new MyOkHttp(okHttpClient());
        mMyOkhttp.get()
                .url(Common.URL_MEMBER_MESSAGE)
                .tag(this)
                .enqueue(new JsonResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, final JSONObject response) {
                        MemberResultBean memberResultBean =
                                JsonUtil.parseJsonWithGson(response
                                        .toString(),MemberResultBean.class);
                        Member member = memberResultBean.getData();
                        int code = memberResultBean.getCode();
                        if (code == 0){
                            handler.onSuccess(member);
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

    public void editMemberMessage(Member member , final EditMemberMsgBusiness.EditMemberMessageResulHandler handler, Context context) {

        //判断没有网络应该如何处理
        if (!app.isNetWorkEnable(context)) {
            handler.onNetworkDisconnect();
        }

        //http post的json数据格式：  {"name": "****","pwd": "******"}
        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("name",member.getName());
            jsonObject.put("idno",member.getIdno());
            jsonObject.put("gender",member.getGender());
            jsonObject.put("mobile", member.getMobile());
            jsonObject.put("addr", member.getAddr());
            jsonObject.put("company",member.getCompany());
            jsonObject.put("job_title",member.getJob_title());
            jsonObject.put("nation",member.getNation());
            jsonObject.put("education",member.getEducation());
            jsonObject.put("month_income",member.getMonth_income());
            jsonObject.put("month_outcome",member.getMonth_outcome());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        MyOkHttp mMyOkhttp = new MyOkHttp(okHttpClient());
        mMyOkhttp.post()
                .url(Common.URL_EDIT_MEMBER_MESSAGE)
                .jsonParams(jsonObject.toString())
                .tag(this)
                .enqueue(new JsonResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, final JSONObject response) {

                        try {
                            int code = response.getInt("code");
                            if(code == 0){
                                handler.onEditMemberMsgSuccess();
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
