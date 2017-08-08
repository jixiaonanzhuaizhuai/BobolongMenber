package com.lgmember.api;

import org.json.JSONObject;

/**
 * Created by Yanan_Wu on 2017/2/27.
 */

public interface HttpHandler {
   //服务器出错
    public void onError(int code);
    //成功
    /*public void onSuccess(JSONObject jsob);*/
    //网络未连接
    public void onNetworkDisconnect();
    //失败
    public void onFailed(int code, String msg);
    /*//有数据返回，不论是否成功
    public void onResult();*/
    //public void onCancelled(Exception arg0);
}
