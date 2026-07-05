package com.techmart.web.servlet.admin;

import com.techmart.entity.Order;
import com.techmart.messaging.NotificationProducer;
import com.techmart.service.OrderService;
import jakarta.ejb.EJB;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "AdminOrderServlet", urlPatterns = {"/admin/orders"})
public class AdminOrderServlet extends HttpServlet {

    @EJB
    private OrderService orderService;

    @EJB
    private NotificationProducer notificationProducer;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Order> allOrders = orderService.getAllOrders();
        req.setAttribute("orderList", allOrders);
        req.getRequestDispatcher("/admin/manage-orders.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");

        if ("updateStatus".equals(action)) {
            Long orderId = Long.parseLong(req.getParameter("orderId"));
            String newStatus = req.getParameter("status");

            orderService.updateOrderStatus(orderId, newStatus);

            Long customerId = orderService.getUserIdByOrderId(orderId);

            if (customerId != null) {
                notificationProducer.sendOrderStatusUpdate(orderId, customerId, newStatus);
            }
        }

        resp.sendRedirect(req.getContextPath() + "/admin/orders");
    }
}
