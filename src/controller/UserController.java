package controller;

import model.User;

public class UserController {
	private static User currentUser;
	
	// Creates a new user with the provided role, name, email, and password
	public static void createUser(String userRole, String userName, String userEmail, String userPassword) {
		User.createUser(userRole, userName, userEmail, userPassword);
	}

    // Updates the role of a user with the specified ID, and validates the user role input
	public static void updateUser(String userRole, int userId) {
		User.updateUser(userRole, userId);
	}
	
    // Deletes a user with the specified ID
	public static void deleteUser(int userId) {
		User.deleteUser(userId);
	}
	
	// Retrieves a user by ID
	public static void getUserById(int userId) {
		User.getUserById(userId);
	}
	
    // Retrieves all users
	public void getAllUsers() {
		User.getAllUsers();
	}

    // Retrieves current user
	public static User getCurrentUser() {
		currentUser = User.getLastUser();
		return currentUser;
	}

    // Validates the registration input, checking if all fields are filled, email is unique, has valid format, password length is sufficient, and passwords match
	public static String authenticateUser(String email, String pass) {
		if (email.isEmpty() || pass.isEmpty()) {
	        return "All fields must be filled out!";
        } if(!User.isEmailRegistered(email, pass)) {
        	return "Email is not registered!";
        } 
        User authenticatedUser = User.authenticateUser(email, pass);
        if(authenticatedUser == null) {
        	return "Password do not match!";	
        } 
        currentUser = authenticatedUser; 
        return null;
	}

    // Retrieves the current authenticated user
	public static User getAuthenticatedUser() {
		return currentUser;
	}
	
    // Authenticates a user by email and password, and validates the authentication input
	public static String validateUserRole(String role, int id) {
		if (role.isEmpty()) {
	        return "Role must be filled out!";
	    }
		if (!role.equals("Customer") && !role.equals("Admin") && !role.equals("Cashier")
				&& !role.equals("Chef") && !role.equals("Waiter")) {
			return "Invalid role! Valid role options are Customer, Admin, Cashier, Chef, Waiter";
		}
		User.updateUser(role, id);
		return null;
	}

    // Retrieves the authenticated user
	public static String validateRegistration(String name, String email, String pass, String confirmPass) {
		if (name.isEmpty() || email.isEmpty() || pass.isEmpty() || confirmPass.isEmpty()) {
	        return "All fields must be filled out!";
	    }
		if (User.isEmailRegistered(email, pass)) {
        	return "Email is already registered!";
        }
	    if (!emailIsValid(email)) {
	        return "Invalid email format! Must contain @ and .";
	    }
	    if (pass.length() < 6) {
	        return "Password must be at least 6 characters long!";
	    }
	    if (!pass.equals(confirmPass)) {
	        return "Password do not match!";
	    }
	    return null;
	}
	
    // Validates the user role input, checking if the role is filled and is a valid role option
	private static boolean emailIsValid(String email) {
	    return email.matches(".+@.+\\..+");
	}
    
    // Retrieves the role of a user by email
	public static String getUserRole(String email) {
        return User.getUserRoleByEmail(email);
    }
}
