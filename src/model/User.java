package model;

import java.sql.PreparedStatement;
//import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import database.Connect;

public class User {
	
	private int userId;
	private String userRole; 
	private String userName;
	private String userEmail;
	private String userPassword;
	
	public User(int userId, String userRole, String userName, String userEmail, String userPassword) {
		super();
		this.userId = userId;
		this.userRole = userRole;
		this.userName = userName;
		this.userEmail = userEmail;
		this.userPassword = userPassword;
	}
	
	public static void updateUser(String userRole, int userId) {
	    String query = "UPDATE users SET userRole = ? WHERE userId = ?";

	    try (PreparedStatement ps = Connect.getConnection().prepareStatement(query)) {
	        ps.setString(1, userRole);
	        ps.setInt(2, userId);

	        Connect.getConnection().executeUpdate(ps);
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}
	
	public static void deleteUser(int userId) {
	    String query = String.format("DELETE FROM users WHERE userId = ?");

		try (PreparedStatement ps = Connect.getConnection().prepareStatement(query)) {
 	        ps.setInt(1, userId);

	        Connect.getConnection().executeUpdate(ps);
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}

	
	public static void getUserById(int userId) {
		String query = String.format("SELECT * FROM users WHERE userId = %d", userId);
		Connect.getConnection().executeQuery(query);
	}
	
	public static User getLastUser() {
		String query = "SELECT * FROM users WHERE userId = (SELECT MAX(userId) FROM users)";
		try (PreparedStatement ps = Connect.getConnection().prepareStatement(query)) {        	
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
            	int userId = rs.getInt("userId");
            	String role = rs.getString("userRole");
            	String name = rs.getString("userName");
                String email = rs.getString("userEmail");
                String pass = rs.getString("userPassword");
                                
                return new User(userId, role, name, email, pass);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
	}
	
	public static User authenticateUser(String userEmail, String userPassword) {
	    String query = "SELECT * FROM users WHERE userEmail = ? AND userPassword = ?";
	    try (PreparedStatement ps = Connect.getConnection().prepareStatement(query)) {
	        ps.setString(1, userEmail);
	        ps.setString(2, userPassword);

	        ResultSet rs = ps.executeQuery();

	        if (rs.next()) {
	            User authenticatedUser = new User(
	                rs.getInt("userId"),
	                rs.getString("userRole"),
	                rs.getString("userName"),
	                rs.getString("userEmail"),
	                rs.getString("userPassword")
	            );

	            return authenticatedUser;
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return null; 
	}

	public static boolean isEmailRegistered(String userEmail, String userPassword) {
		String query = String.format("SELECT * FROM users WHERE userEmail = '%s'",
                userEmail, userPassword);
        try {
            ResultSet rs = Connect.getConnection().executeQuery(query);

            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }	
	
	public static String getUserRoleByEmail(String userEmail) {
		String query = String.format("SELECT userRole FROM users WHERE userEmail = '%s'", userEmail);
		try {
            ResultSet rs = Connect.getConnection().executeQuery(query);

            if (rs.next()) {
                return rs.getString("userRole");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "Customer";
	}
	
	public static ArrayList<User> getAllUsers() {
		ArrayList<User> users = new ArrayList<>();
		String query = "SELECT * FROM users";
		ResultSet rs = Connect.getConnection().executeQuery(query);
		try {
			while(rs.next()) { //selama ada row di setelahnya
				int id = rs.getInt(1);
				String role = rs.getString(2);
				String name = rs.getString(3);
				String email = rs.getString(4);
				String pass = rs.getString(5);
				users.add(new User(id, role, name, email, pass));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return users;
	}
	public static int createOrder(int userId, String orderDate) {
        try {
            String query = "INSERT INTO orders (orderUserId, orderStatus, orderDate, orderTotal) VALUES (?, 'Pending', ?, 0)";

            try (PreparedStatement ps = Connect.getConnection().prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
                ps.setInt(1, userId);
                ps.setString(2, orderDate);

                Connect.getConnection().executeUpdate(ps);

                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    return rs.getInt(1); 
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return -1; 
    }
	public static void createUser(String role, String name, String email, String pass) {
	    String query = "INSERT INTO users (userRole, userName, userEmail, userPassword) VALUES (?, ?, ?, ?)";

	    try (PreparedStatement ps = Connect.getConnection().prepareStatement(query)) {
	        ps.setString(1, role);
	        ps.setString(2, name);
	        ps.setString(3, email);
	        ps.setString(4, pass);

	        Connect.getConnection().executeUpdate(ps);
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getUserRole() {
		return userRole;
	}

	public void setUserRole(String userRole) {
		this.userRole = userRole;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public String getUserPassword() {
		return userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}
	
//	 database query add table users
//	 CREATE TABLE users (
//		    userId INT AUTO_INCREMENT PRIMARY KEY,
//		    userRole VARCHAR(50) NOT NULL,
//		    userName VARCHAR(50) NOT NULL,
//		    userEmail VARCHAR(255) NOT NULL,
//		    userPassword VARCHAR(50) NOT NULL
//		);
}
