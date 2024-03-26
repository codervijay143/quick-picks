package com.ecommerce.portal.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddItemRequest {

    private Long productId;
    private String size;
    private int quantity;
    private Integer price;


}
