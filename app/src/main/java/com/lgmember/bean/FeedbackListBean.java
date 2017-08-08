package com.lgmember.bean;


import com.lgmember.model.Feedback;

import java.util.List;

/**
 * Created by Yanan_Wu on 2017/3/9.
 */

public class FeedbackListBean extends HttpResultBean {
    private int total;
    private List<Feedback> data;

    public FeedbackListBean(){
        super();
    }

    public FeedbackListBean(int total, List<Feedback> data) {
        this.total = total;
        this.data = data;
    }

    public FeedbackListBean(int code, int total, List<Feedback> data) {
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

    public List<Feedback> getData() {
        return data;
    }

    public void setData(List<Feedback> data) {
        this.data = data ;
    }
}
