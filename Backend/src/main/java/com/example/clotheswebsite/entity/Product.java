package com.example.clotheswebsite.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

@Entity
@Table(name = "products")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id", nullable = false, updatable = false)
    private long productId;

    @Column(name = "product_name", nullable = false)
    private String productName;

    @Column(name = "category", nullable = false)
    private String category;

    @Column(name = "age_for", nullable = false)
    private String ageFor;

    @Column(name = "gender_for", nullable = false)
    private String genderFor;

    @Column(name = "image_url", nullable = false)
    private String imageUrl;

    @Column(name = "description")
    private String description;

    @ManyToOne
    @JoinColumn(name = "supplier_id", nullable = false)
    private Supplier supplier;

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonIgnoreProperties("product")  // sẽ ignore thuộc tính product trong đối tượng ProductSize productSize
    private List<ProductSize> productSizes = new ArrayList<>();

    @ManyToMany
    @JoinTable(name = "products_discounts",
                joinColumns = @JoinColumn(name = "product_id", referencedColumnName = "product_id"),
                inverseJoinColumns = @JoinColumn(name = "discount_id", referencedColumnName = "discount_id"))
    @JsonIgnoreProperties("products")
    private List<Discount> discounts;

    @Column(name = "total_sold_quantity", nullable = false)
    private long totalSoldQuantity;

}
