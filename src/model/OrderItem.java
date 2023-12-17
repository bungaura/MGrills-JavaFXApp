package model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import database.Connect;

public class OrderItem {
    private int orderItemId;
    private int orderId;
    private int menuItemId;
    private int quantity;
    private String menuItemName;
    
    public OrderItem(int orderItemId, int orderId, int menuItemId, String menuItemName, int quantity) {
        this.orderItemId = orderItemId;
        this.orderId = orderId;
        this.menuItemId = menuItemId;
        this.menuItemName = menuItemName;
        this.quantity = quantity;
    }
    
    public int getOrderItemId() {
        return orderItemId;
    }

    public void setOrderItemId(int orderItemId) {
        this.orderItemId = orderItemId;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getMenuItemId() {
        return menuItemId;
    }

    public void setMenuItemId(int menuItemId) {
        this.menuItemId = menuItemId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getMenuItemName() {
        return menuItemName;
    }

    public void setMenuItemName(String menuItemName) {
        this.menuItemName = menuItemName;
    }

    // Creates a new order item within a specific order with the provided menu item ID and quantity
    public static void createOrderItem(int orderId, int menuItemId, int quantity) {
        String query = "INSERT INTO orderitems (orderId, menuItemId, quantity) VALUES (?, ?, ?)";

        try (PreparedStatement ps = Connect.getConnection().prepareStatement(query)) {
            ps.setInt(1, orderId);
            ps.setInt(2, menuItemId);
            ps.setInt(3, quantity);

            ps.executeUpdate();
            updateOrderTotal(orderId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    // Updates the quantity of an order item within a specific order
    public static void updateOrderItem(int orderItemId, int menuItemId, int quantity) {
        String query = "UPDATE orderitems SET quantity = ? WHERE orderItemId = ? AND menuItemId = ?";

        try (PreparedStatement ps = Connect.getConnection().prepareStatement(query)) {
            ps.setInt(1, quantity);
            ps.setInt(2, orderItemId);
            ps.setInt(3, menuItemId);

            Connect.getConnection().executeUpdate(ps);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Deletes an order item with the specified ID
    public static void deleteOrderItem(int orderItemId) {
        String query = "DELETE FROM orderitems WHERE orderItemId = ?";

        try (PreparedStatement ps = Connect.getConnection().prepareStatement(query)) {
            ps.setInt(1, orderItemId);

            Connect.getConnection().executeUpdate(ps);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    // Update order total with the specified ID
    private static void updateOrderTotal(int orderId) {
        String query = "UPDATE orders o "
                + "SET o.orderTotal = ( "
                + "    SELECT SUM(oi.quantity * mi.menuItemPrice) "
                + "    FROM orderitems oi "
                + "    JOIN menuitems mi ON oi.menuItemId = mi.menuItemId "
                + "    WHERE oi.orderId = o.orderId "
                + "    GROUP BY oi.orderId "
                + ") "
                + "WHERE o.orderId = ?;";

        try (PreparedStatement ps = Connect.getConnection().prepareStatement(query)) {
            ps.setInt(1, orderId);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
	
    // Retrieves all order items for a specific order ID
	public static ArrayList<OrderItem> getAllOrderItemsByOrderId(int orderId) {
	    ArrayList<OrderItem> orderItems = new ArrayList<>();
	    String query = "SELECT orders.orderUserId, orders.orderTotal, "
	    		+ "orders.orderDate, orderitems.orderItemId, orderitems.menuItemId, "
	    		+ "menuitems.menuItemName, orderitems.quantity, users.userName "
	    		+ "FROM orders JOIN orderitems ON orderitems.orderId = orders.orderId "
	    		+ "JOIN menuitems ON menuitems.menuItemId = orderitems.menuItemId "
	    		+ "JOIN orders AS o ON o.orderId = orderitems.orderId "
	    		+ "JOIN users ON users.userId = o.orderUserId "
	    		+ "WHERE orders.orderId = ?;";
	    
	    try (PreparedStatement ps = Connect.getConnection().prepareStatement(query)) {
	        ps.setInt(1, orderId);
	        
	        ResultSet rs = ps.executeQuery();
	        
	        while (rs.next()) {
	            int orderItemId = rs.getInt("orderItemId");
	            int menuItemId = rs.getInt("menuItemId");
	            int quantity = rs.getInt("quantity");
	            String menuItemName = rs.getString("menuItemName");
	            
	            orderItems.add(new OrderItem(orderItemId, orderId, menuItemId, menuItemName, quantity));
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    
	    return orderItems;
	}

}


	

