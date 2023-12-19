package controller;

import java.util.ArrayList;

import model.OrderItem;

public class OrderItemController {
	
	// Creates a new order item with the provided order ID, menu item ID, and quantity
	public static void createOrderItem(int orderId, int menuItemId, int quantity) {
		OrderItem.createOrderItem(orderId, menuItemId, quantity);
	}
	
    // Updates an existing order item with the specified order item ID, menu item ID, and quantity
	public static void updateOrderItem(int orderItemId, int menuItemId, int quantity) {
		OrderItem.updateOrderItem(orderItemId, menuItemId, quantity);
	}
	
    // Deletes an order item with the specified order item ID
	public static void deleteOrderItem(int orderItemId) {
		OrderItem.deleteOrderItem(orderItemId);
	}
	
    // Retrieves a list of all order items associated with a specific order ID
	public static ArrayList<OrderItem> getAllOrderItemsByOrderId(int orderId) {
		return OrderItem.getAllOrderItemsByOrderId(orderId);
	}
	
    // Validates quantity provided as a string
	public static String validateQuantity(String qty) {
	    if (qty.isEmpty()) {
	        return "Quantity must be filled";
	    }
	    int quantity;
	    try {
	        quantity = Integer.parseInt(qty);

	        if (quantity <= 0) {
	            return "Quantity must be greater than 0";
	        }
	    } catch (NumberFormatException e) {
	        return "Quantity must be a valid number";
	    }
	    return null;
	}
    
    // Deletes an order item with the specified order item ID based on quantity
	public static void deleteOrderItemByQuantity(int orderItemId) {
		OrderItem.deleteOrderItem(orderItemId);
		
	}
}
