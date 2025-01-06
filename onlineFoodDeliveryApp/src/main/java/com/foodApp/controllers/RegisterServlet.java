package com.foodApp.controllers;

import java.io.IOException;

import com.foodApp.dao.UserDAO;
import com.foodApp.daoimpl.UserDAOImpl;
import com.foodApp.model.User;
import com.foodApp.security.Encryption;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/RegisterServlet")
public class RegisterServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private UserDAO userDAO = new UserDAOImpl();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Retrieve data from the registration form
        String userName = request.getParameter("username");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirmPassword");
        String email = request.getParameter("email");
        String address = request.getParameter("address");
        
        

        // Check if password and confirm password match
        if (!password.equals(confirmPassword)) {
            request.setAttribute("error", "Password and Confirm Password do not match!");
            request.getRequestDispatcher("Register.jsp").forward(request, response);
            return;
        }

        // Encrypt password and email
        String encryptedPassword = Encryption.encrypt(password);
        String encryptedEmail = Encryption.encrypt(email);

        // Create User object
        User user = new User(userName, encryptedPassword, encryptedEmail, address);

        // Insert user into the database
        int result = userDAO.insert(user);

        // Check if user insertion was successful
        if (result > 0) {
            // Redirect to success page or login page
            response.sendRedirect("Login.jsp");
        } else {
            // If insertion failed, show error message
//            request.setAttribute("error", "Registration failed. Please try again.");
            request.getRequestDispatcher("register.jsp").forward(request, response);
        }
    }
}
