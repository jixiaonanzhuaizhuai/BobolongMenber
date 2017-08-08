package com.lgmember.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Yanan_Wu on 2017/3/14.
 */

public class ProjectMessage implements Serializable{
    private int id; // 招募信息id
    private String title; // 标题
    private String picture;//图片, base64
    private String content;// 活动宣传内容
    private String hyperlink; // 超链接
    private Boolean saved; // 是否收藏
    private int state;  //会员报名状态   -1未报名;0:报名未签到;1:报名且签到;2:未报名但签到;3:候补报名
    private int count; // 报名总数
    public String start_time;// 活动开始时间
    public String end_time; // 活动结束时间
    public String checkin_end_time;// 报名结束时间
    public ArrayList<Tag> tags; // 标签


    public ProjectMessage(int id, String title, String picture, String content, String hyperlink, Boolean saved, int state, int count, String start_time, String end_time, String checkin_end_time, ArrayList<Tag> tags) {
        this.id = id;
        this.title = title;
        this.picture = picture;
        this.content = content;
        this.hyperlink = hyperlink;
        this.saved = saved;
        this.state = state;
        this.count = count;
        this.start_time = start_time;
        this.end_time = end_time;
        this.checkin_end_time = checkin_end_time;
        this.tags = tags;
    }

    public ProjectMessage(){
        super();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getHyperlink() {
        return hyperlink;
    }

    public void setHyperlink(String hyperlink) {
        this.hyperlink = hyperlink;
    }

    public Boolean getSaved() {
        return saved;
    }

    public void setSaved(Boolean saved) {
        this.saved = saved;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public String getCheckin_end_time() {
        return checkin_end_time;
    }

    public void setCheckin_end_time(String checkin_end_time) {
        this.checkin_end_time = checkin_end_time;
    }

    public ArrayList<Tag> getTags() {
        return tags;
    }

    public void setTags(ArrayList<Tag> tags) {
        this.tags = tags;
    }

    @Override
    public String toString() {
        return "ProjectMessage{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", picture='" + picture + '\'' +
                ", content='" + content + '\'' +
                ", hyperlink='" + hyperlink + '\'' +
                ", saved=" + saved +
                ", state=" + state +
                ", count=" + count +
                ", start_time='" + start_time + '\'' +
                ", end_time='" + end_time + '\'' +
                ", checkin_end_time='" + checkin_end_time + '\'' +
                ", tags=" + tags +
                '}';
    }
}
