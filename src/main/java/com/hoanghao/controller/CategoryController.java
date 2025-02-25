package com.hoanghao.controller;

import com.hoanghao.model.Category;
import com.hoanghao.model.User;
import com.hoanghao.response.ApiResponse;
import com.hoanghao.service.CategoryService;
import com.hoanghao.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private UserService userService;

    @PostMapping("/admin/category")
    public ResponseEntity<?> createCategory(@RequestBody Category category,
                                @RequestHeader("Authorization") String jwtToken) {
        User user = userService.findUserByJwtToken(jwtToken);

        Category newCategory = categoryService.createCategory(category.getName(), user.getId());

        ApiResponse apiResponse = ApiResponse.builder()
                .data(newCategory)
                .message("Category created successfully")
                .status(HttpStatus.CREATED.value())
                .build();

        return ResponseEntity.ok().body(apiResponse);
    }

    @GetMapping("/category/restaurant")
    public ResponseEntity<?> findCategoryByRestaurantId(@RequestHeader("Authorization") String jwtToken) throws Exception {
        User user = userService.findUserByJwtToken(jwtToken);

        List<Category> categories = categoryService.findCategoryByRestaurantId(user.getId());

        ApiResponse apiResponse = ApiResponse.builder()
                .data(categories)
                .message("Retrieved categories successfully")
                .status(HttpStatus.OK.value())
                .build();

        return ResponseEntity.ok().body(apiResponse);
    }

    @GetMapping("/category/{id}")
    public ResponseEntity<?> findCategoryById(@RequestHeader("Authorization") String jwtToken,
            @PathVariable Long id) throws Exception {
        User user = userService.findUserByJwtToken(jwtToken);

        Category category = categoryService.findCategoryById(id);

        ApiResponse apiResponse = ApiResponse.builder()
                .data(category)
                .message("Retrieved category successfully")
                .status(HttpStatus.OK.value())
                .build();

        return ResponseEntity.ok().body(apiResponse);
    }

}
