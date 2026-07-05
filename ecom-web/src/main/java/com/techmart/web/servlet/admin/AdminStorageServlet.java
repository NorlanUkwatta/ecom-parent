package com.techmart.web.servlet.admin;

import com.techmart.entity.Storage;
import com.techmart.service.ProductService;
import jakarta.ejb.EJB;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name = "AdminStorageServlet", urlPatterns = {"/admin/storages"})
public class AdminStorageServlet extends HttpServlet {

    @EJB
    private ProductService productService;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String capacity = req.getParameter("capacity");

            if (capacity != null && !capacity.trim().isEmpty()) {
                Storage storage = new Storage();
                storage.setCapacity(capacity.trim());
                productService.addStorage(storage);

                req.getSession().setAttribute("successMessage", "Storage '" + capacity + "' added successfully!");
            } else {
                req.getSession().setAttribute("errorMessage", "Storage capacity cannot be empty.");
            }
        } catch (Exception e) {
            req.getSession().setAttribute("errorMessage", "Failed to add storage. It may already exist.");
        }

        resp.sendRedirect(req.getContextPath() + "/admin/inventory");
    }
}
