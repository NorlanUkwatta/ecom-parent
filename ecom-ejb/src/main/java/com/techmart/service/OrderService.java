package com.techmart.service;

import com.techmart.entity.Address;
import com.techmart.entity.Order;
import com.techmart.entity.User;
import jakarta.ejb.Local;

import java.util.List;

@Local
public interface OrderService {
    Order createPendingOrder(User user, Address shippingAddress);

    void updateOrderStatus(Long orderId, String status);

    Order getOrderById(Long orderId);

    Long getUserIdByOrderId(Long orderId);

    List<Order> getOrdersByUser(User user);

    List<Order> getAllOrders();
}
