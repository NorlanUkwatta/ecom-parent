package com.techmart.impl;

import com.techmart.entity.*;
import com.techmart.service.ProductService;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

import java.util.List;

@Stateless
public class ProductServiceImpl implements ProductService {

    @PersistenceContext(unitName = "TechMartPU")
    private EntityManager em;

    @Override
    public void addProduct(Product product) {
        em.persist(product);
    }

    @Override
    public List<Product> getAllProducts() {
        return em.createQuery("SELECT p FROM Product p ORDER BY p.id DESC", Product.class).getResultList();
    }

    @Override
    public Product getProductById(Long id) {
        return em.find(Product.class, id);
    }

    @Override
    public void updateProduct(Product product) {
        em.merge(product);
    }

    @Override
    public void deleteProduct(Long id) {
        Product product = em.find(Product.class, id);
        if (product != null) {
            em.remove(product);
        }
    }

    @Override
    public void addCategory(Category category) {
        em.persist(category);
    }

    @Override
    public List<Category> getAllCategories() {
        return em.createQuery("SELECT c FROM Category c ORDER BY c.name ASC", Category.class).getResultList();
    }

    @Override
    public Category getCategoryById(Long id) {
        return em.find(Category.class, id);
    }

    @Override
    public void addColor(Color color) {
        em.persist(color);
    }

    @Override
    public List<Color> getAllColors() {
        return em.createQuery("SELECT c FROM Color c ORDER BY c.name ASC", Color.class).getResultList();
    }

    @Override
    public Color getColorById(Long id) {
        return em.find(Color.class, id);
    }

    @Override
    public void addStorage(Storage storage) {
        em.persist(storage);
    }

    @Override
    public List<Storage> getAllStorages() {
        return em.createQuery("SELECT s FROM Storage s ORDER BY s.capacity ASC", Storage.class).getResultList();
    }

    @Override
    public Storage getStorageById(Long id) {
        return em.find(Storage.class, id);
    }

    @Override
    public void addBrand(Brand brand) {
        em.persist(brand);
    }

    @Override
    public List<Brand> getAllBrands() {
        return em.createQuery("SELECT b FROM Brand b ORDER BY b.name ASC", Brand.class).getResultList();
    }

    @Override
    public Brand getBrandById(Long id) {
        return em.find(Brand.class, id);
    }

    @Override
    public List<Product> getFilteredProducts(Long categoryId, Long brandId, Long colorId, Long storageId, String sort) {
        StringBuilder queryStr = new StringBuilder("SELECT p FROM Product p WHERE 1=1");

        if (categoryId != null) queryStr.append(" AND p.category.id = :categoryId");
        if (brandId != null) queryStr.append(" AND p.brand.id = :brandId");
        if (colorId != null) queryStr.append(" AND p.color.id = :colorId");
        if (storageId != null) queryStr.append(" AND p.storage.id = :storageId");

        if ("price_asc".equals(sort)) {
            queryStr.append(" ORDER BY p.price ASC");
        } else if ("price_desc".equals(sort)) {
            queryStr.append(" ORDER BY p.price DESC");
        } else {
            queryStr.append(" ORDER BY p.id DESC");
        }

        TypedQuery<Product> query = em.createQuery(queryStr.toString(), Product.class);

        if (categoryId != null) query.setParameter("categoryId", categoryId);
        if (brandId != null) query.setParameter("brandId", brandId);
        if (colorId != null) query.setParameter("colorId", colorId);
        if (storageId != null) query.setParameter("storageId", storageId);

        return query.getResultList();
    }

}
