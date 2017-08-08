package com.lgmember.bean;

import com.lgmember.model.Member;
import com.lgmember.model.Message;

import java.util.List;

/**
 * Created by Yanan_Wu on 2017/3/9.
 */

public class MemberResultBean extends HttpResultBean {
    private Member data;

    public MemberResultBean(){
        super();
    }

    public MemberResultBean( Member data) {
        this.data = data;
    }

    public MemberResultBean(int code, Member data) {
        super(code);
        this.data = data;
    }

    public  Member getData() {
        return data;
    }

    public void setData(Member data) {
        this.data = data;
    }
}
