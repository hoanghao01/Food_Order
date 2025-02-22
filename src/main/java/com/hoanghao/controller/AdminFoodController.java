package com.hoanghao.controller;

import com.hoanghao.exception.InvalidJwtAuthenticationException;
import com.hoanghao.model.Food;
import com.hoanghao.model.Restaurant;
import com.hoanghao.model.User;
import com.hoanghao.request.CreateFoodRequest;
import com.hoanghao.response.ApiResponse;
import com.hoanghao.service.FoodService;
import com.hoanghao.service.RestaurantService;
import com.hoanghao.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/food")
public class AdminFoodController {

    @Autowired
    private FoodService foodService;

    @Autowired
    private UserService userService;

    @Autowired
    private RestaurantService restaurantService;

    // methods

    @PostMapping("/create")
    public ResponseEntity<ApiResponse> createFood(@RequestBody CreateFoodRequest req,
                                                  @RequestHeader("Authorization") String jwt) throws InvalidJwtAuthenticationException {
        // check if user is admin
        // check if restaurant exists
        // check if category exists
        // create food
        // return response

        User user = userService.findUserByJwtToken(jwt);
        Restaurant restaurant = restaurantService.getRestaurantById(req.getRestaurantId());
        Food food = foodService.createFood(req, req.getCategory(), restaurant);

        ApiResponse response = ApiResponse.builder()
                .data(food)
                .message("Food created successfully")
                .status(HttpStatus.CREATED.value())
                .build();
        return ResponseEntity.ok().body(response);

    }

    @DeleteMapping("/{foodId}")
    public ResponseEntity<ApiResponse> deleteFood(@PathVariable Long foodId,
                                                  @RequestHeader("Authorization") String jwt) throws InvalidJwtAuthenticationException {


        User user = userService.findUserByJwtToken(jwt);
        foodService.deleteFood(foodId);

        ApiResponse response = ApiResponse.builder()
                .message("Food deleted successfully")
                .status(HttpStatus.OK.value())
                .build();
        return ResponseEntity.ok().body(response);
    }

    @PutMapping("/{foodId}")
    public ResponseEntity<ApiResponse> updateAvailabilityStatus(@PathVariable Long foodId,
                                                  @RequestHeader("Authorization") String jwt) throws InvalidJwtAuthenticationException {


        User user = userService.findUserByJwtToken(jwt);
        Food food = foodService.updateAvailabilityStatus(foodId);

        ApiResponse response = ApiResponse.builder()
                .data(food)
                .message("Food deleted successfully")
                .status(HttpStatus.OK.value())
                .build();
        return ResponseEntity.ok().body(response);
    }
}
