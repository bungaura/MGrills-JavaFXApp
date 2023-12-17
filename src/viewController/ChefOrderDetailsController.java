package viewController;

import controller.OrderController;
import controller.OrderItemController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;
import model.OrderItem;

// A controller class managing the business logic in the chef order details view
// ChefOrderDetailsController handles the loading of order items, order preparation, and related user interactions
public class ChefOrderDetailsController {
    private int orderId;
    private static ObservableList<OrderItem> orderitems;
    private TableView<OrderItem> table;

    // Constructor initializing the controller with the provided orderId and TableView
    public ChefOrderDetailsController(int orderId, TableView<OrderItem> table) {
        this.orderId = orderId;
        this.table = table;
        orderitems = FXCollections.observableArrayList();
        initialize();
    }

    // Initializes the controller and loads order items for the specified orderId
    public void initialize() {
        loadOrderItems(orderId);
    }

    // Loads order items for the specified orderId into the TableView
    private void loadOrderItems(int orderId) {
        orderitems.clear();
        orderitems.addAll(OrderItemController.getAllOrderItemsByOrderId(orderId));
        table.setItems(orderitems);
        table.refresh();
    }

    // Prepares the order based on its current status and updates the TableView
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
}