package com.techmart.web.servlet.client;

import com.techmart.dto.CartDTO;
import com.techmart.dto.CartItemDTO;
import com.techmart.entity.Cart;
import com.techmart.entity.CartItem;
import com.techmart.entity.User;
import com.techmart.service.CartService;
import jakarta.ejb.EJB;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "AddToCartServlet", urlPatterns = {"/cart/add"})
public class AddToCartServlet extends HttpServlet {

    @EJB
    private CartService cartService;

    @Override
    @SuppressWarnings("unchecked")
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(true);
        User loggedUser = (User) session.getAttribute("loggedUser");

        Long productId = Long.parseLong(req.getParameter("productId"));
        int quantity = Integer.parseInt(req.getParameter("quantity"));

        if (loggedUser != null) {
            cartService.addToCart(loggedUser, productId, quantity);

            CartDTO updatedCart = cartService.getCartByUser(loggedUser);
            int newTotal = 0;
            if (updatedCart != null && updatedCart.getItems() != null) {
                for (CartItemDTO item : updatedCart.getItems()) {
                    newTotal += item.getQuantity();
                }
            }

            session.setAttribute("cartCount", newTotal);

            session.setAttribute("successMessage", "Item added to your account cart!");
        } else {

            Map<Long, Integer> guestCart = (Map<Long, Integer>) session.getAttribute("guestCart");
            if (guestCart == null) {
                guestCart = new HashMap<>();
            }

            guestCart.put(productId, guestCart.getOrDefault(productId, 0) + quantity);
            session.setAttribute("guestCart", guestCart);

            session.setAttribute("successMessage", "Item added to your guest cart!");
        }

        resp.sendRedirect(req.getContextPath() + "/shop");
    }

}
