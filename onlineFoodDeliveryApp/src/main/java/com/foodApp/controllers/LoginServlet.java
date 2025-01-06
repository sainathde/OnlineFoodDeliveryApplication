package com.foodApp.controllers;

import java.io.IOException;
import java.sql.Connection;

import com.foodApp.dao.UserDAO;
import com.foodApp.daoimpl.UserDAOImpl;
import com.foodApp.model.User;
import com.foodApp.security.Decryption;
import com.foodApp.security.Encryption;
import com.foodAppDAO.dbutil.DBConnection;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
    private UserDAO userDAO;

    @Override
    public void init() {
        // Initialize the DAO object (assuming DatabaseConnection is available)
        Connection connection = DBConnection.connect();
        userDAO = new UserDAOImpl();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Collect details from login.jsp
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        // Encrypt email and password before checking in the database
        String encryptedEmail = Encryption.encrypt(email);
        String encryptedPassword = Encryption.encrypt(password);

        // Fetch user from database based on encrypted email
        User user = userDAO.getUserByEmail(encryptedEmail);

        if (user != null) {
            // Check if the encrypted password matches the one stored in the database
            if (encryptedPassword.equals(user.getPassword())) {
                // If the password matches, decrypt the email
                String decryptedEmail = Decryption.decrypt(user.getEmail());

                // Set the decrypted email in a cookie
                Cookie emailCookie = new Cookie("userEmail", decryptedEmail);
                emailCookie.setMaxAge(60 * 60 * 24); // Cookie valid for 1 day
                response.addCookie(emailCookie);

                // Create a new User object with decrypted email
                User authenticatedUser = new User(user.getUserId(), user.getUserName(),user.getPassword(),decryptedEmail, user.getAddress());

                // Store the User object in the session
                HttpSession session = request.getSession();
                session.setAttribute("user", authenticatedUser);

                // Redirect to GetAllRestaurantsServlet
                response.sendRedirect("RestaurantServlet");
//                response.getWriter().println("you are welcome !!!");
            } else {
                // Password does not match, redirect to login page with error message
                request.setAttribute("errorMessage", "Incorrect password. Please try again.");
                request.getRequestDispatcher("Login.jsp").forward(request, response);
            }
        } else {
            // User with the given email not found, redirect to login page with error message
            request.setAttribute("errorMessage", "Email not found. Please register.");
            request.getRequestDispatcher("Login.jsp").forward(request, response);
        }
    }

    @Override
    public void destroy() {
        // Clean up resources if needed
    }
}
