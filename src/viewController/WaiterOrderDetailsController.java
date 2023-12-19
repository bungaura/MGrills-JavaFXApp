package viewController;

import controller.OrderController;
import controller.OrderItemController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;
import model.Order;
import model.OrderItem;

//A view controller class for managing the business logic to the UI
//WaiterOrderDetailsController handles the details and actions for serving orders in the waiter's order details view
public class WaiterOrderDetailsController {
	private int orderId;
    private static ObservableList<OrderItem> orderitems;
    private TableView<OrderItem> table;

    // Constructor initializing the controller with the provided orderId and TableView
    public WaiterOrderDetailsController(int orderId, TableView<OrderItem> table) {
        this.orderId = orderId;
        this.table = table;
        orderitems = FXCollections.observableArrayList();
    }

    // Initializes the controller and load order items for the specified orderId
    public void initialize() {
        loadOrderItems(orderId);
    }

    // Lload order items for the specified orderId into the TableView
    private void loadOrderItems(int orderId) {
        orderitems.clear();
        orderitems.addAll(OrderItemController.getAllOrderItemsByOrderId(orderId));
        table.setItems(orderitems);
        table.refresh();
    }

    // Serve the order based on its current status and update the TableView.
    public String serveOrder() {
    	String currentStatus = OrderController.getOrderByOrderId(orderId).getOrderStatus();
        if (currentStatus.equals("Prepared")) {
            OrderController.updateOrder(orderId, "Served");
            loadOrderItems(orderId);
            return "Served the order!";
        } else if (currentStatus.equals("Pending")) {
            return "Wait for the order to be paid!";
        } else if (currentStatus.equals("Paid")) {
        	return "Wait for the order to be prepared!";
        }
        return "The order is already served!";
    }

    // Remove pending orders 
	public String removeOrder() {
        OrderItem selectedOrderItem = table.getSelectionModel().getSelectedItem();
        int orderId = selectedOrderItem.getOrderId();
        Order thisOrder = OrderController.getOrderByOrderId(orderId);
        String orderStatus = thisOrder.getOrderStatus();
        int orderItemId = selectedOrderItem.getOrderItemId();
        
        if (selectedOrderItem != null && orderStatus.equals("Pending")) {
            OrderItemController.deleteOrderItem(orderItemId);
            loadOrderItems(orderId);
            return "Removed Order Item with ID: " + orderItemId;
        } else {
        	return "The order has been paid! Cannot be removed!";
        }
    }

}
