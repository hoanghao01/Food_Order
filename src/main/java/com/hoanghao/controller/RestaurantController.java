package com.hoanghao.controller;

import com.hoanghao.dto.RestaurantDto;
import com.hoanghao.model.Restaurant;
import com.hoanghao.model.User;
import com.hoanghao.request.CreateRestaurantRequest;
import com.hoanghao.response.ApiResponse;
import com.hoanghao.service.RestaurantService;
import com.hoanghao.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.file.Path;
import java.util.List;

@RestController
@RequestMapping("api/restaurants")
public class RestaurantController {

    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    private UserService userService;

    @PostMapping("/search")
    public ResponseEntity<ApiResponse> searchRestaurant(
            @RequestHeader("Authorization") String jwtToken,
            @RequestParam String keyword
    ) throws Exception {

        User user = userService.findUserByJwtToken(jwtToken);

        List<Restaurant> restaurants = restaurantService.searchRestaurant(keyword);

        ApiResponse apiResponse = ApiResponse.builder()
                .data(restaurants)
                .message("Find restaurant successfully!")
                .status(HttpStatus.OK.value())
                .build();

        return ResponseEntity.ok().body(apiResponse);
    }

    @GetMapping("")
    public ResponseEntity<ApiResponse> getAllRestaurants(
            @RequestHeader("Authorization") String jwtToken

    ) throws Exception {

        User user = userService.findUserByJwtToken(jwtToken);

        List<Restaurant> restaurants = restaurantService.getAllRestaurants();

        ApiResponse apiResponse = ApiResponse.builder()
                .data(restaurants)
                .message("Retrieve all restaurants successfully")
                .status(HttpStatus.OK.value())
                .build();

        return ResponseEntity.ok().body(apiResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> findRestaurantById(
            @RequestHeader("Authorization") String jwtToken,
            @PathVariable Long id

    ) throws Exception {

        User user = userService.findUserByJwtToken(jwtToken);

        Restaurant restaurant = restaurantService.getRestaurantById(id);

        ApiResponse apiResponse = ApiResponse.builder()
                .data(restaurant)
                .message("Get restaurant successfully")
                .status(HttpStatus.OK.value())
                .build();

        return ResponseEntity.ok().body(apiResponse);
    }

    @PutMapping("/{id}/add-favorites")
    public ResponseEntity<ApiResponse> addToFavorites(
            @RequestHeader("Authorization") String jwtToken,
            @PathVariable Long id

    ) throws Exception {

        User user = userService.findUserByJwtToken(jwtToken);

        RestaurantDto restaurantDto = restaurantService.addToFavorites(id, user);

        ApiResponse apiResponse = ApiResponse.builder()
                .data(restaurantDto)
                .message("Add favorites successfully")
                .status(HttpStatus.OK.value())
                .build();

        return ResponseEntity.ok().body(apiResponse);
    }
}
