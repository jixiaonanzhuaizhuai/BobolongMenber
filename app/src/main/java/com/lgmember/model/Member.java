package com.lgmember.model;

/**
 * Created by Yanan_Wu on 2017/3/17.
 */

public class Member {

    private String name = null;
    private String idno = null;
    private String mobile = null;
    private Boolean gender = true;
    private String addr = null;
    private String company = null;
    private String job_title = null;
    private int nation = 0;
    private int source = 0;
    private String create_time = null;
    private int education = 0;
    private int month_income = 0;
    private int month_outcome = 0;
    private int state = 0;
    private int level = 0;
    private String avatar = null;
    private int authorized = 0; //0 未实名认证 1 已实名认证 2 提交实名认证 3 提交没有通过
    private int point = 0;
    private String card_no = null;
    private int point_used = 0;
    private Boolean black = false;
    private int num_regist = 0;
    private int num_sign = 0;
    private String  reason;//实名认证消息

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Member(){
        super();
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdno() {
        return idno;
    }

    public void setIdno(String idno) {
        this.idno = idno;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Boolean getGender() {
        return gender;
    }

    public void setGender(Boolean gender) {
        this.gender = gender;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getJob_title() {
        return job_title;
    }

    public void setJob_title(String job_title) {
        this.job_title = job_title;
    }

    public int getNation() {
        return nation;
    }

    public void setNation(int nation) {
        this.nation = nation;
    }

    public int getSource() {
        return source;
    }

    public void setSource(int source) {
        this.source = source;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public int getEducation() {
        return education;
    }

    public void setEducation(int education) {
        this.education = education;
    }

    public int getMonth_income() {
        return month_income;
    }

    public void setMonth_income(int month_income) {
        this.month_income = month_income;
    }

    public int getMonth_outcome() {
        return month_outcome;
    }

    public void setMonth_outcome(int month_outcome) {
        this.month_outcome = month_outcome;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public int getAuthorized() {
        return authorized;
    }

    public void setAuthorized(int authorized) {
        this.authorized = authorized;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public String getCard_no() {
        return card_no;
    }

    public void setCard_no(String card_no) {
        this.card_no = card_no;
    }

    public int getPoint_used() {
        return point_used;
    }

    public void setPoint_used(int point_used) {
        this.point_used = point_used;
    }

    public Boolean getBlack() {
        return black;
    }

    public void setBlack(Boolean black) {
        this.black = black;
    }

    public int getNum_regist() {
        return num_regist;
    }

    public void setNum_regist(int num_regist) {
        this.num_regist = num_regist;
    }

    public int getNum_sign() {
        return num_sign;
    }

    public void setNum_sign(int num_sign) {
        this.num_sign = num_sign;
    }
}
