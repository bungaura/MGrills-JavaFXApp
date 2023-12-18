package viewController;

import controller.OrderController;
import controller.OrderItemController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;
import model.OrderItem;

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
        loadOrderItems(orderId);
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
}
