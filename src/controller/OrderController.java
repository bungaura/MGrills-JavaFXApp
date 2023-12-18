package controller;

import java.time.LocalDate;
import java.util.ArrayList;

import model.Order;

public class OrderController {
	
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

	
	public static void updateOrder(int orderId, String orderStatus) {
	    Order.updateOrder(orderId, orderStatus);
	}

	public static void deleteOrder(int orderId) {
		Order.deleteOrder(orderId);
	}
	
	public static void updateOrderTotal(int orderId) {
		Order.updateOrderTotal(orderId);
	}
	
	public static Double getOrderTotalByOrderId(int orderId) {
		return Order.getOrderTotalByOrderId(orderId);
	}
	
	public static int getNextAvailableOrderId() {
		return Order.getNextAvailableOrderId();
	}

	public static ArrayList<Order> getOrdersByCustomerId(int customerId) {
		return Order.getAllOrdersByCustomerId(customerId);
	}
	
	public static ArrayList<Order> getAllOrderList() {
		return Order.getAllOrderList();
	}
	
	public static Order getOrderByOrderId(int orderId) {
		return Order.getOrderByOrderId(orderId);
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
	
	public static Order getLastOrderInfo(int customerId) {
		return Order.getLastOrder(customerId);
	}

	public static Order getOrder() {
		return Order.getOrder();
	}
}
