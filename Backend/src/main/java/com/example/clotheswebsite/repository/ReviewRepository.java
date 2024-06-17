package com.example.clotheswebsite.repository;

import com.example.clotheswebsite.entity.Product;
import com.example.clotheswebsite.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findReviewsByProduct(Product product);
}
