<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.foodApp.model.*"%>
<%@ page import="java.util.*"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Home - FoodSphere</title>
    <link rel="stylesheet" href="resources/css/loggedinHome.css">
</head>
<body>
    <!-- Navbar -->
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

    <!-- Welcome Section -->
    <div class="welcome" style="text-align:center">
        <h2>Welcome, <%= ((User) session.getAttribute("user")).getUserName() %>!</h2><br>
        <h2>Available Restaurants!</h2>
    </div>

    <!-- Card Container -->
    <div class="card-container">
        <%
        ArrayList<Restaurant> list = (ArrayList) request.getAttribute("restaurantList");
        if (list == null || list.isEmpty()) {
        %>
        <p>No data available</p>
        <%
        } else {
            for (Restaurant restaurant : list) {
        %>
        <div class="card">
            <img src="<%= request.getContextPath() + "/resources" + restaurant.getImagePath() %>" alt="<%= restaurant.getName() %>">
            <div class="info">
                <h3><%= restaurant.getName() %></h3>
                <div class="details">
                    <div class="left-details">
                        <span class="chip"><%= restaurant.getCusineType() %></span>
                        <p class="address"><%= restaurant.getAddress() %></p>
                        <p class="status <%= restaurant.isActive() ? "" : "closed" %>">
                            <%= restaurant.isActive() ? "Opened" : "Closed" %>
                        </p>
                    </div>
                    <div class="right-details">
                        <div class="ratings">
                            <span class="star">★</span><%= restaurant.getRatings() %>
                        </div>
                        <div class="delivery-time">
                            <span>ETA: </span><span><%= restaurant.getDeliveryTime() %> mins</span>
                        </div>
                    </div>
                </div>
                <form action="MenuServlet" method="post">
                    <input type="hidden" name="restaurantId" value="<%= restaurant.getRestaurantId() %>">
                    <button class="menu-button" type="submit">Menu</button>
                </form>
            </div>
        </div>
        <%
            }
        }
        %>
    </div>

    <!-- Footer Section -->
    <footer>
        <p>&copy; 2024 FoodSphere. All rights reserved.</p>
    </footer>
</body>
</html>
