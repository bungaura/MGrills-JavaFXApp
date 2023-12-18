package controller;

import java.util.ArrayList;

import model.OrderItem;

public class OrderItemController {
	
	public static void createOrderItem(int orderId, int menuItemId, int quantity) {
		OrderItem.createOrderItem(orderId, menuItemId, quantity);
	}
	
	public static void updateOrderItem(int orderItemId, int menuItemId, int quantity) {
		OrderItem.updateOrderItem(orderItemId, menuItemId, quantity);
	}
	
	public void deleteOrderItem(int orderId) {
		OrderItem.deleteOrderItem(orderId);
	}
	
	public static ArrayList<OrderItem> getAllOrderItemsByOrderId(int orderId) {
		return OrderItem.getAllOrderItemsByOrderId(orderId);
	}
	
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
	
	public static void deleteOrderItemByQuantity(int orderItemId) {
		OrderItem.deleteOrderItem(orderItemId);
		
	}
}
