package com.lgmember.business.project;

import android.content.Context;

import com.lgmember.api.HttpHandler;
import com.lgmember.bean.ProjectMessageBean;
import com.lgmember.impl.ProjectMessageImpl;
import com.lgmember.model.ProjectMessage;

/**
 * Created by Yanan_Wu on 2017/3/6.
 */

public class ProjectMessageDetailBusiness {

    private int id;
    private Context context;
    private ProjectMessageImpl projectMessageImpl;

    public ProjectMessageDetailBusiness(Context context, int id) {
        super();
        this.context = context;
        this.id = id;
    }

    // 先验证参数的可发性，再登陆
    public void getProjectMessageDetail() {
        // TODO 可能还要验证密码
        // 登陆
        projectMessageImpl = new ProjectMessageImpl();
        projectMessageImpl.getProjectMessageDetail(id,handler,context);
    }

    private ProjectMessageDetailResultHandler handler;

    public interface ProjectMessageDetailResultHandler extends HttpHandler {
        //当参数为空
        public void onProjectMessageDetailSuccess(ProjectMessage projectMessage);

    }
    public void setHandler(ProjectMessageDetailResultHandler handler){
        this.handler = handler;
    }
}
