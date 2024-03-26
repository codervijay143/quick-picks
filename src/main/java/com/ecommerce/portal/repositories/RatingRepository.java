package com.ecommerce.portal.repositories;

import com.ecommerce.portal.entities.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RatingRepository extends JpaRepository<Rating,Long> {

    @Query("SELECT r From Rating r Where r.product.id=:productId")
    List<Rating> getAllProductsRating(@Param("productId") Long productId);
}
