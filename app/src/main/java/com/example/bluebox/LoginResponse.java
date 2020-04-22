package com.example.bluebox;

public class LoginResponse {
    String success;
    String  msg;
    String token;
    String content;

    public LoginResponse(String success, String msg, String token, String content) {
        this.success = success;
        this.msg = msg;
        this.token = token;
        this.content = content;
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

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
