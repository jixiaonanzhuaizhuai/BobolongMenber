package com.lgmember.model;

import java.io.Serializable;

/**
 * Created by Yanan_Wu on 2017/4/20.
 */

public class Tag implements Serializable{
    private int id;
    private String tag;
    private int count;

    public Tag(){
        super();
    }

    public Tag(int id, String tag, int count) {
        this.id = id;
        this.tag = tag;
        this.count = count;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
