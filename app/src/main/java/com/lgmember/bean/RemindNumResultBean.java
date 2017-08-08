package com.lgmember.bean;

import com.lgmember.model.Remind;

import java.lang.reflect.Array;
import java.util.List;

/**
 * Created by Yanan_Wu on 2017/6/1.
 */

public class RemindNumResultBean extends HttpResultBean {
    private int total;
    private List<Integer> data;

    public RemindNumResultBean(int total, List<Integer> data) {
        this.total = total;
        this.data = data;
    }

    public RemindNumResultBean(int code, int total, List<Integer> data) {
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

    public List<Integer> getData() {
        return data;
    }

    public void setData(List<Integer> data) {
        this.data = data;
    }
}
