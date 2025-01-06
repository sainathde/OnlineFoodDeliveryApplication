package com.foodApp.daoimpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import com.foodApp.dao.MenuDAO;
import com.foodApp.model.Menu;
import com.foodAppDAO.dbutil.DBConnection;

public class MenuDAOImpl implements MenuDAO {

    private static final String INSERTMENU = "INSERT INTO menu(menuId, restaurantId, name, description, price, isAvailable, imagePath) VALUES (?, ?, ?, ?, ?, ?, ?)";
    private static final String FETCHALLMENUS = "SELECT * FROM menu";
    private static final String FETCHMENU = "SELECT * FROM menu WHERE menuId=?";
    private static final String UPDATEMENU = "UPDATE menu SET description=? WHERE menuId=?";
    private static final String DELETEMENU = "DELETE FROM menu WHERE menuId=?";
    private static final String FETCHMENUBYrestaurantId = "SELECT * FROM menu WHERE restaurantId=?";
    private static Connection con;

    static {
        con = DBConnection.connect();
    }

    private PreparedStatement pstmt;
    private Statement stmt;
    private ResultSet rset;
    private Menu menu;

    // Non-static list to prevent sharing across instances
    private ArrayList<Menu> menuList = new ArrayList<Menu>();

    @Override
    public int insert(Menu menu) {
        try {
            pstmt = con.prepareStatement(INSERTMENU);
            pstmt.setInt(1, menu.getMenuId());
            pstmt.setInt(2, menu.getRestaurantId());
            pstmt.setString(3, menu.getName());
            pstmt.setString(4, menu.getDescription());
            pstmt.setInt(5, menu.getPrice());
            pstmt.setBoolean(6, menu.isAvailable());
            pstmt.setString(7, menu.getImagePath());

            return pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error inserting menu item", e);  // Custom exception handling
        }
    }

    @Override
    public ArrayList<Menu> fetchAllMenus() {
        try {
            stmt = con.createStatement();
            rset = stmt.executeQuery(FETCHALLMENUS);
            menuList = extractMenuDetailsFromResultSet(rset);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return menuList;
    }

    @Override
    public ArrayList<Menu> fetchMenuByRestaurantId(int restaurantID) {
        try {
            pstmt = con.prepareStatement(FETCHMENUBYrestaurantId);
            pstmt.setInt(1, restaurantID);
            rset = pstmt.executeQuery();
            menuList = extractMenuDetailsFromResultSet(rset);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return menuList;
    }

    @Override
    public Menu fetchMenu(int id) {
        try {
            pstmt = con.prepareStatement(FETCHMENU);
            pstmt.setInt(1, id);
            rset = pstmt.executeQuery();
            menu = extractMenuDetailsFromResultSet(rset).get(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return menu;
    }

    @Override
    public int updateMenu(int id, String description) {
        try {
            pstmt = con.prepareStatement(UPDATEMENU);
            pstmt.setString(1, description);
            pstmt.setInt(2, id);
            return pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public int deleteMenu(int id) {
        try {
            pstmt = con.prepareStatement(DELETEMENU);
            pstmt.setInt(1, id);
            return pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    // This method will clear the list before populating it to avoid duplicates
    public ArrayList<Menu> extractMenuDetailsFromResultSet(ResultSet rset) {
        menuList.clear();  // Clear the list to avoid duplicates
        try {
            while (rset.next()) {
                menuList.add(new Menu(
                        rset.getInt("menuId"),
                        rset.getInt("restaurantId"),
                        rset.getString("name"),
                        rset.getString("description"),
                        rset.getInt("price"),
                        rset.getBoolean("isAvailable"),
                        rset.getString("imagePath")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return menuList;
    }
}
