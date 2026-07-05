package com.techmart.web.servlet.admin;

import com.techmart.service.ProductService;
import jakarta.ejb.EJB;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "AdminProductDeleteServlet", urlPatterns = {"/admin/products/delete"})
public class AdminProductDeleteServlet extends HttpServlet {

    @EJB
    private ProductService productService;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long id = Long.parseLong(req.getParameter("id"));
        productService.deleteProduct(id);

        resp.sendRedirect(req.getContextPath() + "/admin/dashboard");
    }
}