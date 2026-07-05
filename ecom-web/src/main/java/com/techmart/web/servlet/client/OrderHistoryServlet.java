package com.techmart.web.servlet.client;

import com.techmart.entity.Order;
import com.techmart.entity.User;
import com.techmart.service.OrderService;
import jakarta.ejb.EJB;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "OrderHistoryServlet", urlPatterns = {"/my-orders"})
public class OrderHistoryServlet extends HttpServlet {
    @EJB
    private OrderService orderService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        User loggedUser = (session != null) ? (User) session.getAttribute("loggedUser") : null;

        if (loggedUser == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        List<Order> myOrders = orderService.getOrdersByUser(loggedUser);
        req.setAttribute("orderList", myOrders);

        req.getRequestDispatcher("/client/my-orders.jsp").forward(req, resp);
    }
}
