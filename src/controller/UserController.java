package controller;

import model.User;

public class UserController {
	private static User currentUser;
	
	public static void createUser(String userRole, String userName, String userEmail, String userPassword) {
		User.createUser(userRole, userName, userEmail, userPassword);
	}
	
	public static void updateUser(String userRole, int userId) {
		User.updateUser(userRole, userId);
	}
	
	public static void deleteUser(int userId) {
		User.deleteUser(userId);
	}
	
	public static void getUserById(int userId) {
		User.getUserById(userId);
	}
	
	public void getAllUsers() {
		User.getAllUsers();
	}
	
	public static User getCurrentUser() {
		currentUser = User.getLastUser();
		return currentUser;
	}
	
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
	
	public static User getAuthenticatedUser() {
		return currentUser;
	}
	
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

	private static boolean emailIsValid(String email) {
	    return email.matches(".+@.+\\..+");
	}
	
	public static String getUserRole(String email) {
        return User.getUserRoleByEmail(email);
    }
}
