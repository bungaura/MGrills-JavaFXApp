package viewController;

import controller.OrderController;
import controller.OrderItemController;
import model.Order;

// CustomerOrderUpdateController class handling the logic for updating customer orders
// Manages saving changes to order items, including updating quantities and deleting items
public class CustomerOrderUpdateController {
	private Order currentOrder;
	
    // Constructor initializing the controller with the current order
	public CustomerOrderUpdateController(Order currentOrder) {
		this.currentOrder = currentOrder;
	}

    // Saves changes to the order items based on the provided quantity, order item ID, and menu item ID
	public void saveChanges(int qtyLastInput, int orderItemId, int menuItemId) {
		if(qtyLastInput == 0) {
        	OrderItemController.deleteOrderItemByQuantity(orderItemId);
        } else {
            OrderItemController.updateOrderItem(orderItemId, menuItemId, qtyLastInput);
            OrderController.updateOrderTotal(currentOrder.getOrderId());
        }
	}
}