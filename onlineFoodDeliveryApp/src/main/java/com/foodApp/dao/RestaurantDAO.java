package com.foodApp.dao;

import java.util.ArrayList;

import com.foodApp.model.Restaurant;

public interface RestaurantDAO {
    int insert(Restaurant r);
    ArrayList<Restaurant> fetchAllRestaurants();
    Restaurant fetchRestaurant(int i);
    int updateRestaurant(int i, String address);
    int deleteRestaurant(int i);
}
