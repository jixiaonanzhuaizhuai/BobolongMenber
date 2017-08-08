package com.lgmember.bean;

import com.lgmember.model.Remind;

import java.util.List;

/**
 * Created by Yanan_Wu on 2017/6/1.
 */

public class RemindListResultBean extends HttpResultBean {
    private int total;
    private List<Remind> data;

    public RemindListResultBean(int total, List<Remind> data) {
        this.total = total;
        this.data = data;
    }

    public RemindListResultBean(int code, int total, List<Remind> data) {
        super(code);
        this.total = total;
        this.data = data;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<Remind> getData() {
        return data;
    }

    public void setData(List<Remind> data) {
        this.data = data;
    }
}
