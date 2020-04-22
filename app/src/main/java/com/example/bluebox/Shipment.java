package com.example.bluebox;

import android.media.Image;

public class Shipment {
    int imgRes;
    String payStatus;
    String shipId;
    String desc;

    public Shipment(int imgRes, String payStatus, String shipId, String desc) {
        this.imgRes = imgRes;
        this.payStatus = payStatus;
        this.shipId = shipId;
        this.desc = desc;
    }

    public int getImgRes() {
        return imgRes;
    }

    public void setImgRes(int imgRes) {
        this.imgRes = imgRes;
    }

    public String getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(String payStatus) {
        this.payStatus = payStatus;
    }

    public String getShipId() {
        return shipId;
    }

    public void setShipId(String shipId) {
        this.shipId = shipId;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
