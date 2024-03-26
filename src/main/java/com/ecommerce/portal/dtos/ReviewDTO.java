package com.ecommerce.portal.dtos;

import com.ecommerce.portal.entities.Product;
import com.ecommerce.portal.entities.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReviewDTO {

    private Long id;
    private String review;
    private LocalDateTime createdAt;
    private Product product;
    private User user;

}