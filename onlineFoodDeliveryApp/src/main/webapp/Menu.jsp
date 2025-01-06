<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.foodApp.model.*" %>
<%@ page import="java.util.*"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Menu Details</title>
    <link rel="stylesheet" type="text/css" href="resources/css/Menu.css">
</head>
<body>
	
<div class="navbar">
    <div class="app-name">FoodSphere</div>
    <div class="navbar-right">
        <div class="actions">
            <a href="RestaurantServlet" class="btn">Restaurants</a>
            <a href="Cart.jsp" class="btn">Cart</a>
        </div>
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
</div>




    <div class="menu-container">
        <h1>Menu Details of <span><%= request.getAttribute("restaurantName") %></span></h1>
        <%
            // Retrieve the menu list from the request attribute
            ArrayList<Menu> menuList = (ArrayList<Menu>) request.getAttribute("menuList");

            if (menuList != null && !menuList.isEmpty()) {
        %>
        <div class="cards-container">
            <%
                for (Menu menu : menuList) {
            %>
            <div class="menu-card">
                <div class="card-image">
                    <img src="<%= request.getContextPath() + "/resources/" + menu.getImagePath() %>" alt="<%= menu.getName() %>" class="menu-image">
                </div>
                <div class="card-content">
                    <h3><%= menu.getName() %></h3>
                    <p class="menu-price">Price: ₹<%= menu.getPrice() %></p>
                    <p class="menu-status <%= menu.isAvailable() ? "available" : "not-available" %>">
                        <%= menu.isAvailable() ? "Available" : "Not Available" %>
                    </p>
                    <p class="menu-description"><%= menu.getDescription() %></p>
                    <form action="CartServlet" method="post">
                        <input type="hidden" name="itemId" value="<%= menu.getMenuId() %>">
                        <input type="hidden" name="quantity" value="1">
                        <input type="hidden" name="action" value="add">
                        <button type="submit" class="add-to-cart">Add to Cart</button>
                    </form>
                </div>
            </div>
            <%
                }
            %>
        </div>
        <%
            } else {
        %>
        <div class="no-menu">
            <h2>No menu items available for this restaurant.</h2>
        </div>
        <%
            }
        %>
    </div>
</body>
</html>
