package com.lgmember.model;

import java.util.List;

/**
 * Created by Yanan_Wu on 2017/3/20.
 */

public class Feedback {
    private int id;
    private String content;
    private String create_time;
    private List<Response> response;

    public Feedback(){
        super();
    }

    public Feedback(int id, String content, String create_time, List<Response> response) {
        this.id = id;
        this.content = content;
        this.create_time = create_time;
        this.response = response;
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

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public List<Response> getResponse() {
        return response;
    }

    public void setResponse(List<Response> response) {
        this.response = response;
    }
}
