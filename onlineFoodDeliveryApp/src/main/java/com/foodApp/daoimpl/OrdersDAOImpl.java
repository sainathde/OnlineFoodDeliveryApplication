package com.foodApp.daoimpl;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.foodApp.dao.OrdersDAO;
import com.foodApp.model.Orders;
import com.foodAppDAO.dbutil.DBConnection;

public class OrdersDAOImpl implements OrdersDAO {

    private static final String INSERT_ORDER = "INSERT INTO orders(userId, restaruntId, totalAmount, status, paymentMode) VALUES (?, ?, ?, ?, ?)";
    private static final String FETCH_ALL_ORDERS = "SELECT * FROM orders";
    private static final String FETCH_ORDER_BY_ID = "SELECT * FROM orders WHERE orderId = ?";
    private static final String UPDATE_ORDER_STATUS = "UPDATE orders SET status = ? WHERE orderId = ?";
    private static final String DELETE_ORDER = "DELETE FROM orders WHERE orderId = ?";
    
    private static Connection con;
    private PreparedStatement pstmt;
    private Statement stmt;
    private ResultSet rset;

    static {
        con = DBConnection.connect();
    }

    @Override
    public int insert(Orders order) {
        try (PreparedStatement pstmt = con.prepareStatement(INSERT_ORDER, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setInt(1, order.getUserId());
            pstmt.setInt(2, order.getRestaurantId());
            pstmt.setInt(3, order.getTotalAmount());
            pstmt.setString(4, order.getStatus());
            pstmt.setString(5, order.getPaymentMode());
            
            // Execute the update to insert the order
            int affectedRows = pstmt.executeUpdate();
            
            if (affectedRows > 0) {
                // Retrieve generated keys (orderid)
                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int orderId = generatedKeys.getInt(1); // Get the orderid
                        order.setOrderId(orderId); // Set the orderid in the order object
                        return orderId; // Return the generated orderid
                    } else {
                        throw new SQLException("Creating order failed, no ID obtained.");
                    }
                }
            }
            
            return 0; // If no rows affected, return 0
        } catch (Exception e) {
            e.printStackTrace();
            return 0; // Return 0 if any exception occurs
        }
    }



    @Override
    public ArrayList<Orders> fetchAllOrders() {
        ArrayList<Orders> orderList = new ArrayList<>();
        try {
            stmt = con.createStatement();
            rset = stmt.executeQuery(FETCH_ALL_ORDERS);
            orderList = extractOrderDetailsFromResultSet(rset);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return orderList;
    }

    @Override
    public Orders fetchOrder(int orderId) {
        Orders order = null;
        try (PreparedStatement pstmt = con.prepareStatement(FETCH_ORDER_BY_ID)) {
            pstmt.setInt(1, orderId);
            rset = pstmt.executeQuery();
            ArrayList<Orders> fetchedOrders = extractOrderDetailsFromResultSet(rset);
            if (!fetchedOrders.isEmpty()) {
                order = fetchedOrders.get(0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return order;
    }

    private ArrayList<Orders> extractOrderDetailsFromResultSet(ResultSet rset) {
        ArrayList<Orders> tempOrderList = new ArrayList<>();
        try {
            while (rset.next()) {
                tempOrderList.add(new Orders(
                        rset.getInt("orderId"),
                        rset.getInt("userId"),
                        rset.getInt("restaruntId"),
                        rset.getInt("totalAmount"),
                        rset.getString("status"),
                        rset.getString("paymentMode")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tempOrderList;
    }

    @Override
    public int updateOrderStatus(int orderId, String status) {
        try (PreparedStatement pstmt = con.prepareStatement(UPDATE_ORDER_STATUS)) {
            pstmt.setString(1, status);
            pstmt.setInt(2, orderId);
            return pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public int deleteOrder(int orderId) {
        try (PreparedStatement pstmt = con.prepareStatement(DELETE_ORDER)) {
            pstmt.setInt(1, orderId);
            return pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
}
