package com.techmart.web.servlet.admin;

import com.techmart.entity.Brand;
import com.techmart.service.ProductService;
import jakarta.ejb.EJB;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name = "AdminBrandServlet", urlPatterns = {"/admin/brands"})
public class AdminBrandServlet extends HttpServlet {

    @EJB
    private ProductService productService;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String name = req.getParameter("name");

            if (name != null && !name.trim().isEmpty()) {
                Brand brand = new Brand();
                brand.setName(name.trim());
                productService.addBrand(brand);

                req.getSession().setAttribute("successMessage", "Brand '" + name + "' added successfully!");
            } else {
                req.getSession().setAttribute("errorMessage", "Brand name cannot be empty.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            req.getSession().setAttribute("errorMessage", "Failed to add brand. It may already exist.");
        }

        resp.sendRedirect(req.getContextPath() + "/admin/inventory");
    }
}
