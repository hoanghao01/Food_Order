package com.hoanghao.service.impl;

import com.hoanghao.exception.ResourceNotFoundException;
import com.hoanghao.model.IngredientCategory;
import com.hoanghao.model.IngredientItem;
import com.hoanghao.model.Restaurant;
import com.hoanghao.repository.IngredientCategoryRepository;
import com.hoanghao.repository.IngredientItemRepository;
import com.hoanghao.service.IngredientsService;
import com.hoanghao.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class IngredientsServiceImpl implements IngredientsService {

    @Autowired
    private IngredientItemRepository ingredientItemRepository;

    @Autowired
    private IngredientCategoryRepository ingredientCategoryRepository;

    @Autowired
    private RestaurantService restaurantService;

    @Override
    public IngredientCategory createIngredientCategory(String name, Long restaurantId) throws Exception {
        try {
            Restaurant restaurant = restaurantService.getRestaurantById(restaurantId);

            IngredientCategory ingredientCategory = new IngredientCategory();
            ingredientCategory.setName(name);
            ingredientCategory.setRestaurant(restaurant);

            return ingredientCategoryRepository.save(ingredientCategory);
        } catch (ResourceNotFoundException e) {
            throw new RuntimeException("Restaurant not found with id: " + restaurantId);
        }
    }

    @Override
    public IngredientCategory findIngredientCategoryById(Long ingredientCategoryId) throws Exception {
        Optional<IngredientCategory> ingredientCategory = ingredientCategoryRepository.findById(ingredientCategoryId);

        if(ingredientCategory.isEmpty()){
            throw new Exception("Ingredient category not found with id: " + ingredientCategoryId);
        }

        return ingredientCategory.get();
    }

    @Override
    public List<IngredientCategory> findIngredientCategoryByRestaurantId(Long restaurantId) throws Exception {
        return ingredientCategoryRepository.findByRestaurantId(restaurantId);
    }

    @Override
    public IngredientItem createIngredientItem(String ingredientName, Long categoryId, Long restaurantId) throws Exception {
        try {
            Restaurant restaurant = restaurantService.getRestaurantById(restaurantId);
            IngredientCategory ingredientCategory = findIngredientCategoryById(categoryId);

            IngredientItem ingredientItem = new IngredientItem();
            ingredientItem.setName(ingredientName);
            ingredientItem.setRestaurant(restaurant);
            ingredientItem.setCategory(ingredientCategory);

            IngredientItem savedIngredientItem = ingredientItemRepository.save(ingredientItem);
            ingredientCategory.getIngredients().add(savedIngredientItem);
            ingredientCategoryRepository.save(ingredientCategory);  // update the category with the new ingredient

            return savedIngredientItem;

        } catch (ResourceNotFoundException e) {
            throw new RuntimeException("Restaurant not found with id: " + restaurantId);

        } catch (Exception e) {
            throw new RuntimeException("Ingredient category not found with id: " + categoryId);

        }
    }

    @Override
    public List<IngredientItem> findRestaurantIngredients(Long restaurantId) throws Exception {
        return ingredientItemRepository.findByRestaurantId(restaurantId);
    }

    @Override
    public IngredientItem updateStock(Long id) throws Exception {
        Optional<IngredientItem> optionalIngredientItem = ingredientItemRepository.findById(id);
        if(optionalIngredientItem.isEmpty()){
            throw new Exception("Ingredient item not found with id: " + id);
        }
        IngredientItem ingredientItem = optionalIngredientItem.get();
        ingredientItem.setInStoke(!ingredientItem.isInStoke());

        return ingredientItemRepository.save(ingredientItem);
    }
}
