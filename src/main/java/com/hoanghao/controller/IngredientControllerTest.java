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

//@RestController
//@RequestMapping("/api/admin/ingredients")
public class IngredientControllerTest {

    @Autowired
    private IngredientsService ingredientService;

    // Tạo danh mục nguyên liệu
    //@PostMapping("/category")
    public ResponseEntity<ApiResponse> createIngredientCategory(
            @RequestBody IngredientCategoryRequest req) {
        try {
            IngredientCategory ingredientCategory = ingredientService.createIngredientCategory(req.getName(), req.getRestaurantId());

            ApiResponse response = ApiResponse.builder()
                    .data(ingredientCategory)
                    .message("Ingredient category '" + ingredientCategory.getName() + "' created successfully")
                    .status(HttpStatus.CREATED.value())
                    .build();

            return ResponseEntity.status(HttpStatus.CREATED).body(response);

        } catch (Exception e) {
            ApiResponse errorResponse = ApiResponse.builder()
                    .message("Error occurred while creating ingredient category: " + e.getMessage())
                    .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .build();

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    // Tạo nguyên liệu
    //@PostMapping("/")
    public ResponseEntity<ApiResponse> createIngredient(
            @RequestBody IngredientRequest req) {
        try {
            IngredientItem ingredientItem = ingredientService.createIngredientItem(req.getName(), req.getIngredientCategoryId(), req.getRestaurantId());

            ApiResponse response = ApiResponse.builder()
                    .data(ingredientItem)
                    .message("Ingredient item '" + ingredientItem.getName() + "' created successfully")
                    .status(HttpStatus.CREATED.value())
                    .build();

            return ResponseEntity.status(HttpStatus.CREATED).body(response);

        } catch (Exception e) {
            ApiResponse errorResponse = ApiResponse.builder()
                    .message("Error occurred while creating ingredient item: " + e.getMessage())
                    .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .build();

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    // Cập nhật tồn kho nguyên liệu
    //@PutMapping("/{id}/stock")
    public ResponseEntity<ApiResponse> updateIngredientStock(
            @PathVariable Long id) {
        try {
            IngredientItem ingredientItem = ingredientService.updateStock(id);

            ApiResponse response = ApiResponse.builder()
                    .data(ingredientItem)
                    .message("Ingredient stock updated successfully")
                    .status(HttpStatus.OK.value())
                    .build();

            return ResponseEntity.ok().body(response);

        } catch (Exception e) {
            ApiResponse errorResponse = ApiResponse.builder()
                    .message("Error occurred while updating ingredient stock: " + e.getMessage())
                    .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .build();

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    // Lấy nguyên liệu theo nhà hàng
    //@GetMapping("/restaurant/{id}/ingredients")
    public ResponseEntity<ApiResponse> getIngredientByRestaurant(@PathVariable Long id) {
        try {
            List<IngredientItem> ingredients = ingredientService.findRestaurantIngredients(id);

            ApiResponse response = ApiResponse.builder()
                    .data(ingredients)
                    .message("Ingredients found for restaurant ID: " + id)
                    .status(HttpStatus.OK.value())
                    .build();

            return ResponseEntity.ok().body(response);

        } catch (Exception e) {
            ApiResponse errorResponse = ApiResponse.builder()
                    .message("Error occurred while fetching ingredients for restaurant: " + e.getMessage())
                    .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .build();

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    // Lấy danh mục nguyên liệu theo nhà hàng
    //@GetMapping("/restaurant/{id}/categories")
    public ResponseEntity<ApiResponse> getIngredientCategoryByRestaurant(@PathVariable Long id) {
        try {
            List<IngredientCategory> ingredientCategories = ingredientService.findIngredientCategoryByRestaurantId(id);

            ApiResponse response = ApiResponse.builder()
                    .data(ingredientCategories)
                    .message("Ingredient categories found for restaurant ID: " + id)
                    .status(HttpStatus.OK.value())
                    .build();

            return ResponseEntity.ok().body(response);

        } catch (Exception e) {
            ApiResponse errorResponse = ApiResponse.builder()
                    .message("Error occurred while fetching ingredient categories for restaurant: " + e.getMessage())
                    .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .build();

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    // nên sử dụng try-catch để bắt các ngoại lệ không làm chương trình bị crash
}

