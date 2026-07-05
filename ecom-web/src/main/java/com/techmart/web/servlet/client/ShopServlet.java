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
import java.util.List;

@WebServlet(name = "ShopServlet", urlPatterns = {"/shop"})
public class ShopServlet extends HttpServlet {
    @EJB
    private ProductService productService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String catParam = req.getParameter("category");
        String brandParam = req.getParameter("brand");
        String colParam = req.getParameter("color");
        String storParam = req.getParameter("storage");
        String sortParam = req.getParameter("sort");

        Long catId = parseSafeLong(catParam);
        Long brandId = parseSafeLong(brandParam);
        Long colId = parseSafeLong(colParam);
        Long storId = parseSafeLong(storParam);

        List<Product> products = productService.getFilteredProducts(catId, brandId, colId, storId, sortParam);
        req.setAttribute("products", products);

        req.setAttribute("categories", productService.getAllCategories());
        req.setAttribute("brands", productService.getAllBrands());
        req.setAttribute("colors", productService.getAllColors());
        req.setAttribute("storages", productService.getAllStorages());

        req.setAttribute("selCat", catParam);
        req.setAttribute("selBrand", brandParam);
        req.setAttribute("selCol", colParam);
        req.setAttribute("selStor", storParam);
        req.setAttribute("selSort", sortParam);

        req.getRequestDispatcher("/client/shop.jsp").forward(req, resp);
    }
    private Long parseSafeLong(String value) {
        if (value == null || value.trim().isEmpty()) return null;
        try {
            return Long.parseLong(value);
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
