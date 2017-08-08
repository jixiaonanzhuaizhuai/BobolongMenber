package com.lgmember.api;

import com.franmontiel.persistentcookiejar.ClearableCookieJar;
import com.franmontiel.persistentcookiejar.PersistentCookieJar;
import com.franmontiel.persistentcookiejar.cache.SetCookieCache;
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor;
import com.lgmember.app.App;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;


/**
 * Created by Yanan_Wu on 2017/2/27.
 */

public class HttpApi {

    public App app = new App();

    public HttpApi() {
    }

    private static HttpApi api;

    //单例getInstance
    public static HttpApi getInstance() {
        if (api == null) {
            api = new HttpApi();
        }
        return api;
    }

    public void setApp(App app) {
        this.app = app;
    }


    //设置开启 cookie

    public static OkHttpClient okHttpClient() {

//        OkHttpClient okHttpClient = new OkHttpClient.Builder()
//                .cookieJar(app.getCookieJar())
//                .connectTimeout(10000L, TimeUnit.MILLISECONDS)
//                .readTimeout(10000L, TimeUnit.MILLISECONDS)
//                //其他配置
//                .build();
        return getInstance().app.okHttpClient();
    }


}
