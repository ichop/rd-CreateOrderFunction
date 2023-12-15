package com.rd.project.service;

import com.rd.project.dao.OrderDAO;
import com.rd.project.dao.OrderItemDAO;
import com.rd.project.model.Order;


public class OrderService {

    private final OrderDAO orderDAO = new OrderDAO();
    private final OrderItemDAO orderItemDAO = new OrderItemDAO();


    public Integer createOrder(Order order) {
        int orderId = orderDAO.createOrder(order);
        order.getOrderItems().forEach(item -> item.setOrderId(orderId));
        orderItemDAO.createOrderItems(order.getOrderItems());
        return orderId;
    }
}
