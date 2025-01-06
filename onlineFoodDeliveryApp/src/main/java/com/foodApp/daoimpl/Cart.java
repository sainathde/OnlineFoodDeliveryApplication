package com.foodApp.daoimpl;

import java.util.HashMap;
import java.util.Map;

import com.foodApp.dao.CartDAO;
import com.foodApp.model.CartItem;

public class Cart implements CartDAO {

    private Map<Integer, CartItem> items; // Map to store items with their ID as the key

    public Cart() {
        this.items = new HashMap<>();
    }

    // Add an item to the cart
    @Override
    public void addItem(CartItem item) {
        int itemId = item.getItemId();
        if (items.containsKey(itemId)) {
            CartItem existingItem = items.get(itemId);
            existingItem.setQuantity(existingItem.getQuantity() + item.getQuantity());
        } else {
            items.put(itemId, item);
        }
    }

    // Update the quantity of an item in the cart
    @Override
    public void updateItem(int itemId, int quantity) {
        if (items.containsKey(itemId)) {
            if (quantity <= 0) {
                items.remove(itemId);
            } else {
                CartItem existingItem = items.get(itemId);
                existingItem.setQuantity(quantity);
            }
        }
    }

    // Remove an item from the cart
    @Override
    public void removeItem(int itemId) {
        items.remove(itemId);
    }

    // Get all items in the cart
    @Override
    public Map<Integer, CartItem> getItems() {
        return items;
    }

    // Clear the cart
    @Override
    public void clear() {
        items.clear();
    }

   
}
