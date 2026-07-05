package com.techmart.impl;

import com.techmart.dto.CartDTO;
import com.techmart.dto.CartItemDTO;
import com.techmart.entity.Cart;
import com.techmart.entity.CartItem;
import com.techmart.entity.Product;
import com.techmart.entity.User;
import com.techmart.service.CartService;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;

import java.util.List;
import java.util.Map;

@Stateless
public class CartServiceImpl implements CartService {

    @PersistenceContext(unitName = "TechMartPU")
    private EntityManager em;

    private Cart getCartEntityByUser(User user) {
        List<Cart> carts = em.createQuery("SELECT c FROM Cart c LEFT JOIN FETCH c.items i LEFT JOIN FETCH i.product WHERE c.user = :user", Cart.class)
                .setParameter("user", user)
                .getResultList();

        if (carts != null && !carts.isEmpty()) {
            return carts.get(0);
        } else {
            Cart newCart = new Cart();
            newCart.setUser(user);
            em.persist(newCart);
            return newCart;
        }
    }

    @Override
    public CartDTO getCartByUser(User user) {
        Cart cartEntity = getCartEntityByUser(user);
        CartDTO dto = new CartDTO();
        dto.setId(cartEntity.getId());

        if (cartEntity.getItems() != null) {
            for (CartItem item : cartEntity.getItems()) {
                CartItemDTO itemDTO = new CartItemDTO();
                itemDTO.setId(item.getId());
                itemDTO.setQuantity(item.getQuantity());

                if (item.getProduct() != null) {
                    itemDTO.setProductId(item.getProduct().getId());
                    itemDTO.setProductName(item.getProduct().getName());
                    itemDTO.setPrice(item.getProduct().getPrice());
                    itemDTO.setImage1(item.getProduct().getImage1());

                    if (item.getProduct().getBrand() != null) {
                        itemDTO.setBrandName(item.getProduct().getBrand().getName());
                    }
                }
                dto.getItems().add(itemDTO);
            }
        }
        return dto;
    }

    @Override
    public void addToCart(User user, Long productId, int quantity) {
        Cart cart = getCartEntityByUser(user);
        Product product = em.find(Product.class, productId);

        if (product == null) return;

        for (CartItem item : cart.getItems()) {
            if (item.getProduct().getId().equals(productId)) {
                item.setQuantity(item.getQuantity() + quantity);
                em.merge(item);
                return;
            }
        }

        CartItem newItem = new CartItem();
        newItem.setCart(cart);
        newItem.setProduct(product);
        newItem.setQuantity(quantity);
        cart.getItems().add(newItem);
        em.merge(cart);
    }

    @Override
    public void mergeGuestCart(User user, Map<Long, Integer> guestCart) {
        if (guestCart == null || guestCart.isEmpty()) return;

        Cart cart = getCartEntityByUser(user);

        for (Map.Entry<Long, Integer> entry : guestCart.entrySet()) {
            Long productId = entry.getKey();
            Integer quantity = entry.getValue();

            Product product = em.find(Product.class, productId);
            if (product != null) {
                addToCart(user, productId, quantity);
            }
        }
    }

    @Override
    public void removeFromCart(User user, Long productId) {
        Cart cart = getCartEntityByUser(user);

        if (cart != null && cart.getItems() != null) {
            CartItem itemToRemove = null;

            for (CartItem item : cart.getItems()) {
                if (item.getProduct().getId().equals(productId)) {
                    itemToRemove = item;
                    break;
                }
            }

            if (itemToRemove != null) {
                cart.getItems().remove(itemToRemove);
                em.merge(cart);
            }
        }

    }
}
