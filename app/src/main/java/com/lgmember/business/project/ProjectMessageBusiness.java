package com.lgmember.business.project;

import android.content.Context;

import com.lgmember.api.HttpHandler;
import com.lgmember.bean.MessageBean;
import com.lgmember.bean.ProjectMessageBean;
import com.lgmember.impl.MessageImpl;
import com.lgmember.impl.ProjectMessageImpl;
import com.lgmember.model.Project;

/**
 * Created by Yanan_Wu on 2017/3/6.
 */

public class ProjectMessageBusiness {

    private int pageNo;
    private int pageSize;
    private Context context;
    private ProjectMessageImpl projectMessageImpl;

    public ProjectMessageBusiness(Context context, int pageNo, int pageSize) {
        super();
        this.context = context;
        this.pageNo = pageNo;
        this.pageSize = pageSize;
    }

    // 先验证参数的可发性，再登陆
    public void getProjectMessage() {
        // TODO 可能还要验证密码
        // 登陆
        projectMessageImpl = new ProjectMessageImpl();
        //projectMessageImpl.getProjectMessage(pageNo,pageSize,handler,context);
    }

    private ProjectMessageResultHandler handler;

    public interface ProjectMessageResultHandler extends HttpHandler {
        //当参数为空
        public void onSuccess(ProjectMessageBean bean);

    }
    public void setHandler(ProjectMessageResultHandler handler){
        this.handler = handler;
    }
}
