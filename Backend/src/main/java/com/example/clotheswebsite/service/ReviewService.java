package com.example.clotheswebsite.service;

import com.example.clotheswebsite.dto.ReviewDTO;
import com.example.clotheswebsite.entity.Review;
import org.springframework.stereotype.Service;

import java.util.List;

public interface ReviewService {
    void addReview(ReviewDTO reviewDTO);
    List<Review> getAllReviewsByProduct(long productId);
    void deleteReview(long reviewId);
//    void updateReview(long reviewId, ReviewDTO reviewDTO);
}
