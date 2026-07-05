package com.techmart.web.servlet.admin;

import com.techmart.entity.Category;
import com.techmart.service.ProductService;
import jakarta.ejb.EJB;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name = "AdminCategoryServlet", urlPatterns = {"/admin/categories"})
public class AdminCategoryServlet extends HttpServlet {

    @EJB
    private ProductService productService;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String name = req.getParameter("name");

            if (name != null && !name.trim().isEmpty()) {
                Category category = new Category();
                category.setName(name.trim());
                productService.addCategory(category);

                req.getSession().setAttribute("successMessage", "Category '" + name + "' added successfully!");
            } else {
                req.getSession().setAttribute("errorMessage", "Category name cannot be empty.");
            }
        } catch (Exception e) {
            req.getSession().setAttribute("errorMessage", "Failed to add category. It may already exist.");
        }

        resp.sendRedirect(req.getContextPath() + "/admin/inventory");
    }
}
