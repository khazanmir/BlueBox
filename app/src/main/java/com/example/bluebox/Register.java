package com.example.bluebox;

import java.util.Date;

public class Register {
    String dob;
    String address;
    String username;
    String password;
    String phone;
    String alt_Phone;
    String id_type;
    String legal_name;
    String id_number;
    String id_address;
    String sex;
    String issue_date_id;
    String expiry_date_id;

    public Register(String dob, String address, String username, String password, String phone, String alt_Phone, String id_type, String legal_name, String id_number, String id_address, String sex, String issue_date_id, String expiry_date_id) {
        this.dob = dob;
        this.address = address;
        this.username = username;
        this.password = password;
        this.phone = phone;
        this.alt_Phone = alt_Phone;
        this.id_type = id_type;
        this.legal_name = legal_name;
        this.id_number = id_number;
        this.id_address = id_address;
        this.sex = sex;
        this.issue_date_id = issue_date_id;
        this.expiry_date_id = expiry_date_id;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAlt_Phone() {
        return alt_Phone;
    }

    public void setAlt_Phone(String alt_Phone) {
        this.alt_Phone = alt_Phone;
    }

    public String getId_type() {
        return id_type;
    }

    public void setId_type(String id_type) {
        this.id_type = id_type;
    }

    public String getLegal_name() {
        return legal_name;
    }

    public void setLegal_name(String legal_name) {
        this.legal_name = legal_name;
    }

    public String getId_number() {
        return id_number;
    }

    public void setId_number(String id_number) {
        this.id_number = id_number;
    }

    public String getId_address() {
        return id_address;
    }

    public void setId_address(String id_address) {
        this.id_address = id_address;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getIssue_date_id() {
        return issue_date_id;
    }

    public void setIssue_date_id(String issue_date_id) {
        this.issue_date_id = issue_date_id;
    }

    public String getExpiry_date_id() {
        return expiry_date_id;
    }

    public void setExpiry_date_id(String expiry_date_id) {
        this.expiry_date_id = expiry_date_id;
    }
}
