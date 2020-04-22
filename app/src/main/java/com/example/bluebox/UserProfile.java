package com.example.bluebox;

import java.util.ArrayList;

public class UserProfile {
    String success;
    String msg;
    UserContent profile_content;

    public UserProfile(String success, String msg, UserContent profile_content) {
        this.success = success;
        this.msg = msg;
        this.profile_content = profile_content;
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public UserContent getContent() {
        return profile_content;
    }

    public void setContent(UserContent content) {
        this.profile_content = content;
    }
}
