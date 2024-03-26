package com.ecommerce.portal.repositories;

import com.ecommerce.portal.entities.Rating;
import com.ecommerce.portal.entities.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review,Long> {
    @Query("Select r From Review r Where r.product.id=:productId")
    List<Review> getAllProductsReview(@Param("productId") Long productId);

}
