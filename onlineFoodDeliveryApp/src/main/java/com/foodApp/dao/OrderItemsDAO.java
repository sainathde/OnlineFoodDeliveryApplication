package com.foodApp.dao;

import java.util.ArrayList;

import com.foodApp.model.OrderItems;

public interface OrderItemsDAO {

    int insert(OrderItems orderItem);

    ArrayList<OrderItems> fetchAllOrderItems();

    OrderItems fetchOrderItem(int orderItemId);

    int updateOrderItemQuantity(int orderItemId, int quantity);

    int deleteOrderItem(int orderItemId);
}
