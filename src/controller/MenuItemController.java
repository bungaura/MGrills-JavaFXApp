package controller;

import java.util.ArrayList;

import model.MenuItem;

public class MenuItemController {
	
	public static void createMenuItem(String menuItemName, String menuItemDescription, Double menuItemPrice) {
		MenuItem.createMenuItem(menuItemName, menuItemDescription, menuItemPrice);
	}
	
	public static void updateMenuItem(int menuItemId, String menuItemName, String menuItemDescription, Double menuItemPrice) {
		MenuItem.updateMenuItem(menuItemId, menuItemName, menuItemDescription, menuItemPrice);
	}
	
	public static void deleteMenuItem(int menuItemId) {
		MenuItem.deleteMenuItem(menuItemId);
	}
	
	public void getMenuItemById(int menuItemId) {
		MenuItem.getMenuItemById(menuItemId);
	}
	
	public static ArrayList<MenuItem> getAllMenuItems() {
		return MenuItem.getAllMenuItems();
	}
	
	public static String validateItemOnModify(String name, String desc, Double price) {
		if(name.isEmpty() || desc.isEmpty() || price.equals(null)) {
			return "All fields must be filled out!";
		}
		if(price < 2.5) {
			return "Price must be equal to or greater than 2.5!";
		}
		return null;
	}
	
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
		return null;
	}

}
