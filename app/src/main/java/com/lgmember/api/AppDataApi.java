package com.lgmember.api;

import android.content.SharedPreferences;

import com.lgmember.app.App;

/**
 * 用来操作SharedPreferences中的数据
 */


public class AppDataApi {

    public static final String APP_DATA = "app_data";

    private SharedPreferences appData;

    private App app;

    private AppDataApi() {
        super();

    }

    private static AppDataApi api;
    //单例
    public static AppDataApi getInstance() {
        if (api == null) {
            api = new AppDataApi();
        }
        return api;
    }

    public void setApp(App app) {
        this.app = app;
        appData = app.getSharedPreferences(APP_DATA, App.MODE_APPEND);
    }

    /*
	 * -------------设置数据-------------
	 */
    public void setShareData(String name, boolean bo) {
        appData.edit().putBoolean(name, bo).commit();
    }

    public void setShareData(String name, String value) {
        appData.edit().putString(name, value).commit();
    }

    public void setShareData(String name, int value) {
        appData.edit().putInt(name, value).commit();
    }

    /*
     * -------------获取data-------------
     */
    public boolean getShareDataBoo(String name, boolean defValue) {
        return appData.getBoolean(name, defValue);
    }

    public String getShareDataStr(String name) {
        return appData.getString(name, "");
    }
    public String getShareDataStr(String name, String defValue) {
        return appData.getString(name, defValue);
    }

    public int getShareDataInt(String name) {
        return this.getShareDataInt(name, -1);
    }

    public int getShareDataInt(String name, int defValue) {
        return appData.getInt(name, defValue);
    }

}
