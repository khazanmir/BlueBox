package com.example.bluebox;

import java.util.ArrayList;

public class OrderLink {
    ArrayList<String> link_description;
    String link_url;
    String _id;
    String link_shipping_id;
    String link_status;
    String link_img_url_1;
    String link_img_url_2;
    String link_img_url_3;
    String link_price;

    public OrderLink(ArrayList<String> link_description, String _id, String link_url, String link_shipping_id, String link_status, String link_img_url_1, String link_img_url_2, String link_img_url_3, String link_price) {
        this.link_description = link_description;
        this._id = _id;
        this.link_url = link_url;
        this.link_shipping_id = link_shipping_id;
        this.link_status = link_status;
        this.link_img_url_1 = link_img_url_1;
        this.link_img_url_2 = link_img_url_2;
        this.link_img_url_3 = link_img_url_3;
        this.link_price = link_price;
    }

    public ArrayList<String> getLink_description() {
        return link_description;
    }

    public void setLink_description(ArrayList<String> link_description) {
        this.link_description = link_description;
    }

    public String getLink_url() {
        return link_url;
    }

    public void setLink_url(String link_url) {
        this.link_url = link_url;
    }

    public String getLink_shipping_id() {
        return link_shipping_id;
    }

    public void setLink_shipping_id(String link_shipping_id) {
        this.link_shipping_id = link_shipping_id;
    }

    public String getLink_status() {
        return link_status;
    }

    public void setLink_status(String link_status) {
        this.link_status = link_status;
    }

    public String getLink_img_url_1() {
        return link_img_url_1;
    }

    public void setLink_img_url_1(String link_img_url_1) {
        this.link_img_url_1 = link_img_url_1;
    }

    public String getLink_img_url_2() {
        return link_img_url_2;
    }

    public void setLink_img_url_2(String link_img_url_2) {
        this.link_img_url_2 = link_img_url_2;
    }

    public String getLink_img_url_3() {
        return link_img_url_3;
    }

    public void setLink_img_url_3(String link_img_url_3) {
        this.link_img_url_3 = link_img_url_3;
    }

    public String getLink_price() {
        return link_price;
    }

    public void setLink_price(String link_price) {
        this.link_price = link_price;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }
}
