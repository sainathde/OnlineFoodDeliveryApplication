<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="com.foodApp.daoimpl.*" %>
<%@ page import="com.foodApp.model.*" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Your Cart</title>
    <link rel="stylesheet" href="resources/css/Cart.css"> <!-- Link to external CSS -->
</head>
<body>

    <div style="text-align:center">
        <h2>Your Cart</h2>
    </div>
     
    <div class="container">
        <%-- Fetch cart from session --%>
        <% Cart cart = (Cart) session.getAttribute("cart"); %>

        <% if (cart == null || cart.getItems() == null || cart.getItems().isEmpty()) { %>
            <div class="empty-cart">
                <p>Your cart is empty</p>
                <button><a href="RestaurantServlet">Go to Restaurants</a></button>
            </div>
        <% } else { %>
            <div class="cart-items">
                <% double totalAmount = 0; %>

                <% for (Map.Entry<Integer, CartItem> entry : cart.getItems().entrySet()) { %>
                    <% CartItem cartItem = entry.getValue(); %>
                    <% totalAmount += cartItem.getQuantity() * cartItem.getPrice(); %>

                    <div class="cart-card">
                        <div class="item-details">
                            <h3><%= cartItem.getName() %></h3>
                            <p>Price: &#8377;<%= cartItem.getPrice() %></p>
                            <p>Total: &#8377;<%= cartItem.getQuantity() * cartItem.getPrice() %></p>
                        </div>
                        <div class="item-actions">
                            <%-- Form for decreasing quantity --%>
                            <form action="CartServlet" method="post" style="display: inline;">
                                <input type="hidden" name="itemId" value="<%= cartItem.getItemId() %>">
                                <input type="hidden" name="quantity" value="<%= cartItem.getQuantity() - 1 %>">
                                <input type="hidden" name="action" value="update">
                                <button type="submit" <%= cartItem.getQuantity() <= 1 ? "disabled" : "" %>>-</button>
                            </form>

                            <%-- Display current quantity --%>
                            <span><%= cartItem.getQuantity() %></span>

                            <%-- Form for increasing quantity --%>
                            <form action="CartServlet" method="post" style="display: inline;">
                                <input type="hidden" name="itemId" value="<%= cartItem.getItemId() %>">
                                <input type="hidden" name="quantity" value="<%= cartItem.getQuantity() + 1 %>">
                                <input type="hidden" name="action" value="update">
                                <button type="submit">+</button>
                            </form>

                            <%-- Form for removing the item --%>
                            <form action="CartServlet" method="post" style="display: inline;">
                                <input type="hidden" name="itemId" value="<%= cartItem.getItemId() %>">
                                <input type="hidden" name="action" value="remove">
                                <button type="submit" style="background-color: red; color: white;">Remove</button>
                            </form>
                        </div>
                    </div>
                <% } %>
            </div>

            <div class="cart-summary">
                <p>Total Amount: &#8377;<%= totalAmount %></p>
                
                <%-- Form for adding more items --%>
                <form action="MenuServlet" method="post" style="display: inline;">
                    <input type="hidden" name="restaurantId" value="<%= session.getAttribute("restaurantId") %>">
                    <button class="menu-button" type="submit">Add More Items</button>
                </form>
                
               <form action="CartServlet" method="post">
    				<input type="hidden" name="action" value="clear">
    				<button type="submit" style="background-color: red; color: white;">Clear Cart</button>
				</form>
                
                <%-- Button for proceeding to checkout --%>
                <form action="checkout.jsp" method="post">
  					<button type="submit">Go to Checkout</button>
				</form>
				
            </div>
            
        <% } %>
    </div>
</body>
</html>
