package viewController;

import controller.OrderController;
import controller.OrderItemController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;
import model.Order;
import model.OrderItem;
import model.User;
import viewCustomer.CustomerOrderUpdate;

// CustomerOrderDetailsController class handling the logic for customer order details
// Manages loading, updating, and handling interactions for order items in the customer order details view
public class CustomerOrderDetailsController {
	private static ObservableList<OrderItem> orderItems;
    private static TableView<OrderItem> table;
	private User currentUser;
	private Order currentOrder;
	public int orderId;
	
    // Constructor initializing the controller with the current user, order, order ID, and TableView
	public CustomerOrderDetailsController(User currentUser, Order currentOrder, int orderId, TableView<OrderItem> table) {
		this.currentOrder = currentOrder;
		this.currentUser = currentUser;
		this.orderId = orderId;
		CustomerOrderDetailsController.table = table;
		orderItems = FXCollections.observableArrayList();
	}

    // Initializes the controller by setting up table selection listener and loading order items
	public void initialize() {
		setupTableSelectionListener();
        loadOrders(orderId);
        checkOrderItemsInOrder(orderId);
        OrderController.updateOrderTotal(orderId);
	}
	
    // Sets up a listener for the table selection to open the order item update view
	private void setupTableSelectionListener() {
		table.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
        		new CustomerOrderUpdate(currentUser, currentOrder).initialize(
                		newSelection.getOrderItemId(),
                		newSelection.getMenuItemId(), 
                		newSelection.getMenuItemName(),
                		newSelection.getQuantity()); 
            }
        );
	}
	
    // Checks if there are order items in the order and deletes the order if empty
	void checkOrderItemsInOrder(int orderId) {
    	if(OrderItemController.getAllOrderItemsByOrderId(orderId).isEmpty()) {
    		OrderController.deleteOrder(orderId);
    	}
    }

    // Loads order items for the specified orderId into the TableView
    static void loadOrders(int orderId) {
        orderItems.clear();
        orderItems.addAll(OrderItemController.getAllOrderItemsByOrderId(orderId));
        table.setItems(orderItems);
        table.refresh();
    }

    // Adds order items to the TableView and updates the order total
    public void addOrder() {
    	loadOrders(orderId);
    	OrderController.updateOrderTotal(orderId);
    }

}