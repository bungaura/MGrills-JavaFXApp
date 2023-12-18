package viewController;

import controller.OrderController;
import controller.OrderItemController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;
import model.OrderItem;

public class WaiterOrderDetailsController {
	private int orderId;
    private static ObservableList<OrderItem> orderitems;
    private TableView<OrderItem> table;

    public WaiterOrderDetailsController(int orderId, TableView<OrderItem> table) {
        this.orderId = orderId;
        this.table = table;
        orderitems = FXCollections.observableArrayList();
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

}
