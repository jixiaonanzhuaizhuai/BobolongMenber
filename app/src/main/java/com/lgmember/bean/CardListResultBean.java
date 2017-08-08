package com.lgmember.bean;

import com.lgmember.model.Card;
import com.lgmember.model.Message;

import java.util.List;

/**
 * Created by Yanan_Wu on 2017/3/9.
 */

public class CardListResultBean extends HttpResultBean {
    private int total;
    private List<Card> data;

    public CardListResultBean(){
        super();
    }

    public CardListResultBean(int total, List<Card> data) {
        this.total = total;
        this.data = data;
    }

    public CardListResultBean(int code, int total, List<Card> data) {
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

    public List<Card> getData() {
        return data;
    }

    public void setData(List<Card> data) {
        this.data = data;
    }
}
