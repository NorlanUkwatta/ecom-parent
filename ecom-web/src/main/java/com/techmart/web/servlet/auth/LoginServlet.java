package com.techmart.web.servlet.auth;

import com.techmart.core.util.SecurityUtil;
import com.techmart.dto.CartDTO;
import com.techmart.dto.CartItemDTO;
import com.techmart.entity.User;
import com.techmart.service.AuthService;
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

@WebServlet(name = "LoginServlet", urlPatterns = {"/login"})
public class LoginServlet extends HttpServlet {

    @EJB
    private AuthService authService;

    @EJB
    private CartService cartService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);

        if (session != null && session.getAttribute("loggedUser") != null) {
            User user = (User) session.getAttribute("loggedUser");

            if (user.getRole().name().equals("ADMIN")) {
                resp.sendRedirect(req.getContextPath() + "/admin/inventory");
            } else {
                resp.sendRedirect(req.getContextPath() + "/shop");
            }
            return;
        }
        req.getRequestDispatcher("/client/login.jsp").forward(req, resp);
    }

    @Override
    @SuppressWarnings("unchecked")
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String identifier = req.getParameter("identifier");
        String password = req.getParameter("password");

        String hashedPassword = SecurityUtil.hashPassword(password);
        User user = authService.login(identifier.trim().toLowerCase(), hashedPassword);

        if (user != null) {

            HttpSession session = req.getSession(true);

            Map<Long, Integer> guestCart = (Map<Long, Integer>) session.getAttribute("guestCart");

            if (guestCart != null && !guestCart.isEmpty()) {
                cartService.mergeGuestCart(user, guestCart);
                session.removeAttribute("guestCart");
                session.setAttribute("successMessage", "Your guest cart has been saved to your account!");
            }

            CartDTO userCart = cartService.getCartByUser(user);
            int totalItems = 0;
            if (userCart != null && userCart.getItems() != null) {
                for (CartItemDTO item : userCart.getItems()) {
                    totalItems += item.getQuantity();
                }
            }
            session.setAttribute("cartCount", totalItems);

            session.setAttribute("loggedUser", user);

            if (user.getRole().name().equals("ADMIN")) {
                resp.sendRedirect(req.getContextPath() + "/admin/inventory");
            } else {
                resp.sendRedirect(req.getContextPath() + "/shop");
            }
        } else {
            req.getSession().setAttribute("errorMessage", "Invalid credentials or suspended account.");
            resp.sendRedirect(req.getContextPath() + "/login");
        }
    }
}