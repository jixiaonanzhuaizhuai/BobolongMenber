package com.lgmember.bean;


import com.lgmember.model.SignResult;

import java.util.List;

/**
 * Created by Yanan_Wu on 2017/3/9.
 */

public class SignListBean extends HttpResultBean {
    private int total;
    private List<SignResult> data;

    public SignListBean(int code, int total, List<SignResult> data) {
        super(code);
        this.total = total;
        this.data = data;
    }

    public SignListBean (){
        super();
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<SignResult> getData() {
        return data;
    }

    public void setData(List<SignResult> data) {
        this.data = data;
    }
}

