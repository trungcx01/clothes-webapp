package com.example.clotheswebsite.controller;

import com.example.clotheswebsite.dto.ReviewDTO;
import com.example.clotheswebsite.message.ResponseMessage;
import com.example.clotheswebsite.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class ReviewController {
    private final ReviewService reviewService;

    @Autowired
    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @GetMapping("/products/{productId}/reviews")
    public ResponseEntity<?> getAllReviewsByProduct(@PathVariable long productId){
        return ResponseEntity.ok(reviewService.getAllReviewsByProduct(productId));
    }

    @PostMapping("/add-review")
    public ResponseEntity<?> addNewReview(@RequestBody ReviewDTO reviewDTO){
        reviewService.addReview(reviewDTO);
        return ResponseEntity.ok(new ResponseMessage("Add review successfully!"));
    }

    @DeleteMapping("/delete-review/{reviewId}")
    public ResponseEntity<?> deleteReview(@PathVariable long reviewId){
        reviewService.deleteReview(reviewId);
        return ResponseEntity.ok(new ResponseMessage("Delete review successfully!"));
    }
}
