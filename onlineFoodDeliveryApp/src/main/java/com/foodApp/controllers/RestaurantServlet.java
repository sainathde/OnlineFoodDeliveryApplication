package com.foodApp.controllers;

import java.io.IOException;
import java.util.List;

import com.foodApp.dao.RestaurantDAO;
import com.foodApp.daoimpl.RestaurantDAOImpl;
import com.foodApp.model.Restaurant;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/RestaurantServlet")
public class RestaurantServlet extends HttpServlet {

    private RestaurantDAO restaruntDAO;

    @Override
    public void init() throws ServletException {
        super.init();
        restaruntDAO = new RestaurantDAOImpl();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Check for cookie (user email) to confirm login
        Cookie[] cookies = request.getCookies();
        String userEmail = null;

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("userEmail".equals(cookie.getName())) {
                    userEmail = cookie.getValue();
                    break;
                }
            }
        }

        // If cookie is not found, user is not logged in
        if (userEmail == null) {
            response.sendRedirect("login.jsp"); // Redirect to login page
            return;
        }

        // User is logged in, fetch all restaurants
        List<Restaurant> restaurantList = restaruntDAO.fetchAllRestaurants();

        // Add restaurants to request object (or session based on your decision)
        request.setAttribute("restaurantList", restaurantList);

        // Forward control to loggedinHome.jsp
        RequestDispatcher dispatcher = request.getRequestDispatcher("loggedinHome.jsp");
        dispatcher.forward(request, response);
        
//        response.getWriter().println("success");
    }
}
