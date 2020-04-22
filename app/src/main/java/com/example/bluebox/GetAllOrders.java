package com.example.bluebox;

import java.util.ArrayList;

public class GetAllOrders {
    String success;
    String message;
    ArrayList<Order> orders;

    public GetAllOrders(String success, String message, ArrayList<Order> orders) {
        this.success = success;
        this.message = message;
        this.orders = orders;
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ArrayList<Order> getOrders() {
        return orders;
    }

    public void setOrders(ArrayList<Order> orders) {
        this.orders = orders;
    }
}
