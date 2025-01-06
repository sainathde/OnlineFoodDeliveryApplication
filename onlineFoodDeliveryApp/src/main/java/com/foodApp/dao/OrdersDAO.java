package com.foodApp.dao;

import java.util.ArrayList;

import com.foodApp.model.Orders;

public interface OrdersDAO {
	int insert(Orders order);

    ArrayList<Orders> fetchAllOrders();

    Orders fetchOrder(int orderId);

    int updateOrderStatus(int orderId, String status);

    int deleteOrder(int orderId);
}
