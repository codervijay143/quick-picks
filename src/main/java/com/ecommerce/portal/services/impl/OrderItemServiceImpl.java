package com.ecommerce.portal.services.impl;

import com.ecommerce.portal.entities.OrderItem;
import com.ecommerce.portal.repositories.OrderItemRepository;
import com.ecommerce.portal.services.OrderItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderItemServiceImpl implements OrderItemService {

    private final OrderItemRepository orderItemRepository;


    @Override
    public OrderItem createOrderItem(OrderItem orderItem) {
        return this.orderItemRepository.save(orderItem);
    }
}
