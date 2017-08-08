package com.lgmember.bean;

import com.lgmember.model.Card;
import com.lgmember.model.Gift;

import java.util.List;

/**
 * Created by Yanan_Wu on 2017/3/9.
 */

public class GiftDetailResultBean extends HttpResultBean {
    private Gift data;

    public GiftDetailResultBean(){
        super();
    }

    public GiftDetailResultBean(int code, Gift data) {
        super(code);
        this.data = data;
    }

    public Gift getData() {
        return data;
    }

    public void setData(Gift data) {
        this.data = data;
    }
}
