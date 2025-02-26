package com.hoanghao.service;

import com.hoanghao.model.Cart;
import com.hoanghao.model.CartItem;
import com.hoanghao.request.AddCartItemRequest;

public interface CartService {

    public CartItem addItemToCart(AddCartItemRequest addCartItemRequest, String jwt) throws Exception;

    public CartItem updateCartItemQuantity(Long cartItemId, int quantity) throws Exception;

    public Cart removeCartItem(Long cartItemId, String jwt) throws Exception;

    public Long calculateCartTotal(Cart cart) throws Exception;

    public Cart findCartById(Long cartId) throws Exception;

    public Cart findCartByUserId(String jwt) throws Exception;

    public Cart clearCart(String jwt) throws Exception;
}
