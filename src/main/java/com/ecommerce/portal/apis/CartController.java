package com.ecommerce.portal.apis;

import com.ecommerce.portal.dtos.AddItemRequest;
import com.ecommerce.portal.dtos.ApiResponse;
import com.ecommerce.portal.dtos.CartDTO;
import com.ecommerce.portal.dtos.CartItemDTO;
import com.ecommerce.portal.entities.Cart;
import com.ecommerce.portal.entities.CartItem;
import com.ecommerce.portal.entities.User;
import com.ecommerce.portal.exceptions.ProductException;
import com.ecommerce.portal.exceptions.UserException;
import com.ecommerce.portal.services.CartService;
import com.ecommerce.portal.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {
	
	private final CartService cartService;
	private final UserService userService;
	

	
	@GetMapping("/")
	public ResponseEntity<CartDTO> findUserCartHandler(@RequestHeader("Authorization") String jwt) throws UserException {
		
		User user=this.userService.findUserProfileByJwt(jwt);
		
		Cart cart=this.cartService.findUserCart(user.getId());
		
		CartDTO cartDTO=this.entityToDTO(cart);
		return new ResponseEntity<CartDTO>(cartDTO,HttpStatus.OK);
	}

	@PutMapping("/add")
	public ResponseEntity<CartItemDTO> addItemToCart(@RequestBody AddItemRequest req,
													 @RequestHeader("Authorization") String jwt) throws UserException, ProductException {
		
		User user=this.userService.findUserProfileByJwt(jwt);
		
		CartItem item = this.cartService.addCartItem(user.getId(), req);
		
		ApiResponse res= new ApiResponse("Item Added To Cart Successfully",true);

		CartItemDTO cartItemDTO=this.entityToDTO2(item);
		return new ResponseEntity<>(cartItemDTO,HttpStatus.ACCEPTED);
		
	}

	private CartDTO entityToDTO(Cart cart) {

	    CartDTO cartDTO = new CartDTO();
	    cartDTO.setId(cart.getId());
	    cartDTO.setUser(cart.getUser());
	    cartDTO.setCartItems(cart.getCartItems());
	    cartDTO.setTotalPrice(cart.getTotalPrice());
	    cartDTO.setTotalItem(cart.getTotalItem());
	    cartDTO.setTotalDiscountedPrice(cart.getTotalDiscountedPrice());
	    cartDTO.setDiscount(cart.getDiscount());
	    return cartDTO;

	}

	private CartItemDTO entityToDTO2(CartItem item) {
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
