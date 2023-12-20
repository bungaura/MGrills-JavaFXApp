package viewController;

import controller.OrderController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;
import model.Order;
import viewChef.ChefOrderDetails;

<<<<<<< Updated upstream
=======
//A view controller class for managing the business logic to the UI
//ChefOrderViewController handles the chef order data and table view in the chef dashboard
>>>>>>> Stashed changes
public class ChefOrderViewController {
	private static ObservableList<Order> orders;
    private static TableView<Order> table;
    
    public ChefOrderViewController(TableView<Order> table) {
    	ChefOrderViewController.table = table;
    	orders = FXCollections.observableArrayList();
    }

	public void initialize() {
		loadOrders();		
		setupTableSelectionListener();
	}

	private void setupTableSelectionListener() {
        table.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                new ChefOrderDetails().initialize(newSelection.getOrderId());
            }
        });
    }
	
<<<<<<< Updated upstream
=======
    // Loads orders into the table from the controller and refreshes the TableView
>>>>>>> Stashed changes
	public static void loadOrders() {
        orders.clear();
        orders.addAll(OrderController.getAllOrderList());
        table.setItems(orders);
        table.refresh();            
    }
}
