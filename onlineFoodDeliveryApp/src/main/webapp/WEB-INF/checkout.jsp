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

    <!-- Navbar -->
    <div class="navbar">
        <div class="app-name">Food Delivery</div>
        <div class="profile">
            <div class="icon">U</div>
            <div class="dropdown">
                <ul>
                    <li><a href="#">My Orders</a></li>
                    <li><a href="#">Profile</a></li>
                    <li><a href="#">Logout</a></li>
                </ul>
            </div>
        </div>
    </div>

    <!-- Checkout Container -->
    <div class="container content">
        <h1 class="heading">Order Form</h1>
        <form action="Confirmation.jsp" method="post" class="order-form">
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
