package com.foodApp.dao;

import java.util.ArrayList;
import com.foodApp.model.Menu;

public interface MenuDAO {
    int insert(Menu menu);
    ArrayList<Menu> fetchAllMenus();
    Menu fetchMenu(int id);
    int updateMenu(int id, String description);
    int deleteMenu(int id);
    ArrayList<Menu> fetchMenuByRestaurantId(int restaurantID);
}
