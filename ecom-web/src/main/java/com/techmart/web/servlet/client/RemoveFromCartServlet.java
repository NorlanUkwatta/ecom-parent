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
import java.util.Map;

@WebServlet(name = "RemoveFromCartServlet", urlPatterns = {"/cart/remove"})
public class RemoveFromCartServlet extends HttpServlet {

    @EJB
    private CartService cartService;

    @Override
    @SuppressWarnings("unchecked")
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(true);
        User loggedUser = (User) session.getAttribute("loggedUser");

        try {
            Long productId = Long.parseLong(req.getParameter("productId"));

            if (loggedUser != null) {

                cartService.removeFromCart(loggedUser, productId);

                CartDTO updatedCart = cartService.getCartByUser(loggedUser);
                int newTotal = 0;
                if (updatedCart != null && updatedCart.getItems() != null) {
                    for (CartItemDTO item : updatedCart.getItems()) {
                        newTotal += item.getQuantity();
                    }
                }
                session.setAttribute("cartCount", newTotal);

            } else {

                Map<Long, Integer> guestCart = (Map<Long, Integer>) session.getAttribute("guestCart");
                if (guestCart != null) {
                    guestCart.remove(productId);
                    session.setAttribute("guestCart", guestCart);
                }
            }
        } catch (NumberFormatException e) {
            // Silently ignore if someone tampers with the HTML form
        }

        resp.sendRedirect(req.getContextPath() + "/cart");
    }
}
