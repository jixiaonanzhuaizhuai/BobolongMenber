package com.lgmember.bean;

import com.lgmember.model.Card;
import com.lgmember.model.Tag;

import java.util.List;

/**
 * Created by Yanan_Wu on 2017/3/9.
 */

public class TagsListResultBean extends HttpResultBean {
    private List<Tag> data;

    public TagsListResultBean(){
        super();
    }

    public TagsListResultBean(int total, List<Tag> data) {
        this.data = data;
    }

    public List<Tag> getData() {
        return data;
    }

    public void setData(List<Tag> data) {
        this.data = data;
    }
}
