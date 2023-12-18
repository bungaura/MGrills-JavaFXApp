package model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import database.Connect;

public class MenuItem {
	
	private int menuItemId;
	private String menuItemName;
	private String menuItemDescription;
	private Double menuItemPrice;

	public MenuItem(int menuItemId, String menuItemName, String menuItemDescription, Double menuItemPrice) {
		super();
		this.menuItemId = menuItemId;
		this.menuItemName = menuItemName;
		this.menuItemDescription = menuItemDescription;
		this.menuItemPrice = menuItemPrice;
	}

	public static void createMenuItem(String menuItemName, String menuItemDescription, Double menuItemPrice) {
		String query = "INSERT INTO menuitems (menuItemName, menuItemDescription, menuItemPrice) VALUES (?, ?, ?)";

	    try (PreparedStatement ps = Connect.getConnection().prepareStatement(query)) {
	        ps.setString(1, menuItemName);
	        ps.setString(2, menuItemDescription);
	        ps.setDouble(3, menuItemPrice);

	        Connect.getConnection().executeUpdate(ps);
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}
	
	public static void updateMenuItem(int menuItemId, String menuItemName, String menuItemDescription, Double menuItemPrice) {
		String query = "UPDATE menuitems SET menuItemName = ?, menuItemDescription = ?, menuItemPrice = ? WHERE menuItemId = ?";

	    try (PreparedStatement ps = Connect.getConnection().prepareStatement(query)) {
	        ps.setString(1, menuItemName);
	        ps.setString(2, menuItemDescription);
	        ps.setDouble(3, menuItemPrice);
	        ps.setInt(4, menuItemId);

	        Connect.getConnection().executeUpdate(ps);
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}
	public static void deleteMenuItem(int menuItemId) {
		String query = String.format("DELETE FROM menuitems WHERE menuItemId = ?");

		try (PreparedStatement ps = Connect.getConnection().prepareStatement(query)) {
 	        ps.setInt(1, menuItemId);

	        Connect.getConnection().executeUpdate(ps);
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}
	public static String getMenuItemById(int menuItemId) {
		String query = String.format("SELECT menuItemName FROM menuitems WHERE menuItemId = %d", menuItemId);
		try {
            ResultSet rs = Connect.getConnection().executeQuery(query);

            if (rs.next()) {
                return rs.getString("menuItemName");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "Not Found";
	}
	
	public static MenuItem getMenuItemObjectById(int menuItemId) {
		String query = String.format("SELECT * FROM menuitems WHERE menuItemId = %d", menuItemId);
		try {
            ResultSet rs = Connect.getConnection().executeQuery(query);

            if (rs.next()) {
            	int id = rs.getInt("menuItemId");
            	String name = rs.getString("menuItemName");
            	String description = rs.getString("menuItemDescription");
            	double price = rs.getDouble("menuItemPrice");
            	MenuItem item = new MenuItem(id, name, description, price);
                return item;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
	}
	
	public static ArrayList<MenuItem> getAllMenuItems() {
		ArrayList<MenuItem> items = new ArrayList<>();
		String query = "SELECT * FROM menuitems";
		ResultSet rs = Connect.getConnection().executeQuery(query);
		try {
			while(rs.next()) { 
				int id = rs.getInt(1);
				String name = rs.getString(2);
				String desc = rs.getString(3);
				Double price = rs.getDouble(4);
				items.add(new MenuItem(id, name, desc, price));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return items;
	}
	
	public static boolean isItemRegistered(String menuItemName) {
		String query = String.format("SELECT * FROM menuitems WHERE menuItemName = '%s'",
				menuItemName);
        try {
            ResultSet rs = Connect.getConnection().executeQuery(query);

            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }	

	public int getMenuItemId() {
		return menuItemId;
	}

	public void setMenuItemId(int menuItemId) {
		this.menuItemId = menuItemId;
	}

	public String getMenuItemName() {
		return menuItemName;
	}

	public void setMenuItemName(String menuItemName) {
		this.menuItemName = menuItemName;
	}

	public String getMenuItemDescription() {
		return menuItemDescription;
	}

	public void setMenuItemDescription(String menuItemDescription) {
		this.menuItemDescription = menuItemDescription;
	}

	public Double getMenuItemPrice() {
		return menuItemPrice;
	}

	public void setMenuItemPrice(Double menuItemPrice) {
		this.menuItemPrice = menuItemPrice;
	}
	
//	 database query add table menuitems
//	CREATE TABLE menuitems (
//		    menuItemId INT AUTO_INCREMENT PRIMARY KEY,
//		    menuItemName VARCHAR(50) NOT NULL,
//		    menuItemDescription VARCHAR(255) NOT NULL,
//		    menuItemPrice DOUBLE NOT NULL
//		);

}
