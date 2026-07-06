package com.techmart.impl;

import com.techmart.entity.Cart;
import com.techmart.entity.CartItem;
import com.techmart.entity.Product;
import com.techmart.entity.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

public class CartServiceImplTest {

    @Mock
    private EntityManager em;

    @Mock
    private TypedQuery<Cart> cartQuery;

    @InjectMocks
    private CartServiceImpl cartService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddToCart_NewProduct_AddsToCartItems() {
        User mockUser = new User();
        Product mockProduct = new Product();
        mockProduct.setId(99L);

        Cart mockCart = new Cart();
        mockCart.setItems(new ArrayList<>());

        List<Cart> cartList = new ArrayList<>();
        cartList.add(mockCart);

        when(em.createQuery(anyString(), eq(Cart.class))).thenReturn(cartQuery);
        when(cartQuery.setParameter(anyString(), any())).thenReturn(cartQuery);
        when(cartQuery.getResultList()).thenReturn(cartList);

        when(em.find(Product.class, 99L)).thenReturn(mockProduct);

        cartService.addToCart(mockUser, 99L, 2);

        verify(em, times(1)).merge(mockCart);
        assert(mockCart.getItems().size() == 1);
        assert(mockCart.getItems().get(0).getQuantity() == 2);
    }
}