<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Login</title>
    <!-- Link to Google Fonts for professional font -->
    <link href="https://fonts.googleapis.com/css2?family=Roboto:wght@400;500;700&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="resources/css/Login.css">
</head>
<body>
    <header>
        <div class="header-container">
            <h1>FoodSphere</h1>
        </div>
    </header>

    <div class="form-container">
        <div class="card">
            <h2>Sign In</h2>
            <form action="LoginServlet" method="post">
                <label for="email">Email</label>
                <input type="email" id="email" name="email" placeholder="Enter your email" required>
                
                <label for="password">Password</label>
                <input type="password" id="password" name="password" placeholder="Enter your password" required>
                
                <button class="login-btn" type="submit">Login</button>
                <div class="register-link">
                    <p>Don't have an account? <a href="Register.jsp">Register here</a></p>
                </div>
            </form>
        </div>
    </div>

    <footer>
        <p>FoodSphere © 2024 - All rights reserved.</p>
    </footer>
</body>
</html>
