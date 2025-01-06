package com.foodApp.dao;

import java.util.ArrayList;

import com.foodApp.model.User;

public interface UserDAO {
    int insert(User u);
    ArrayList<User> fetchAllUsers();
    User fetchUser(int i);
    User getUserByEmail(String email);
    int updateUser(int i, String address);
    int delete(int i);
}
