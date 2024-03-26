package com.ecommerce.portal.dtos;

import com.ecommerce.portal.entities.CartItem;
import com.ecommerce.portal.entities.User;

import lombok.*;

import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class CartDTO {
    private Long id;
    private User user;
    private Set<CartItem> cartItems=new HashSet<>();
    private double totalPrice;
    private int totalItem;
    private int totalDiscountedPrice;
    private int discount;

}
