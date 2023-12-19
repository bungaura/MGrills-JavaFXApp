package viewController;

import controller.OrderController;
import controller.OrderItemController;
import model.User;

// A controller class handling the business logic for menu-related operations in the customer menu view
// CustomerMenuDetailsController manages the addition of menu items to the customer's order
public class CustomerMenuDetailsController {
	private User currentUser;
	
    // Constructor initializing the controller with the current user
	public CustomerMenuDetailsController(User currentUser) {
		this.currentUser = currentUser;
	}

    // Adds the selected menu item to the customer's order and returns a message with the order ID
	public String addOrder(int menuItemId, int quantity) {
		 int orderId = OrderController.createOrder(currentUser.getUserId());
         OrderItemController.createOrderItem(orderId, menuItemId, quantity);
         return "Added to Order ID: " + orderId + "!";
	}

}
