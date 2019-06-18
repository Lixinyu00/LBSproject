package com.example.lxy.lbsproject.data.model;

import cn.bmob.v3.BmobObject;

public class Notice extends BmobObject {

    private String type;
    private String title;
    private String content;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
