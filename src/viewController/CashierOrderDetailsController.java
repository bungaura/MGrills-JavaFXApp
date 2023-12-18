package viewController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import controller.OrderController;
import controller.OrderItemController;
import controller.ReceiptController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;
import model.OrderItem;

public class CashierOrderDetailsController{

	private int orderId;
    private static ObservableList<OrderItem> orderitems = FXCollections.observableArrayList();
    private TableView<OrderItem> table;
    
    public CashierOrderDetailsController(int orderId, TableView<OrderItem> table) {
        this.orderId = orderId;
        this.table = table;
        orderitems = FXCollections.observableArrayList();
    }

	public void initialize() {
    	loadOrderItems(orderId);
    }
	
	public String validatePaymentInput(String typeInput, Double amountInput) {
		String currentStatus = OrderController.getOrderByOrderId(orderId).getOrderStatus();
		if (currentStatus.equals("Pending")) {
    		Double orderTotal = OrderController.getOrderTotalByOrderId(orderId);
    		String validationMessage = ReceiptController.validatePaymentInput(typeInput, amountInput, orderTotal);
    		return validationMessage;
        } else if (currentStatus.equals("Paid")) {
    		return "This order has already been paid!";
    	}
		return null;
	}
    
	public String processOrder(String typeInput, Double amountInput) { 
    	OrderController.updateOrder(orderId, "Paid");
    	LocalDateTime currentDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDateTime = currentDateTime.format(formatter);
        
    	ReceiptController.createReceipt(orderId, typeInput, amountInput, formattedDateTime);
    	loadOrderItems(orderId);
    	
    	return "Order with ID: " + orderId + " has been paid!";
  	}
	
	public String removeOrder() {
		if (OrderController.getOrderByOrderId(orderId).getOrderStatus().equals("Pending")) {
			OrderController.deleteOrder(orderId);
        	loadOrderItems(orderId);
    		return "Order with ID: " + orderId + " has been removed!";
    	} else {
    		return "Cannot remove order! This order has been paid!";
    	}
	}
	
	private void loadOrderItems(int orderId) { 
    	orderitems.clear();
    	orderitems.addAll(OrderItemController.getAllOrderItemsByOrderId(orderId));
        table.setItems(orderitems);
        table.refresh();
    }
	
}
