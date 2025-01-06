<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Registration</title>
    <!-- Link to Google Fonts for professional font -->
    <link href="https://fonts.googleapis.com/css2?family=Roboto:wght@400;500;700&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="resources/css/Register.css">
</head>
<body>
    <header>
        <div class="header-container">
            <h1>FoodSphere</h1>
        </div>
    </header>

    <div class="form-container">
        <div class="card">
            <h2>Signup Form</h2>
            <form action="RegisterServlet" method="post">
                <label for="username">Username</label>
                <input type="text" id="username" name="username" placeholder="Enter your username" required>
                
                <label for="password">Password</label>
                <input type="password" id="password" name="password" placeholder="Enter your password" required>
                
                <label for="confirmPassword">Confirm Password</label>
                <input type="password" id="confirmPassword" name="confirmPassword" placeholder="Re-enter your password" required>
                
                <label for="email">Email</label>
                <input type="email" id="email" name="email" placeholder="Enter your email" required>
                
                <label for="address">Address</label>
                <textarea id="address" name="address" placeholder="Enter your address" required></textarea>
                
                <button class="register-btn" type="submit">Register</button>
                <div class="login-link">
                    <p>Already have an account? <a href="Login.jsp">Click here to login</a></p>
                </div>
            </form>
        </div>
    </div>

    <footer>
        <p>FoodSphere © 2024 - All rights reserved.</p>
    </footer>
</body>
</html>
