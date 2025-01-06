package com.foodApp.daoimpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import com.foodApp.dao.UserDAO;
import com.foodApp.model.User;
import com.foodAppDAO.dbutil.DBConnection;

public class UserDAOImpl implements UserDAO {

    static ArrayList<User> userList = new ArrayList<User>();

    // Updated INSERT statement excluding the userId (as it is auto-incremented)
    private static final String INSERTUSER = "insert into user(username, password, email, address) values(?,?,?,?)";
    private static final String FETCHUSERS = "select * from user";
    private static final String FETCHUSER = "select * from user where userid=?";
    private static final String FETCHUSERBYEMAIL = "select * from user where email=?";
    private static final String UPDATEUSER = "update user set address=? where userid=?";
    private static final String DELETEUSER = "delete from user where userid=?";
    
    private static Connection con;

    static {
        con = DBConnection.connect();
    }

    private PreparedStatement pstmt;
    private Statement stmt;
    private ResultSet rset;

    private User u;

    @Override
    public int insert(User u) {
        try {
            pstmt = con.prepareStatement(INSERTUSER);
            pstmt.setString(1, u.getUserName());
            pstmt.setString(2, u.getPassword());
            pstmt.setString(3, u.getEmail());
            pstmt.setString(4, u.getAddress());
            return pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public ArrayList<User> fetchAllUsers() {
        try {
            stmt = con.createStatement();
            rset = stmt.executeQuery(FETCHUSERS);
            userList = extractUserDetailsFromResultSet(rset);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return userList;
    }

    @Override
    public User fetchUser(int i) {
        try {
            pstmt = con.prepareStatement(FETCHUSER);
            pstmt.setInt(1, i);
            rset = pstmt.executeQuery();
            u = extractUserDetailsFromResultSet(rset).get(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return u;
    }

    // New method to get user by email
    @Override
    public User getUserByEmail(String email) {
        try {
            pstmt = con.prepareStatement(FETCHUSERBYEMAIL);
            pstmt.setString(1, email);
            rset = pstmt.executeQuery();
            if (rset.next()) {
                u = new User(rset.getInt("userid"),
                             rset.getString("username"),
                             rset.getString("password"),
                             rset.getString("email"),
                             rset.getString("address"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return u; // Return user object if found, else return null
    }

    public ArrayList<User> extractUserDetailsFromResultSet(ResultSet rset) {
        try {
            while (rset.next()) {
                userList.add(new User(rset.getInt("userid"),
                        rset.getString("username"),
                        rset.getString("password"),
                        rset.getString("email"),
                        rset.getString("address")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return userList;
    }

    @Override
    public int updateUser(int i, String address) {
        try {
            pstmt = con.prepareStatement(UPDATEUSER);
            pstmt.setString(1, address);
            pstmt.setInt(2, i);
            return pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public int delete(int i) {
        try {
            pstmt = con.prepareStatement(DELETEUSER);
            pstmt.setInt(1, i);
            return pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
}
