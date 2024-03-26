package com.ecommerce.portal.dtos;

import com.ecommerce.portal.entities.Category;
import com.ecommerce.portal.entities.Rating;
import com.ecommerce.portal.entities.Review;
import com.ecommerce.portal.entities.Size;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductDTO {

    private Long id;
    private String description;
    private int price;
    private String title;
    private int discountedPrice;
    private int discountPersent;
    private int quantity;
    private String brand;
    private String color;
    private String imageUrl;
    private int numRatings;
    private LocalDateTime createdAt;
    private List<Rating> ratingList=new ArrayList<>();
    private List<Review> reviewList=new ArrayList<>();
    private Set<Size> size=new HashSet<>();
    private Category category;
}