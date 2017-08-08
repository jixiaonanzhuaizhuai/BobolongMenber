package com.lgmember.business.project;

import android.content.Context;

import com.lgmember.api.HttpHandler;
import com.lgmember.bean.ProjectMessageBean;
import com.lgmember.impl.ProjectMessageImpl;

/**
 * Created by Yanan_Wu on 2017/3/6.
 */

public class ProjectMessageListBusiness {

    private int pageNo,pageSize,tag;
    private Context context;
    private ProjectMessageImpl projectMessageImpl;

    public ProjectMessageListBusiness(Context context,int pageNo,int pageSize,int tag) {
        super();
        this.context = context;
        this.pageNo = pageNo;
        this.pageSize = pageSize;
        this.tag = tag;
    }

    // 先验证参数的可发性，再登陆
    public void getAlreadJoinList() {
        // TODO 可能还要验证密码
        // 登陆
        projectMessageImpl = new ProjectMessageImpl();
        projectMessageImpl.getAlreadJoinList(pageNo,pageSize,tag,handler,context);
    }
    public void getSoonJoinList() {
        // TODO 可能还要验证密码
        // 登陆
        projectMessageImpl = new ProjectMessageImpl();
        projectMessageImpl.getSoonJoinList(pageNo,pageSize,tag,handler,context);
    }

    public void getHotProjectMessage() {
        // TODO 可能还要验证密码
        // 登陆
        projectMessageImpl = new ProjectMessageImpl();
        projectMessageImpl.getHotList(pageNo,pageSize,tag,handler,context);
    }

    public void getProjectMessageAllList() {
        // TODO 可能还要验证密码
        // 登陆
        projectMessageImpl = new ProjectMessageImpl();
        projectMessageImpl.getAllList(pageNo,pageSize,tag,handler,context);
    }

    private ProjectMessageListResultHandler handler;

    public interface ProjectMessageListResultHandler extends HttpHandler {
        //当参数为空
        public void onSuccess(ProjectMessageBean bean);
        //当参数为空
        public void onHotSuccess(ProjectMessageBean bean);


    }
    public void setHandler(ProjectMessageListResultHandler handler){
        this.handler = handler;
    }
}
