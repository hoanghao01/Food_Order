package com.hoanghao.request;

import lombok.Data;

@Data
public class IngredientRequest {
    private String name;
    private Long ingredientCategoryId;
    private Long restaurantId;
}
