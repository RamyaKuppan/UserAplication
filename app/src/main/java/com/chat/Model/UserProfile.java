package com.chat.Model;


import java.util.Date;

public class UserProfile {

    private String email;
    private String username;
    private String password;
    private Date createTime;

    public UserProfile() {

    }

    public UserProfile(String email, String username, String password) {
        this.email = email;
        this.username = username;
        this.password = password;
    }

    public UserProfile(String email, String username, String password, Date createTime) {
        this.email = email;
        this.username = username;
        this.password = password;
        this.createTime = createTime;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }


}
