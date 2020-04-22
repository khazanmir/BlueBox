package com.example.bluebox;

import java.util.ArrayList;

public class Order {
    String _id;
    String order_type;
    ArrayList<OrderLink> order_links;
    String link_shipping_id;

    public Order(String _id, String order_type, ArrayList<OrderLink> order_links, String link_shipping_id) {
        this._id = _id;
        this.order_type = order_type;
        this.order_links = order_links;
        this.link_shipping_id = link_shipping_id;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getOrder_type() {
        return order_type;
    }

    public void setOrder_type(String order_type) {
        this.order_type = order_type;
    }

    public ArrayList<OrderLink> getOrder_links() {
        return order_links;
    }

    public void setOrder_links(ArrayList<OrderLink> order_links) {
        this.order_links = order_links;
    }

    public String getLink_shipping_id() {
        return link_shipping_id;
    }

    public void setLink_shipping_id(String link_shipping_id) {
        this.link_shipping_id = link_shipping_id;
    }
}
