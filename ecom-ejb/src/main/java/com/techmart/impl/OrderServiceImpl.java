package com.techmart.impl;

import com.techmart.core.enums.OrderStatus;
import com.techmart.core.monitoring.TrackPerformance;
import com.techmart.entity.*;
import com.techmart.service.OrderService;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.List;

@Stateless
@TrackPerformance
public class OrderServiceImpl implements OrderService {

    @PersistenceContext(unitName = "TechMartPU")
    private EntityManager em;

    @Override
    public Order createPendingOrder(User user, Address shippingAddress) {

        em.persist(shippingAddress);

        List<Cart> carts = em.createQuery(
                        "SELECT c FROM Cart c LEFT JOIN FETCH c.items i LEFT JOIN FETCH i.product WHERE c.user = :user", Cart.class)
                .setParameter("user", user)
                .getResultList();

        if (carts == null || carts.isEmpty() || carts.get(0).getItems().isEmpty()) {
            throw new IllegalStateException("Cannot create an order from an empty cart.");
        }
        Cart cart = carts.get(0);

        Order order = new Order();
        order.setUser(user);
        order.setShippingAddress(shippingAddress);
        order.setStatus(OrderStatus.PENDING);

        em.persist(order);

        double totalAmount = 0.0;

        for (CartItem cartItem : cart.getItems()) {
            Product product = cartItem.getProduct();

            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setProduct(product);

            orderItem.setPurchasedProductName(product.getName());
            orderItem.setPurchasedPrice(product.getPrice());
            orderItem.setQuantity(cartItem.getQuantity());

            em.persist(orderItem);

            totalAmount += (product.getPrice() * cartItem.getQuantity());
        }

        order.setTotalAmount(totalAmount);
        em.merge(order);

        for (CartItem cartItem : cart.getItems()) {
            em.remove(cartItem);
        }
        cart.getItems().clear();
        em.merge(cart);

        return order;
    }

    @Override
    public void updateOrderStatus(Long orderId, String status) {
        Order order = em.find(Order.class, orderId);

        if (order != null) {
            try {
                OrderStatus newStatus = OrderStatus.valueOf(status.toUpperCase());
                order.setStatus(newStatus);

                if (newStatus == OrderStatus.PAID) {

                    List<OrderItem> items = em.createQuery(
                                    "SELECT oi FROM OrderItem oi JOIN FETCH oi.product WHERE oi.order = :order", OrderItem.class)
                            .setParameter("order", order)
                            .getResultList();

                    for (OrderItem item : items) {
                        Product product = item.getProduct();
                        int newStock = product.getStockQuantity() - item.getQuantity();

                        product.setStockQuantity(Math.max(0, newStock));
                        em.merge(product);
                    }
                    System.out.println(">>> Stock deducted successfully for Order #" + orderId);
                }
                em.merge(order);
            } catch (IllegalArgumentException e) {
                System.err.println("Attempted to set invalid order status: " + status);
            }
        }
    }

    @Override
    public Order getOrderById(Long orderId) {
        try {
            return em.createQuery(
                            "SELECT o FROM Order o JOIN FETCH o.user WHERE o.id = :id", Order.class)
                    .setParameter("id", orderId)
                    .getSingleResult();
        } catch (Exception e) {
            System.err.println("Order not found with ID: " + orderId);
            return null;
        }
    }

    @Override
    public Long getUserIdByOrderId(Long orderId) {
        try {
            return em.createQuery("SELECT o.user.id FROM Order o WHERE o.id = :id", Long.class)
                    .setParameter("id", orderId)
                    .getSingleResult();
        } catch (Exception e) {
            System.err.println("Could not find user ID for order: " + orderId);
            return null;
        }
    }

    @Override
    public List<Order> getOrdersByUser(User user) {
        return em.createQuery("SELECT o FROM Order o WHERE o.user = :user ORDER BY o.id DESC", Order.class)
                .setParameter("user", user)
                .getResultList();
    }

    @Override
    public List<Order> getAllOrders() {
        return em.createQuery(
                        "SELECT o FROM Order o LEFT JOIN FETCH o.shippingAddress ORDER BY o.id DESC", Order.class)
                .getResultList();
    }
}
