package com.ecommerce.portal.services;

import com.ecommerce.portal.dtos.ReviewRequest;
import com.ecommerce.portal.entities.Review;
import com.ecommerce.portal.entities.User;
import com.ecommerce.portal.exceptions.ProductException;

import java.util.List;

public interface ReviewService {

    Review createReview(ReviewRequest reviewRequest, User user)throws ProductException;

    List<Review> getProductsReview(Long productId);
}
