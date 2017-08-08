package com.lgmember.bean;

import com.lgmember.model.Message;

import java.util.List;

/**
 * Created by Yanan_Wu on 2017/3/9.
 */

public class MessageBean extends HttpResultBean {
    private int total;
    private List<Message> data;

    public MessageBean(){
        super();
    }

    public MessageBean(int total, List<Message> data) {
        this.total = total;
        this.data = data;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<Message> getData() {
        return data;
    }

    public void setData(List<Message> data) {
        this.data = data;
    }
}
