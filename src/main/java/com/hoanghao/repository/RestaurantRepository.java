package com.hoanghao.repository;

import com.hoanghao.model.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {

    @Query("SELECT r " +
            "FROM Restaurant r " +
            "WHERE lower(r.name) LIKE lower(concat('%', :query, '%')) OR" +
            " lower(r.description) LIKE lower(concat('%', :query, '%')) OR" +
            " lower(r.cuisineType) LIKE lower(concat('%', :query, '%'))")
    List<Restaurant> searchRestaurant(String keyword);

    Restaurant findByOwnerId(Long userId);
}
