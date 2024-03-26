package com.ecommerce.portal.services.impl;

import com.ecommerce.portal.dtos.ReviewRequest;
import com.ecommerce.portal.entities.Product;
import com.ecommerce.portal.entities.Rating;
import com.ecommerce.portal.entities.Review;
import com.ecommerce.portal.entities.User;
import com.ecommerce.portal.exceptions.ProductException;
import com.ecommerce.portal.repositories.ProductRepository;
import com.ecommerce.portal.repositories.ReviewRepository;
import com.ecommerce.portal.services.ProductService;
import com.ecommerce.portal.services.ReviewService;
import com.ecommerce.portal.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final ProductRepository productRepository;
    private final ProductService productService;
    private final ReviewRepository reviewRepository;

    @Override
    public Review createReview(ReviewRequest reviewRequest, User user) throws ProductException {
        Product product=this.productService.findProductById(reviewRequest.getProductId());

        Review review=new Review();
        review.setProduct(product);
        review.setUser(user);
        review.setReview(reviewRequest.getReview());
        review.setCreatedAt(LocalDateTime.now());
        return this.reviewRepository.save(review);
    }

    @Override
    public List<Review> getProductsReview(Long productId) {
        return this.reviewRepository.getAllProductsReview(productId);
    }
}
