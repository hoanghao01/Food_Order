package com.hoanghao.controller;

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

@RestController
@RequestMapping("/api/admin/restaurants")
public class AdminRestaurantController {

    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    private UserService userService;

    @PostMapping("")
    public ResponseEntity<ApiResponse> createRestaurant(
            @RequestBody CreateRestaurantRequest req,
            @RequestHeader("Authorization") String jwtToken
            ) throws Exception {

        User user = userService.findUserByJwtToken(jwtToken);

        Restaurant restaurant = restaurantService.createRestaurant(req, user);

        ApiResponse apiResponse = ApiResponse.builder()
                .data(restaurant)
                .message("Restaurant created successfully")
                .status(HttpStatus.CREATED.value())
                .build();

        return ResponseEntity.ok().body(apiResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> updateRestaurant(
            @RequestBody CreateRestaurantRequest req,
            @RequestHeader("Authorization") String jwtToken,
            @PathVariable("restaurantId") Long id
    ) throws Exception {

        User user = userService.findUserByJwtToken(jwtToken);

        Restaurant restaurant = restaurantService.updateRestaurant(id, req);

        ApiResponse apiResponse = ApiResponse.builder()
                .data(restaurant)
                .message("Restaurant was updated successfully")
                .status(HttpStatus.OK.value())
                .build();

        return ResponseEntity.ok().body(apiResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteRestaurant(
            @RequestBody CreateRestaurantRequest req,
            @RequestHeader("Authorization") String jwtToken,
            @PathVariable("restaurantId") Long id
    ) throws Exception {

        User user = userService.findUserByJwtToken(jwtToken);

        restaurantService.deleteRestaurant(id);

        ApiResponse apiResponse = ApiResponse.builder()
                .data(null)
                .message("Restaurant was deleted successfully!")
                .status(HttpStatus.OK.value())
                .build();

        return ResponseEntity.ok().body(apiResponse);
    }
}
