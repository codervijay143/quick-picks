package com.ecommerce.portal.services;

import com.ecommerce.portal.dtos.CartItemDTO;
import com.ecommerce.portal.entities.Cart;
import com.ecommerce.portal.entities.CartItem;
import com.ecommerce.portal.entities.Product;
import com.ecommerce.portal.exceptions.CartItemException;
import com.ecommerce.portal.exceptions.UserException;

public interface CartItemService {

    CartItem createCartItem(CartItem cartItem);

    CartItem updateCartItem(Long userId, Long id, CartItemDTO cartItem)throws CartItemException, UserException;

    CartItem isCartItemExist(Cart cart, Product product, String size, Long userId);

    void removeCartItem(Long userId,Long cartItemId)throws CartItemException,UserException;

    CartItem findCartItemById(Long cartItemId)throws CartItemException;

}
