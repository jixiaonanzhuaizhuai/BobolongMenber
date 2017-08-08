package com.lgmember.model;

/**
 * Created by Yanan_Wu on 2017/4/7.
 */


/*
*
页码：pageNo,Int
页大小：pageSize，Int
审核状态：review_state，int
项目名称：project_name，String
开始时间开始范围：start_time_start， 时间格式类似：2017-08-09 08:00:00
开始时间结束范围：start_time_end，时间格式类似：2017-08-09 08:00:00
结束时间开始范围：end_time_start，时间格式类似 ：2017-08-09 08:00:00
结束时间结束范围end_time_end，时间格式类似：2017-08-09 08:00:00
会员是否报名：is_checked_in，Boolean
会员是否为目标人群：is_doubled，Boolean
*
* */

public class ProjectParameter {

    private int pageNo;
    private int pageSize;
    private int review_state = 5;
    private String project_name = "";
    private String start_time_start ="";
    private String start_time_end ="";
    private String end_time_start ="";
    private String end_time_end ="";
    private String is_checked_in ="";
    private String is_doubled ="";

    public ProjectParameter(){
        super();
    }

    public ProjectParameter(String is_doubled, int pageSize, int review_state, String project_name, String start_time_start, String start_time_end, String end_time_start, String end_time_end, String is_checked_in, int pageNo) {
        this.is_doubled = is_doubled;
        this.pageSize = pageSize;
        this.review_state = review_state;
        this.project_name = project_name;
        this.start_time_start = start_time_start;
        this.start_time_end = start_time_end;
        this.end_time_start = end_time_start;
        this.end_time_end = end_time_end;
        this.is_checked_in = is_checked_in;
        this.pageNo = pageNo;
    }

    public int getPageNo() {
        return pageNo;
    }

    public String getIs_checked_in() {
        return is_checked_in;
    }

    public void setIs_checked_in(String is_checked_in) {
        this.is_checked_in = is_checked_in;
    }

    public String getIs_doubled() {
        return is_doubled;
    }

    public void setIs_doubled(String is_doubled) {
        this.is_doubled = is_doubled;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getReview_state() {
        return review_state;
    }

    public void setReview_state(int review_state) {
        this.review_state = review_state;
    }

    public String getProject_name() {
        return project_name;
    }

    public void setProject_name(String project_name) {
        this.project_name = project_name;
    }

    public String getStart_time_start() {
        return start_time_start;
    }

    public void setStart_time_start(String start_time_start) {
        this.start_time_start = start_time_start;
    }

    public String getStart_time_end() {
        return start_time_end;
    }

    public void setStart_time_end(String start_time_end) {
        this.start_time_end = start_time_end;
    }

    public String getEnd_time_start() {
        return end_time_start;
    }

    public void setEnd_time_start(String end_time_start) {
        this.end_time_start = end_time_start;
    }

    public String getEnd_time_end() {
        return end_time_end;
    }

    public void setEnd_time_end(String end_time_end) {
        this.end_time_end = end_time_end;
    }


}
