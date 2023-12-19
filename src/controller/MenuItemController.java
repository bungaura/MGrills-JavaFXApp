package controller;

import java.util.ArrayList;

import model.MenuItem;

public class MenuItemController {
	
    // Creates a new menu item with the provided details connected from view to model
	public static void createMenuItem(String menuItemName, String menuItemDescription, Double menuItemPrice) {
		MenuItem.createMenuItem(menuItemName, menuItemDescription, menuItemPrice);
	}
	
    // Updates an existing menu item with the specified ID using the provided details connected from view to model
	public static void updateMenuItem(int menuItemId, String menuItemName, String menuItemDescription, Double menuItemPrice) {
		MenuItem.updateMenuItem(menuItemId, menuItemName, menuItemDescription, menuItemPrice);
	}
	
	// Deletes a menu item with the specified ID
	public static void deleteMenuItem(int menuItemId) {
		MenuItem.deleteMenuItem(menuItemId);
	}
	
    // Retrieves the name of a menu item by ID
	public void getMenuItemById(int menuItemId) {
		MenuItem.getMenuItemById(menuItemId);
	}
	
    // Retrieves a list of all menu items
	public static ArrayList<MenuItem> getAllMenuItems() {
		return MenuItem.getAllMenuItems();
	}
	
    // Validates the price input when modifying a menu item
	public static String validateItemOnModify(String name, String desc, Double price) {
		if(name.isEmpty() || desc.isEmpty() || price.equals(null)) {
			return "All fields must be filled out!";
		}
		if(price < 2.5) {
			return "Price must be equal to or greater than 2.5!";
		}
		return null;
	}
	
	// Validates the price input when creating a new menu item
	public static String validateItemCreation(String name, String desc, Double price) {
		if(name.isEmpty() || desc.isEmpty() || price.equals(null)) {
			return "All fields must be filled out!";
		}
		if (MenuItem.isItemRegistered(name)) {
			return "This item is already registered!";
		}
		if(price < 2.5) {
			return "Price must be equal to or greater than 2.5!";
		}
		
		if(desc.length() < 10) {
			return "Description must be more than 10 characters";
		}
		return null;
	}

}
