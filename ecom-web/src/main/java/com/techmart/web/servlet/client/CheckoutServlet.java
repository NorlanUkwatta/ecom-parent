package com.techmart.web.servlet.client;

import com.techmart.entity.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet(name = "CheckoutServlet", urlPatterns = {"/checkout"})
public class CheckoutServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        User loggedUser = (session != null) ? (User) session.getAttribute("loggedUser") : null;

        if (loggedUser == null) {
            HttpSession newSession = req.getSession(true);
            newSession.setAttribute("errorMessage", "Please log in to proceed to checkout.");
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        Integer cartCount = (Integer) session.getAttribute("cartCount");
        if (cartCount == null || cartCount == 0) {
            resp.sendRedirect(req.getContextPath() + "/cart");
            return;
        }

        // 3. Show the checkout form
        req.getRequestDispatcher("/client/checkout.jsp").forward(req, resp);
    }
}
