package com.example.clotheswebsite.service;

import com.example.clotheswebsite.dto.OrderDTO;
import com.example.clotheswebsite.entity.Order;
import com.example.clotheswebsite.entity.enums.OrderStatus;

import java.util.List;

public interface OrderService {
    List<Order> getAllOrders();
    Order getOrderById(long orderId);
    Order addOrder(OrderDTO orderDTO);
    Order updateOrder(OrderStatus orderStatus, long orderId);

    void deleteOrder(long orderId);

}
