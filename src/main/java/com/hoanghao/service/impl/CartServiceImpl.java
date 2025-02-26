package com.hoanghao.service.impl;

import com.hoanghao.exception.ResourceNotFoundException;
import com.hoanghao.model.Cart;
import com.hoanghao.model.CartItem;
import com.hoanghao.model.Food;
import com.hoanghao.model.User;
import com.hoanghao.repository.CartItemRepository;
import com.hoanghao.repository.CartRepository;
import com.hoanghao.request.AddCartItemRequest;
import com.hoanghao.service.CartService;
import com.hoanghao.service.FoodService;
import com.hoanghao.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private FoodService foodService;

    @Override
    public CartItem addItemToCart(AddCartItemRequest req, String jwt) throws Exception {
        User user = userService.findUserByJwtToken(jwt);

        Food food = foodService.findFoodById(req.getFoodId());

        Cart cart = cartRepository.findByCustomerId(user.getId());

        for (CartItem item : cart.getItems()) {
            if (item.getFood().getId().equals(food.getId())) {
                //int newQuantity = item.getQuantity() + req.getQuantity();
                //return updateCartItemQuantity(item.getId(), newQuantity);

                item.setQuantity(item.getQuantity() + req.getQuantity());
                return cartItemRepository.save(item);
            }
        }

        CartItem newCartItem = CartItem.builder()
                .food(food)
                .cart(cart)
                .quantity(req.getQuantity())
                .ingredients(req.getIngredients())
                .totalPrice(food.getPrice() * req.getQuantity())
                .build();

        CartItem savedCartItem = cartItemRepository.save(newCartItem);
        cart.getItems().add(savedCartItem);
        //cartRepository.save(cart);

        return savedCartItem;
    }

    @Override
    public CartItem updateCartItemQuantity(Long cartItemId, int quantity) throws Exception {

        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart item not found with id: " + cartItemId));

        cartItem.setQuantity(quantity);
        cartItem.setTotalPrice(cartItem.getFood().getPrice() * quantity);


        return cartItemRepository.save(cartItem);
    }

    @Override
    public Cart removeCartItem(Long cartItemId, String jwt) throws Exception {

        User user = userService.findUserByJwtToken(jwt);

        Cart cart = cartRepository.findByCustomerId(user.getId());

        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart item not found with id: " + cartItemId));

        cart.getItems().remove(cartItem);

        return cartRepository.save(cart);
    }

    @Override
    public Long calculateCartTotal(Cart cart) throws Exception {

        Long total = 0L;

        for (CartItem item : cart.getItems()) {
            //total += item.getTotalPrice();
            total += item.getFood().getPrice() * item.getQuantity();
        }
        return total;
    }

    @Override
    public Cart findCartById(Long cartId) throws Exception {
        return cartRepository.findById(cartId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart not found with id: " + cartId));
    }

    @Override
    public Cart findCartByUserId(String jwt) throws Exception {
        try {
            User user = userService.findUserByJwtToken(jwt);
            return cartRepository.findByCustomerId(user.getId());
        } catch (Exception e) {
            throw new ResourceNotFoundException("Cart not found with user id: ");
        }
    }

    @Override
    public Cart clearCart(String jwt) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        Cart cart = findCartByUserId(jwt);

        //Cart cart = cartRepository.findByCustomerId(user.getId());
        cart.getItems().clear();

        return cartRepository.save(cart);
    }
}
