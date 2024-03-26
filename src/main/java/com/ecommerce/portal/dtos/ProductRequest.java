package com.ecommerce.portal.dtos;

import com.ecommerce.portal.entities.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductRequest {

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
    private Set<Size> size=new HashSet<>();
    private String topLavelCategory;
    private String secondLavelCategory;
    private String thirdLavelCategory;
}
