package com.lgmember.bean;

import com.lgmember.model.ProjectMessage;

import java.util.List;

/**
 * Created by Yanan_Wu on 2017/3/9.
 */

public class ProjectMessageBean extends HttpResultBean {
    private int total;
    private List<ProjectMessage> list;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<ProjectMessage> getList() {
        return list;
    }

    public void setList(List<ProjectMessage> list) {
        this.list = list;
    }

    public ProjectMessageBean(){
        super();
    }

    public ProjectMessageBean(int total, List<ProjectMessage> list) {
        this.total = total;
        this.list = list;
    }

    public ProjectMessageBean(int code, int total, List<ProjectMessage> list) {
        super(code);
        this.total = total;
        this.list = list;
    }
}
