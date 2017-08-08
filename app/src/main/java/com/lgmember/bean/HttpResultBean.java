package com.lgmember.bean;

/**
 * Created by Yanan_Wu on 2017/3/9.
 */

public class HttpResultBean {
    private int code;
    
    public HttpResultBean(){
        super();
    }

    public HttpResultBean(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
