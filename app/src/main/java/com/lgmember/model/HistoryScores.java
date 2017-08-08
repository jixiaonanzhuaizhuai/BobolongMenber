package com.lgmember.model;

/**
 * Created by Yanan_Wu on 2017/3/6.
 */

public class HistoryScores {
    private int before_point;
    private int after_point;
    private String create_time;
    private int uid;
    private String reason;
    private String userName;

    public int getBefore_point() {
        return before_point;
    }

    public void setBefore_point(int before_point) {
        this.before_point = before_point;
    }

    public int getAfter_point() {
        return after_point;
    }

    public void setAfter_point(int after_point) {
        this.after_point = after_point;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public HistoryScores(int before_point, int after_point, String create_time, int uid, String reason, String userName) {
        this.before_point = before_point;
        this.after_point = after_point;
        this.create_time = create_time;
        this.uid = uid;
        this.reason = reason;
        this.userName = userName;
    }
}
