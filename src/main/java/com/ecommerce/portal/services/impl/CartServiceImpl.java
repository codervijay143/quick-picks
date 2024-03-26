package com.ecommerce.portal.services.impl;

import com.ecommerce.portal.dtos.AddItemRequest;
import com.ecommerce.portal.entities.Cart;
import com.ecommerce.portal.entities.CartItem;
import com.ecommerce.portal.entities.Product;
import com.ecommerce.portal.entities.User;
import com.ecommerce.portal.exceptions.ProductException;
import com.ecommerce.portal.repositories.CartRepository;
import com.ecommerce.portal.services.CartItemService;
import com.ecommerce.portal.services.CartService;
import com.ecommerce.portal.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final CartItemService cartItemService;
    private final ProductService productService;

    @Override
    public Cart createCart(User user) {

        Cart cart=new Cart();
        cart.setUser(user);
        return this.cartRepository.save(cart);
    }

    @Override
//    @Transactional
    public CartItem addCartItem(Long userId, AddItemRequest addItemRequest) throws ProductException {

        Cart cart=this.cartRepository.findByUserId(userId);
        Product product=this.productService.findProductById(addItemRequest.getProductId());

        CartItem isPresent=this.cartItemService.isCartItemExist(cart,product,addItemRequest.getSize(),userId);

        if(isPresent==null){
            CartItem cartItem=new CartItem();
            cartItem.setProduct(product);
            cartItem.setCart(cart);
            cartItem.setQuantity(addItemRequest.getQuantity());
            cartItem.setUserId(userId);

            int price=addItemRequest.getQuantity()*product.getDiscountedPrice();
            cartItem.setPrice(price);
            cartItem.setSize(addItemRequest.getSize());

            CartItem createdCartItem=this.cartItemService.createCartItem(cartItem);

//            cart.getCartItems().add(createdCartItem);
//           cart.setCartItems(createdCartItem);
            cart.setCartItems((Set<CartItem>) createdCartItem);
            return createdCartItem;
        }
        return isPresent;
    }

    @Override
    public Cart findUserCart(Long userId) {
        Cart cart=this.cartRepository.findByUserId(userId);

        int totalPrice=0;
        int totalDiscountedPrice=0;
        int totalItem=0;

        for(CartItem cartItem:cart.getCartItems()){
            totalPrice=totalPrice+cartItem.getPrice();
            totalDiscountedPrice=totalDiscountedPrice+cartItem.getDiscountedPrice();
            totalItem=totalItem+cartItem.getQuantity();
        }
        cart.setTotalDiscountedPrice(totalDiscountedPrice);
        cart.setTotalPrice(totalPrice);
        cart.setTotalItem(cart.getCartItems().size());
        cart.setDiscount(totalPrice-totalDiscountedPrice);
        cart.setTotalItem(totalItem);
        return this.cartRepository.save(cart);
    }
}
