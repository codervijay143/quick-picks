package com.ecommerce.portal.apis;

import java.util.List;

import com.ecommerce.portal.dtos.ApiResponse;
import com.ecommerce.portal.dtos.OrderDTO;
import com.ecommerce.portal.entities.Order;
import com.ecommerce.portal.exceptions.OrderException;
import com.ecommerce.portal.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin/orders")
public class AdminOrderController {

	@Autowired
	private OrderService orderService;

	@GetMapping("/")
	public ResponseEntity<List<OrderDTO>> getAllOrdersHandler(){
		List<OrderDTO> orderDTOS=orderService.getAllOrders();
		
		return new ResponseEntity<>(orderDTOS,HttpStatus.ACCEPTED);
	}
	
	@PutMapping("/{orderId}/confirmed")
	public ResponseEntity<OrderDTO> ConfirmedOrderHandler(@PathVariable Long orderId,
			@RequestHeader("Authorization") String jwt) throws OrderException {
		OrderDTO orderDTO=orderService.confirmedOrder(orderId);
		return new ResponseEntity<OrderDTO>(orderDTO,HttpStatus.ACCEPTED);
	}
	
	@PutMapping("/{orderId}/ship")
	public ResponseEntity<OrderDTO> shippedOrderHandler(@PathVariable Long orderId,
			@RequestHeader("Authorization") String jwt) throws OrderException{
		OrderDTO orderDTO=orderService.shippedOrder(orderId);
		return new ResponseEntity<OrderDTO>(orderDTO,HttpStatus.ACCEPTED);
	}
	
	@PutMapping("/{orderId}/deliver")
	public ResponseEntity<OrderDTO> deliveredOrderHandler(@PathVariable Long orderId,
			@RequestHeader("Authorization") String jwt) throws OrderException{
		OrderDTO orderDTO=orderService.deliveredOrder(orderId);
		return new ResponseEntity<OrderDTO>(orderDTO,HttpStatus.ACCEPTED);
	}
	
	@PutMapping("/{orderId}/cancel")
	public ResponseEntity<OrderDTO> canceledOrderHandler(@PathVariable Long orderId,
			@RequestHeader("Authorization") String jwt) throws OrderException{
		OrderDTO orderDTO=orderService.cancledOrder(orderId);
		return new ResponseEntity<OrderDTO>(orderDTO,HttpStatus.ACCEPTED);
	}
	
	@DeleteMapping("/{orderId}/delete")
	public ResponseEntity<ApiResponse> deleteOrderHandler(@PathVariable Long orderId,
														  @RequestHeader("Authorization") String jwt) throws OrderException{
		orderService.deleteOrder(orderId);
		ApiResponse res=new ApiResponse("Order Deleted Successfully",true);
		System.out.println("delete method working....");
		return new ResponseEntity<>(res,HttpStatus.ACCEPTED);
	}

}
