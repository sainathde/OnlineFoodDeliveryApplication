<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.foodApp.model.*"%>
<%@ page import="java.util.*"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Checkout</title>
    <link rel="stylesheet" type="text/css" href="resources/css/checkout.css">
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
    
    <br><br><br><br>

    <!-- Checkout Form below the Navbar -->
    <div class="container">
        <h1 class="heading">Order Form</h1>
        <form action="confirmation.jsp" method="post" class="order-form">
            <div class="form-group">
                <label for="area">Area:</label>
                <input type="text" id="area" name="area" placeholder="Enter your area" required>
            </div>
            <div class="form-group">
                <label for="street">Street:</label>
                <input type="text" id="street" name="street" placeholder="Enter your street" required>
            </div>
            <div class="form-group">
                <label for="flatno">Flat No:</label>
                <input type="text" id="flatno" name="flatno" placeholder="Enter your flat number" required>
            </div>
            <div class="form-group">
                <label for="mobile">Mobile Number:</label>
                <input type="tel" id="mobile" name="mobile" placeholder="Enter your mobile number" required>
            </div>
            <div class="form-group">
                <label for="paymentMode">Payment Mode:</label>
                <select id="paymentMode" name="paymentMode" required>
                    <option value="upi">UPI Payment</option>
                    <option value="online">Online Payment</option>
                    <option value="cod">Cash on Delivery</option>
                </select>
            </div>
            <div class="form-group">
                <button type="submit" class="btn-confirm">Confirm</button>
            </div>
        </form>
    </div>

</body>
</html>
