package viewController;

import controller.OrderController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;
import model.Order;
import viewCashier.CashierOrderDetails;

//A view controller class managing the business logic in the cashier order view
//CashierOrderViewController handles the loading of orders, setting up selection listeners, and navigating to order details
public class CashierOrderViewController {
	private static ObservableList<Order> orders;
    private TableView<Order> table;
    
    // Constructor initializing the controller with the provided TableView
    public CashierOrderViewController(TableView<Order> table) {
        this.table = table;
        orders = FXCollections.observableArrayList();
    }

    // Initializes the controller and loads orders while setting up table selection listeners
    public void initialize() {
    	loadOrders();
    	setupTableSelectionListener();
    }
    
    // Sets up selection listeners for the TableView to navigate to order details based on the selected order
	private void setupTableSelectionListener() {
        table.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
        	if (newSelection != null) {
                new CashierOrderDetails().initialize(newSelection.getOrderId());
        	}
        });
    }
	
    // Loads orders into the TableView
	private void loadOrders() {
        orders.clear();
        orders.addAll(OrderController.getAllOrderList());
        table.setItems(orders);
        table.refresh();            
    }
	
}
