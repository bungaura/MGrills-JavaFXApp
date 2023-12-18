package viewController;

import controller.OrderController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;
import model.Order;
import viewCashier.CashierOrderDetails;

public class CashierOrderViewController {
	
	private static ObservableList<Order> orders;
    private TableView<Order> table;
    
    public CashierOrderViewController(TableView<Order> table) {
        this.table = table;
        orders = FXCollections.observableArrayList();
    }

    public void initialize() {
    	loadOrders();
    	setupTableSelectionListener();
    }
    
	private void setupTableSelectionListener() {
        table.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
        	if (newSelection != null) {
                new CashierOrderDetails().initialize(newSelection.getOrderId());
        	}
        });
    }
	
	private void loadOrders() {
        orders.clear();
        orders.addAll(OrderController.getAllOrderList());
        table.setItems(orders);
        table.refresh();            
    }
	
}
