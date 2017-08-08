package com.lgmember.util;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Yanan_Wu on 2017/7/25.
 */

public class DataLargeHolder {

   private Map<Integer, WeakReference<Object>> data = new HashMap<Integer, WeakReference<Object>>();

   public void save(int id, Object object) {
        data.put(id, new WeakReference<Object>(object));
    }

    public Object retrieve(int id) {
        WeakReference<Object> objectWeakReference = data.get(id);
        return objectWeakReference.get();
    }

    private static final DataLargeHolder holder = new DataLargeHolder();
    public static DataLargeHolder getInstance() {return holder;}
}




