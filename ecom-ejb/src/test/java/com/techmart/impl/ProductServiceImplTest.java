package com.techmart.impl;

import com.techmart.entity.Product;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;

public class ProductServiceImplTest {

    @Mock
    private EntityManager em;

    @InjectMocks
    private ProductServiceImpl productService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testDeleteProduct_WhenProductExists_CallsRemove() {
        Product mockProduct = new Product();
        mockProduct.setId(5L);

        when(em.find(Product.class, 5L)).thenReturn(mockProduct);

        productService.deleteProduct(5L);

        verify(em, times(1)).remove(mockProduct);
    }

    @Test
    void testDeleteProduct_WhenProductDoesNotExist_IgnoresGracefully() {
        when(em.find(Product.class, 99L)).thenReturn(null);

        productService.deleteProduct(99L);

        verify(em, never()).remove(any());
    }
}