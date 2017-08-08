package com.lgmember.model;


public class ScoresRule {
    private int redup;//红卡升级所需积分
    private int redcut;//红卡升级后扣除积分
    private int agup;//银卡升级所需积分
    private int agcut;//银卡升级后扣除积分
    private int auup;//金卡升级所需积分
    private int aucut;//金卡升级后扣除积分
    private int agdown;//银卡降级积分
    private int audown;//金卡降级积分
    private int sign;   //会员报名积分
    private int register;//注册积分
    private int punish;//积分惩罚
    private int complete;//完善资料积分
    private int share;//分享活动积分
    private int project_A;//A级活动基础积分
    private int project_B;//B级活动基础积分
    private int project_C;//C级活动基础积分
    private double project_double;//活动签到积分翻倍倍数
    private int program;//节目签到积分
    private double program_double;//节目签到积分翻倍倍数
    private double A_red_double;//A级活动红卡会员翻倍倍数
    private double A_ag_double;//A级活动银卡会员翻倍倍数
    private double A_au_double;//A级活动金卡会员翻倍倍数
    private double A_diamond_double;//A级活动钻石卡会员翻倍倍数
    private double B_red_double;//B级活动红卡会员翻倍倍数
    private double B_ag_double;//B级活动银卡会员翻倍倍数
    private double B_au_double;//B级活动金卡会员翻倍倍数
    private double B_diamond_double;//B级活动钻石卡会员翻倍倍数
    private double C_red_double;//C级活动红卡会员翻倍倍数
    private double C_ag_double;//C级活动银卡会员翻倍倍数
    private double C_au_double;//C级活动金卡会员翻倍倍数
    private double C_diamond_double;//C级活动钻石卡会员翻倍倍数

    public int getRedup() {
        return redup;
    }

    public void setRedup(int redup) {
        this.redup = redup;
    }

    public int getRedcut() {
        return redcut;
    }

    public void setRedcut(int redcut) {
        this.redcut = redcut;
    }

    public int getAgup() {
        return agup;
    }

    public void setAgup(int agup) {
        this.agup = agup;
    }

    public int getAgcut() {
        return agcut;
    }

    public void setAgcut(int agcut) {
        this.agcut = agcut;
    }

    public int getAuup() {
        return auup;
    }

    public void setAuup(int auup) {
        this.auup = auup;
    }

    public int getAucut() {
        return aucut;
    }

    public void setAucut(int aucut) {
        this.aucut = aucut;
    }

    public int getAgdown() {
        return agdown;
    }

    public void setAgdown(int agdown) {
        this.agdown = agdown;
    }

    public int getAudown() {
        return audown;
    }

    public void setAudown(int audown) {
        this.audown = audown;
    }

    public int getSign() {
        return sign;
    }

    public void setSign(int sign) {
        this.sign = sign;
    }

    public int getRegister() {
        return register;
    }

    public void setRegister(int register) {
        this.register = register;
    }

    public int getPunish() {
        return punish;
    }

    public void setPunish(int punish) {
        this.punish = punish;
    }

    public int getComplete() {
        return complete;
    }

    public void setComplete(int complete) {
        this.complete = complete;
    }

    public int getShare() {
        return share;
    }

    public void setShare(int share) {
        this.share = share;
    }

    public int getProject_A() {
        return project_A;
    }

    public void setProject_A(int project_A) {
        this.project_A = project_A;
    }

    public int getProject_B() {
        return project_B;
    }

    public void setProject_B(int project_B) {
        this.project_B = project_B;
    }

    public int getProject_C() {
        return project_C;
    }

    public void setProject_C(int project_C) {
        this.project_C = project_C;
    }

    public double getProject_double() {
        return project_double;
    }

    public void setProject_double(double project_double) {
        this.project_double = project_double;
    }

    public int getProgram() {
        return program;
    }

    public void setProgram(int program) {
        this.program = program;
    }

    public double getProgram_double() {
        return program_double;
    }

    public void setProgram_double(double program_double) {
        this.program_double = program_double;
    }

    public double getA_red_double() {
        return A_red_double;
    }

    public void setA_red_double(double a_red_double) {
        A_red_double = a_red_double;
    }

    public double getA_ag_double() {
        return A_ag_double;
    }

    public void setA_ag_double(double a_ag_double) {
        A_ag_double = a_ag_double;
    }

    public double getA_au_double() {
        return A_au_double;
    }

    public void setA_au_double(double a_au_double) {
        A_au_double = a_au_double;
    }

    public double getA_diamond_double() {
        return A_diamond_double;
    }

    public void setA_diamond_double(double a_diamond_double) {
        A_diamond_double = a_diamond_double;
    }

    public double getB_red_double() {
        return B_red_double;
    }

    public void setB_red_double(double b_red_double) {
        B_red_double = b_red_double;
    }

    public double getB_ag_double() {
        return B_ag_double;
    }

    public void setB_ag_double(double b_ag_double) {
        B_ag_double = b_ag_double;
    }

    public double getB_au_double() {
        return B_au_double;
    }

    public void setB_au_double(double b_au_double) {
        B_au_double = b_au_double;
    }

    public double getB_diamond_double() {
        return B_diamond_double;
    }

    public void setB_diamond_double(double b_diamond_double) {
        B_diamond_double = b_diamond_double;
    }

    public double getC_red_double() {
        return C_red_double;
    }

    public void setC_red_double(double c_red_double) {
        C_red_double = c_red_double;
    }

    public double getC_ag_double() {
        return C_ag_double;
    }

    public void setC_ag_double(double c_ag_double) {
        C_ag_double = c_ag_double;
    }

    public double getC_au_double() {
        return C_au_double;
    }

    public void setC_au_double(double c_au_double) {
        C_au_double = c_au_double;
    }

    public double getC_diamond_double() {
        return C_diamond_double;
    }

    public void setC_diamond_double(double c_diamond_double) {
        C_diamond_double = c_diamond_double;
    }
}
