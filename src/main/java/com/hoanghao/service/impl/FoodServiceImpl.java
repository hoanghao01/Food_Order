package com.hoanghao.service.impl;

import com.hoanghao.exception.ResourceNotFoundException;
import com.hoanghao.model.Category;
import com.hoanghao.model.Food;
import com.hoanghao.model.Restaurant;
import com.hoanghao.repository.FoodRepository;
import com.hoanghao.request.CreateFoodRequest;
import com.hoanghao.service.FoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FoodServiceImpl implements FoodService {

    @Autowired
    private FoodRepository foodRepository;

    @Override
    public List<Food> getAllFood() {
        return foodRepository.findAll();
    }

    @Override
    public Food createFood(CreateFoodRequest createFoodRequest, Category category, Restaurant restaurant) {
        // check if category and restaurant do not exist

        Food newFood = Food.builder()
                .name(createFoodRequest.getName())
                .description(createFoodRequest.getDescription())
                .price(createFoodRequest.getPrice())
                .category(category)
                .images(createFoodRequest.getImages())
                .restaurant(restaurant)
                .isVegetarian(createFoodRequest.isVegetarian())
                .isSeasonal(createFoodRequest.isSeasonal())
                .ingredients(createFoodRequest.getIngredients())
                .build();

        Food savedFood = foodRepository.save(newFood);
        restaurant.getFoods().add(savedFood);   // add food to restaurant
        return foodRepository.save(newFood);
    }

    @Override
    public void deleteFood(Long foodId) {
        //Food food = foodRepository.findById(foodId)
        //        .orElseThrow(() -> new ResourceNotFoundException("Restaurant not found with id: " + foodId));
        //food.setRestaurant(null);   // remove food from restaurant
        //foodRepository.delete(food);

        Food food = findFoodById(foodId);
        food.setRestaurant(null);   // remove food from restaurant
        foodRepository.save(food);
    }

    @Override
    public List<Food> getFoodByRestaurantId(Long restaurantId,
                                            boolean isVegetarian,
                                            boolean isNonveg,
                                            boolean isSeasonal,
                                            String foodCategory) {
        List<Food> foods = foodRepository.findByRestaurantId(restaurantId);

        // filter foods based on the given parameters
        if (isVegetarian) {
            foods = filterByVegetarian(foods, isVegetarian);
        }

        if (isNonveg) {
            foods = filterByNonveg(foods, isNonveg);
        }

        if (isSeasonal) {
            foods = filterBySeasonal(foods, isSeasonal);
        }

        if (foodCategory != null && !foodCategory.isEmpty()) {
            foods = filterByCategory(foods, foodCategory);
        };


        return foods;
    }

    private List<Food> filterByCategory(List<Food> foods, String foodCategory) {
        return foods.stream().filter(food -> {
            if(food.getCategory() != null){
                return food.getCategory().getName().equals(foodCategory);
            }
            return false;
        }).collect(Collectors.toList());
    }

    private List<Food> filterBySeasonal(List<Food> foods, boolean isSeasonal) {
        return foods.stream().filter(Food::isSeasonal).collect(Collectors.toList());
    }

    // ???????????????????????????????
    private List<Food> filterByNonveg(List<Food> foods, boolean isNonveg) {
        return foods.stream().filter(food -> !food.isVegetarian()).collect(Collectors.toList());
    }

    private List<Food> filterByVegetarian(List<Food> foods, boolean isVegetarian) {
        return foods.stream().filter(Food::isVegetarian).collect(Collectors.toList());
    }

    @Override
    public List<Food> searchFood(String keyword) {
        return foodRepository.searchFood(keyword);
    }

    @Override
    public Food findFoodById(Long foodId) {
        Optional<Food> food = foodRepository.findById(foodId);

        if (food.isEmpty()) {
            throw new ResourceNotFoundException("Food not found with id: " + foodId);
        }
        return food.get();
    }

    @Override
    public Food updateAvailabilityStatus(Long foodId) {
        Food food = findFoodById(foodId);
        food.setAvailable(!food.isAvailable());

        return foodRepository.save(food);
    }
}
