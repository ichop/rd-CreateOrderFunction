package com.rd.project.utils;


import com.rd.project.model.OrderItem;

import java.util.List;

public class Request {

    private Integer userId;
    private String deliveryAddress;
    private List<OrderItem> orderItems;

    public Request() {
    }

    public Request(Integer userId, String deliveryAddress, List<OrderItem> orderItems) {
        this.userId = userId;
        this.deliveryAddress = deliveryAddress;
        this.orderItems = orderItems;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }
}
