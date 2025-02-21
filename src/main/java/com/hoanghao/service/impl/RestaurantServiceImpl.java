package com.hoanghao.service.impl;

import com.hoanghao.dto.RestaurantDto;
import com.hoanghao.exception.ResourceNotFoundException;
import com.hoanghao.model.Address;
import com.hoanghao.model.Restaurant;
import com.hoanghao.model.User;
import com.hoanghao.repository.AddressRepository;
import com.hoanghao.repository.RestaurantRepository;
import com.hoanghao.repository.UserRepository;
import com.hoanghao.request.CreateRestaurantRequest;
import com.hoanghao.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class RestaurantServiceImpl implements RestaurantService {

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public Restaurant createRestaurant(CreateRestaurantRequest request, User user) {
        Address address = addressRepository.save(request.getAddress());

        Restaurant newRestaurant = Restaurant.builder()
                .contactInformation(request.getContactInformation())
                .name(request.getName())
                .description(request.getDescription())
                .cuisineType(request.getCuisineType())
                .address(address)
                .images(request.getImages())
                .openingHours(request.getOpeningHours())
                .registrationDate(LocalDateTime.now())
                .owner(user)
                .build();

        return restaurantRepository.save(newRestaurant);
    }

    @Override
    public Restaurant updateRestaurant(Long restaurantId, CreateRestaurantRequest request){
        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new ResourceNotFoundException("Restaurant not found with id: " + restaurantId));

        if (request.getCuisineType() != null) {
            restaurant.setCuisineType(request.getCuisineType());
        }

        if (request.getDescription() != null) {
            restaurant.setDescription(request.getDescription());
        }

        if (request.getName() != null) {
            restaurant.setName(request.getName());
        }

        return restaurantRepository.save(restaurant);
    }

    @Override
    public void deleteRestaurant(Long restaurantId){
        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new ResourceNotFoundException("Restaurant not found with id: " + restaurantId));
        restaurantRepository.delete(restaurant);
    }

    @Override
    public List<Restaurant> getAllRestaurants() {
        return restaurantRepository.findAll();

    }

    @Override
    public List<Restaurant> searchRestaurant(String keyword) {

        return restaurantRepository.searchRestaurant(keyword);
    }

    @Override
    public Restaurant getRestaurantById(Long restaurantId){
        Optional<Restaurant> restaurant = restaurantRepository.findById(restaurantId);

        if (restaurant.isEmpty()) {
            throw new ResourceNotFoundException("Restaurant not found with id: " + restaurantId);
        }

        return restaurant.get();

    }

    @Override
    public Restaurant getRestaurantByUserId(Long userId){
        Restaurant restaurant = restaurantRepository.findByOwnerId(userId);

        if (restaurant == null) {
            throw new ResourceNotFoundException("Restaurant not found with owner id: " + userId);
        }
        return restaurant;
    }

    @Override
    public RestaurantDto addToFavorites(Long restaurantId, User user) throws Exception {

        Restaurant restaurant = getRestaurantById(restaurantId);

        RestaurantDto restaurantDto = new RestaurantDto();
        restaurantDto.setDescription(restaurant.getDescription());
        restaurantDto.setImages(restaurant.getImages());
        restaurantDto.setTitle(restaurant.getName());
        restaurantDto.setId(restaurant.getId());

        if (user.getFavorites().contains(restaurant)){
            user.getFavorites().remove(restaurant);
        } else {
            user.getFavorites().add(restaurantDto);
        }

        userRepository.save(user);
        return restaurantDto;
    }

    @Override
    public Restaurant updateRestaurantStatus(Long restaurantId) throws Exception {
        Restaurant restaurant = getRestaurantById(restaurantId);
        restaurant.setOpen(!restaurant.isOpen());

        return restaurantRepository.save(restaurant);
    }
}
