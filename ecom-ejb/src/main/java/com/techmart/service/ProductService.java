package com.techmart.service;

import com.techmart.entity.*;
import jakarta.ejb.Remote;

import java.util.List;

@Remote
public interface ProductService {
    void addProduct(Product product);

    List<Product> getAllProducts();

    Product getProductById(Long id);

    void updateProduct(Product product);

    void deleteProduct(Long id);

    void addCategory(Category category);

    List<Category> getAllCategories();

    Category getCategoryById(Long id);

    void addColor(Color color);

    List<Color> getAllColors();

    Color getColorById(Long id);

    void addStorage(Storage storage);

    List<Storage> getAllStorages();

    Storage getStorageById(Long id);

    void addBrand(Brand brand);

    List<Brand> getAllBrands();

    Brand getBrandById(Long id);

    List<Product> getFilteredProducts(Long categoryId, Long brandId, Long colorId, Long storageId, String sort);

}
