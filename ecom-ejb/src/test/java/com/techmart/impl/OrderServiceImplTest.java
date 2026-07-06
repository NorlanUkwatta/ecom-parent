package com.techmart.impl;

import com.techmart.core.enums.OrderStatus;
import com.techmart.entity.Order;
import com.techmart.entity.OrderItem;
import com.techmart.entity.Product;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

public class OrderServiceImplTest {

    @Mock
    private EntityManager em;

    @Mock
    private TypedQuery<OrderItem> mockQuery;

    @InjectMocks
    private OrderServiceImpl orderService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testUpdateOrderStatus_WhenPaid_DeductsStockCorrectly() {
        Long orderId = 1L;
        Order mockOrder = new Order();
        mockOrder.setId(orderId);
        mockOrder.setStatus(OrderStatus.PENDING);

        Product mockProduct = new Product();
        mockProduct.setName("Gaming Laptop");
        mockProduct.setStockQuantity(10);

        OrderItem mockItem = new OrderItem();
        mockItem.setOrder(mockOrder);
        mockItem.setProduct(mockProduct);
        mockItem.setQuantity(2);

        List<OrderItem> mockItems = new ArrayList<>();
        mockItems.add(mockItem);

        when(em.find(Order.class, orderId)).thenReturn(mockOrder);
        when(em.createQuery(anyString(), eq(OrderItem.class))).thenReturn(mockQuery);
        when(mockQuery.setParameter(anyString(), any())).thenReturn(mockQuery);
        when(mockQuery.getResultList()).thenReturn(mockItems);

        orderService.updateOrderStatus(orderId, "PAID");

        assertEquals(OrderStatus.PAID, mockOrder.getStatus());

        assertEquals(8, mockProduct.getStockQuantity());

        verify(em, times(1)).merge(mockProduct);
        verify(em, times(1)).merge(mockOrder);
    }
}