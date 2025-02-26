package com.hoanghao.controller;

import com.hoanghao.model.Cart;
import com.hoanghao.model.CartItem;
import com.hoanghao.request.AddCartItemRequest;
import com.hoanghao.request.UpdateCartItemRequest;
import com.hoanghao.response.ApiResponse;
import com.hoanghao.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class CartController {

    @Autowired
    private CartService cartService;

    @PutMapping("/cart/add")
    public ResponseEntity<?> addItemToCart(
            @RequestBody AddCartItemRequest req,
            @RequestHeader("Authorization") String jwt) throws Exception {

        CartItem cartItem = cartService.addItemToCart(req, jwt);

        ApiResponse response = ApiResponse.builder()
                .data(cartItem)
                .message("Item added to cart successfully")
                .status(HttpStatus.OK.value())
                .build();

        return ResponseEntity.ok().body(response);
    }

    @PutMapping("/cart-item/update")
    public ResponseEntity<?> updateCartItemQuantity(
            @RequestBody UpdateCartItemRequest req,
            @RequestHeader("Authorization") String jwt) throws Exception {

        CartItem cartItem = cartService.updateCartItemQuantity(req.getCartItemId(), req.getQuantity());

        ApiResponse response = ApiResponse.builder()
                .data(cartItem)
                .message("Cart item updated successfully")
                .status(HttpStatus.OK.value())
                .build();

        return ResponseEntity.ok().body(response);
    }

    @DeleteMapping("/cart-item/{id}/remove")
    public ResponseEntity<?> deleteCartItem(
            @PathVariable Long id,
            @RequestHeader("Authorization") String jwt) throws Exception {

        Cart cart = cartService.removeCartItem(id, jwt);

        ApiResponse response = ApiResponse.builder()
                .data(cart)
                .message("Cart item removed successfully with id: " + id)
                .status(HttpStatus.OK.value())
                .build();

        return ResponseEntity.ok().body(response);
    }

    @PutMapping("/cart/clear")
    public ResponseEntity<?> clearCart(
            @RequestHeader("Authorization") String jwt) throws Exception {

        Cart cart = cartService.clearCart(jwt);

        ApiResponse response = ApiResponse.builder()
                .data(cart)
                .message("Cart cleared successfully")
                .status(HttpStatus.OK.value())
                .build();

        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/cart")
    public ResponseEntity<?> findUserCart(
            @RequestHeader("Authorization") String jwt) throws Exception {

        Cart cart = cartService.findCartByUserId(jwt);

        ApiResponse response = ApiResponse.builder()
                .data(cart)
                .message("Cart retrieved successfully")
                .status(HttpStatus.OK.value())
                .build();

        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/cart/{id}")
    public ResponseEntity<?> findCartById(
            @PathVariable Long id) throws Exception {

        Cart cart = cartService.findCartById(id);

        ApiResponse response = ApiResponse.builder()
                .data(cart)
                .message("Cart retrieved successfully with id: " + id)
                .status(HttpStatus.OK.value())
                .build();

        return ResponseEntity.ok().body(response);
    }
}
