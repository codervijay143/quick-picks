package com.ecommerce.portal.dtos;

import com.ecommerce.portal.entities.Address;
import com.ecommerce.portal.entities.OrderItem;
import com.ecommerce.portal.entities.PaymentDetails;
import com.ecommerce.portal.entities.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderDTO {
    private Long  id;
    private String orderId;
    private User user;
    private List<OrderItem> orderItems=new ArrayList<>();
    private LocalDateTime orderDate;
    private LocalDateTime deliveryDate;
    private Address shippingAddress;
    private PaymentDetails paymentDetails=new PaymentDetails();
    private double totalPrice;
    private Integer totalDiscountedPrice;
    private Integer discount;
    private OrderStatus orderStatus;
    private int totalItem;
    private LocalDateTime createdAt;
}