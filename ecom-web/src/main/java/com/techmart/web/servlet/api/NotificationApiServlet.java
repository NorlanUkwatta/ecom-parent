package com.techmart.web.servlet.api;

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
import java.io.PrintWriter;
import java.util.List;

@WebServlet(name = "NotificationApiServlet", urlPatterns = {"/fetch-notifications"})
public class NotificationApiServlet extends HttpServlet {

    @EJB
    NotificationService notificationService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        PrintWriter out = resp.getWriter();

        HttpSession session = req.getSession(false);
        User loggedUser = (session != null) ? (User) session.getAttribute("loggedUser") : null;

        if (loggedUser == null) {
            out.print("[]");
            return;
        }

        List<Notification> notifications = notificationService.getTop5UnreadNotifications(loggedUser);

        StringBuilder json = new StringBuilder("[");
        for (int i = 0; i < notifications.size(); i++) {
            Notification n = notifications.get(i);
            json.append("{")
                    .append("\"id\":").append(n.getId()).append(",")
                    .append("\"title\":\"").append(escapeJson(n.getTitle())).append("\",")
                    .append("\"message\":\"").append(escapeJson(n.getMessage())).append("\"")
                    .append("}");
            if (i < notifications.size() - 1) json.append(",");
        }
        json.append("]");

        out.print(json.toString());

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String notifIdStr = req.getParameter("id");
        if (notifIdStr != null && !notifIdStr.isEmpty()) {
            notificationService.markAsRead(Long.parseLong(notifIdStr));
        }
        resp.setStatus(200);
    }

    private String escapeJson(String data) {
        if (data == null) {
            return "";
        }

        return data.replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("\n", " ")
                .replace("\r", "");
    }
}
