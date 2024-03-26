package com.ecommerce.portal.apis;

import com.ecommerce.portal.dtos.OrderDTO;
import com.ecommerce.portal.entities.Address;
import com.ecommerce.portal.entities.Order;
import com.ecommerce.portal.entities.User;
import com.ecommerce.portal.exceptions.OrderException;
import com.ecommerce.portal.exceptions.UserException;
import com.ecommerce.portal.services.OrderService;
import com.ecommerce.portal.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderAPI {

    private final OrderService orderService;
    private final UserService userService;

    @PostMapping("/")
    public ResponseEntity<OrderDTO> createOrderHandler(@RequestBody Address spippingAddress,
                                                    @RequestHeader("Authorization")String jwt) throws UserException{

        User user=this.userService.findUserProfileByJwt(jwt);
        OrderDTO order =this.orderService.createOrder(user, spippingAddress);

        return new ResponseEntity<OrderDTO>(order, HttpStatus.OK);

    }

    @GetMapping("/user")
    public ResponseEntity<List<OrderDTO>> usersOrderHistoryHandler(@RequestHeader("Authorization")
                                                                 String jwt) throws OrderException, UserException{

        User user=this.userService.findUserProfileByJwt(jwt);
        List<OrderDTO> orders=this.orderService.usersOrderHistory(user.getId());
        return new ResponseEntity<>(orders,HttpStatus.ACCEPTED);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity< Order> findOrderHandler(@PathVariable Long orderId, @RequestHeader("Authorization")
    String jwt) throws OrderException, UserException{

        User user=this.userService.findUserProfileByJwt(jwt);
        Order orders=this.orderService.findOrderById(orderId);
        return new ResponseEntity<>(orders,HttpStatus.ACCEPTED);
    }
}
