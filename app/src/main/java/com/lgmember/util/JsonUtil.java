package com.lgmember.util;

import com.google.android.gms.common.data.DataHolder;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by Yanan_Wu on 2017/3/9.
 */

public class JsonUtil {
    //将Json数据解析成相应的映射对象

    public static <T> T parseJsonWithGson(String jsonData,Class<T> type){
        Gson gson = new Gson();
        T result = gson.fromJson(jsonData,type);
        return result;

    }

    //将Json数据解析成相应的映射对象列表
    public static <T> List<T> parseJsonArrayWithGson(String jsonData,Class<T> type){

            List<T> list = new ArrayList<T>();
            try {
                Gson gson = new Gson();
                JsonArray arry = new JsonParser().parse(jsonData).getAsJsonArray();
                for (JsonElement jsonElement : arry) {
                    list.add(gson.fromJson(jsonElement, type));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return list;
        }

}
