package com.lgmember.bean;

import com.lgmember.api.HttpHandler;
import com.lgmember.model.HistoryScores;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Yanan_Wu on 2017/3/9.
 */

public class HistoryScoresBean extends HttpResultBean {
    private int total;
    private List<HistoryScores> data;

    public HistoryScoresBean(){
        super();
    }

    public HistoryScoresBean(int total, List<HistoryScores> data) {
        this.total = total;
        this.data = data;
    }

    public HistoryScoresBean(int code, int total, List<HistoryScores> data) {
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

    public List<HistoryScores> getData() {
        return data;
    }

    public void setData(List<HistoryScores> data) {
        this.data = data;
    }
}
