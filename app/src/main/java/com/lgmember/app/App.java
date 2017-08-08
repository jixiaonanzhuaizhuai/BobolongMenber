package com.lgmember.app;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.facebook.stetho.Stetho;
import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.franmontiel.persistentcookiejar.ClearableCookieJar;
import com.franmontiel.persistentcookiejar.PersistentCookieJar;
import com.franmontiel.persistentcookiejar.cache.SetCookieCache;
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor;
import com.lgmember.api.AppDataApi;
import com.lgmember.api.HttpApi;
import com.lgmember.util.Common;

import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;


/**
 * Created by Yanan_Wu on 2017/2/27.
 */

public class App extends Application {

    public OkHttpClient okHttpClient() {
        return new OkHttpClient.Builder()
                .cookieJar(new CookieJar() {

                  final   PersistentCookieStore cookieStore = new PersistentCookieStore(getApplicationContext());
                    @Override
                    public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
                        if (Common.FLAG){
                            Common.FLAG = false;
                            if (cookies != null && cookies.size() > 0) {
                                for (Cookie item : cookies) {
                                    cookieStore.add(url, item);

                            }
                            }
                        }
                    }
                    @Override
                    public List<Cookie> loadForRequest(HttpUrl url) {
                        List<Cookie>  cookies = cookieStore.getCookies();

                        if (cookies == null){
                            return cookieStore.get(url);
                        }else {
                            return cookies;
                        }
                    }
                })
                .addNetworkInterceptor(new StethoInterceptor())
                //其他配置
                .build();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        HttpApi.getInstance().setApp(this);
        AppDataApi.getInstance().setApp(this);
        Stetho.initializeWithDefaults(this);//测试 工具 ，，正式开发的时候 ，要去掉的
    }
   /* public void exit(){
        cookieStore.removeAll();
    }*/



    /**
     * 判断网络是否可用
     *
     * @return
     */
    public boolean isNetWorkEnable(Context context) {
        ConnectivityManager manager = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (manager == null) {
            return false;
        } else {
            NetworkInfo networkInfo = manager.getActiveNetworkInfo();
            // networkInfo.isConnectedOrConnecting();
            if (networkInfo == null || !networkInfo.isAvailable()) {
                return false;
            }
            return true;
        }

    }

    /**
     * 获得当前app的版本信息
     *
     * @return
     */
    public String getVersion() {
        try {
            PackageManager manager = this.getPackageManager();
            PackageInfo info = manager.getPackageInfo(this.getPackageName(), 0);
            String version = info.versionName;
            return version;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

     /* *//**
     * 检测是否开启wify或gprs
     * @param context
     * @return
     *//*
    public boolean checkNetwork(final Context context){
        ConnectivityManager manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        //mobile 3G Data Network
       State mobile = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState();
        //wifi
        State wifi = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState();

        //如果3G网络和wifi网络都未连接，且不是处于正在连接状态 则进入Network Setting界面 由用户配置网络连接
        if(mobile==State.CONNECTED||mobile==State.CONNECTING)
            return true;
        if(wifi==State.CONNECTED||wifi==State.CONNECTING)
            return true;


        AlertDialog.Builder b = new android.app.AlertDialog.Builder(context).setTitle("没有可用的网络").setMessage("请开启GPRS或WIFI网络连接");
        b.setPositiveButton("设置网络", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                context.startActivity(new Intent(Settings.ACTION_WIRELESS_SETTINGS));//进入无线网络配置界面
//              context.startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS)); //进入手机中的wifi网络设置界面
            }
        }).setNeutralButton("取消", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                dialog.cancel();
            }
        }).create();
        b.show();
        return false;

    }*/
}
