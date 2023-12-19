package viewController;

import controller.OrderController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;
import model.Order;
import viewChef.ChefOrderDetails;

//A view controller class for managing the business logic to the UI
//ChefOrderViewController handles the chef order data and table view in the chef dashboard
public class ChefOrderViewController {
	private static ObservableList<Order> orders;
    private static TableView<Order> table;
    
    // Constructor initializing the controller with the provided TableView
    public ChefOrderViewController(TableView<Order> table) {
    	ChefOrderViewController.table = table;
    	orders = FXCollections.observableArrayList();
    }

    // Initializes the controller and loads orders into the TableView
	public void initialize() {
		loadOrders();		
		setupTableSelectionListener();
	}

    // Sets up a listener for TableView selection to display detailed order information
	private void setupTableSelectionListener() {
        table.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                new ChefOrderDetails().initialize(newSelection.getOrderId());
            }
        });
    }
	
    // Loads orders into the table from the controller and refreshes the TableView
	public static void loadOrders() {
        orders.clear();
        orders.addAll(OrderController.getAllChefOrderList());
        table.setItems(orders);
        table.refresh();            
    }
}
