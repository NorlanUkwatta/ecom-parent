package com.techmart.web.servlet.auth;

import com.techmart.entity.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet(name = "LogoutServlet", urlPatterns = {"/logout"})
public class LogoutServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);

        String redirectUrl = req.getContextPath() + "/shop";

        if (session != null) {
            User loggedUser = (User) session.getAttribute("loggedUser");
            if (loggedUser != null && loggedUser.getRole().name().equals("ADMIN")) {
                redirectUrl = req.getContextPath() + "/login";
            }
            session.invalidate();
        }

        HttpSession newSession = req.getSession(true);
        newSession.setAttribute("successMessage", "You have been successfully signed out.");

        resp.sendRedirect(redirectUrl);
    }
}
