package com.foodApp.controllers;

import java.io.IOException;
import java.util.ArrayList;

import com.foodApp.dao.MenuDAO;
import com.foodApp.dao.RestaurantDAO;
import com.foodApp.daoimpl.MenuDAOImpl;
import com.foodApp.daoimpl.RestaurantDAOImpl;
import com.foodApp.model.Menu;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/MenuServlet")
public class MenuServlet extends HttpServlet {

    // DAO objects to interact with Menu and Restaurant data
    MenuDAO mdao = new MenuDAOImpl();
    RestaurantDAO rdao = new RestaurantDAOImpl();
    private ArrayList<Menu> menuList;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        
        // Retrieve restaurantId from the request parameter (from button click)
        int restId = Integer.parseInt(req.getParameter("restaurantId"));
        
        // Fetch the menu list based on the restaurantId
        menuList = mdao.fetchMenuByRestaurantId(restId);
        
        // Fetch the restaurant name
        String restName = rdao.fetchRestaurant(restId).getName();
      
        // Store the restaurantId and restaurantName as request attributes
        req.setAttribute("menuList", menuList);
        req.setAttribute("restaurantName", restName);
        
        HttpSession session = req.getSession();
        session.setAttribute("restaurantId", restId);
        
        // Forward the request to the Menu.jsp page
        req.getRequestDispatcher("Menu.jsp").forward(req, resp);
//        resp.getWriter().println(restId);
//        resp.getWriter().println(restName);
        
    }
}
