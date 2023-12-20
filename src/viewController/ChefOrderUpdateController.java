package viewController;

import controller.OrderController;
import controller.OrderItemController;
import model.Order;
import viewChef.ChefOrderDetails;

//ChefOrderUpdateController class handling the logic for updating selected orders
//Manages saving changes to order items, including updating quantities and deleting items
public class ChefOrderUpdateController {

	// Saves changes to the order items based on the provided quantity, order item ID, and menu item ID
	public String saveChanges(int orderId, int qtyLastInput, int orderItemId, int menuItemId) {
		Order thisOrder = OrderController.getOrderByOrderId(orderId);
	    String orderStatus = thisOrder.getOrderStatus();
	        
		if(qtyLastInput == 0) {
			OrderItemController.deleteOrderItemByQuantity(orderItemId);
			return "Removed Order Item with ID: " + orderItemId;
	    } else if (orderStatus.equals("Pending")) {
	        OrderItemController.updateOrderItem(orderItemId, menuItemId, qtyLastInput);
	        OrderController.updateOrderTotal(orderId);            
	        new ChefOrderDetails().initialize(orderId);
	        return "";
	    } else {
	        return "Cannot update order! This order has been paid!";
	    }
	}
	// Remove pending orders 
		
	public String removeOrder(int orderId, int orderItemId) {
		Order thisOrder = OrderController.getOrderByOrderId(orderId);
		String orderStatus = thisOrder.getOrderStatus();
	        
		if (orderStatus.equals("Pending")) {
			OrderItemController.deleteOrderItem(orderItemId);
			return "Removed Order Item with ID: " + orderItemId;
		} else {
			return "The order has been paid! Cannot be removed!";
		}
	}
}
