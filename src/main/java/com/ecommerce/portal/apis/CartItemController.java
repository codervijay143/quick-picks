package com.ecommerce.portal.apis;

import com.ecommerce.portal.dtos.ApiResponse;
import com.ecommerce.portal.dtos.CartItemDTO;
import com.ecommerce.portal.entities.CartItem;
import com.ecommerce.portal.entities.User;
import com.ecommerce.portal.exceptions.CartItemException;
import com.ecommerce.portal.exceptions.UserException;
import com.ecommerce.portal.services.CartItemService;
import com.ecommerce.portal.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/cart_items")
@RequiredArgsConstructor
@Tag(name="Cart Item Management", description = "create cart item delete cart item")
public class CartItemController {

	private final CartItemService cartItemService;
	private final UserService userService;

	
	@DeleteMapping("/{cartItemId}")
	public ResponseEntity<ApiResponse> deleteCartItemHandler(@PathVariable Long cartItemId, @RequestHeader("Authorization")String jwt) throws CartItemException, UserException, CartItemException, UserException {
		
		User user=userService.findUserProfileByJwt(jwt);
		cartItemService.removeCartItem(user.getId(), cartItemId);
		
		ApiResponse res=new ApiResponse("Item Remove From Cart",true);
		
		return new ResponseEntity<ApiResponse>(res,HttpStatus.ACCEPTED);
	}
	
	@PutMapping("/{cartItemId}")
	public ResponseEntity<CartItemDTO> updateCartItemHandler(@PathVariable Long cartItemId, @RequestBody CartItemDTO cartItem, @RequestHeader("Authorization")String jwt) throws CartItemException, UserException{
		
		User user=userService.findUserProfileByJwt(jwt);
		
		CartItem updatedCartItem =cartItemService.updateCartItem(user.getId(), cartItemId, cartItem);
		
		CartItemDTO cartItemDTO=this.entityToDTO(updatedCartItem);
		return new ResponseEntity<>(cartItemDTO,HttpStatus.ACCEPTED);
	}

	private CartItemDTO entityToDTO(CartItem item) {
		CartItemDTO cartItemDTO = new CartItemDTO();
		cartItemDTO.setId(item.getId());
		cartItemDTO.setProduct(item.getProduct());
		cartItemDTO.setSize(item.getSize());
		cartItemDTO.setQuantity(item.getQuantity());
		cartItemDTO.setPrice(item.getPrice());
		cartItemDTO.setDiscountedPrice(item.getDiscountedPrice());
		cartItemDTO.setUserId(item.getUserId());
		return cartItemDTO;
	}
}
