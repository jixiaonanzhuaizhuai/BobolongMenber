package com.lgmember.model;


/**
 * Created by Yanan_Wu on 2017/3/13.
 */

public class Project {

    private int project_id;
    private String project_name;
    private int project_no;
    private String start_time;
    private String end_time;
    private int state;
    private String member_state;
    private String is_end;

    public Project(){
        super();
    }

    public Project(int project_id, String project_name,
                   int project_no, String start_time,
                   String end_time, int state,
                   String member_state, String is_end) {
        this.project_id = project_id;
        this.project_name = project_name;
        this.project_no = project_no;
        this.start_time = start_time;
        this.end_time = end_time;
        this.state = state;
        this.member_state = member_state;
        this.is_end = is_end;
    }

    public int getProject_id() {
        return project_id;
    }

    public void setProject_id(int project_id) {
        this.project_id = project_id;
    }

    public String getProject_name() {
        return project_name;
    }

    public void setProject_name(String project_name) {
        this.project_name = project_name;
    }

    public int getProject_no() {
        return project_no;
    }

    public void setProject_no(int project_no) {
        this.project_no = project_no;
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

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getMember_state() {
        return member_state;
    }

    public void setMember_state(String member_state) {
        this.member_state = member_state;
    }

    public String getIs_end() {
        return is_end;
    }

    public void setIs_end(String is_end) {
        this.is_end = is_end;
    }
}
