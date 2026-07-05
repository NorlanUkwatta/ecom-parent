package com.techmart.web.servlet.admin;

import com.techmart.entity.Notification;
import com.techmart.service.NotificationService;
import jakarta.ejb.EJB;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "AdminNotificationServlet", urlPatterns = {"/admin/notifications"})
public class AdminNotificationServlet extends HttpServlet {

    @EJB
    private NotificationService notificationService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Notification> adminNotifs = notificationService.getAdminNotifications();
        req.setAttribute("notificationList", adminNotifs);

        req.getRequestDispatcher("/admin/admin-notifications.jsp").forward(req, resp);
    }
}
