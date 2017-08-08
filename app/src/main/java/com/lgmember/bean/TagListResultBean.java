package com.lgmember.bean;

import com.lgmember.model.HistoryScores;
import com.lgmember.model.Tag;

import java.util.List;

/**
 * Created by Yanan_Wu on 2017/3/9.
 */

public class TagListResultBean extends HttpResultBean {
    private int total;
    private List<Tag> data;

    public TagListResultBean(){
        super();
    }

    public TagListResultBean(int total, List<Tag> data) {
        this.total = total;
        this.data = data;
    }

    public TagListResultBean(int code, int total, List<Tag> data) {
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

    public List<Tag> getData() {
        return data;
    }

    public void setData(List<Tag> data) {
        this.data = data;
    }
}
