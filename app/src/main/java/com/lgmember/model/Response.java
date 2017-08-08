package com.lgmember.model;

/**
 * Created by Yanan_Wu on 2017/3/20.
 */

public class Response {
    private int id;
    private String content;
    private String user_name;
    private String create_time;

    public Response(){
        super();
    }

    public Response(int id, String content, String user_name, String create_time) {
        this.id = id;
        this.content = content;
        this.user_name = user_name;
        this.create_time = create_time;
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

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }
}
