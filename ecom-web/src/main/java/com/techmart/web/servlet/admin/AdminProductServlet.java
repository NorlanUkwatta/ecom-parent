package com.techmart.web.servlet.admin;

import com.techmart.entity.Product;
import com.techmart.service.ProductService;
import jakarta.ejb.EJB;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@WebServlet(name = "AdminProductServlet", urlPatterns = {"/admin/products"})
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 * 2, // 2MB before writing to disk
        maxFileSize = 1024 * 1024 * 10,      // 10MB max per file
        maxRequestSize = 1024 * 1024 * 50    // 50MB max overall request
)
public class AdminProductServlet extends HttpServlet {

    @EJB
    private ProductService productService;

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession session = req.getSession();

        try {

            String name = req.getParameter("name");
            String description = req.getParameter("description");
            String priceStr = req.getParameter("price");
            String stockStr = req.getParameter("stock");
            String categoryIdStr = req.getParameter("categoryId");
            String brandIdStr = req.getParameter("brandId");
            String colorIdStr = req.getParameter("colorId");
            String storageIdStr = req.getParameter("storageId");

            if (name == null || name.trim().isEmpty()) {
                session.setAttribute("errorMessage", "Validation Failed: Product name cannot be empty.");
                resp.sendRedirect(req.getContextPath() + "/admin/inventory");
                return;
            }

            Double price = Double.parseDouble(priceStr);
            if (price <= 0) {
                session.setAttribute("errorMessage", "Validation Failed: Price must be greater than zero.");
                resp.sendRedirect(req.getContextPath() + "/admin/inventory");
                return;
            }

            Integer stock = Integer.parseInt(stockStr);
            if (stock < 0) {
                session.setAttribute("errorMessage", "Validation Failed: Stock cannot be negative.");
                resp.sendRedirect(req.getContextPath() + "/admin/inventory");
                return;
            }

            Product product = new Product();
            product.setName(name.trim());
            product.setDescription(description != null ? description.trim() : "");
            product.setPrice(price);
            product.setStockQuantity(stock);

            if (categoryIdStr != null && !categoryIdStr.isEmpty()) {
                product.setCategory(productService.getCategoryById(Long.parseLong(categoryIdStr)));
            }
            if (colorIdStr != null && !colorIdStr.isEmpty()) {
                product.setColor(productService.getColorById(Long.parseLong(colorIdStr)));
            }
            if (storageIdStr != null && !storageIdStr.isEmpty()) {
                product.setStorage(productService.getStorageById(Long.parseLong(storageIdStr)));
            }
            if (brandIdStr != null && !brandIdStr.isEmpty()) {
                product.setBrand(productService.getBrandById(Long.parseLong(brandIdStr)));
            }

            product.setImage1(saveImage(req.getPart("image1"), req));
            product.setImage2(saveImage(req.getPart("image2"), req));
            product.setImage3(saveImage(req.getPart("image3"), req));
            product.setImage4(saveImage(req.getPart("image4"), req));

            productService.addProduct(product);

            session.setAttribute("successMessage", "Product '" + product.getName() + "' was successfully added!");

        } catch (NumberFormatException e) {
            session.setAttribute("errorMessage", "Validation Failed: Price and Stock must be valid numbers.");
        } catch (Exception e) {
            e.printStackTrace();
            session.setAttribute("errorMessage", "A system error occurred. Please try again.");
        }
        resp.sendRedirect(req.getContextPath() + "/admin/inventory");
    }

    private String saveImage(Part part, HttpServletRequest request) throws IOException {
        if (part == null || part.getSize() == 0 || part.getSubmittedFileName().isEmpty()) {
            return null;
        }

        String fileName = UUID.randomUUID().toString() + "_" + part.getSubmittedFileName();

        String uploadPath = "C:" + File.separator + "techmart_uploads";

        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) {
            uploadDir.mkdir();
        }

        Path filePath = Paths.get(uploadPath, fileName);
        Files.copy(part.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        return fileName;
    }
}
