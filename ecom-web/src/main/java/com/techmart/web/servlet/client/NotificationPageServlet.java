package com.techmart.web.servlet.client;

import com.techmart.entity.Notification;
import com.techmart.entity.User;
import com.techmart.service.NotificationService;
import jakarta.ejb.EJB;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "NotificationPageServlet", urlPatterns = {"/notifications"})
public class NotificationPageServlet extends HttpServlet {

    @EJB
    private NotificationService notificationService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        User loggedUser = (session != null) ? (User) session.getAttribute("loggedUser") : null;

        if (loggedUser == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        List<Notification> allNotifications = notificationService.getAllUserNotifications(loggedUser);
        req.setAttribute("notificationList", allNotifications);

        req.getRequestDispatcher("/client/notifications.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        User loggedUser = (session != null) ? (User) session.getAttribute("loggedUser") : null;

        if (loggedUser != null) {
            String action = req.getParameter("action");

            if ("markAllRead".equals(action)) {
                notificationService.markAllAsRead(loggedUser);
            } else if ("markSingle".equals(action)) {
                String notifIdStr = req.getParameter("id");
                if (notifIdStr != null && !notifIdStr.isEmpty()) {
                    notificationService.markAsRead(Long.parseLong(notifIdStr));
                }
            }
        }
        resp.sendRedirect(req.getContextPath() + "/notifications");
    
    }
}
