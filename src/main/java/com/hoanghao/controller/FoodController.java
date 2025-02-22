package com.hoanghao.controller;

import com.hoanghao.model.Food;
import com.hoanghao.model.User;
import com.hoanghao.response.ApiResponse;
import com.hoanghao.service.FoodService;
import com.hoanghao.service.RestaurantService;
import com.hoanghao.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/foods")
public class FoodController {

    @Autowired
    private FoodService foodService;

    @Autowired
    private UserService userService;

    @Autowired
    private RestaurantService restaurantService;

    @GetMapping("/search")
    public ResponseEntity<ApiResponse> searchFoods(@RequestParam String keyword,
                                                   @RequestHeader("Authorization") String jwt) {
        User user = userService.findUserByJwtToken(jwt);

        List<Food> foods = foodService.searchFood(keyword);

        ApiResponse response = ApiResponse.builder()
                .data(foods)
                .message("Foods found")
                .status(HttpStatus.OK.value())
                .build();

        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/restaurant/{restaurantId}")
    public ResponseEntity<ApiResponse> getRestaurantFood(
            @PathVariable boolean isVegetarian,
            @PathVariable boolean isNonveg,
            @PathVariable boolean isSeasonal,
            @PathVariable(required = false) String foodCategory,
            @PathVariable Long restaurantId,
            @RequestHeader("Authorization") String jwt) {
        User user = userService.findUserByJwtToken(jwt);

        List<Food> foods = foodService.getFoodByRestaurantId(restaurantId, isVegetarian, isNonveg, isSeasonal, foodCategory);

        ApiResponse response = ApiResponse.builder()
                .data(foods)
                .message("Foods found for restaurant")
                .status(HttpStatus.OK.value())
                .build();

        return ResponseEntity.ok().body(response);
    }
}
