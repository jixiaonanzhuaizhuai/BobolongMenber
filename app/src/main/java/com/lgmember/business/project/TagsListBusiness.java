package com.lgmember.business.project;

import android.content.Context;
import android.text.TextUtils;

import com.lgmember.api.HttpHandler;
import com.lgmember.bean.TagsListResultBean;
import com.lgmember.impl.ProjectMessageImpl;

import org.json.JSONObject;

/**
 * Created by Yanan_Wu on 2017/2/27.
 */

public class TagsListBusiness {
    private int tab;
    private Context context;
    private ProjectMessageImpl projectMessageImpl;

    public TagsListBusiness(Context context, int tab) {
        super();
        this.context = context;
        this.tab = tab;
    }

    public void getTagsList() {

        // 判断活动码是否有效
        projectMessageImpl = new ProjectMessageImpl();
        projectMessageImpl.getTagsList(tab,handler,context);
    }

    private TagsListResulHandler handler;

    public interface TagsListResulHandler extends HttpHandler {
        //当参数为空
        public void onSuccess(TagsListResultBean tagsListResultBean);

    }
    public void setHandler(TagsListResulHandler handler){
        this.handler = handler;
    }

    }