package com.ecommerce.portal.services.impl;

import com.ecommerce.portal.dtos.CartItemDTO;
import com.ecommerce.portal.entities.Cart;
import com.ecommerce.portal.entities.CartItem;
import com.ecommerce.portal.entities.Product;
import com.ecommerce.portal.entities.User;
import com.ecommerce.portal.exceptions.CartItemException;
import com.ecommerce.portal.exceptions.UserException;
import com.ecommerce.portal.repositories.CartItemRepository;
import com.ecommerce.portal.repositories.CartRepository;
import com.ecommerce.portal.services.CartItemService;
import com.ecommerce.portal.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartItemServiceImpl implements CartItemService {

    private final CartItemRepository cartItemRepository;
    private final UserService userService;
    private final CartRepository cartRepository;

    @Override
    public CartItem createCartItem(CartItem cartItem) {
        cartItem.setQuantity(1);
        cartItem.setPrice(cartItem.getProduct().getPrice()*cartItem.getQuantity());
        cartItem.setDiscountedPrice(cartItem.getProduct().getDiscountedPrice()*cartItem.getQuantity());

        return this.cartItemRepository.save(cartItem);
    }

    @Override
    public CartItem updateCartItem(Long userId, Long id, CartItemDTO cartItem) throws CartItemException, UserException {
        CartItem item=findCartItemById(id);
        User user=this.userService.findUserById(item.getUserId());

        if(user.getId().equals(userId)){
            item.setQuantity(cartItem.getQuantity());
            item.setPrice(item.getQuantity()*item.getProduct().getPrice());
            item.setDiscountedPrice(item.getProduct().getDiscountedPrice()* item.getQuantity());
        }
        return this.cartItemRepository.save(item);
    }

    @Override
    public CartItem isCartItemExist(Cart cart, Product product, String size, Long userId) {
        return this.cartItemRepository.isCartItemExist(cart,product,size,userId);
    }

    @Override
    public void removeCartItem(Long userId, Long cartItemId) throws CartItemException, UserException {

    CartItem cartItem=findCartItemById(cartItemId);
    User user=this.userService.findUserById(cartItem.getUserId());

    User reqUser=this.userService.findUserById(userId);

        if(user.getId().equals(reqUser.getId())){
        this.cartItemRepository.deleteById(cartItemId);
        }
        else{
        throw new UserException("ypu can't remove another users Item");
        }
    }

    @Override
    public CartItem findCartItemById(Long cartItemId) throws CartItemException {
        Optional<CartItem> optional=this.cartItemRepository.findById(cartItemId);

        if(optional.isPresent()){
            return optional.get();
        }
        throw new CartItemException("cartItem not Found with Id: "+cartItemId);
    }
}
