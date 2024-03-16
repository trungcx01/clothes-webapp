package com.example.clotheswebsite.repository;

import com.example.clotheswebsite.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>{
    List<Product> findByProductNameContainingIgnoreCase(String keyword);

    @Query(value = "SELECT * \n" +
            "FROM products p\n" +
            "ORDER BY p.total_sold_quantity DESC\n" +
            "LIMIT 8", nativeQuery = true)
    List<Product> findTop8BestSellerProduct();

    @Query(value = "SELECT * \n" +
            "FROM products p\n" +
            "ORDER BY p.product_id DESC\n" +
            "LIMIT 8", nativeQuery = true)
    List<Product> findTop8NewProduct();
}
