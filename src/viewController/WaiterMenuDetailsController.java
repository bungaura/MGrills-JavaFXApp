package viewController;

import controller.OrderItemController;

// A controller class handling the business logic for menu-related operations in the customer menu view
// CustomerMenuDetailsController manages the addition of menu items to the customer's order
public class WaiterMenuDetailsController {
	private int orderId;
	
    // Constructor initializing the controller with the current user
	public WaiterMenuDetailsController(int orderId) {
		this.orderId = orderId;
	}

    // Adds the selected menu item to the customer's order and returns a message with the order ID
	public String addOrder(int menuItemId, int quantity) {
         OrderItemController.createOrderItem(orderId, menuItemId, quantity);
         return "Added to Order ID: " + orderId + "!";
	}

}
