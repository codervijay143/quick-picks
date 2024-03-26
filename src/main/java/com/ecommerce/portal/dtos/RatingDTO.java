package com.ecommerce.portal.dtos;

import com.ecommerce.portal.entities.Product;
import com.ecommerce.portal.entities.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class RatingDTO {

    private Long id;
    private User user;
    private Product product;
    private Double rating;
    private LocalDateTime createdAt;
}