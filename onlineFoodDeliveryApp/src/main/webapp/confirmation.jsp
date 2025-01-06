<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="com.foodApp.daoimpl.*" %>
<%@ page import="com.foodApp.model.*" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Order Confirmation</title>
    <link rel="stylesheet" href="resources/css/confirmation.css">
</head>
<body>

	<!-- Navbar at the top -->
    <div class="navbar">
        <div class="app-name">FoodSphere</div>
        <div class="profile">
            <div class="icon">
                <%= ((User) session.getAttribute("user")).getUserName().substring(0, 1) %>
            </div>
            <!-- Profile Card -->
            <div class="profile-card">
                <p><strong>Name:</strong> <%= ((User) session.getAttribute("user")).getUserName() %></p>
                <p><strong>Email:</strong> <%= ((User) session.getAttribute("user")).getEmail() %></p>
                <a href="Login.jsp">Logout</a>
            </div>
        </div> 
    </div>






    <% String paymentMode = request.getParameter("paymentMode"); %>
  
    <div class="confirmation-container">
        <div class="confirmation-header">
            <h1>Thanks for Your Order</h1>
        </div>

        <div class="order-summary">
            <h2>Your Order Summary</h2>
            <% 
                Cart cart = (Cart) session.getAttribute("cart");
                double totalAmount = 0;
                if (cart != null && cart.getItems() != null && !cart.getItems().isEmpty()) { 
            %>
            <table>
                <thead>
                    <tr>
                        <th>Item Name</th>
                        <th>Price</th>
                        <th>Quantity</th>
                        <th>Total</th>
                    </tr>
                </thead>
                <tbody>
                    <% 
                        for (Map.Entry<Integer, CartItem> entry : cart.getItems().entrySet()) { 
                            CartItem cartItem = entry.getValue();
                            double itemTotal = cartItem.getQuantity() * cartItem.getPrice();
                            totalAmount += itemTotal;
                    %>
                    <tr>
                        <td><%= cartItem.getName() %></td>
                        <td>&#8377;<%= cartItem.getPrice() %></td>
                        <td><%= cartItem.getQuantity() %></td>
                        <td>&#8377;<%= itemTotal %></td>
                    </tr>
                    <% } %>
                    <tr class="grand-total">
                        <td colspan="3"><strong>Grand Total</strong></td>
                        <td><strong>&#8377;<%= totalAmount %></strong></td>
                    </tr>
                </tbody>
            </table>

            <div class="order-actions">
                <form action="CheckOutServlet" method="post">
                    <input type="hidden" name="payment" value="<%= paymentMode %>">
                    <button type="submit" class="confirm-button">Confirm Order</button>
                </form>
                <!-- Cancel Order Form (Clearing the Cart) -->
                <form action="CartServlet" method="post">
                    <input type="hidden" name="action" value="clear" />
                    <button type="submit" class="cancel-button">Cancel Order</button>
                </form>
            </div>

            <% } else { %>
            <p>Your cart is empty. Please add items to your cart before proceeding.</p>
            <% } %>
        </div>
    </div>
</body>
</html>
