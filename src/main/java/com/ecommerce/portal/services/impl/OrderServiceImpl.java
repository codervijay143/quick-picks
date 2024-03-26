package com.ecommerce.portal.services.impl;

import com.ecommerce.portal.dtos.OrderDTO;
import com.ecommerce.portal.dtos.OrderStatus;
import com.ecommerce.portal.dtos.PaymentStatus;
import com.ecommerce.portal.entities.*;
import com.ecommerce.portal.exceptions.OrderException;
import com.ecommerce.portal.repositories.*;
import com.ecommerce.portal.services.CartService;
import com.ecommerce.portal.services.OrderItemService;
import com.ecommerce.portal.services.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final OrderItemService orderItemService;
    private final CartService cartService;
    private final OrderItemRepository orderItemRepository;
    private final AddressRepository addressRepository;

    @Override
    public OrderDTO createOrder(User user, Address shippingAddress) {

    shippingAddress.setUser(user);
    Address address=this.addressRepository.save(shippingAddress);
    user.getAddress().add(address);
    this.userRepository.save(user);

    Cart cart=this.cartService.findUserCart(user.getId());

    List<OrderItem> orderItems=new ArrayList<>();

    for(CartItem item:cart.getCartItems()){
        OrderItem orderItem=new OrderItem();

        orderItem.setProduct(item.getProduct());
        orderItem.setSize(item.getSize());
        orderItem.setQuantity(item.getQuantity());
        orderItem.setPrice(item.getPrice());
        orderItem.setDiscountedPrice(item.getDiscountedPrice());
        orderItem.setUserId(item.getUserId());

        OrderItem createdOrderItem=this.orderItemRepository.save(orderItem);

        orderItems.add(createdOrderItem);
    }

    Order createdOrder=new Order();
    createdOrder.setUser(user);
    createdOrder.setOrderItems(orderItems);
    createdOrder.setShippingAddress(address);
    createdOrder.setTotalPrice(cart.getTotalPrice());
    createdOrder.setTotalDiscountedPrice(cart.getTotalDiscountedPrice());
    createdOrder.setDiscount(cart.getDiscount());
    createdOrder.setOrderStatus(OrderStatus.PENDING);
    createdOrder.getPaymentDetails().setStatus(PaymentStatus.PENDING);
    createdOrder.setTotalItem(cart.getTotalItem());
    createdOrder.setCreatedAt(LocalDateTime.now());
    createdOrder.setOrderDate(LocalDateTime.now());

    Order savedOrder=this.orderRepository.save(createdOrder);

    for(OrderItem item:orderItems){
        item.setOrder(savedOrder);
        this.orderItemRepository.save(item);
    }
        return this.entityToDTO(savedOrder);
    }

    @Override
    public Order findOrderById(Long orderId) throws OrderException {

        Optional<Order> optional=this.orderRepository.findById(orderId);

        if(optional.isPresent()){
            return optional.get();
        }
        throw new OrderException("Order not exist with id "+orderId);
    }

    @Override
    public List<OrderDTO> usersOrderHistory(Long userId) {
        List<Order> orders=this.orderRepository.getUsersOrders(userId);
        List<OrderDTO> orderDTOS=new ArrayList<>();
        for (Order order:orders){
            OrderDTO orderDTO=this.entityToDTO(order);
            orderDTOS.add(orderDTO);
        }
        return orderDTOS;
    }

    @Override
    public OrderDTO placedOrder(Long orderId) throws OrderException {

        Order order=findOrderById(orderId);
        order.setOrderStatus(OrderStatus.PLACED);
        order.getPaymentDetails().setStatus(PaymentStatus.COMPLETED);
        return this.entityToDTO(order);
    }

    @Override
    public OrderDTO confirmedOrder(Long orderId) throws OrderException {

        Order order=findOrderById(orderId);
        order.setOrderStatus(OrderStatus.CONFIRMED);
        Order order1 = this.orderRepository.save(order);
        return this.entityToDTO(order1);
    }

    @Override
    public OrderDTO shippedOrder(Long orderId) throws OrderException {

        Order order=findOrderById(orderId);
        order.setOrderStatus(OrderStatus.SHIPPED);
        Order order1=this.orderRepository.save(order);
        return this.entityToDTO(order1);
    }

    @Override
    public OrderDTO deliveredOrder(Long orderId) throws OrderException {
        Order order=findOrderById(orderId);
        order.setOrderStatus(OrderStatus.DELIVERED);
        Order order1=this.orderRepository.save(order);
        return this.entityToDTO(order1);
    }

    @Override
    public OrderDTO cancledOrder(Long orderId) throws OrderException {
        Order order=findOrderById(orderId);
        order.setOrderStatus(OrderStatus.CANCELLED);
        Order order1=this.orderRepository.save(order);
        return this.entityToDTO(order1);
    }

    @Override
    public List<OrderDTO> getAllOrders() {
        List<Order> orders=this.orderRepository.findAll();
        List<OrderDTO> orderDTOS=new ArrayList<>();
        for (Order order:orders){
            OrderDTO orderDTO=this.entityToDTO(order);
            orderDTOS.add(orderDTO);
        }
        return orderDTOS;
    }

    @Override
    public void deleteOrder(Long orderId) throws OrderException {

        Order order=findOrderById(orderId);

        this.orderRepository.deleteById(orderId);
    }

    private OrderDTO entityToDTO(Order order){

        OrderDTO orderDTO=new OrderDTO();
        orderDTO.setId(order.getId());
        orderDTO.setOrderId(order.getOrderId());
        orderDTO.setUser(order.getUser());
        orderDTO.setOrderItems(order.getOrderItems());
        orderDTO.setOrderDate(order.getOrderDate());
        orderDTO.setDeliveryDate(order.getDeliveryDate());
        orderDTO.setShippingAddress(order.getShippingAddress());
        orderDTO.setPaymentDetails(order.getPaymentDetails());
        orderDTO.setTotalPrice(order.getTotalPrice());
        orderDTO.setTotalDiscountedPrice(order.getTotalDiscountedPrice());
        orderDTO.setDiscount(order.getDiscount());
        orderDTO.setOrderStatus(order.getOrderStatus());
        orderDTO.setTotalItem(order.getTotalItem());
        orderDTO.setCreatedAt(order.getCreatedAt());

    return orderDTO;
    }
}
