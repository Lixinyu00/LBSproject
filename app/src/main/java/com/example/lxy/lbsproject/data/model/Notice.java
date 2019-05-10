package com.example.lxy.lbsproject.data.model;

import cn.bmob.v3.BmobObject;

public class Notice extends BmobObject {

    private String user;
    private String title;
    private String content;

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
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
