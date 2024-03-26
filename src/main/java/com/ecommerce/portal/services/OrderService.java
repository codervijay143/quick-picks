package com.ecommerce.portal.services;

import com.ecommerce.portal.dtos.OrderDTO;
import com.ecommerce.portal.entities.Address;
import com.ecommerce.portal.entities.Order;
import com.ecommerce.portal.entities.User;
import com.ecommerce.portal.exceptions.OrderException;

import java.util.List;

public interface OrderService {

    OrderDTO createOrder(User user, Address shippingAddress);

    Order findOrderById(Long orderId)throws OrderException;

    List<OrderDTO> usersOrderHistory(Long userId);

    OrderDTO placedOrder(Long orderId)throws OrderException;

    OrderDTO confirmedOrder(Long orderId)throws OrderException;

    OrderDTO shippedOrder(Long orderId)throws OrderException;

    OrderDTO deliveredOrder(Long orderId)throws OrderException;

    OrderDTO cancledOrder(Long orderId)throws OrderException;

    List<OrderDTO> getAllOrders();

    void deleteOrder(Long orderId)throws OrderException;

}
