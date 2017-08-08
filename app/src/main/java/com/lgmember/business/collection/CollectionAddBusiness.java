package com.lgmember.business.collection;

import android.content.Context;

import com.lgmember.api.HttpHandler;
import com.lgmember.bean.ProjectMessageBean;
import com.lgmember.impl.CollectionImpl;

/**
 * Created by Yanan_Wu on 2017/2/27.
 */

public class CollectionAddBusiness {
    private int id;
    private Context context;
    private CollectionImpl collectionImpl;

    public CollectionAddBusiness(Context context, int id) {
        super();
        this.context = context;
        this.id = id;
    }

    // 添加收藏
    public void addCollection() {

        // 判断活动码是否有效
        collectionImpl = new CollectionImpl();
        collectionImpl.addCollection(id,handler,context);
    }

    //删除收藏
    public void deleteCollection() {

        // 判断活动码是否有效
        collectionImpl = new CollectionImpl();
        collectionImpl.deleteCollection(id,handler,context);
    }

    private CollectionResulHandler handler;

    public interface CollectionResulHandler extends HttpHandler {

            public void onCollectionSuccess(String str);

    }
    public void setHandler(CollectionResulHandler handler){
        this.handler = handler;
    }

    }