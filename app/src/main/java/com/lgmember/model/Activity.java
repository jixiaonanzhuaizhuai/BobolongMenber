package com.lgmember.model;

/**
 * Created by Yanan_Wu on 2016/12/23.
 */

public class Activity {
    private String name;
    private int imageId;
    private String introduction;

    public Activity(String name, int imageId, String introduction) {
        this.name = name;
        this.introduction = introduction;
        this.imageId = imageId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }
}
