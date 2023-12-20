package viewController;

import controller.OrderController;
import controller.OrderItemController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;
import model.OrderItem;
import viewChef.ChefOrderMenuView;
import viewChef.ChefOrderUpdate;

public class ChefOrderDetailsController {
    private int orderId;
    private static ObservableList<OrderItem> orderitems;
    private TableView<OrderItem> table;

    public ChefOrderDetailsController(int orderId, TableView<OrderItem> table) {
        this.orderId = orderId;
        this.table = table;
        orderitems = FXCollections.observableArrayList();
        initialize();
    }

    public void initialize() {
    	setupTableSelectionListener();
        loadOrderItems(orderId);
    }
    
    // Set up table selection listener and pass the order data to another window
    private void setupTableSelectionListener() {
		table.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
        		new ChefOrderUpdate().initialize(
        				newSelection.getOrderId(),
                		newSelection.getOrderItemId(),
                		newSelection.getMenuItemId(), 
                		newSelection.getMenuItemName(),
                		newSelection.getQuantity()); 
            }
        );
	}

    private void loadOrderItems(int orderId) {
        orderitems.clear();
        orderitems.addAll(OrderItemController.getAllOrderItemsByOrderId(orderId));
        table.setItems(orderitems);
        table.refresh();
    }

    public String prepareOrder() {
    	String currentStatus = OrderController.getOrderByOrderId(orderId).getOrderStatus();
        if (currentStatus.equals("Paid")) {
            OrderController.updateOrder(orderId, "Prepared");
            loadOrderItems(orderId);
            return "Prepared the order";
        } else if (currentStatus.equals("Pending")) {
            return "Wait for the order to be paid!";
        } 
        return "The order is already prepared!";
    }
<<<<<<< Updated upstream
=======

    // Update customer order by adding new order item
    public String addNewOrderItem() {
		Order thisOrder = OrderController.getOrderByOrderId(orderId);
        String orderStatus = thisOrder.getOrderStatus();
		if (orderStatus.equals("Pending")) {
        	new ChefOrderMenuView().initialize(orderId);
        	return "";
		} else {
			return "Cannot add order! This order has been paid!";
		}
	}
>>>>>>> Stashed changes
}
