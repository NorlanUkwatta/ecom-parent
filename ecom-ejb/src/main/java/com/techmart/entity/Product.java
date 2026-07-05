package com.techmart.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Entity
@Table(name = "products")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Product implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(length = 1000)
    private String description;

    @Column(nullable = false, precision = 10, scale = 2)
    private Double price;

    @Column(nullable = false)
    private Integer stockQuantity;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @ManyToOne
    @JoinColumn(name = "color_id", nullable = false)
    private Color color;

    @ManyToOne
    @JoinColumn(name = "storage_id", nullable = true)
    private Storage storage;

    @ManyToOne
    @JoinColumn(name = "brand_id", nullable = false)
    private Brand brand;

    @Column(name = "image_1")
    private String image1;

    @Column(name = "image_2")
    private String image2;

    @Column(name = "image_3")
    private String image3;

    @Column(name = "image_4")
    private String image4;

}
