package com.techmart.web.servlet.admin;

import com.techmart.entity.User;
import com.techmart.service.UserService;
import jakarta.ejb.EJB;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "AdminCustomerServlet", urlPatterns = {"/admin/customers"})
public class AdminCustomerServlet extends HttpServlet {
    @EJB
    private UserService userService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();

        if (session.getAttribute("successMessage") != null) {
            req.setAttribute("successMessage", session.getAttribute("successMessage"));
            session.removeAttribute("successMessage");
        }

        List<User> customers = userService.getAllCustomers();
        req.setAttribute("customers", customers);
        req.getRequestDispatcher("/admin/customers.jsp").forward(req, resp);

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            Long userId = Long.parseLong(req.getParameter("userId"));
            userService.toggleCustomerStatus(userId);

            req.getSession().setAttribute("successMessage", "Customer status successfully updated.");
        } catch (Exception e) {
            req.getSession().setAttribute("errorMessage", "Failed to update customer status.");
        }

        resp.sendRedirect(req.getContextPath() + "/admin/customers");
    }
}
