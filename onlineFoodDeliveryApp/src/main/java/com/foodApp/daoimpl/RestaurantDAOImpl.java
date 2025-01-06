package com.foodApp.daoimpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import com.foodApp.dao.RestaurantDAO;
import com.foodApp.model.Restaurant;
import com.foodAppDAO.dbutil.DBConnection;

public class RestaurantDAOImpl implements RestaurantDAO {

    private static final String INSERTRESTAURANT = "insert into restaurant(restaurantId,name,cuisineType,deliveryTime,address,ratings,isActive,imagePath) values(?,?,?,?,?,?,?,?)";
    private static final String FETCHALLRESTAURANTS  ="select * from restaurant";    
    private static final String FETCHRESTAURANT = "select * from restaurant where restaurantId=?";
    private static final String UPDATERESTAURANT = "update restaurant set name=? where restaurantId=?";
    private static final String DELETERESTAURANT = "delete from restaurant where restaurantId=?";
    private static Connection con;

    static {
        con = DBConnection.connect();
    }

    private PreparedStatement pstmt;
    private Statement stmt;
    private ResultSet rset;

    @Override
    public int insert(Restaurant r) {
        try (PreparedStatement pstmt = con.prepareStatement(INSERTRESTAURANT)) {  
            pstmt.setInt(1, r.getRestaurantId());
            pstmt.setString(2, r.getName());
            pstmt.setString(3, r.getCusineType());
            pstmt.setInt(4, r.getDeliveryTime());
            pstmt.setString(5, r.getAddress());
            pstmt.setFloat(6, r.getRatings());
            pstmt.setBoolean(7, r.isActive());
            pstmt.setString(8, r.getImagePath());
            
            return pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public ArrayList<Restaurant> fetchAllRestaurants() {
        ArrayList<Restaurant> restaurantList = new ArrayList<>(); 
        try (Statement stmt = con.createStatement()) {
            rset = stmt.executeQuery(FETCHALLRESTAURANTS);
            restaurantList = extractRestaurantDetailsFromResultSet(rset);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return restaurantList;  // Return the local list
    }

    @Override
    public Restaurant fetchRestaurant(int i) {
        try (PreparedStatement pstmt = con.prepareStatement(FETCHRESTAURANT)) {
            pstmt.setInt(1, i);
            rset = pstmt.executeQuery();
            
            if (rset.next()) {
                return new Restaurant(
                    rset.getInt("restaurantId"),
                    rset.getString("name"),
                    rset.getString("cuisineType"),
                    rset.getInt("deliveryTime"),
                    rset.getString("address"),
                    rset.getFloat("ratings"),
                    rset.getBoolean("isActive"),
                    rset.getString("imagePath")
                );
            } else {
                return null;  
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private ArrayList<Restaurant> extractRestaurantDetailsFromResultSet(ResultSet rset) {
        ArrayList<Restaurant> tempList = new ArrayList<>();  
        try {
            while (rset.next()) {
                tempList.add(new Restaurant(
                    rset.getInt("restaurantId"),
                    rset.getString("name"),
                    rset.getString("cuisineType"),
                    rset.getInt("deliveryTime"),
                    rset.getString("address"),
                    rset.getFloat("ratings"),
                    rset.getBoolean("isActive"),
                    rset.getString("imagePath")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tempList;  
    }

    @Override
    public int updateRestaurant(int i, String address) {
        try (PreparedStatement pstmt = con.prepareStatement(UPDATERESTAURANT)) {
            pstmt.setString(1, address);
            pstmt.setInt(2, i);
            return pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public int deleteRestaurant(int i) {
        try (PreparedStatement pstmt = con.prepareStatement(DELETERESTAURANT)) {
            pstmt.setInt(1, i);
            return pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
}
