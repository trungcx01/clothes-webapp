package com.example.clotheswebsite.repository;

import com.example.clotheswebsite.entity.Product;
import com.example.clotheswebsite.entity.ProductSize;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductSizeRepository extends JpaRepository<ProductSize, Long> {
    List<ProductSize> findProductSizesByProduct(Product product);
}
