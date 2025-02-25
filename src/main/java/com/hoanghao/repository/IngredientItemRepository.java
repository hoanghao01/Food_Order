package com.hoanghao.repository;

import com.hoanghao.model.IngredientItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IngredientItemRepository extends JpaRepository<IngredientItem, Long> {

    List<IngredientItem> findByRestaurantId(Long restaurantId);

    IngredientItem findIngredientItemById(Long id);
}
