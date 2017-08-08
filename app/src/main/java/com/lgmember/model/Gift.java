package com.lgmember.model;

/**
 * Created by Yanan_Wu on 2017/3/7.
 */

public class Gift {
    private int id;
    private String name = null;
    private String description = null;
    private int point = 0;
    private String picture = null;
    private int number = 0;
    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public Gift(){
        super();
    }

    public Gift(int id, String name, String description, int point, String picture,int number) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.point = point;
        this.picture = picture;
        this.number = number;
    }
}
