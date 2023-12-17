package viewController;

import controller.OrderController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;
import model.Order;
import model.User;
import viewCustomer.CustomerOrderDetails;

// CustomerOrderViewController class managing the logic for displaying and handling customer orders
// Handles loading and displaying customer orders in a TableView and enables order details view
public class CustomerOrderViewController {
	private static ObservableList<Order> orders;
    private static TableView<Order> table;
    private User currentUser;
    private Order currentOrder;
    
    // Constructor initializing the controller with the current user, current order, and TableView
    public CustomerOrderViewController(User currentUser, Order currentOrder, TableView<Order> table) {
    	this.currentOrder = currentOrder;
    	this.currentUser = currentUser;
    	CustomerOrderViewController.table = table;
    	orders = FXCollections.observableArrayList();
    }

    // Sets up a selection listener for the TableView to navigate to order details upon selection
	private void setupTableSelectionListener() {
        table.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                int orderId = newSelection.getOrderId();
                new CustomerOrderDetails(currentUser, currentOrder, orderId).initialize();
            }
        });
    }
	
    // Loads and displays customer orders in the TableView
	public void loadOrders() {
        orders.clear();
        orders.addAll(OrderController.getOrdersByCustomerId(currentUser.getUserId()));
        table.setItems(orders);
        table.refresh();
    }

    // Initializes the controller by loading orders and setting up table selection
	public void initialize() {
		loadOrders();
		setupTableSelectionListener();
	}
}