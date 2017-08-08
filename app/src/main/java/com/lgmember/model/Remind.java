package com.lgmember.model;

/**
 * Created by Yanan_Wu on 2017/6/1.
 */
/*
* * @param id           ID
 * @param content      内容
 * @param arg          参数
 * @param remind_type  提醒类型
 * @param remind_stime 提醒时间
 * @param unread       是否已读
 * * @param got         是否已领取
* */

public class Remind {
    private int id;
    private String content;
    private int arg;
    private int remind_type;
    private String remind_time;
    private boolean unread;
    private boolean got;

    public Remind(int id, String content, int arg, int remind_type, String remind_stime, boolean unread,boolean got) {
        this.id = id;
        this.content = content;
        this.arg = arg;
        this.remind_type = remind_type;
        this.remind_time = remind_time;
        this.unread = unread;
        this.got = got;
    }

    public boolean getGot() {
        return got;
    }

    public void setGot(boolean got) {
        this.got = got;
    }

    public  Remind(){
        super();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getArg() {
        return arg;
    }

    public void setArg(int arg) {
        this.arg = arg;
    }

    public int getRemind_type() {
        return remind_type;
    }

    public void setRemind_type(int remind_type) {
        this.remind_type = remind_type;
    }

    public String getRemind_time() {
        return remind_time;
    }

    public void setRemind_time(String remind_time) {
        this.remind_time = remind_time;
    }

    public boolean getUnread() {
        return unread;
    }

    public void setUnread(boolean unread) {
        this.unread = unread;
    }
}
