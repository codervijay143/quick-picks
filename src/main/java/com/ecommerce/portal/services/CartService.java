package com.ecommerce.portal.services;

import com.ecommerce.portal.dtos.AddItemRequest;
import com.ecommerce.portal.entities.Cart;
import com.ecommerce.portal.entities.CartItem;
import com.ecommerce.portal.entities.User;
import com.ecommerce.portal.exceptions.ProductException;

public interface CartService {

    Cart createCart(User user);

    CartItem addCartItem(Long userId, AddItemRequest addItemRequest)throws ProductException;

    Cart findUserCart(Long userId);
}
