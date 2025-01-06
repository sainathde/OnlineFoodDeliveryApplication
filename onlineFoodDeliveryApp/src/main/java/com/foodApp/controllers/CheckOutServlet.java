package com.foodApp.controllers;

import java.io.IOException;
import java.util.Map;

import com.foodApp.dao.OrderItemsDAO;
import com.foodApp.dao.OrdersDAO;
import com.foodApp.daoimpl.Cart;
import com.foodApp.daoimpl.OrderItemsDAOImpl;
import com.foodApp.daoimpl.OrdersDAOImpl;
import com.foodApp.model.CartItem;
import com.foodApp.model.OrderItems;
import com.foodApp.model.Orders;
import com.foodApp.model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/CheckOutServlet")
public class CheckOutServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Step 1: Get the user session and retrieve user details
        HttpSession session = req.getSession();
        User user = (User)session.getAttribute("user");
        Integer userId = user.getUserId();
        Integer restaurantId = (Integer) session.getAttribute("restaurantId"); // Retrieve restaurantId from session
        


        // Step 2: Retrieve the Cart object from the session
        Cart cart = (Cart) session.getAttribute("cart"); // Retrieve the Cart object
        
        if (userId == null || restaurantId == null || cart == null || cart.getItems().isEmpty()) {
            resp.getWriter().println("Cart is empty or session expired.");
            return;
        }

        // Step 3: Calculate the total amount by summing up the price * quantity of each cart item
        int totalAmount = 0;
        for (Map.Entry<Integer, CartItem> entry : cart.getItems().entrySet()) {
            CartItem cartItem = entry.getValue();
            totalAmount += cartItem.getPrice() * cartItem.getQuantity();
        }

        // Step 4: Get payment mode from the confirmation form
        String paymentMode = req.getParameter("payment");

        if (paymentMode == null || paymentMode.isEmpty()) {
            resp.getWriter().println("Payment mode is required.");
            return;
        }

        // Step 5: Create the Orders object with the calculated details
        Orders order = new Orders();
        order.setUserId(userId);
        order.setRestaurantId(restaurantId);
        order.setTotalAmount(totalAmount);
        order.setStatus("Pending");
        order.setPaymentMode(paymentMode);

        // Step 6: Use OrdersDAO to insert the order into the database
        OrdersDAO ordersDAO = new OrdersDAOImpl();
        int orderId = ordersDAO.insert(order);

        if (orderId > 0) {
            // Step 7: Insert each cart item into the order_items table
            OrderItemsDAO orderItemsDAO = new OrderItemsDAOImpl();
            boolean allItemsInserted = true;
            for (Map.Entry<Integer, CartItem> entry : cart.getItems().entrySet()) {
                CartItem cartItem = entry.getValue();
                OrderItems orderItem = new OrderItems();
                orderItem.setOrderId(orderId);
                orderItem.setMenuId(cartItem.getItemId());
                orderItem.setQuantity(cartItem.getQuantity());
                orderItem.setItemTotal((int) (cartItem.getPrice() * cartItem.getQuantity()));

                int result = orderItemsDAO.insert(orderItem);
                if (result <= 0) {
                    allItemsInserted = false;
                    break;
                }
            }

            // Step 8: Update order status based on item insertion success/failure
            if (allItemsInserted) {
                // If all items were inserted successfully, confirm the order
                order.setStatus("Confirmed");
                ordersDAO.updateOrderStatus(orderId,order.getStatus());
                // Step 9: Clear the cart after successful order and order items creation
                cart.clear(); 
                session.removeAttribute("cart"); 

                // Redirect to success page
                resp.sendRedirect("orderSuccess.jsp");
            } else {
                // If any item insertion failed, cancel the order
                order.setStatus("Cancelled");
                ordersDAO.updateOrderStatus(orderId,order.getStatus());
                
                // Delete the order since the insertion failed
                ordersDAO.deleteOrder(orderId); 

                req.setAttribute("errorMessage", "Failed to place the order. Please try again.");
                req.getRequestDispatcher("checkout.jsp").forward(req, resp);
            }
        } else {
            // If order creation failed, forward the request to checkout page with an error message
            req.setAttribute("errorMessage", "Failed to place the order. Please try again.");
            req.getRequestDispatcher("checkout.jsp").forward(req, resp);
        }
    }
}
