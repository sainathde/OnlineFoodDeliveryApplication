package com.foodApp.daoimpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import com.foodApp.dao.OrderItemsDAO;
import com.foodApp.model.OrderItems;
import com.foodAppDAO.dbutil.DBConnection;

public class OrderItemsDAOImpl implements OrderItemsDAO {

    private static final String INSERT_ORDER_ITEM = "INSERT INTO order_items(orderItemId, orderId, menuId, quantity, itemTotal) VALUES (?, ?, ?, ?, ?)";
    private static final String FETCH_ALL_ORDER_ITEMS = "SELECT * FROM order_items";
    private static final String FETCH_ORDER_ITEM_BY_ID = "SELECT * FROM order_items WHERE orderItemId = ?";
    private static final String UPDATE_ORDER_ITEM_QUANTITY = "UPDATE order_items SET quantity = ? WHERE orderItemId = ?";
    private static final String DELETE_ORDER_ITEM = "DELETE FROM order_items WHERE orderItemId = ?";

    @Override
    public int insert(OrderItems orderItem) {
        String query = INSERT_ORDER_ITEM;
        try (Connection con = DBConnection.connect();
             PreparedStatement pstmt = con.prepareStatement(query)) {
            pstmt.setInt(1, orderItem.getOrderItemId());
            pstmt.setInt(2, orderItem.getOrderId());
            pstmt.setInt(3, orderItem.getMenuId());
            pstmt.setInt(4, orderItem.getQuantity());
            pstmt.setInt(5, orderItem.getItemTotal());
            return pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace(); // Consider using a logging framework
            return 0;
        }
    }

    @Override
    public ArrayList<OrderItems> fetchAllOrderItems() {
        ArrayList<OrderItems> orderItemsList = new ArrayList<>();
        String query = FETCH_ALL_ORDER_ITEMS;
        try (Connection con = DBConnection.connect();
             Statement stmt = con.createStatement();
             ResultSet rset = stmt.executeQuery(query)) {
            orderItemsList = extractOrderItemsDetailsFromResultSet(rset);
        } catch (Exception e) {
            e.printStackTrace(); // Consider using a logging framework
        }
        return orderItemsList;
    }

    @Override
    public OrderItems fetchOrderItem(int orderItemId) {
        OrderItems orderItem = null;
        String query = FETCH_ORDER_ITEM_BY_ID;
        try (Connection con = DBConnection.connect();
             PreparedStatement pstmt = con.prepareStatement(query)) {
            pstmt.setInt(1, orderItemId);
            try (ResultSet rset = pstmt.executeQuery()) {
                if (rset.next()) {
                    orderItem = new OrderItems(
                            rset.getInt("orderItemId"),
                            rset.getInt("orderId"),
                            rset.getInt("menuId"),
                            rset.getInt("quantity"),
                            rset.getInt("itemTotal")
                    );
                }
            }
        } catch (Exception e) {
            e.printStackTrace(); // Consider using a logging framework
        }
        return orderItem;
    }

    private ArrayList<OrderItems> extractOrderItemsDetailsFromResultSet(ResultSet rset) {
        ArrayList<OrderItems> orderItemsList = new ArrayList<>();
        try {
            while (rset.next()) {
                orderItemsList.add(new OrderItems(
                        rset.getInt("orderItemId"),
                        rset.getInt("orderId"),
                        rset.getInt("menuId"),
                        rset.getInt("quantity"),
                        rset.getInt("itemTotal")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace(); // Consider using a logging framework
        }
        return orderItemsList;
    }

    @Override
    public int updateOrderItemQuantity(int orderItemId, int quantity) {
        String query = UPDATE_ORDER_ITEM_QUANTITY;
        try (Connection con = DBConnection.connect();
             PreparedStatement pstmt = con.prepareStatement(query)) {
            pstmt.setInt(1, quantity);
            pstmt.setInt(2, orderItemId);
            return pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace(); // Consider using a logging framework
            return 0;
        }
    }

    @Override
    public int deleteOrderItem(int orderItemId) {
        String query = DELETE_ORDER_ITEM;
        try (Connection con = DBConnection.connect();
             PreparedStatement pstmt = con.prepareStatement(query)) {
            pstmt.setInt(1, orderItemId);
            return pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace(); // Consider using a logging framework
            return 0;
        }
    }
}
