package com.lgmember.api;

import com.tsy.sdk.myokhttp.MyOkHttp;
import com.tsy.sdk.myokhttp.builder.OkHttpRequestBuilderHasParam;

import java.util.LinkedHashMap;



/**
 * Created by Yanan_Wu on 2017/3/10.
 */

public abstract  class MyOkHttpSelf extends OkHttpRequestBuilderHasParam {

    public MyOkHttpSelf(MyOkHttp myOkHttp) {
        super(myOkHttp);
    }

    public OkHttpRequestBuilderHasParam addParamInt(String key, int val) {
        if (this.mParams == null)
        {
            mParams = new LinkedHashMap<>();
        }
        mParams.put(key, val);
        return this;
    }


}




