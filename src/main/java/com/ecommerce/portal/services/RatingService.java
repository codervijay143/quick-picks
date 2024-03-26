package com.ecommerce.portal.services;

import com.ecommerce.portal.dtos.RatingRequest;
import com.ecommerce.portal.entities.Rating;
import com.ecommerce.portal.entities.User;
import com.ecommerce.portal.exceptions.ProductException;

import java.util.List;

public interface RatingService {

    Rating createRating(RatingRequest ratingRequest, User user)throws ProductException;

    List<Rating> getProductsRating(Long productId);
}
