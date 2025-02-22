package com.hoanghao.service;

import com.hoanghao.model.Category;
import com.hoanghao.model.Food;
import com.hoanghao.model.Restaurant;
import com.hoanghao.request.CreateFoodRequest;

import java.util.List;

public interface FoodService {

    public List<Food> getAllFood();
    public Food createFood(CreateFoodRequest createFoodRequest, Category category, Restaurant restaurant);
    void deleteFood(Long foodId);

    public List<Food> getFoodByRestaurantId(Long restaurantId,
                                            boolean isVegetarian,
                                            boolean isNonveg,
                                            boolean isSeasonal,
                                            String foodCategory);

    public List<Food> searchFood(String keyword);
    public Food findFoodById(Long foodId);
    public Food updateAvailabilityStatus(Long foodId);
}
