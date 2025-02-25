package com.hoanghao.service.impl;

import com.hoanghao.model.Category;
import com.hoanghao.model.Restaurant;
import com.hoanghao.repository.CategoryRepository;
import com.hoanghao.service.CategoryService;
import com.hoanghao.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private RestaurantService restaurantService;

    @Override
    public Category createCategory(String name, Long userId) {

        Restaurant restaurant = restaurantService.getRestaurantByUserId(userId);

        Category category = Category.builder()
                .name(name)
                .restaurant(restaurant)
                .build();

        return categoryRepository.save(category);
    }

    @Override
    public List<Category> findCategoryByRestaurantId(Long restaurantId) throws Exception {
        Restaurant restaurant = restaurantService.getRestaurantById(restaurantId);

        return categoryRepository.findCategoryByRestaurantId(restaurant.getId());
    }

    @Override
    public Category findCategoryById(Long categoryId) throws Exception {
        Optional<Category> category = categoryRepository.findById(categoryId);

        if (category.isEmpty()){
            throw new Exception("Category not found with id: " + categoryId);
        }

        return category.get();
    }
}
