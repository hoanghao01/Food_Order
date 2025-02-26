package com.hoanghao.controller;

import com.hoanghao.model.IngredientCategory;
import com.hoanghao.model.IngredientItem;
import com.hoanghao.request.IngredientCategoryRequest;
import com.hoanghao.request.IngredientRequest;
import com.hoanghao.response.ApiResponse;
import com.hoanghao.service.IngredientsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/ingredients")
public class IngredientController {

    @Autowired
    private IngredientsService ingredientService;

    @PostMapping("/category")
    public ResponseEntity<?> createIngredientCategory(
            @RequestBody IngredientCategoryRequest req) throws Exception {

        IngredientCategory ingredientCategory = ingredientService.createIngredientCategory(req.getName(), req.getRestaurantId());

        ApiResponse response = ApiResponse.builder()
                .data(ingredientCategory)
                .message("Ingredient category created successfully")
                .status(HttpStatus.CREATED.value())
                .build();

        return ResponseEntity.ok().body(response);

    }


    @PostMapping("/")
    public ResponseEntity<?> createIngredient(
            @RequestBody IngredientRequest req) throws Exception {

        IngredientItem ingredientItem = ingredientService.createIngredientItem(req.getName(), req.getIngredientCategoryId(), req.getRestaurantId());

        ApiResponse response = ApiResponse.builder()
                .data(ingredientItem)
                .message("Ingredient item created successfully")
                .status(HttpStatus.CREATED.value())
                .build();

        return ResponseEntity.ok().body(response);
    }

    @PutMapping("/{id}/stoke")
    public ResponseEntity<?> updateIngredientStock(
            @PathVariable Long id) throws Exception {

        IngredientItem ingredientItem = ingredientService.updateStock(id);

        ApiResponse response = ApiResponse.builder()
                .data(ingredientItem)
                .message("Ingredient stock updated successfully")
                .status(HttpStatus.OK.value())
                .build();

        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/restaurant/{d}")
    public ResponseEntity<?> getIngredientByRestaurant(
            @PathVariable Long id) throws Exception {

        List<IngredientItem> ingredients = ingredientService.findRestaurantIngredients(id);

        ApiResponse response = ApiResponse.builder()
                .data(ingredients)
                .message("Ingredients found for restaurant")
                .status(HttpStatus.OK.value())
                .build();

        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/restaurant/{d}/category")
    public ResponseEntity<?> getIngredientCategoryByRestaurant(
            @PathVariable Long id) throws Exception {

        List<IngredientCategory> ingredientCategories = ingredientService.findIngredientCategoryByRestaurantId(id);

        ApiResponse response = ApiResponse.builder()
                .data(ingredientCategories)
                .message("Ingredient categories found for restaurant")
                .status(HttpStatus.OK.value())
                .build();

        return ResponseEntity.ok().body(response);
    }

}
