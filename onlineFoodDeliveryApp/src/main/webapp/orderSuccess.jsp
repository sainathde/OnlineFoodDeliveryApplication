<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Order Confirmation</title>
    <link rel="stylesheet" type="text/css" href="resources/css/orderSuccess.css">
</head>
<body>
    <div class="order-success-container">
        <!-- Delivery Bike GIF -->
        <div class="gif-container">
            <img src="resources/images/deliveryBike.gif" alt="Delivery Bike" class="delivery-bike-gif">
        </div>
        
        <!-- Order Confirmed Message -->
        <div class="order-confirmation">
            <h1>Order Confirmed!</h1>
            <p class="confirmation-message">Thank you for your order! Your food is on the way.</p>
        </div>

        <!-- Additional Message -->
        <div class="thank-you-message">
            <p>We appreciate your business. Our team is working to deliver your meal promptly. You will receive a notification once your order is out for delivery.</p>
            <p>If you have any questions or need assistance, feel free to contact us.</p>
        </div>

        <a href="RestaurantServlet" class="back-to-home">Back to Home</a>
    </div>
</body>
</html>
