package com.hoanghao.service;

import com.hoanghao.dto.RestaurantDto;
import com.hoanghao.model.Restaurant;
import com.hoanghao.model.User;
import com.hoanghao.request.CreateRestaurantRequest;

import java.util.List;

public interface RestaurantService {
    public Restaurant createRestaurant(CreateRestaurantRequest request, User user);

    public Restaurant updateRestaurant(Long restaurantId, CreateRestaurantRequest request);

    public void deleteRestaurant(Long restaurantId);

    public List<Restaurant> getAllRestaurants();

    public List<Restaurant> searchRestaurant(String keyword);

    public Restaurant getRestaurantById(Long restaurantId);

    public Restaurant getRestaurantByUserId(Long userId);

    public RestaurantDto addToFavorites(Long restaurantId, User user) throws Exception;

    public Restaurant updateRestaurantStatus(Long restaurantId) throws Exception;

}
