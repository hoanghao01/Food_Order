package com.hoanghao.service;

import com.hoanghao.model.IngredientCategory;
import com.hoanghao.model.IngredientItem;

import java.util.List;

public interface IngredientsService {

    public IngredientCategory createIngredientCategory(String name, Long restaurantId) throws Exception;

    public IngredientCategory findIngredientCategoryById(Long ingredientCategoryId) throws Exception;

    public List<IngredientCategory> findIngredientCategoryByRestaurantId(Long restaurantId) throws Exception;

    public IngredientItem createIngredientItem(String ingredientName, Long categoryId, Long restaurantId) throws Exception;

    public List<IngredientItem> findRestaurantIngredients(Long restaurantId) throws Exception;

    public IngredientItem updateStock(Long id) throws Exception;
}
