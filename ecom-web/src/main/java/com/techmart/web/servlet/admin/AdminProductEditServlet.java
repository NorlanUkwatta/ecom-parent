package com.techmart.web.servlet.admin;

import com.techmart.entity.Product;
import com.techmart.service.ProductService;
import jakarta.ejb.EJB;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@WebServlet(name = "AdminProductEditServlet", urlPatterns = {"/admin/products/edit"})
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 * 2,
        maxFileSize = 1024 * 1024 * 10,
        maxRequestSize = 1024 * 1024 * 50
)
public class AdminProductEditServlet extends HttpServlet {
    @EJB
    private ProductService productService;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long id = Long.parseLong(req.getParameter("id"));
        Product product = productService.getProductById(id);

        product.setName(req.getParameter("name"));
        product.setDescription(req.getParameter("description"));
        product.setPrice(Double.parseDouble(req.getParameter("price")));
        product.setStockQuantity(Integer.parseInt(req.getParameter("stock")));
        String categoryIdStr = req.getParameter("categoryId");
        String colorIdStr = req.getParameter("colorId");
        String storageIdStr = req.getParameter("storageId");

        if (categoryIdStr != null && !categoryIdStr.isEmpty()) {
            product.setCategory(productService.getCategoryById(Long.parseLong(categoryIdStr)));
        } else {
            product.setCategory(null);
        }

        if (colorIdStr != null && !colorIdStr.isEmpty()) {
            product.setColor(productService.getColorById(Long.parseLong(colorIdStr)));
        } else {
            product.setColor(null);
        }

        if (storageIdStr != null && !storageIdStr.isEmpty()) {
            product.setStorage(productService.getStorageById(Long.parseLong(storageIdStr)));
        } else {
            product.setStorage(null);
        }

        String newImg1 = saveImage(req.getPart("image1"), req);
        if (newImg1 != null) product.setImage1(newImg1);

        String newImg2 = saveImage(req.getPart("image2"), req);
        if (newImg2 != null) product.setImage2(newImg2);

        String newImg3 = saveImage(req.getPart("image3"), req);
        if (newImg3 != null) product.setImage3(newImg3);

        String newImg4 = saveImage(req.getPart("image4"), req);
        if (newImg4 != null) product.setImage4(newImg4);

        productService.updateProduct(product);
        resp.sendRedirect(req.getContextPath() + "/admin/inventory");
    }

    private String saveImage(Part part, HttpServletRequest request) throws IOException {
        if (part == null || part.getSize() == 0 || part.getSubmittedFileName().isEmpty()) {
            return null;
        }

        String fileName = UUID.randomUUID().toString() + "_" + part.getSubmittedFileName();
        String uploadPath = request.getServletContext().getRealPath("") + File.separator + "uploads";

        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) uploadDir.mkdir();

        Path filePath = Paths.get(uploadPath, fileName);
        Files.copy(part.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        return fileName;
    }
}
