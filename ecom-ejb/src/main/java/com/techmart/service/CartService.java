package com.techmart.service;

import com.techmart.dto.CartDTO;
import com.techmart.entity.Cart;
import com.techmart.entity.User;
import jakarta.ejb.Remote;

import java.util.Map;

@Remote
public interface CartService {
    CartDTO getCartByUser(User user);
    void addToCart(User user, Long productId, int quantity);
    void mergeGuestCart(User user, Map<Long, Integer> guestCart);
    void removeFromCart(User user, Long productId);
}
