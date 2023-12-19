package viewController;

import controller.OrderController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;
import model.Order;
import viewWaiter.WaiterOrderDetails;

// A view controller class for managing the business logic to the UI
// WaiterOrderViewController handles the waiter order data and table view in the waiter dashboard
public class WaiterOrderViewController {
	private static ObservableList<Order> orders;
    private static TableView<Order> table;
    
    // Constructor to set the table for this controller
    public WaiterOrderViewController(TableView<Order> table) {
    	WaiterOrderViewController.table = table;
    	orders = FXCollections.observableArrayList();
    }

    // Initialize the controller
	public void initialize() {
        loadOrders();
        setupTableSelectionListener();
	}
	
    // Load orders into the table
	public static void loadOrders() {
        orders.clear();
        orders.addAll(OrderController.getAllPreparedOrderList());
        table.setItems(orders);
        table.refresh();            
    }
	
    // Setup table selection listener to open order details view when an order is selected
	private void setupTableSelectionListener() {
        table.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                new WaiterOrderDetails().initialize(newSelection.getOrderId());
            }
        });
    }

}
