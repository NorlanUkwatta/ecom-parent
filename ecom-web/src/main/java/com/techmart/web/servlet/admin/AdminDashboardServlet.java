package com.techmart.web.servlet.admin;

import com.techmart.entity.Product;
import com.techmart.service.ProductService;
import jakarta.ejb.EJB;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "AdminDashboardServlet", urlPatterns = {"/admin/inventory"})
public class AdminDashboardServlet extends HttpServlet {

    @EJB
    private ProductService productService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();

        String successMessage = (String) session.getAttribute("successMessage");
        String errorMessage = (String) session.getAttribute("errorMessage");

        if (successMessage != null) {
            req.setAttribute("successMessage", successMessage);
            session.removeAttribute("successMessage");
        }
        if (errorMessage != null) {
            req.setAttribute("errorMessage", errorMessage);
            session.removeAttribute("errorMessage");
        }

        List<Product> products = productService.getAllProducts();
        req.setAttribute("products", products);
        req.setAttribute("categories", productService.getAllCategories());
        req.setAttribute("colors", productService.getAllColors());
        req.setAttribute("storages", productService.getAllStorages());
        req.setAttribute("brands", productService.getAllBrands());
        req.getRequestDispatcher("/admin/inventory.jsp").forward(req, resp);
    }
}
