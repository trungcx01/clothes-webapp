package com.example.clotheswebsite.service.impl;

import com.example.clotheswebsite.dto.ReviewDTO;
import com.example.clotheswebsite.entity.Product;
import com.example.clotheswebsite.entity.Review;
import com.example.clotheswebsite.entity.UserEntity;
import com.example.clotheswebsite.repository.ProductRepository;
import com.example.clotheswebsite.repository.ReviewRepository;
import com.example.clotheswebsite.repository.UserRepository;
import com.example.clotheswebsite.service.ReviewService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ReviewServiceImpl implements ReviewService {
    private final ReviewRepository reviewRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public ReviewServiceImpl(ReviewRepository reviewRepository, ProductRepository productRepository, UserRepository userRepository, ModelMapper modelMapper) {
        this.reviewRepository = reviewRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }


    @Override
    @Transactional
    public void addReview(ReviewDTO reviewDTO) {
        Product product = productRepository.findById(reviewDTO.getProductId()).orElse(null);
        double newRate = (product.getAverageRate() * product.getReviews().size() + reviewDTO.getRating()) / (product.getReviews().size() + 1);

        Review review = modelMapper.map(reviewDTO, Review.class);
        review.setProduct(product);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        UserEntity user = userRepository.findByUsername(username).orElse(null);
        review.setUser(user);
        reviewRepository.save(review);

        product.setAverageRate(newRate);
        productRepository.save(product);

    }

    @Override
    public List<Review> getAllReviewsByProduct(long productId) {
        Product product = productRepository.findById(productId).orElse(null);
        return reviewRepository.findReviewsByProduct(product);
    }

    @Override
    @Transactional
    public void deleteReview(long reviewId) {
        Review existReview = reviewRepository.findById(reviewId).orElse(null);
        Product product = existReview.getProduct();
        double newRate = (product.getAverageRate() * product.getReviews().size() - existReview.getRating()) / (product.getReviews().size() - 1);
        product.getReviews().remove(existReview);
        product.setAverageRate(product.getReviews().size() == 0 ? 0 : newRate);
        reviewRepository.delete(existReview);
        productRepository.save(product);
    }

//    @Override
//    @Transactional
//    public void updateReview(long reviewId, ReviewDTO reviewDTO) {
//        Review existReview = reviewRepository.findById(reviewId).orElse(null);
//        existReview.setContent(reviewDTO.getContent());
//        existReview.setRating(reviewDTO.getRating());
//        reviewRepository.save(existReview);
//
//        Product product = existReview.getProduct();
//        double newRate = (product.getAverageRate() * product.getReviews().size() - reviewDTO.getRating()) / product.getReviews().size();
//        product.setAverageRate(newRate);
//        productRepository.save(product);
//    }
}
