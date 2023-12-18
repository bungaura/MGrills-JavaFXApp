package viewController;

import controller.OrderController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;
import model.Order;
import viewWaiter.WaiterOrderDetails;

public class WaiterOrderViewController {
	private static ObservableList<Order> orders;
    private static TableView<Order> table;
    
    public WaiterOrderViewController(TableView<Order> table) {
    	WaiterOrderViewController.table = table;
    	orders = FXCollections.observableArrayList();
    }

	public void initialize() {
        loadOrders();
        setupTableSelectionListener();
	}
	
	public static void loadOrders() {
        orders.clear();
        orders.addAll(OrderController.getAllOrderList());
        table.setItems(orders);
        table.refresh();            
    }
	
	private void setupTableSelectionListener() {
        table.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                new WaiterOrderDetails().initialize(newSelection.getOrderId());
            }
        });
    }

}
