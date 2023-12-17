package model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
	
	// Creates a new user with the provided role, name, email, and password
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
	
	// Updates the role of a user with the specified user ID
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
	
	// Deletes a user with the specified user ID
	public static void deleteUser(int userId) {
	    String query = String.format("DELETE FROM users WHERE userId = ?");

		try (PreparedStatement ps = Connect.getConnection().prepareStatement(query)) {
 	        ps.setInt(1, userId);

	        Connect.getConnection().executeUpdate(ps);
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}

	// Retrieves user information by user ID
	public static void getUserById(int userId) {
		String query = String.format("SELECT * FROM users WHERE userId = %d", userId);
		Connect.getConnection().executeQuery(query);
	}

	// Retrieves the latest registered user
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
	
	// Authenticates a user by matching the provided email and password
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

	// Checks if an email is already registered
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
	
	// Retrieves the role of a user by email
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

	// Retrieves a list of all users in the database
	public static ArrayList<User> getAllUsers() {
		ArrayList<User> users = new ArrayList<>();
		String query = "SELECT * FROM users";
		ResultSet rs = Connect.getConnection().executeQuery(query);
		try {
			while(rs.next()) {
				int id = rs.getInt(1);
				String role = rs.getString(2);
				String name = rs.getString(3);
				String email = rs.getString(4);
				String pass = rs.getString(5);
				users.add(new User(id, role, name, email, pass));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return users;
	}

}
