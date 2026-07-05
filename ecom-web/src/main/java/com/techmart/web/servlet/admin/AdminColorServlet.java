package com.techmart.web.servlet.admin;

import com.techmart.entity.Color;
import com.techmart.service.ProductService;
import jakarta.ejb.EJB;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name = "AdminColorServlet", urlPatterns = {"/admin/colors"})
public class AdminColorServlet extends HttpServlet {

    @EJB
    private ProductService productService;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String name = req.getParameter("name");
            String hexCode = req.getParameter("hexcode");

            if (name != null && !name.trim().isEmpty() && hexCode != null && !hexCode.trim().isEmpty()) {
                Color color = new Color();
                color.setName(name.trim());
                color.setHexCode(hexCode.trim());
                productService.addColor(color);

                req.getSession().setAttribute("successMessage", "Color '" + name + "' added successfully!");
            } else {
                req.getSession().setAttribute("errorMessage", "Color name cannot be empty.");
            }
        } catch (Exception e) {
            req.getSession().setAttribute("errorMessage", "Failed to add color. It may already exist.");
        }

        resp.sendRedirect(req.getContextPath() + "/admin/inventory");
    }
}
