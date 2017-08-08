package com.lgmember.bean;

import com.lgmember.model.Gift;

import java.util.List;

/**
 * Created by Yanan_Wu on 2017/4/13.
 */

public class ExchangeGiftResultBean extends HttpResultBean {

    private int total;
    private List<Gift> data ;



    public ExchangeGiftResultBean(int code, int total, List<Gift> data) {
        super(code);
        this.total = total;
        this.data = data;
    }

    public ExchangeGiftResultBean(){
        super();
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<Gift> getData() {
        return data;
    }

    public void setData(List<Gift> data) {
        this.data = data;
    }
}
