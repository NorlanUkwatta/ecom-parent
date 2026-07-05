package com.techmart.web.servlet.auth;

import com.techmart.core.enums.Role;
import com.techmart.core.util.SecurityUtil;
import com.techmart.entity.User;
import com.techmart.service.AuthService;
import jakarta.ejb.EJB;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet(name = "SignUpServlet", urlPatterns = {"/signup"})
public class SignUpServlet extends HttpServlet {

    @EJB
    private AuthService authService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("client/signup.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        HttpSession session = req.getSession();

        String username = req.getParameter("username");
        String email = req.getParameter("email");
        String mobile = req.getParameter("mobile");
        String password = req.getParameter("password");
        String rePassword = req.getParameter("rePassword");

        if (!password.equals(rePassword)) {
            session.setAttribute("errorMessage", "Passwords do not match!");
            resp.sendRedirect(req.getContextPath() + "/signup");
            return;
        }
        if (password.length() < 6) {
            session.setAttribute("errorMessage", "Password must be at least 6 characters.");
            resp.sendRedirect(req.getContextPath() + "/signup");
            return;
        }
        if (!email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            session.setAttribute("errorMessage", "Invalid email format.");
            resp.sendRedirect(req.getContextPath() + "/signup");
            return;
        }
        if (!mobile.matches("^(?:0|94|\\+94)7[0-8]\\d{7}$")) {
            session.setAttribute("errorMessage", "Invalid Sri Lankan mobile number format.");
            resp.sendRedirect(req.getContextPath() + "/signup");
            return;
        }

        User user = new User();
        user.setUsername(username.trim());
        user.setEmail(email.trim().toLowerCase());
        user.setMobile(mobile.trim());
        user.setPassword(SecurityUtil.hashPassword(password));
        user.setRole(Role.CUSTOMER);
        user.setActive(true);

        if (authService.registerCustomer(user)) {
            session.setAttribute("successMessage", "Account created successfully! Please log in.");
            resp.sendRedirect(req.getContextPath() + "/login");
        } else {
            session.setAttribute("errorMessage", "Username, Email, or Mobile is already registered.");
            resp.sendRedirect(req.getContextPath() + "/signup");
        }

    }
}
