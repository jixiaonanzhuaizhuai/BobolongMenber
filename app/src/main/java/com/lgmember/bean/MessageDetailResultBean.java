package com.lgmember.bean;

import com.lgmember.model.Message;

/**
 * Created by Yanan_Wu on 2017/5/22.
 */

public class MessageDetailResultBean extends HttpResultBean {

    private Message message;

    public MessageDetailResultBean() {
        super();
    }

    public MessageDetailResultBean(Message message) {
        this.message = message;
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }
}
