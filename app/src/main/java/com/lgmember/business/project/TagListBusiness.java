package com.lgmember.business.project;

import android.content.Context;

import com.lgmember.api.HttpHandler;
import com.lgmember.bean.TagsListResultBean;
import com.lgmember.impl.MessageImpl;
import com.lgmember.impl.TagImpl;

/**
 * Created by Yanan_Wu on 2017/3/6.
 */

public class TagListBusiness {

    private int tab;
    private Context context;
    private TagImpl tagImpl;

    public TagListBusiness(Context context, int tab) {
        super();
        this.context = context;
        this.tab = tab;
    }

    // 先验证参数的可发性，再登陆
    public void getAllTagList() {
        // TODO 可能还要验证密码
        // 登陆
        tagImpl = new TagImpl();
        tagImpl.getAllList(tab,handler,context);
    }

    private TagListResultHandler handler;

    public interface TagListResultHandler extends HttpHandler {
        //当参数为空
        public void onTagListSuccess(TagsListResultBean tagsListResultBean);

    }
    public void setHandler(TagListResultHandler handler){
        this.handler = handler;
    }
}
