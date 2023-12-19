package controller;

import java.time.LocalDate;
import java.util.ArrayList;

import model.Order;

public class OrderController {
	
    // Creates a new order based on the provided user ID and current date
	public static int createOrder(int orderUserId) {
	    Order lastOrder = Order.getLastOrder(orderUserId);

	    if (lastOrder == null) {
	        String currentDate = LocalDate.now().toString();
	        return Order.createOrder(orderUserId, currentDate);
	    } else {
	        String status = Order.isOrderProcessed(lastOrder.getOrderId());
	        if (!status.equals("Pending")) {
	            String currentDate = LocalDate.now().toString();
	            return Order.createOrder(orderUserId, currentDate);
	        } else {
	            return lastOrder.getOrderId();
	        }
	    }
	}

    // Updates the status of an order with the specified ID
	public static void updateOrder(int orderId, String orderStatus) {
	    Order.updateOrder(orderId, orderStatus);
	}

    // Deletes an order with the specified ID
	public static void deleteOrder(int orderId) {
		Order.deleteOrder(orderId);
	}
	
    // Updates the total amount of an order with the specified ID
	public static void updateOrderTotal(int orderId) {
		Order.updateOrderTotal(orderId);
	}
	
    // Retrieves the total amount of an order with the specified ID
	public static Double getOrderTotalByOrderId(int orderId) {
		return Order.getOrderTotalByOrderId(orderId);
	}
	
    // Retrieves the next available order ID
	public static int getNextAvailableOrderId() {
		return Order.getNextAvailableOrderId();
	}

    // Retrieves a list of orders associated with a specific customer ID
	public static ArrayList<Order> getOrdersByCustomerId(int customerId) {
		return Order.getAllOrdersByCustomerId(customerId);
	}
	
    // Retrieves a list of all orders
	public static ArrayList<Order> getAllOrderList() {
		return Order.getAllOrderList();
	}
	
	// Retrieves a list of all allowed orders to be viewed by chef
	public static ArrayList<Order> getAllChefOrderList() {
		return Order.getAllChefOrderList();
	}
	
	// Retrieves a list of all paid orders
	public static ArrayList<Order> getAllPaidOrderList() {
		return Order.getAllPaidOrderList();
	}
		
	// Retrieves a list of all prepared orders
	public static ArrayList<Order> getAllPreparedOrderList() {
		return Order.getAllPreparedOrderList();
	}
	
    // Retrieves an order with the specified order ID
	public static Order getOrderByOrderId(int orderId) {
		return Order.getOrderByOrderId(orderId);
	}

    // Validates the quantity provided as a string
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
	        return "Please enter a valid numeric quantity!";
	    }
	    return null;
	}
	
    // Retrieves information about the last order for a specific customer
	public static Order getLastOrderInfo(int customerId) {
		return Order.getLastOrder(customerId);
	}

    // Retrieves the current order
	public static Order getOrder() {
		return Order.getOrder();
	}
}
