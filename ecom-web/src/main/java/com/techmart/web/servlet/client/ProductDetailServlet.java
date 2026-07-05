package com.techmart.web.servlet.client;

import com.techmart.entity.Product;
import com.techmart.service.ProductService;
import jakarta.ejb.EJB;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name = "ProductDetailServlet", urlPatterns = {"/shop/product"})
public class ProductDetailServlet extends HttpServlet {

    @EJB
    private ProductService productService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String idParam = req.getParameter("id");

        if (idParam == null || idParam.trim().isEmpty()) {
            resp.sendRedirect(req.getContextPath() + "/shop");
            return;
        }

        try {
            Long productId = Long.parseLong(idParam);
            Product product = productService.getProductById(productId);

            if (product != null) {
                req.setAttribute("product", product);
                req.getRequestDispatcher("/client/product.jsp").forward(req, resp);
            } else {
                req.getSession().setAttribute("errorMessage", "Product not found.");
                resp.sendRedirect(req.getContextPath() + "/shop");
            }
        } catch (NumberFormatException e) {
            resp.sendRedirect(req.getContextPath() + "/shop");
        }
    }
}
