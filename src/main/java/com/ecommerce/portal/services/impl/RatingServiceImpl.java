package com.ecommerce.portal.services.impl;

import com.ecommerce.portal.dtos.RatingRequest;
import com.ecommerce.portal.entities.Product;
import com.ecommerce.portal.entities.Rating;
import com.ecommerce.portal.entities.User;
import com.ecommerce.portal.exceptions.ProductException;
import com.ecommerce.portal.repositories.RatingRepository;
import com.ecommerce.portal.services.ProductService;
import com.ecommerce.portal.services.RatingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RatingServiceImpl implements RatingService {

    private final RatingRepository ratingRepository;
    private final ProductService productService;

    @Override
    public Rating createRating(RatingRequest ratingRequest, User user) throws ProductException {
        Product product=this.productService.findProductById(ratingRequest.getProductId());

        Rating rating=new Rating();
        rating.setProduct(product);
        rating.setUser(user);
        rating.setRating(ratingRequest.getRating());
        rating.setCreatedAt(LocalDateTime.now());
        return this.ratingRepository.save(rating);
    }

    @Override
    public List<Rating> getProductsRating(Long productId) {
        return this.ratingRepository.getAllProductsRating(productId);
    }
}
