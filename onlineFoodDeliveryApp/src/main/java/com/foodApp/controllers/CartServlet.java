package com.foodApp.controllers;

import java.io.IOException;

import com.foodApp.dao.MenuDAO;
import com.foodApp.daoimpl.Cart;
import com.foodApp.daoimpl.MenuDAOImpl;
import com.foodApp.model.CartItem;
import com.foodApp.model.Menu;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/CartServlet")
public class CartServlet extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	    
	    // Step 1: Get the session for the cart
	    HttpSession session = req.getSession();
	    Cart cart = (Cart) session.getAttribute("cart");

	    // Step 2: Check if the cart exists or not, if not create a new cart
	    if (cart == null) {
	        cart = new Cart();
	        session.setAttribute("cart", cart);
	    }

	    // Step 3: Fetch the request parameters (action and itemId)
	    String action = req.getParameter("action");

	    if (action.equals("clear")) {
	        // Step 4: Clear the entire cart (no need for itemId)
	        cart.clear();
	    } else {
	        // For actions like add, update, and remove, we need itemId
	        int itemId = Integer.parseInt(req.getParameter("itemId"));

	        // Step 5: Create an object of MenuDAO (for fetching the details of the menu item)
	        MenuDAO mdao = null;
	        try {
	            mdao = new MenuDAOImpl();
	        } catch (Exception e) {
	            e.printStackTrace();
	        }

	        try {
	            // Step 6: Perform actions based on the 'action' parameter
	            if (action.equals("add")) {
	                // Add item to the cart
	                Menu menuItem = mdao.fetchMenu(itemId);
	                if (menuItem != null) {
	                    int quantity = 1; // Default quantity
	                    CartItem cartItem = new CartItem(
	                            menuItem.getMenuId(),
	                            menuItem.getRestaurantId(),
	                            menuItem.getName(),
	                            quantity,
	                            menuItem.getPrice());
	                    cart.addItem(cartItem);
	                }
	            } else if (action.equals("update")) {
	                // Update item quantity in the cart
	                int quantity = Integer.parseInt(req.getParameter("quantity"));
	                if (quantity > 0) {
	                    cart.updateItem(itemId, quantity);
	                } else {
	                    cart.removeItem(itemId); // If quantity is less than or equal to 0, remove the item
	                }
	            } else if (action.equals("remove")) {
	                // Remove item from the cart
	                cart.removeItem(itemId);
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }

	    // Step 7: Set the cart object in session and redirect to the cart page
	    session.setAttribute("cart", cart);
	    resp.sendRedirect("Cart.jsp");
	}

}
