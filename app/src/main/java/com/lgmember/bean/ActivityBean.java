package com.lgmember.bean;

import com.lgmember.model.Project;

import java.util.List;

/**
 * Created by Yanan_Wu on 2017/3/9.
 */

public class ActivityBean extends HttpResultBean {
    private int total;
    private List<Project> data;

    public ActivityBean(){
        super();
    }

    public ActivityBean(int total, List<Project> data) {
        this.total = total;
        this.data = data;
    }

    public ActivityBean(int code, int total, List<Project> data) {
        super(code);
        this.total = total;
        this.data = data;
    }

    public List<Project> getData() {
        return data;
    }

    public void setData(List<Project> data) {
        this.data = data;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

}
