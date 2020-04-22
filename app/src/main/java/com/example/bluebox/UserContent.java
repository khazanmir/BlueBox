package com.example.bluebox;

import java.util.Date;

public class UserContent {
    String name;
    String email;
    Date dob;
    String address;
    String phone;
    String identity;
    String sex;

    public UserContent(String name, String email, Date dob, String address, String phone, String identity, String sex) {
        this.name = name;
        this.email = email;
        this.dob = dob;
        this.address = address;
        this.phone = phone;
        this.identity = identity;
        this.sex = sex;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }
}
