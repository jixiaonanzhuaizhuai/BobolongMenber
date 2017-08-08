package com.lgmember.model;

/**
 * Created by Yanan_Wu on 2017/4/5.
 */

public class SignResult {

    /*
     * @param session_id   会话id
    * @param create_time  录音时间
    * @param dep_name     频道名称
    * @param program_name 节目名称
    * @param result_time  识别时间
    * @param state        状态: 0 等待识别 1 识别中 2 签到完成
    * */

    private String session_id;
    private String create_time;
    private String dep_name;
    private String program_name;
    private String result_time;
    private int state;

    public SignResult(String session_id, String create_time, String dep_name, String program_name, String result_time, int state) {
        this.session_id = session_id;
        this.create_time = create_time;
        this.dep_name = dep_name;
        this.program_name = program_name;
        this.result_time = result_time;
        this.state = state;
    }

    public  SignResult(){
        super();
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getSession_id() {
        return session_id;
    }

    public void setSession_id(String session_id) {
        this.session_id = session_id;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getDep_name() {
        return dep_name;
    }

    public void setDep_name(String dep_name) {
        this.dep_name = dep_name;
    }

    public String getProgram_name() {
        return program_name;
    }

    public void setProgram_name(String program_name) {
        this.program_name = program_name;
    }

    public String getResult_time() {
        return result_time;
    }

    public void setResult_time(String result_time) {
        this.result_time = result_time;
    }
}
